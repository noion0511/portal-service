package kr.ac.jejunu;

import java.sql.*;

public class UserDao {
    public User findById(Integer id) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(
                "jdbc:mysql://192.168.151.176:3306/jeju",
                "jeju",
                "jejupw"
        );
        PreparedStatement preparedStatement =
                connection.prepareStatement(
                        "select * from userinfo where id = ?"
                );
        preparedStatement.setInt(1, id);

        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        user.setPassword(resultSet.getString("password"));
        resultSet.close();
        preparedStatement.close();
        connection.close();
        return user;
    }
}
