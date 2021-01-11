package com.codecool.web.service.simple;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.model.Schedule;
import com.codecool.web.model.User;

import java.sql.SQLException;
import java.util.List;

public class ScheduleService {

    private ScheduleDao scheduleDao;

    public ScheduleService(ScheduleDao scheduleDao) {
        this.scheduleDao = scheduleDao;
    }


    public void addSchedule(String scheduleName, int scheduleLength, Boolean isPublic, int userId) throws SQLException {
        scheduleDao.addSchedule(scheduleName, scheduleLength, isPublic, userId);
    }

    public List<Schedule> getSchedules(User user) throws SQLException {

        if (user.isAdmin()) {
            return scheduleDao.findAllSchedule();
        }

        if (user.getUsername().equals("Guest") || user.equals(null)) {
            return scheduleDao.findAllPublicSchedule();
        }

        int userId = user.getId();
        return scheduleDao.findAllScheduleByUserId(userId);

    }

//    public List<Schedule> getAllScheduleByUserId(int userId) throws SQLException {
//        return scheduleDao.findAllScheduleByUserId(userId);
//    }
//
//    public List<Schedule> getAllPublicSchedule() throws SQLException {
//        return scheduleDao.findAllPublicSchedule();
//    }
//
//    public List<Schedule> getAllSchedule() throws SQLException {
//        return scheduleDao.findAllSchedule();
//    }

    public void deleteSchedule(int scheduleId) throws SQLException {
        scheduleDao.deleteSchedule(scheduleId);
    }

    public void editSchedule(int scheduleId, String scheduleName, int scheduleLength, Boolean isPublic) throws SQLException {
        scheduleDao.editSchedule(scheduleId, scheduleName, scheduleLength, isPublic);
    }

}
