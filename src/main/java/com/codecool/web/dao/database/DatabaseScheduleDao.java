package com.codecool.web.dao.database;

import com.codecool.web.dao.ScheduleDao;
import com.codecool.web.model.Schedule;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseScheduleDao extends AbstractDao implements ScheduleDao {


    public DatabaseScheduleDao(Connection connection) {
        super(connection);
    }


    @Override
    public void addSchedule(String scheduleName, int scheduleLength, Boolean isPublic, int userId) throws SQLException {

        String sql = "INSERT INTO schedules(schedulename, schedulelength, ispublic, userid) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, scheduleName);
            statement.setInt(2, scheduleLength);
            statement.setBoolean(3, isPublic);
            statement.setInt(4, userId);
            executeInsert(statement);
        }
    }

    @Override
    public List<Schedule> findAllScheduleByUserId(int userId) throws SQLException {
        if (userId == 0) {
            throw new IllegalArgumentException("User ID cannot be null or empty");
        }

        String sql = "SELECT * FROM schedules WHERE userid = ? ORDER BY scheduleid";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            List<Schedule> schedules = new ArrayList<>();
            try (ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    schedules.add(fetchSchedule(resultSet));
                }
                return schedules;
            }
        }
    }

    @Override
    public Schedule findScheduleById(int scheduleId) throws SQLException {
        if (scheduleId == 0) {
            throw new IllegalArgumentException("Schedule ID cannot be null or empty");
        }
        String sql = "SELECT * FROM schedules WHERE scheduleId = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, scheduleId);
            Schedule schedules = null;
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    schedules = fetchSchedule(resultSet);
                }
                return schedules;
            }
        }
    }

    @Override
    public List<Schedule> findAllPublicSchedule() throws SQLException {

        String sql = "SELECT * FROM schedules WHERE ispublic ORDER BY scheduleid";

        Statement statement = connection.createStatement();
        List<Schedule> schedules = new ArrayList<>();

        try (ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                schedules.add(fetchSchedule(resultSet));
            }
            return schedules;
        }
    }

    @Override
    public List<Schedule> findAllSchedule() throws SQLException {

        String sql = "SELECT * FROM schedules ORDER BY scheduleid";

        List<Schedule> schedules = new ArrayList<>();
        Statement statement = connection.createStatement();

        try (ResultSet resultSet = statement.executeQuery(sql)) {

            while (resultSet.next()) {
                schedules.add(fetchSchedule(resultSet));
            }
            return schedules;
        }
    }

    @Override
    public void deleteSchedule(int scheduleId) throws SQLException {

        String sql = "DELETE FROM schedules WHERE scheduleid=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, scheduleId);
            statement.execute();
        }
    }

    @Override
    public void editSchedule(int scheduleId, String scheduleName, int scheduleLength, Boolean isPublic) throws SQLException {

        String sql = "UPDATE schedules SET schedulename=?, schedulelength=?, ispublic=? WHERE scheduleid=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, scheduleName);
            statement.setInt(2, scheduleLength);
            statement.setBoolean(3, isPublic);
            statement.setInt(4, scheduleId);
            statement.execute();
        }
    }

/*    private void close(Connection connection, Statement statement, ResultSet resultSet) {

        try {
            if (resultSet != null) {
                resultSet.close();
            }

            if (statement != null) {
                statement.close();
            }

            if (connection != null) {
                connection.close();   // doesn't really close it ... just puts back in connection pool (marks as available), so others can use it
            }
        }
        catch (Exception exc) {
            exc.printStackTrace();
        }
    }*/

    private Schedule fetchSchedule(ResultSet resultSet) throws SQLException{

        int id = resultSet.getInt("scheduleid");
        String scheduleName = resultSet.getString("schedulename");
        int scheduleLength = resultSet.getInt("schedulelength");
        boolean isPublic = resultSet.getBoolean("ispublic");
        int userId = resultSet.getInt("userid");
        return new Schedule(id, scheduleName, scheduleLength, isPublic, userId);
    }

}
