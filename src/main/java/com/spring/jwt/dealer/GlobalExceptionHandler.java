package com.spring.jwt.dealer;

import com.spring.jwt.dealer.DTO.DealerResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DealerNotFoundException.class)
    public ResponseEntity<DealerResponseDto> handleDealerNotFound(DealerNotFoundException ex) {
        DealerResponseDto response = DealerResponseDto.error(
                "Dealer Not Found",
                ex.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidDealerDataException.class)
    public ResponseEntity<DealerResponseDto> handleInvalidDealerData(InvalidDealerDataException ex) {
        DealerResponseDto response = DealerResponseDto.error(
                "Invalid Dealer Data",
                ex.getMessage()
        );
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
