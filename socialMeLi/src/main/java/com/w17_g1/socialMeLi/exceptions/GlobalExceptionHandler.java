package com.w17_g1.socialMeLi.exceptions;

import com.w17_g1.socialMeLi.dto.output.ExceptionDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ElementNotFoundException.class)
    public ResponseEntity<?> elementNotFound(ElementNotFoundException e){
        return ResponseEntity.status(404).body(new ExceptionDTO(e.getMessage()));
    }

    @ExceptionHandler(DuplicateElementException.class)
    public ResponseEntity<?> duplicateElementException(DuplicateElementException e){
        return ResponseEntity.status(409).body(new ExceptionDTO(e.getMessage()));
    }

    @ExceptionHandler(UserCantFollowItselfException.class)
    public ResponseEntity<?> userCantFollowItSelf(UserCantFollowItselfException e){
        return ResponseEntity.status(409).body(new ExceptionDTO(e.getMessage()));
    }

    @ExceptionHandler(UserAlreadyFollowedException.class)
    public ResponseEntity<?> userAlreadyFollowed(UserAlreadyFollowedException e){
        return ResponseEntity.status(409).body(new ExceptionDTO(e.getMessage()));
    }

    @ExceptionHandler(UserIsNotFollowedException.class)
    public ResponseEntity<?> userIsNotFollowed(UserIsNotFollowedException e){
        return ResponseEntity.status(409).body(new ExceptionDTO(e.getMessage()));
    }

    @ExceptionHandler(UserCantUnfollowItselfException.class)
    public ResponseEntity<?> userCantUnfollowItSelfException(UserCantUnfollowItselfException e){
        return ResponseEntity.status(409).body(new ExceptionDTO(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ExceptionDTO> handleValidationExceptions(MethodArgumentNotValidException e) {
        ExceptionDTO error = new ExceptionDTO(e.getBindingResult().getFieldError().getDefaultMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ExceptionDTO> handleValidationExceptions(HttpMessageNotReadableException e) {
        ExceptionDTO error = new ExceptionDTO(e.getMessage());
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }
}
