package com.example.iet_events.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "taskDb")
public class Task extends TaskId{

    @PrimaryKey(autoGenerate = true)
    private int primary_key;
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

    public int getPrimary_key() {
        return primary_key;
    }

    public void setPrimary_key(int primary_key) {
        this.primary_key = primary_key;
    }
}
