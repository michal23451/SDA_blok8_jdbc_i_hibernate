package com.sda.jdbc;

import com.sda.jdbc.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.sda.jdbc.Configuration.*;

public class Main7UserExercise {

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM user");

        List<User> users = new ArrayList<>();

        while (resultSet.next()) {
            long id = resultSet.getLong("id");
            String username = resultSet.getString("username");
            String password = resultSet.getString("password");
            User user = new User(id, username, password);
            users.add(user);
        }

        resultSet.close();
        statement.close();
        connection.close();

        users.forEach(System.out::println);
       // users.forEach(user -> System.out.println(user));

    }
}
