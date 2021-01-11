package com.codecool.web.servlet;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.dao.database.DatabaseScheduleDao;
import com.codecool.web.service.simple.ScheduleService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Connection;
import java.sql.SQLException;

@WebServlet("/protected/createschedule")
public class CreateScheduleServlet extends AbstractServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) {
        try (Connection connection = getConnection(req.getServletContext())) {

            ScheduleDao scheduleDao = new DatabaseScheduleDao(connection);
            ScheduleService scheduleService = new ScheduleService(scheduleDao);
            String scheduleName = req.getParameter("schedulename");
            Integer scheduleLength = Integer.parseInt(req.getParameter("length"));
            Boolean isPublic = Boolean.parseBoolean(req.getParameter("ispublic"));
            Integer userId = 5;
            scheduleService.addSchedule(scheduleName,scheduleLength,isPublic,userId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
