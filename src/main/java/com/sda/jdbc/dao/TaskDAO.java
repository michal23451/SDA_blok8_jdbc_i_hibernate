package com.sda.jdbc.dao;

import com.sda.jdbc.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.sda.jdbc.Configuration.*;

public class TaskDAO implements AutoCloseable {

    private Connection connection = null;

    public TaskDAO() {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS task(id BIGINT NOT NULL, description VARCHAR(255), user_id BIGINT, PRIMARY KEY (id), CONSTRAINT user_id_fk FOREIGN KEY (user_id) REFERENCES user(id))");
            statement.executeUpdate("DELETE from task");
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void create(Task task) throws SQLException {
        // tworzymy nowy task w bazie danych na podstawie informacji z argumentu
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO task(id, description, user_id) VALUES (?, ?, ?)");
        preparedStatement.setLong(1, task.getId());
        preparedStatement.setString(2, task.getDescription());
        preparedStatement.setLong(3, task.getUserId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public Optional<Task> read(long id) throws SQLException {
        // wyciągamy dane z bazy na podstawie id taska i przypisujemy do obiektu klasy Task
        // jeśli znajdzie wiersz to zwracamy Optional.of(new Task(...))
        // jeśli nie znajdzie to Optional.empty()
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM task WHERE id = ?");
        preparedStatement.setLong(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();

        /* try {
            resultSet.next(); //pobieramy element
            long idDB = resultSet.getLong(1);
            String descriptionDB = resultSet.getString(2);
            long userIdDB = resultSet.getLong(3);
            resultSet.close();
            preparedStatement.close();
            return Optional.of(new Task(idDB, descriptionDB, userIdDB));
        } catch (NullPointerException e) {
            return Optional.empty();
        }*/

        Optional<Task> result;
        if (resultSet.next()) {//select ma zwracać jeden wiersz
            long idDB = resultSet.getLong(1);
            String descriptionDB = resultSet.getString(2);
            long userIdDB = resultSet.getLong(3);
            result = Optional.of(new Task(idDB, descriptionDB, userIdDB));
        } else {
            result = Optional.empty();
        }
        resultSet.close();
        preparedStatement.close();
        return result;


    }

    public List<Task> readAll() throws SQLException {
        // wyciągamy wszystkie wiersze z bazy danych
        // wyniki zapisujemy w liście obiektów klasy Task
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM task");
        List<Task> taskList = new ArrayList<>();

        while (resultSet.next()) {
            Task task = new Task(resultSet.getLong("id"), resultSet.getString("description"), resultSet.getLong("user_id"));
            //Task task = new Task(resultSet.getLong(1), resultSet.getString(2), resultSet.getLong(3));
            taskList.add(task);
        }

        resultSet.close();
        statement.close();

        return taskList;
    }

    public void update(Task task) throws SQLException {
        // aktualizujemy description i user_id na podstawie id taska
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE task SET description = ?, user_id = ? where id = ?");
        preparedStatement.setString(1, task.getDescription());
        preparedStatement.setLong(2, task.getUserId());
        preparedStatement.setLong(3, task.getId());
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public void delete(long id) throws SQLException {
        // usuwamy wiersz z bazy na podstawie id taska
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM task WHERE ID = ?");
        preparedStatement.setLong(1, id);
        preparedStatement.executeUpdate();
        preparedStatement.close();
    }

    public List<Task> readAllForUser(String username) throws SQLException {
        // dla ochotników
        // konstruujemy query z użyciem JOIN i odwołaniem do tabeli user
        PreparedStatement preparedStatement = connection.prepareStatement("SELECT t.* FROM task AS t " +
                "JOIN user AS u ON t.user_id = u.id " +
                "WHERE u.username = ?");
        preparedStatement.setString(1, username);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Task> taskList = new ArrayList<>();
        while (resultSet.next()) {
            taskList.add(new Task(resultSet.getLong("id"), resultSet.getString("description"), resultSet.getLong("user_id")));
        }

        resultSet.close();
        preparedStatement.close();
        return taskList;
    }

    @Override
    public void close() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}