package com.codecool.web.dao.database;


import com.codecool.web.dao.LoggerDao;
import com.codecool.web.dao.database.AbstractDao;
import com.codecool.web.model.Log;

import java.io.*;
import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class SimpleLoggerDao extends AbstractDao implements LoggerDao {

    public SimpleLoggerDao(Connection connection) {
        super(connection);
    }

    public List<Log> getLogContent(String path) throws FileNotFoundException, ParseException {
        List<Log> logs = new ArrayList<>();
        String[] properties;
        String username = "";
        Scanner s = new Scanner(new File(path + "schmaster.log"));
        while (s.hasNextLine()) {
            String line = s.nextLine();
            properties = line.split(";");
            if (properties.length == 7) {
                username = properties[6];
            } else if (properties.length == 1) {
                continue;
            }
            logs.add(fetchLog(properties, username));
        }
        return logs.subList(logs.size()-21, logs.size()-1);
    }

    public void appendLogFile(String message) {
        String pattern = "yyyy-MM-dd";
        String dateInString =new SimpleDateFormat(pattern).format(new Date());
        String logFileName = "sm-"+ dateInString +".log";
        try(FileWriter fw = new FileWriter(logFileName, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            out.println(message);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private Log fetchLog(String[] properties, String username) throws ParseException {
        String date = properties[0].substring(0, properties[0].length() - 4);
        String type = properties[1];
        String location = properties[4];
        String msg = properties[5];

        return new Log(username, dateParser(date), type, location, msg);
    }

    private Date dateParser(String stringDate) throws ParseException {
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-d HH:mm:ss");
        return formatter.parse(stringDate);
    }
}
