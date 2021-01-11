package com.codecool.web.dao;

import com.codecool.web.model.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDao {

    User findByUserName(String username) throws SQLException;

    void addUser(String userName, String email) throws SQLException;

    void deleteUser(int userId) throws SQLException;

    public List<User> findAllUser() throws SQLException;

    void editUser(int userId, Boolean adminStatus) throws SQLException;
}
