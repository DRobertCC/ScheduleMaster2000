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

@WebServlet("/protected/schedule")
public class ScheduleServlet extends AbstractServlet {

    private static final Logger logger = LoggerFactory.getLogger(ScheduleServlet.class);

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        try (Connection connection = getConnection(request.getServletContext())) {
            DatabaseScheduleDao databaseScheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService ss = new ScheduleService(databaseScheduleDao);

            User user = (User) request.getSession().getAttribute("user");
            int userId = user.getId();

            String scheduleId = request.getParameter("scheduleid");
            String scheduleName = request.getParameter("schedulename");
            int scheduleLength = Integer.parseInt( request.getParameter("schedulelength") );
            Boolean isPublic = Boolean.parseBoolean( request.getParameter("ispublic") );

            ss.addSchedule(scheduleName, scheduleLength, isPublic, userId);

            // logger.info("Schedule added"); // Ez a Tomcat konzolra ír!
            String logMessage = "Schedule: "+ scheduleName +"(id = " + scheduleId +") added by: " + user.getUsername() + "(id = " + userId + ").";
            sendMessage(response, HttpServletResponse.SC_OK, logMessage);
            logger(connection, logMessage);

            doGet(request, response);

        } catch (SQLException ex) {
            handleSqlError(response, ex);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try ( Connection connection = getConnection( request.getServletContext() ) ) {
            DatabaseScheduleDao databaseScheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService scheduleService = new ScheduleService(databaseScheduleDao);

            User user = (User) request.getSession().getAttribute("user");

            List<Schedule> schedules = scheduleService.getSchedules(user);

/*
            List<Schedule> allSchedules = scheduleService.getAllSchedule();
            List<Schedule> publicSchedules = scheduleService.getAllPublicSchedule();
            List<Schedule> mySchedules = scheduleService.getAllScheduleByUserId(userId);

            ScheduleDto scheduleDto = new ScheduleDto(mySchedules, publicSchedules, allSchedules);
*/

            sendMessage(response, HttpServletResponse.SC_OK, schedules);

            request.setAttribute("schedules", schedules);

        } catch (SQLException ex) {
            handleSqlError(response, ex);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try (Connection connection = getConnection(request.getServletContext())) {
            DatabaseScheduleDao databaseScheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService scheduleService = new ScheduleService(databaseScheduleDao);

            int schedule_id = Integer.parseInt(request.getParameter("scheduleId"));

            scheduleService.deleteSchedule(schedule_id);

            doGet(request, response);

        } catch (SQLException ex) {
            handleSqlError(response, ex);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try (Connection connection = getConnection(request.getServletContext())) {
            DatabaseScheduleDao databaseScheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService scheduleService = new ScheduleService(databaseScheduleDao);

            String scheduleName = request.getParameter("scheduleName");
            int scheduleLength = Integer.parseInt( request.getParameter("scheduleLength") );
            Boolean isPublic = Boolean.parseBoolean( request.getParameter("scheduleStatus") );
            int scheduleId = Integer.parseInt(request.getParameter("scheduleId"));

            scheduleService.editSchedule(scheduleId, scheduleName, scheduleLength, isPublic);
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
