package com.codecool.web.servlet;

import com.codecool.web.dao.UserDao;
import com.codecool.web.dao.database.DatabaseUserDao;
import com.codecool.web.model.User;
import com.codecool.web.service.simple.UserService;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken.Payload;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collections;

// https://developers.google.com/identity/sign-in/android/backend-auth
@WebServlet("/googlesignin")
public final class GoogleSignInServlet extends AbstractServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {
            JsonFactory jsonFactory = new JacksonFactory();
            HttpTransport transport = new NetHttpTransport();
            GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                // Specify the CLIENT_ID of the app that accesses the backend:
                .setAudience(Collections.singletonList("18129417719-rntl1l3s5c6d8soqip7r2nugn48hnc9l.apps.googleusercontent.com"))
                // Or, if multiple clients access the backend:
                //.setAudience(Arrays.asList(CLIENT_ID_1, CLIENT_ID_2, CLIENT_ID_3))
                .build();

            try {
                // (Receive idTokenString by HTTPS POST):
                String id = req.getParameter("idtoken");
                // Verify it:
                GoogleIdToken idToken = verifier.verify(id);
                if (idToken != null) {
                    Payload payload = idToken.getPayload();

                    // Print user identifier:
                    String userId = payload.getSubject();
                    System.out.println("User ID: " + userId);

                    // Get profile information from payload:
                    //String email = payload.getEmail();
                    //boolean emailVerified = payload.getEmailVerified();
                    String name = (String) payload.get("name");
                    //String pictureUrl = (String) payload.get("picture");
                    //String locale = (String) payload.get("locale");
                    //String familyName = (String) payload.get("family_name");
                    //String givenName = (String) payload.get("given_name");

                    // Use or store profile information:
                    UserDao userDao = new DatabaseUserDao(connection);
                    UserService us = new UserService(userDao);
                    User user;
                    if (userDao.findByUserName(name) != null) {
                        user = userDao.findByUserName(name);
                    } else {
                        us.registerUser(name, id);
                        user = userDao.findByUserName(name);
                    }
                    //HttpSession session = req.getSession(true);
                    req.getSession().setAttribute("user", user);
                    sendMessage(resp, HttpServletResponse.SC_OK, user);
                } else {
                    System.out.println("Invalid ID token.");
                }
            } catch (GeneralSecurityException ex) {
                sendMessage(resp, HttpServletResponse.SC_BAD_REQUEST, ex.getMessage());
            }
        } catch (SQLException ex) {
            handleSqlError(resp, ex);
        }
    }
}
