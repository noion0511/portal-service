package kr.ac.jejunu;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class UpdateStatementStrategy implements StatementStrategy {
    private User user;
    public UpdateStatementStrategy(User user) {
        this.user = user;
    }

    @Override
    public PreparedStatement makeStatement(Connection connection) throws SQLException {
        PreparedStatement preparedStatement =
                connection.prepareStatement(
                        "update userinfo set name = ?, password =? where id = ?"
                        , Statement.RETURN_GENERATED_KEYS
                );
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getPassword());
        preparedStatement.setInt(3, user.getId());
        return preparedStatement;
    }
}
