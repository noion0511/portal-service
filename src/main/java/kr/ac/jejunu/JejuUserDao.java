package kr.ac.jejunu;

public class JejuUserDao extends UserDao {
    public JejuUserDao(ConnectionMaker connectionMaker) {
        super(connectionMaker);
    }

//    @Override
//    public Connection getConnection() throws ClassNotFoundException, SQLException {
//        Class.forName("com.mysql.cj.jdbc.Driver");
//
//        return DriverManager.getConnection(
//                "jdbc:mysql://192.168.151.176:3306/jeju",
//                "jeju",
//                "jejupw"
//        );
//    }
}
