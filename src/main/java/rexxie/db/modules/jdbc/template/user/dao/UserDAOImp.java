package rexxie.db.modules.jdbc.template.user.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import rexxie.db.modules.jdbc.template.user.exceptions.UserIsAlreadyExistsException;
import rexxie.db.modules.jdbc.template.user.exceptions.UserNotFoundException;
import rexxie.db.modules.jdbc.template.user.mappers.UserRowMapper;
import rexxie.db.modules.jdbc.template.user.models.User;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;

@Component
public class UserDAOImp implements UserDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setJdbcTemplate(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public List<User> getUserList(Integer limit, Integer offset) {
        String query = "SELECT * FROM `users`.`user` LIMIT ?, ?";
        return jdbcTemplate.query(query, new Object[]{offset, limit}, new UserRowMapper());
    }

    @Override
    public User getUserById(Integer id) throws UserNotFoundException {
        String query = "SELECT * FROM `users`.`user` WHERE id = ?";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{id}, new UserRowMapper());
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException(id);
        }
    }

    @Override
    public User create(User user) throws UserIsAlreadyExistsException {
        String INSERT_QUERY = "INSERT INTO `users`.`user` (" + User.EMAIL + ") VALUES (?)";

        try {
            KeyHolder keyHolder = new GeneratedKeyHolder();

            jdbcTemplate.update((connection) -> {
                        PreparedStatement ps = connection.prepareStatement(INSERT_QUERY, new String[]{"id"});
                        ps.setString(1, user.getEmail());
                        return ps;
                    },
                    keyHolder);

            return User.createBuilder()
                    .setId(keyHolder.getKey().intValue())
                    .setEmail(user.getEmail())
                    .build();

        } catch (DuplicateKeyException e) {
            throw new UserIsAlreadyExistsException(user);
        }
    }

}
