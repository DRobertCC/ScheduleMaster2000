package com.codecool.web.dto;

import com.codecool.web.model.Schedule;

import java.util.List;

public class ScheduleDto {

    private List<Schedule> mySchedules;
    private List<Schedule> publicSchedules;
    private List<Schedule> allSchedules;

    public ScheduleDto(List<Schedule> mySchedules, List<Schedule> publicSchedules, List<Schedule> allSchedules) {
        this.mySchedules = mySchedules;
        this.publicSchedules = publicSchedules;
        this.allSchedules = allSchedules;
    }


    public List<Schedule> getMySchedules() {
        return mySchedules;
    }

    public List<Schedule> getPublicSchedules() {
        return publicSchedules;
    }

    public List<Schedule> getAllSchedules() {
        return allSchedules;
    }
}
