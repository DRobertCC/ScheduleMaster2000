package com.codecool.web.dao;

import com.codecool.web.model.Log;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;

public interface LoggerDao {
    List<Log> getLogContent(String path) throws FileNotFoundException, ParseException;
    void appendLogFile(String logMessage);
}
