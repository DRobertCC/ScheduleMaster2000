package com.codecool.web.model;

import java.util.Objects;

public class Schedule extends AbstractModel {

    private String scheduleName;
    private int scheduleLength;
    private boolean isPublic;
    private int userId;

    public Schedule(int id, String scheduleName, int scheduleLength, boolean isPublic, int userId) {
        super(id);
        this.scheduleName = scheduleName;
        this.scheduleLength = scheduleLength;
        this.isPublic = isPublic;
        this.userId = userId;
    }


    public String getScheduleName() {
        return scheduleName;
    }

    public int getScheduleLength() {
        return scheduleLength;
    }

    public boolean isPublic() {
        return isPublic;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Schedule schedule = (Schedule) o;
        return userId == schedule.userId &&
            scheduleName.equals(schedule.scheduleName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), scheduleName, userId);
    }
}
