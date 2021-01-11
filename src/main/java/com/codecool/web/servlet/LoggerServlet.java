package com.codecool.web.servlet;

import com.codecool.web.dao.LoggerDao;
import com.codecool.web.dao.database.SimpleLoggerDao;
import com.codecool.web.model.Log;
import com.codecool.web.service.LoggerService;
import com.codecool.web.service.simple.SimpleLoggerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.List;

import static javax.servlet.http.HttpServletResponse.SC_OK;

@WebServlet("/logger")
public class LoggerServlet extends AbstractServlet {
    private static final Logger logger = LoggerFactory.getLogger(TaskServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try (Connection connection = getConnection(req.getServletContext())) {

            LoggerDao loggerDao = new SimpleLoggerDao(connection);
            LoggerService loggerService = new SimpleLoggerService(loggerDao);

            String path = req.getServletContext().getRealPath("/");
            List<Log> logs = loggerService.getLogContent(path);

            logger.info("Checking Log");
            sendMessage(resp, SC_OK, logs);
        } catch (FileNotFoundException exception) {
            logger.error("Log file not found!", exception);
        } catch (ParseException parseException) {
            logger.error("Date string cannot be parsed! (invalid pattern)!", parseException);
        } catch (SQLException exception) {
            handleSqlError(resp, exception);
            logger.error("SQLException!");
        }
    }
}
