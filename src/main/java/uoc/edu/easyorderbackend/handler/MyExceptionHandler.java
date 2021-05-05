package uoc.edu.easyorderbackend.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import uoc.edu.easyorderbackend.exceptions.EasyOrderBackendException;
import uoc.edu.easyorderbackend.exceptions.EasyOrderException;

@ControllerAdvice
public class MyExceptionHandler {

    private final static Logger logger = LoggerFactory.getLogger(MyExceptionHandler.class);

    // handle specific exceptions
    @ExceptionHandler(EasyOrderBackendException.class)
    public ResponseEntity<EasyOrderException> handleEasyOrderException(EasyOrderBackendException exception, WebRequest request) {
        logger.error("MyExceptionHandler: ", exception);
        return new ResponseEntity<>(new EasyOrderException(exception.getMessage()),
                exception.getStatus());
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<EasyOrderException> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException exception, WebRequest request) {
        logger.error("MyExceptionHandler: ", exception);
        return new ResponseEntity<>(new EasyOrderException(exception.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<EasyOrderException> handleNullPointerExceotion(NullPointerException exception, WebRequest request) {
        logger.error("MyExceptionHandler: ", exception);
        return new ResponseEntity<>(new EasyOrderException("Null pointer"),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    // handle global exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<EasyOrderException> handleGenericException (Exception exception, WebRequest request) {
        logger.error("MyExceptionHandler: ", exception);
        return new ResponseEntity<>(new EasyOrderException(exception.getMessage()),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
