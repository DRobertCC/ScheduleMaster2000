package com.codecool.web.listener;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.sql.DataSource;
import java.sql.Connection;

@WebListener
public final class WebappContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("This method is invoked once when the webapp gets deployed.");
        DataSource dataSource = putDataSourceToServletContext(sce);
        runDatabaseInitScript(dataSource, "/init.sql");
    }

    private DataSource putDataSourceToServletContext(ServletContextEvent sce) {

        try {
            Context initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            DataSource dataSource = (DataSource) envCtx.lookup("jdbc/schedulemaster2000");
            ServletContext servletCtx = sce.getServletContext();
            servletCtx.setAttribute("dataSource", dataSource);
            return dataSource;

        } catch (NamingException ex) {
            ex.printStackTrace();
            throw new IllegalStateException(ex);
        }
    }

    private void runDatabaseInitScript(DataSource dataSource, String resource) {

        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection, new ClassPathResource(resource));
        } catch (Throwable t) {
            t.printStackTrace();
            throw new IllegalStateException(t);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
