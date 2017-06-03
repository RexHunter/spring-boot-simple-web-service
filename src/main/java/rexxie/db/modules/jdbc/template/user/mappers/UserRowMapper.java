package rexxie.db.modules.jdbc.template.user.mappers;

import org.springframework.jdbc.core.RowMapper;
import rexxie.db.modules.jdbc.template.user.models.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet resultSet, int i) throws SQLException {
        Integer id = resultSet.getInt(User.ID);
        String email = resultSet.getString(User.EMAIL);

        return User.createBuilder()
                .setId(id)
                .setEmail(email)
                .build();
    }
}
