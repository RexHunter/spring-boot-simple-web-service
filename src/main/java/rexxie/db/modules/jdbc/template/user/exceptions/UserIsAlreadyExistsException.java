package rexxie.db.modules.jdbc.template.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import rexxie.db.modules.jdbc.template.user.models.User;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserIsAlreadyExistsException extends Exception {
    public UserIsAlreadyExistsException(User user) {
        super("User is already exists: " + user.getEmail());
    }
}
