package com.codecool.web.model;

import java.util.Objects;

public class Task extends AbstractModel {

    private String taskName;
    private String taskContent;
    private int userId;

    public Task(int id, String taskName, String taskContent, int userId) {
        super(id);
        this.taskName = taskName;
        this.taskContent = taskContent;
        this.userId = userId;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskContent() {
        return taskContent;
    }

    public int getUserId() {
        return userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return userId == task.userId &&
            taskName.equals(task.taskName) &&
            taskContent.equals(task.taskContent);
    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, taskContent, userId);
    }
}
