package com.w17_g1.socialMeLi.exceptions;

import com.w17_g1.socialMeLi.dto.output.ExceptionDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<?> elementNotFound(ElementNotFoundException e){
        return ResponseEntity.status(404).body(new ExceptionDTO(e.getMessage()));
    }


}
