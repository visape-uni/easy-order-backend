package uoc.edu.easyorderbackend.exceptions;

public class EasyOrderException {
    private String message;
    public EasyOrderException(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
