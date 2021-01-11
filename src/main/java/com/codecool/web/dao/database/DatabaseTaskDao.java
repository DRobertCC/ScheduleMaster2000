package com.codecool.web.dao.database;

import com.codecool.web.dao.TaskDao;
import com.codecool.web.model.Task;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseTaskDao extends AbstractDao implements TaskDao {

    public DatabaseTaskDao(Connection connection) {
        super(connection);
    }


    @Override
    public void addTask(String taskName, String taskContent, int userId) throws SQLException {

        String sql = "INSERT INTO tasks(taskname, taskcontent, userid) VALUES (?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, taskName);
            statement.setString(2, taskContent);
            statement.setInt(3, userId);
            executeInsert(statement);
        }
    }

    @Override
    public List<Task> findAllTaskByUserId(int userId) throws SQLException {
        if (userId == 0) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        String sql = "SELECT * FROM tasks WHERE userid = ? ORDER BY taskid";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            List<Task> tasks = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    tasks.add(fetchTask(resultSet));
                }
                return tasks;
            }
        }
    }

    @Override
    public List<Task> findAllTask() throws SQLException {
        String sql = "SELECT * FROM tasks ORDER BY taskid";

        List<Task> tasks = new ArrayList<>();
        Statement statement = connection.createStatement();

            try (ResultSet resultSet = statement.executeQuery(sql)) {

                while (resultSet.next()) {
                    tasks.add(fetchTask(resultSet));
                }
                return tasks;
            }
    }

    @Override
    public Task findTaskById(int taskId) throws SQLException {
        if (taskId == 0) {
            throw new IllegalArgumentException("Task ID cannot be null or empty");
        }

        String sql = "SELECT * FROM tasks WHERE taskid = ?";
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setInt(1, taskId);

                try (ResultSet resultSet = statement.executeQuery()) {

                    if (resultSet.next()) {
                        return fetchTask(resultSet);
                    }
                    return null;
                }
            }
    }


    @Override
    public void deleteTask(int taskId) throws SQLException {

        String sql = "DELETE FROM tasks WHERE taskid = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, taskId);
            statement.execute();
        }
    }

    @Override
    public void editTask(int taskId, String taskName, String taskContent) throws SQLException {
        String sql = "UPDATE tasks SET taskname = ?, taskContent = ? WHERE taskid = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, taskName);
            statement.setString(2, taskContent);
            statement.setInt(3, taskId);
            statement.execute();
        }
    }

    private Task fetchTask(ResultSet resultSet) throws SQLException {

        int taskId = resultSet.getInt("taskid");
        String taskName = resultSet.getString("taskname");
        String taskContent = resultSet.getString("taskcontent");
        int userId = resultSet.getInt("userid");
        return new Task(taskId, taskName, taskContent, userId);
    }

}
