package UserApplication.exception;


import org.springframework.http.HttpStatus;
public class UserException {
    private final HttpStatus status;
    private final String message;
    private final Throwable cause;
    public UserException(HttpStatus status, String message, Throwable cause) {
        this.status = status;
        this.message = message;
        this.cause = cause;
    }
    public HttpStatus getStatus() {
        return status;
    }
    public String getMessage() {
        return message;
    }
    public Throwable getCause() {
        return cause;
    }
}
