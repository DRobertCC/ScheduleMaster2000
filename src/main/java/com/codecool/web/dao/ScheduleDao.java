package com.codecool.web.dao;

import com.codecool.web.model.Schedule;

import java.sql.SQLException;
import java.util.List;

public interface ScheduleDao {

    void addSchedule(String scheduleName, int scheduleLength, Boolean isPublic, int userId) throws SQLException;

    List<Schedule> findAllScheduleByUserId(int userId) throws SQLException;

    Schedule findScheduleById(int scheduleId) throws SQLException;

    List<Schedule> findAllPublicSchedule() throws SQLException;

    List<Schedule> findAllSchedule() throws SQLException;

    void deleteSchedule(int scheduleId) throws SQLException;

    void editSchedule(int scheduleId, String scheduleName, int scheduleLength, Boolean isPublic) throws SQLException;

}
