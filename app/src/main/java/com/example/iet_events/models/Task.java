package com.example.iet_events.models;

public class Task extends TaskId{

    private String Description, Status;

    public Task() {
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }
}
