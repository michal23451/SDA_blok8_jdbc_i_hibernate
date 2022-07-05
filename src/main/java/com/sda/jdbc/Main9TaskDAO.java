package com.sda.jdbc;

import com.sda.jdbc.dao.TaskDAO;
import com.sda.jdbc.model.Task;

import java.sql.Array;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Main9TaskDAO {
    public static void main(String[] args) /*throws SQLException*/ {

        try {
            TaskDAO taskDAO = new TaskDAO();

            //create
            taskDAO.create(new Task(1, "Administrator", 25));
            taskDAO.create(new Task(2, "Moderator", 26));
            taskDAO.create(new Task(3, "Author", 25));

            //read
            Optional<Task> optionalTask = taskDAO.read(1);
            optionalTask.ifPresentOrElse(o -> System.out.println(o), () -> System.out.println("nie znaleziono wiersza"));
            optionalTask = taskDAO.read(999);
            optionalTask.ifPresentOrElse(o -> System.out.println(o), () -> System.out.println("nie znaleziono wiersza"));
            //optionalTask = taskDAO.read(2);
            //System.out.println(optionalTask.get().getDescription());

            //readAll
            List<Task> taskList = taskDAO.readAll();
            System.out.println(taskList);

            //update
            Task task1 = new Task(1, "Administrator_after_update", 25);
            Task task2 = new Task(999, "Administrator", 25);
            Task task3 = new Task(1, "Administrator", 999);
            taskDAO.update(task1);
            //taskDAO.update(task2);
            //taskDAO.update(task3);

            //delete
            taskDAO.delete(99);

            //readAllForUser
            System.out.println(taskDAO.readAllForUser("Jan"));


            taskDAO.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
