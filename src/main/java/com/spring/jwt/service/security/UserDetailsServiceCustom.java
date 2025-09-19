package com.spring.jwt.service.security;


import com.spring.jwt.entity.User;
import com.spring.jwt.entity.UserProfile;
import com.spring.jwt.exception.BaseException;
import com.spring.jwt.repository.UserProfileRepository;
import com.spring.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceCustom implements UserDetailsService {

    private final UserRepository userRepository;
    
    @Autowired
    private UserProfileRepository userProfileRepository;
    

    

    
    public UserDetailsServiceCustom(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDetailsCustom userDetailsCustom = getUserDetails(username);

        if(ObjectUtils.isEmpty(userDetailsCustom)){
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Invalid username or password!");
        }

        return userDetailsCustom;
    }

    private UserDetailsCustom getUserDetails(String username) {
        User user = userRepository.findByEmail(username);
        if (ObjectUtils.isEmpty(user)) {
            throw new BaseException(String.valueOf(HttpStatus.BAD_REQUEST.value()), "Invalid username or password!");
        }

        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        String firstName = null;
        Integer userId = null;
        Integer userProfileId = null;


        firstName = user.getFirstName();
        userId = user.getId();

        if (authorities.contains(new SimpleGrantedAuthority("USER"))) {
            UserProfile student = userProfileRepository.findByUserId(userId);
            if (student != null) {
                userProfileId = student.getUserProfileId();
            }
        }


        return new UserDetailsCustom(
                user.getEmail(),
                user.getPassword(),
                firstName,
                userId,
                userProfileId,
                authorities
        );
    }
}