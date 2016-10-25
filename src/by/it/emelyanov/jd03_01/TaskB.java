package by.it.emelyanov.jd03_01;

import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.sql.*;

public class TaskB {
    public static void main(String[] args) {
        try {
            Driver driver = new FabricMySQLDriver();
            DriverManager.registerDriver(driver);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try (Connection connection =
                     DriverManager.getConnection
                             (CN.URL_DB, CN.USER_DB, CN.PASSWORD_DB);
             Statement statement = connection.createStatement()) {

            ResultSet resultSet = statement.executeQuery("SELECT * FROM users, roles WHERE users.FK_Role = roles.ID;");

            while (resultSet.next()) {

                System.out.printf("ID: %s; Имя пользователя: %s; Пароль: %s; E-mail: %s; ID пользователя: %s; Тип пользователя: %s\n",

                        resultSet.getInt("ID"), resultSet.getString("Login"), resultSet.getString("Password"),
                        resultSet.getString("Email"), resultSet.getInt("FK_Role"), resultSet.getString("Name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
