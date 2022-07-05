package com.sda.jdbc;

import java.sql.*;

import static com.sda.jdbc.Configuration.*;

public class Main8Transaction {

    public static void main(String[] args) throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        connection.setAutoCommit(false);
        Statement statement = connection.createStatement();
        statement.executeUpdate("UPDATE user SET username = 'Ambroży' WHERE username = 'Jan'");
        Savepoint savepoint = connection.setSavepoint();
        statement.executeUpdate("UPDATE user SET username = 'Eustachy' WHERE username = 'Ala'");

        try {
            statement.executeUpdate("UPDATE user SET wrongColumn = 'Jadzia' WHERE username = 'Ola'");
        } catch (SQLException e) {
            connection.rollback(savepoint);
        }

        statement.close();
        connection.commit();
        connection.close();
    }
}
