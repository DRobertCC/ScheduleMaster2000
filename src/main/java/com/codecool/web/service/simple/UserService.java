package com.codecool.web.service.simple;

import com.codecool.web.dao.UserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.exception.ServiceException;

import java.sql.SQLException;
import java.util.List;

public class UserService {

    private UserDao userDao;

    public UserService(UserDao userDao) {
        this.userDao = userDao;
    }

    public void registerUser(String userName, String password) throws SQLException {
        userDao.addUser(userName, password);
    }

    public List<User> getAllUser(User user) throws SQLException, ServiceException {

        if (user.isAdmin()) {
            return userDao.findAllUser();
        } else throw new ServiceException("You must have Admin privileges to see this!");

    }

    public void deleteUser(int userId) throws SQLException {
        userDao.deleteUser(userId);
    }

    public void editUser(int userId, Boolean adminStatus) throws SQLException{
        userDao.editUser(userId, adminStatus);
    }
}
