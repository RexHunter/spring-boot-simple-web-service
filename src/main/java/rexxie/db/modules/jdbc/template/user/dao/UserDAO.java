package rexxie.db.modules.jdbc.template.user.dao;

import rexxie.db.modules.jdbc.template.user.exceptions.UserIsAlreadyExistsException;
import rexxie.db.modules.jdbc.template.user.exceptions.UserNotFoundException;
import rexxie.db.modules.jdbc.template.user.models.User;

import java.util.List;

public interface UserDAO {
    List<User> getUserList(Integer limit, Integer offset);
    User getUserById(Integer id) throws UserNotFoundException;
    User create(User user) throws UserIsAlreadyExistsException;
}
