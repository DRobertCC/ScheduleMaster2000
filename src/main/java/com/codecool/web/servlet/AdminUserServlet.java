package com.codecool.web.servlet;

import com.codecool.web.dao.database.DatabaseUserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.exception.ServiceException;
import com.codecool.web.service.simple.UserService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/protected/adminuser")
public class AdminUserServlet extends AbstractServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try (Connection connection = getConnection(request.getServletContext())) {
            DatabaseUserDao databaseUserDao = new DatabaseUserDao(connection);
            UserService userService = new UserService(databaseUserDao);

            User user = (User) request.getSession().getAttribute("user");

            List<User> allUsers = userService.getAllUser(user);

            sendMessage(response, HttpServletResponse.SC_OK, allUsers);

            request.setAttribute("allUsers", allUsers);

        } catch (SQLException ex) {
            handleSqlError(response, ex);
        } catch (ServiceException ex) {
            sendMessage(response, HttpServletResponse.SC_FORBIDDEN, ex.getMessage());
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try (Connection connection = getConnection(request.getServletContext())) {
            DatabaseUserDao databaseUserDao = new DatabaseUserDao(connection);
            UserService userService = new UserService(databaseUserDao);

            int userId = Integer.parseInt(request.getParameter("userId"));

            userService.deleteUser(userId);

            doGet(request, response);

        } catch (SQLException ex) {
            handleSqlError(response, ex);
        }
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {

        try (Connection connection = getConnection(request.getServletContext())) {
            DatabaseUserDao databaseUserDao = new DatabaseUserDao(connection);
            UserService userService = new UserService(databaseUserDao);


            Boolean adminStatus = Boolean.parseBoolean( request.getParameter("adminStatus") );
            int userId = Integer.parseInt(request.getParameter("userId"));

            userService.editUser(userId,adminStatus);
            sendMessage(response, HttpServletResponse.SC_OK, null);
            doGet(request, response);

        } catch (SQLException ex) {
            handleSqlError(response, ex);
        }
    }


}
