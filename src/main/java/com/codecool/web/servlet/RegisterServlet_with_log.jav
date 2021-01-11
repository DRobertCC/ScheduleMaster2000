package com.codecool.web.servlet;

import com.codecool.web.dao.LoggerDao;
import com.codecool.web.dao.UserDao;
import com.codecool.web.dao.database.DatabaseUserDao;
import com.codecool.web.dao.database.SimpleLoggerDao;
import com.codecool.web.model.User;
import com.codecool.web.service.LoggerService;
import com.codecool.web.service.simple.SimpleLoggerService;
import com.codecool.web.service.simple.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/register")
public final class RegisterServlet_with_log extends AbstractServlet {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try (Connection connection = getConnection(request.getServletContext())) {
            UserDao userDao = new DatabaseUserDao(connection);
            UserService us = new UserService(userDao);

            String userName = request.getParameter("username");
            String password = request.getParameter("password");

            us.registerUser(userName, password);
            User user = userDao.findByUserName(userName);

            request.getSession().setAttribute("user", user);

            int userId = user.getId();
            logger.info("New user registered");
            String logMessage = "Username: " + userName + "(id = " + userId + ").";
            sendMessage(response, HttpServletResponse.SC_OK, logMessage);
            logger(connection, logMessage);

            sendMessage(response, HttpServletResponse.SC_OK, user);

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
