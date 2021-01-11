package com.codecool.web.service.simple;

import com.codecool.web.dao.TaskDao;
import com.codecool.web.model.Task;
import com.codecool.web.model.User;

import java.sql.SQLException;
import java.util.List;

public class TaskService {

    private TaskDao taskDao;

    public TaskService(TaskDao taskDao) {
        this.taskDao = taskDao;
    }


    public void addTask(String taskName, String taskContent, int userId) throws SQLException {
        taskDao.addTask(taskName, taskContent, userId);
    }

    public List<Task> getTasks(User user) throws SQLException {

        if (user.isAdmin()) {
            return taskDao.findAllTask();
        }

        return taskDao.findAllTaskByUserId(user.getId());

    }

//    public List<Task> getAllTaskByUserId(int userId) throws SQLException {
//        return taskDao.findAllTaskByUserId(userId);
//    }
//
//    public List<Task> getAllTask() throws SQLException {
//        return taskDao.findAllTask();
//    }

    public Task getTaskById(int taskId) throws SQLException {
        return taskDao.findTaskById(taskId);
    }

    public void deleteTask(int taskId) throws SQLException {
        taskDao.deleteTask(taskId);
    }

    public void editTask(int taskId, String taskName, String taskContent) throws SQLException {
        taskDao.editTask(taskId, taskName, taskContent);
    }

}
