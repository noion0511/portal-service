package kr.ac.jejunu;

import javax.sql.DataSource;
import java.sql.*;

public class JdbcContext {
    private final DataSource dataSource;

    public JdbcContext(DataSource dataSource) {
        this.dataSource = dataSource;
    }

     User jdbcContextForFind(StatementStrategy statementStrategy) throws SQLException {
        User user = null;
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();
            preparedStatement = statementStrategy.makeStatement(connection);

            resultSet = preparedStatement.executeQuery();
            if(resultSet.next()) {
                user = new User();
                user.setId(resultSet.getInt("id"));
                user.setName(resultSet.getString("name"));
                user.setPassword(resultSet.getString("password"));
            }
        } finally {
            resultSet.close();
            preparedStatement.close();
            connection.close();
        }
        return user;
    }

    void jdbcContextForInsert(User user, StatementStrategy statementStrategy) throws SQLException {
        ResultSet resultSet = null;
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();

            preparedStatement = statementStrategy.makeStatement(connection);

            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();

            user.setId(resultSet.getInt(1));
        } finally {
            try {
                resultSet.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    void jdbcContextForUpdate(StatementStrategy statementStrategy) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = dataSource.getConnection();

            preparedStatement = statementStrategy.makeStatement(connection);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                preparedStatement.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void update(String sql, Object[] params) {
        StatementStrategy statementStrategy = connection -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            sql
                    );

            for(int i = 0; i< params.length; i++) {
                preparedStatement.setObject(i+1, params[i]);
            }
            return preparedStatement;
        };
        jdbcContextForUpdate(statementStrategy);
    }

    public void insert(User user, String sql, Object[] params, UserDao userDao) throws SQLException {
        StatementStrategy statementStrategy = connection -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            sql
                            , Statement.RETURN_GENERATED_KEYS
                    );
            for(int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i+1, params[i]);
            }

            return preparedStatement;
        };
        jdbcContextForInsert(user, statementStrategy);
    }

    public User find(String sql, Object[] params) throws SQLException {
        StatementStrategy statementStrategy = connection -> {
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            sql
                    );

            for(int i = 0; i < params.length; i++) {
                preparedStatement.setObject(i+1, params[i]);
            }
            return preparedStatement;
        };
        return jdbcContextForFind(statementStrategy);
    }
}