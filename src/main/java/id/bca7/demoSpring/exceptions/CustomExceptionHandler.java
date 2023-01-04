package id.bca7.demoSpring.exceptions;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import id.bca7.demoSpring.exceptions.custom.CustomNotFoundExceptions;
import id.bca7.demoSpring.models.dto.response.ResponseError;

@ControllerAdvice
public class CustomExceptionHandler {
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        Map<String, String> errors = new HashMap<>();

        e.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        
        ResponseError responseError = new ResponseError(e.getStatusCode().value(), LocalDateTime.now(), "Error Validation", errors);
        return ResponseEntity.status(responseError.getStatus()).body(responseError);
    }

    @ExceptionHandler(value = CustomNotFoundExceptions.class)
    public ResponseEntity<?> handleNotFoundException(CustomNotFoundExceptions e) {
        ResponseError responseError = new ResponseError(404, LocalDateTime.now(), e.getMessage(), null);
        return ResponseEntity.status(responseError.getStatus()).body(responseError);
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<?> handleExceptionGlobal(Exception e) {
        ResponseError responseError = new ResponseError(500, LocalDateTime.now(), e.getMessage(), null);
        return ResponseEntity.status(responseError.getStatus()).body(responseError);
    }
}
