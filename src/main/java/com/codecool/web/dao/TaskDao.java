package com.codecool.web.dao;

import com.codecool.web.model.Task;

import java.sql.SQLException;
import java.util.List;

public interface TaskDao {

    void addTask(String taskName, String taskContent, int userId) throws SQLException;

    List<Task> findAllTaskByUserId(int userId) throws SQLException;

    List<Task> findAllTask() throws SQLException;

    Task findTaskById(int taskId) throws SQLException;

    void deleteTask(int taskId) throws SQLException;

    void editTask(int taskId, String taskName, String taskContent) throws SQLException;


}
