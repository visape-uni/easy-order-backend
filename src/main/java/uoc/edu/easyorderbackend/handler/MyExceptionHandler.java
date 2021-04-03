package uoc.edu.easyorderbackend.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import uoc.edu.easyorderbackend.exceptions.EasyOrderException;

@ControllerAdvice
public class MyExceptionHandler {

    // handle specific exceptions
    @ExceptionHandler(EasyOrderException.class)
    public ResponseEntity<?> handleEasyOrderException(EasyOrderException exception, WebRequest request) {
        return new ResponseEntity(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SecurityException.class)
    public ResponseEntity<?> handleSecurityException (SecurityException exception, WebRequest request) {
        return new ResponseEntity<>(exception, HttpStatus.UNAUTHORIZED);
    }

    // handle global exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleSecurityException (Exception exception, WebRequest request) {
        return new ResponseEntity<>(exception, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
