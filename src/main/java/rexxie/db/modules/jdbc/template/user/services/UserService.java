package rexxie.db.modules.jdbc.template.user.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import rexxie.db.modules.jdbc.template.user.dao.UserDAO;
import rexxie.db.modules.jdbc.template.user.exceptions.UserIsAlreadyExistsException;
import rexxie.db.modules.jdbc.template.user.exceptions.UserNotFoundException;
import rexxie.db.modules.jdbc.template.user.models.User;

import java.util.List;

@Component
public class UserService {
    private UserDAO userDAO;

    @Autowired
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public User getUserById(Integer id) throws UserNotFoundException {
        return userDAO.getUserById(id);
    }

    public List<User> getUserList(Integer limit, Integer offset) {
        return userDAO.getUserList(limit, offset);
    }

    public User create(User user) throws UserIsAlreadyExistsException {
        return userDAO.create(user);
    }
}
