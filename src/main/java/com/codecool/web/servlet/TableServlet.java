package com.codecool.web.servlet;

import com.codecool.web.dao.LoggerDao;
import com.codecool.web.dao.database.DatabaseScheduleDao;
import com.codecool.web.dao.database.SimpleLoggerDao;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.User;
import com.codecool.web.service.LoggerService;
import com.codecool.web.service.simple.ScheduleService;
import com.codecool.web.service.simple.SimpleLoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/protected/tableview")
public class TableServlet extends AbstractServlet {

    private static final Logger logger = LoggerFactory.getLogger(TableServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try ( Connection connection = getConnection( request.getServletContext() ) ) {
            DatabaseScheduleDao databaseScheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService scheduleService = new ScheduleService(databaseScheduleDao);

            User user = (User) request.getSession().getAttribute("user");

            List<Schedule> schedules = scheduleService.getSchedules(user);


            sendMessage(response, HttpServletResponse.SC_OK, schedules);

            request.setAttribute("schedules", schedules);

        } catch (SQLException ex) {
            handleSqlError(response, ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {


    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {


    }

    private void logger(Connection connection, String logMessage){
        LoggerDao loggerDao = new SimpleLoggerDao(connection);
        LoggerService loggerService = new SimpleLoggerService(loggerDao);
        loggerService.appendLogFile(logMessage);
    }


}
