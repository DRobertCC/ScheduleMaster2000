package com.codecool.web.dao.database;

import com.codecool.web.dao.UserDao;
import com.codecool.web.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public final class DatabaseUserDao extends AbstractDao implements UserDao {


    public DatabaseUserDao(Connection connection) {
        super(connection);
    }

    public List<User> findAllUser() throws SQLException {
        String sql = "SELECT * FROM users ORDER BY username";
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                users.add(fetchUser(resultSet));
            }
            return users;
        }
    }

    @Override
    public void editUser(int userId, Boolean adminStatus) throws SQLException {
        String sql = "UPDATE users SET isadmin=? WHERE userid=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setBoolean(1, adminStatus);
            statement.setInt(2, userId);
            statement.execute();
        }
    }

    @Override
    public User findByUserName(String username) throws SQLException {
        if (username == null || "".equals(username)) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        String sql = "SELECT userid, username, userpassword, isadmin FROM users WHERE username = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return fetchUser(resultSet);
                }
            }
        }
        return null;
    }

    @Override
    public void addUser(String userName, String userpassword) throws SQLException {
        String sql = "INSERT INTO users(username, userpassword) VALUES (?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, userName);
            statement.setString(2, userpassword);
            executeInsert(statement);
        }
    }

    @Override
    public void deleteUser(int userId) throws SQLException {
        String sql = "DELETE FROM users WHERE userid=?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, userId);
            statement.execute();
        }
    }

    private User fetchUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("userid");
        String username = resultSet.getString("username");
        String password = resultSet.getString("userpassword");
        boolean isAdmin = resultSet.getBoolean("isadmin");
        System.out.println(isAdmin);
        return new User(id, username, password, isAdmin);
    }
}
