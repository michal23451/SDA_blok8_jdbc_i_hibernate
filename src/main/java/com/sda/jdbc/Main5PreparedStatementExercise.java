package com.sda.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static com.sda.jdbc.Configuration.*;

public class Main5PreparedStatementExercise {

    public static void main(String[] args) throws SQLException {
        List<String> names = List.of("Jan", "Ala", "Mikołaj", "Ola");
        List<String> passwords = List.of("password1", "password2", "password3", "password4");

        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO user(username, password) " +
                "VALUES (?, ?)");

//        AtomicInteger a = new AtomicInteger(0);
//        names.forEach(name -> {
//            String password = passwords.get(a.getAndIncrement());
//        });

        for (int i = 0; i < names.size(); i++) { // i=0 i=1 i=2 i=3
            preparedStatement.setString(1, names.get(i));
            preparedStatement.setString(2, passwords.get(i));
            preparedStatement.executeUpdate();
        }

        preparedStatement.close();
        connection.close();
    }
}
