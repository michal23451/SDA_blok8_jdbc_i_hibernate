package com.sda.jdbc;

import java.sql.*;

import static com.sda.jdbc.Configuration.*;

public class Main3StatementExercise {

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        Statement statement = connection.createStatement();

        statement.execute("CREATE TABLE IF NOT EXISTS user(" +
                "id BIGINT NOT NULL AUTO_INCREMENT, " +
                "username VARCHAR(50) UNIQUE, " +
                "password VARCHAR(50), " +
                "PRIMARY KEY(id))");

        statement.executeUpdate("INSERT INTO user(username, password) VALUES " +
                "('Jan','password1')," +
                "('Ala','password2')," +
                "('Mikołaj','password3')," +
                "('Kasia','password4')," +
                "('Ola','password5')," +
                "('Michał','password6')");

        ResultSet resultSet = statement.executeQuery("SELECT username FROM user");
        while (resultSet.next()){
            String name = resultSet.getString("username");
            System.out.println(name);
        }
        resultSet.close();

        statement.executeUpdate("DELETE FROM user");

        statement.close();
        connection.close();
    }
}
