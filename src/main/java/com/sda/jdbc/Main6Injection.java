package com.sda.jdbc;

import java.sql.*;

import static com.sda.jdbc.Configuration.*;

public class Main6Injection {

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);

        System.out.println(login(connection, "Ala", "password2")); // true
        System.out.println(login(connection, "Ala", "wrongPassword")); // false

        System.out.println(login(connection, "Ala", "' OR '1'='1"));
        System.out.println(securedLogin(connection, "Ala", "' OR '1'='1"));

        connection.close();
    }

    private static boolean securedLogin(Connection connection, String username, String password) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("SELECt * FROM user WHERE username = ? AND password = ?");
        preparedStatement.setString(1, username);
        preparedStatement.setString(2, password);
        ResultSet resultSet = preparedStatement.executeQuery();
        boolean isLogged = resultSet.next();
        resultSet.close();
        preparedStatement.close();
        return isLogged;
    }

    private static boolean login(Connection connection, String username, String password) throws SQLException {
        Statement statement = connection.createStatement();
        String query = "SELECT * FROM user WHERE username = '" + username + "' AND password = '" + password + "'";

        // SELECT * FROM user WHERE username = 'Ala' AND password = '' OR '1'='1'

        ResultSet resultSet = statement.executeQuery(query);
        boolean isLogged = resultSet.next();

        resultSet.close();
        statement.close();

        return isLogged;
    }
}
