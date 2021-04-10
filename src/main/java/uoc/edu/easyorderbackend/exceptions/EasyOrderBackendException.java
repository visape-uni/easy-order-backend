package uoc.edu.easyorderbackend.exceptions;

import org.springframework.http.HttpStatus;

public class EasyOrderBackendException extends RuntimeException{

    private HttpStatus status;
    private String message;

    public EasyOrderBackendException(HttpStatus status, String message) {
        super();
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
