package com.example.iet_events.models;

public class Meeting {

    private String Date, Description, Time, Location_Link;

    public Meeting() {
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getTime() {
        return Time;
    }

    public void setTime(String time) {
        Time = time;
    }

    public String getLocation_Link() {
        return Location_Link;
    }

    public void setLocation_Link(String location_Link) {
        Location_Link = location_Link;
    }
}
