package com.spring.jwt.jwt.impl;

import com.spring.jwt.exception.BaseException;
import com.spring.jwt.jwt.JwtConfig;
import com.spring.jwt.jwt.JwtService;
import com.spring.jwt.jwt.TokenBlacklistService;
import com.spring.jwt.jwt.ActiveSessionService;
import com.spring.jwt.repository.UserRepository;
import com.spring.jwt.service.security.UserDetailsCustom;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JwtServiceImpl implements JwtService {
    private static final String CLAIM_KEY_DEVICE_FINGERPRINT = "dfp";
    private static final String CLAIM_KEY_TOKEN_TYPE = "token_type";
    private static final String TOKEN_TYPE_ACCESS = "access";
    private static final String TOKEN_TYPE_REFRESH = "refresh";

    private final UserRepository userRepository;
    private final JwtConfig jwtConfig;
    private final UserDetailsService userDetailsService;
    private final TokenBlacklistService tokenBlacklistService;
    private final ActiveSessionService activeSessionService;

    @Autowired
    public JwtServiceImpl(@Lazy UserDetailsService userDetailsService, 
                          UserRepository userRepository, 
                          @Lazy JwtConfig jwtConfig,
                           TokenBlacklistService tokenBlacklistService,
                           ActiveSessionService activeSessionService) {
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.jwtConfig = jwtConfig;
        this.tokenBlacklistService = tokenBlacklistService;
        this.activeSessionService = activeSessionService;
    }

    @Override
    public Claims extractClaims(String token) {
        return Jwts
                .parserBuilder()
                .setAllowedClockSkewSeconds(jwtConfig.getAllowedClockSkewSeconds())
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    @Override
    public Key  getKey() {
        byte[] key = Decoders.BASE64.decode(jwtConfig.getSecret());
        return Keys.hmacShaKeyFor(key);
    }

    @Override
    public String generateToken(UserDetailsCustom userDetailsCustom) {
        return generateToken(userDetailsCustom, null);
    }

    @Override
    public String generateToken(UserDetailsCustom userDetailsCustom, String deviceFingerprint) {
        Instant now = Instant.now();
        Instant notBefore = now.plusSeconds(Math.max(0, jwtConfig.getNotBefore()));

        List<String> roles = userDetailsCustom.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        log.info("Roles: {}", roles);

        Integer userId = userDetailsCustom.getUserId();
        String firstName = userDetailsCustom.getFirstName();
        
        log.debug("Generating access token for user: {}, device: {}", 
                userDetailsCustom.getUsername(), 
                deviceFingerprint != null ? deviceFingerprint.substring(0, 8) + "..." : "none");

            JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(userDetailsCustom.getUsername())
                .setIssuer(jwtConfig.getIssuer())
                .setAudience(jwtConfig.getAudience())
                .setId(UUID.randomUUID().toString())
                .claim("firstname", firstName)
                .claim("userId", userId)
                .claim("authorities", roles)
                .claim("isEnable", userDetailsCustom.isEnabled());

        if (userDetailsCustom.getUserProfileId() != null) {
            jwtBuilder.claim("userProfileId", userDetailsCustom.getUserProfileId());
        }
        

        
        jwtBuilder.claim(CLAIM_KEY_TOKEN_TYPE, TOKEN_TYPE_ACCESS)
                .setIssuedAt(Date.from(now))
                .setNotBefore(Date.from(notBefore))
                .setExpiration(Date.from(now.plusSeconds(jwtConfig.getExpiration())))
                .signWith(getKey(), SignatureAlgorithm.HS256);

        if (jwtConfig.isDeviceFingerprintingEnabled() && StringUtils.hasText(deviceFingerprint)) {
            jwtBuilder.claim(CLAIM_KEY_DEVICE_FINGERPRINT, deviceFingerprint);
        }

        return jwtBuilder.compact();
    }
    
    @Override
    public String generateRefreshToken(UserDetailsCustom userDetailsCustom, String deviceFingerprint) {
        Instant now = Instant.now();
        Instant notBefore = now.plusSeconds(Math.max(0, jwtConfig.getNotBefore()));
        
        log.debug("Generating refresh token for user: {}, device: {}", 
                userDetailsCustom.getUsername(), 
                deviceFingerprint != null ? deviceFingerprint.substring(0, 8) + "..." : "none");

        List<String> roles = userDetailsCustom.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

            JwtBuilder jwtBuilder = Jwts.builder()
                .setSubject(userDetailsCustom.getUsername())
                .setIssuer(jwtConfig.getIssuer())
                .setId(UUID.randomUUID().toString())
                .claim("userId", userDetailsCustom.getUserId())
                .claim("authorities", roles);

        if (userDetailsCustom.getUserProfileId() != null) {
            jwtBuilder.claim("userProfileId", userDetailsCustom.getUserProfileId());
        }
        

        
        jwtBuilder.claim(CLAIM_KEY_TOKEN_TYPE, TOKEN_TYPE_REFRESH)
                .setIssuedAt(Date.from(now))
                .setNotBefore(Date.from(notBefore))
                .setExpiration(Date.from(now.plusSeconds(jwtConfig.getRefreshExpiration())))
                .signWith(getKey(), SignatureAlgorithm.HS256);

        if (jwtConfig.isDeviceFingerprintingEnabled() && StringUtils.hasText(deviceFingerprint)) {
            jwtBuilder.claim(CLAIM_KEY_DEVICE_FINGERPRINT, deviceFingerprint);
        }

        return jwtBuilder.compact();
    }
    
    @Override
    public String extractDeviceFingerprint(String token) {
        try {
            Claims claims = extractClaims(token);
            return claims.get(CLAIM_KEY_DEVICE_FINGERPRINT, String.class);
        } catch (Exception e) {
            log.warn("Error extracting device fingerprint from token", e);
            return null;
        }
    }
    
    @Override
    public boolean isRefreshToken(String token) {
        try {
            Claims claims = extractAllClaims(token);
            String tokenType = claims.get(CLAIM_KEY_TOKEN_TYPE, String.class);
            log.debug("Token type: {}", tokenType);
            return TOKEN_TYPE_REFRESH.equals(tokenType);
        } catch (Exception e) {
            log.warn("Error checking if token is refresh token: {}", e.getMessage());
            return false;
        }
    }
    
    @Override
    public String generateDeviceFingerprint(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        
        try {
            String ip = request.getHeader("X-Forwarded-For");
            if (ip != null && ip.contains(",")) {
                ip = ip.split(",")[0].trim();
            }
            if (ip == null || ip.isBlank()) {
                ip = request.getRemoteAddr();
            }
            String ua = request.getHeader("User-Agent");
            String lang = request.getHeader("Accept-Language");
            String enc = request.getHeader("Accept-Encoding");

            StringBuilder deviceInfo = new StringBuilder();
            deviceInfo.append(ua).append("|");
            deviceInfo.append(ip).append("|");
            deviceInfo.append(lang).append("|");
            deviceInfo.append(enc);

            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(deviceInfo.toString().getBytes(StandardCharsets.UTF_8));

            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            log.error("Error generating device fingerprint", e);
            return null;
        }
    }
    
    @Override
    public Map<String, Object> extractAllCustomClaims(String token) {
        Claims claims = extractClaims(token);

        Map<String, Object> customClaims = new HashMap<>(claims);
        customClaims.remove("sub");
        customClaims.remove("iat");
        customClaims.remove("exp");
        customClaims.remove("jti");
        customClaims.remove("iss");
        customClaims.remove("aud");
        customClaims.remove("nbf");
        
        return customClaims;
    }

    @Override
    public boolean isValidToken(String token) {
        return isValidToken(token, null);
    }
    
    @Override
    public boolean isValidToken(String token, String deviceFingerprint) {
        try {
            if (isBlacklisted(token)) {
                log.warn("Token is blacklisted");
                return false;
            }
            
            final String username = extractUsername(token);
            
            if (StringUtils.isEmpty(username)) {
                log.debug("Token validation failed: empty username");
                return false;
            }
    
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            if (ObjectUtils.isEmpty(userDetails)) {
                log.debug("Token validation failed: user not found");
                return false;
            }

            Claims claims = extractAllClaims(token);

            Date nbf = claims.getNotBefore();
            if (nbf != null && nbf.after(new Date())) {
                log.debug("Token not yet valid. Current time: {}, Not before: {}", 
                        new Date(), nbf);
                return false;
            }
            if (jwtConfig.isDeviceFingerprintingEnabled()) {
                String tokenDeviceFingerprint = claims.get(CLAIM_KEY_DEVICE_FINGERPRINT, String.class);
                // If request supplied a fingerprint, enforce it matches the token's fingerprint
                if (StringUtils.hasText(deviceFingerprint) && StringUtils.hasText(tokenDeviceFingerprint)
                        && !tokenDeviceFingerprint.equals(deviceFingerprint)) {
                    log.warn("Device fingerprint mismatch: token={}, request={}",
                            tokenDeviceFingerprint.substring(0, 8) + "...",
                            deviceFingerprint.substring(0, 8) + "...");
                    return false;
                }

                // Enforce single active session: token fingerprint must match the one stored for the user
                try {
                    var user = userRepository.findByEmail(username);
                    if (user != null && StringUtils.hasText(user.getDeviceFingerprint())
                            && StringUtils.hasText(tokenDeviceFingerprint)
                            && !user.getDeviceFingerprint().equals(tokenDeviceFingerprint)) {
                        log.warn("Token fingerprint is no longer current for user: {}", username);
                        return false;
                    }
                } catch (Exception e) {
                    log.warn("Could not verify user's current device fingerprint: {}", e.getMessage());
                }
            }
            
            // Enforce single active session: token must be the current active token for this user
            try {
                String tokenId = claims.getId();
                if (StringUtils.hasText(tokenId) && !activeSessionService.isCurrentAccessToken(username, tokenId)) {
                    log.warn("Access token is not current for user: {}", username);
                    return false;
                }
            } catch (Exception e) {
                log.warn("Could not verify active session: {}", e.getMessage());
            }

            log.debug("Token validation successful for user: {}", username);
            return true;
        } catch (Exception e) {
            log.debug("Token validation failed with exception: {}", e.getMessage());
            return false;
        }
    }

    private String extractUsername(String token){
        return extractClaims(token, Claims::getSubject);
    }

    private <T> T extractClaims(String token, Function<Claims, T> claimsTFunction){
        final Claims claims = extractAllClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractAllClaims(String token){
        Claims claims;

        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }catch (ExpiredJwtException e){
            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "Token expiration");
        }catch (UnsupportedJwtException e){
            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "Token's not supported");
        }catch (MalformedJwtException e){
            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "Invalid format 3 part of token");
        }catch (SignatureException e){
            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), "Invalid format token");
        }catch (Exception e){
            throw new BaseException(String.valueOf(HttpStatus.UNAUTHORIZED.value()), e.getLocalizedMessage());
        }

        return claims;
    }

    @Override
    public void blacklistToken(String token) {
        try {
            Claims claims = extractClaims(token);
            String tokenId = claims.getId();
            Date expiration = claims.getExpiration();
            
            if (tokenId != null && expiration != null) {
                tokenBlacklistService.blacklistToken(tokenId, expiration.toInstant());
                log.debug("Token blacklisted: {}", tokenId);
            }
        } catch (Exception e) {
            log.error("Error blacklisting token: {}", e.getMessage());
        }
    }
    
    @Override
    public String extractTokenId(String token) {
        try {
            Claims claims = extractClaims(token);
            return claims.getId();
        } catch (Exception e) {
            log.error("Error extracting token ID: {}", e.getMessage());
            return null;
        }
    }

    @Override
    public boolean isBlacklisted(String token) {
        try {
            String tokenId = extractTokenId(token);
            return tokenId != null && tokenBlacklistService.isBlacklisted(tokenId);
        } catch (Exception e) {
            log.error("Error checking blacklist: {}", e.getMessage());
            return false;
        }
    }
}


