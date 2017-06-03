package rexxie.db.modules.jdbc.template.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NO_CONTENT)
public class UserNotFoundException extends Exception {
    public UserNotFoundException(Integer id) {
        super("Couldn't find user with id:" + id);
    }
}
