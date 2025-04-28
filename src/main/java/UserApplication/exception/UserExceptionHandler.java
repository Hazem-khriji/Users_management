package UserApplication.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserExceptionHandler {
    public UserExceptionHandler() {}
    @ExceptionHandler({UserNotFoundException.class})
    public ResponseEntity<UserException> handleUserException(UserNotFoundException ex) {
        UserException userException= new UserException(HttpStatus.NOT_FOUND ,ex.getMessage(),ex.getCause());
        return new ResponseEntity<>(userException,HttpStatus.NOT_FOUND);

    }
}
