package com.codecool.web.servlet;

import com.codecool.web.dao.LoggerDao;
import com.codecool.web.dao.database.DatabaseTaskDao;
import com.codecool.web.dao.database.SimpleLoggerDao;
import com.codecool.web.model.Task;
import com.codecool.web.model.User;
import com.codecool.web.service.LoggerService;
import com.codecool.web.service.simple.SimpleLoggerService;
import com.codecool.web.service.simple.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/protected/task")
public class TaskServlet extends AbstractServlet {

    private static final Logger logger = LoggerFactory.getLogger(TaskServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try (Connection connection = getConnection(request.getServletContext())) {
            DatabaseTaskDao databaseTaskDao = new DatabaseTaskDao(connection);
            TaskService ts = new TaskService(databaseTaskDao);

            User user = (User) request.getSession().getAttribute("user");
            int userId = user.getId();

            String taskId = request.getParameter("taskid");
            String taskName = request.getParameter("taskName");
            String taskContent = request.getParameter("content");

            ts.addTask(taskName, taskContent, userId);

            //logger.info("Task added"); // Ez a Tomcat konzolra ír!
            String logMessage = "Task: "+ taskName +"(id = " + taskId +") added by: " + user.getUsername() + "(id = " + userId + ").";
            sendMessage(response, HttpServletResponse.SC_OK, logMessage);
            logger(connection, logMessage);

            doGet(request, response);

        } catch (SQLException ex) {
            handleSqlError(response, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try (Connection connection = getConnection(request.getServletContext())) {
            DatabaseTaskDao databaseTaskDao = new DatabaseTaskDao(connection);
            TaskService taskService = new TaskService(databaseTaskDao);

            User user = (User) request.getSession().getAttribute("user");

            List<Task> tasks = taskService.getTasks(user);


            sendMessage(response, HttpServletResponse.SC_OK, tasks);

        } catch (SQLException ex) {
            handleSqlError(response, ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try (Connection connection = getConnection(request.getServletContext())) {
            DatabaseTaskDao databaseTaskDao = new DatabaseTaskDao(connection);
            TaskService taskService = new TaskService(databaseTaskDao);

            int taskId = Integer.parseInt(request.getParameter("taskId"));

            taskService.deleteTask(taskId);

            doGet(request, response);

        } catch (SQLException ex) {
            handleSqlError(response, ex);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try (Connection connection = getConnection(request.getServletContext())) {
            DatabaseTaskDao databaseTaskDao = new DatabaseTaskDao(connection);
            TaskService taskService = new TaskService(databaseTaskDao);

            int taskId = Integer.parseInt(request.getParameter("taskId"));
            String taskName = request.getParameter("taskName");
            String taskContent = request.getParameter("taskContent");

            taskService.editTask(taskId, taskName, taskContent);
            sendMessage(response, HttpServletResponse.SC_OK, null);
            doGet(request, response);

        } catch (SQLException ex) {
            handleSqlError(response, ex);
        }
    }

    private void logger(Connection connection, String logMessage){
        LoggerDao loggerDao = new SimpleLoggerDao(connection);
        LoggerService loggerService = new SimpleLoggerService(loggerDao);
        loggerService.appendLogFile(logMessage);
    }

}
