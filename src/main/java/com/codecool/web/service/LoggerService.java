package com.codecool.web.service;

import com.codecool.web.model.Log;

import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.List;

public interface LoggerService {
    List<Log> getLogContent(String path) throws FileNotFoundException, ParseException;
    void appendLogFile (String logMessage);
}
