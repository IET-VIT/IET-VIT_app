package com.example.iet_events.models;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "userDb")
public class Users extends UserID{

    @PrimaryKey(autoGenerate = true)
    private int primary_key;
    private String Name, Role;

    public Users() {
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getRole() {
        return Role;
    }

    public void setRole(String role) {
        Role = role;
    }

    public int getPrimary_key() {
        return primary_key;
    }

    public void setPrimary_key(int primary_key) {
        this.primary_key = primary_key;
    }
}
