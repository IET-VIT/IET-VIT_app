package com.example.iet_events.models;

import androidx.annotation.NonNull;

import com.google.firebase.database.Exclude;

public class UserID {

    @Exclude
    public String UserID;

    public <T extends UserID> T withID (@NonNull final String id){
        this.UserID = id;
        return (T) this;
    }
}
