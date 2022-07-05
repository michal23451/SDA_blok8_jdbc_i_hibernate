package com.sda.jdbc;

import java.sql.*;

import static com.sda.jdbc.Configuration.*;

public class Main4PreparedStatement {

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT age FROM animal WHERE name = ?");
        preparedStatement.setString(1, "Benio");

        // SELECT age FROM animal WHERE name = Benio

        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int age = resultSet.getInt("age");
            System.out.println(age);
        }

        resultSet.close();
        preparedStatement.close();
        connection.close();
    }
}
