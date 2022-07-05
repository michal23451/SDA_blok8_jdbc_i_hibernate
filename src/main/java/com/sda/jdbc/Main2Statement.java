package com.sda.jdbc;

import java.sql.*;

import static com.sda.jdbc.Configuration.*;

public class Main2Statement {

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        Statement statement = connection.createStatement();

        int amount = statement.executeUpdate("UPDATE animal SET name = 'Jasio' WHERE id = 2");
        System.out.println("Amount: " + amount);

        ResultSet resultSet = statement.executeQuery("SELECT name FROM animal");
        while (resultSet.next()) {
            String name = resultSet.getString("name");
            System.out.println(name);
        }
        resultSet.close();

        boolean hasResult = statement.execute("TRUNCATE TABLE animal");
        System.out.println(hasResult);

        statement.close();
        connection.close();
    }
}
