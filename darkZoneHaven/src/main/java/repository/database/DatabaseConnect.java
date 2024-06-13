package repository.database;

import java.sql.*;

public class DatabaseConnect {
    public static Connection connect() {
        String dbUrl = "jdbc:mysql://localhost:3306/g30db";
        String username = "root";
        String password = "Nithi_Siri_1B";
        Connection connection = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(dbUrl, username, password);
            
        } catch(ClassNotFoundException | SQLException e) {
            System.out.println(e);
        }
        return connection;
        }

    public static boolean isConnect() {
        try (Connection connection = connect()) {
            return connection != null;
        } catch (SQLException e) {
            return false;
        }
    }
}
