package com.example.iet_events.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.iet_events.models.Users;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    void insertUser(Users user);

    @Delete
    void deleteUser(Users user);

    @Query("DELETE FROM userDb")
    void clearDb();

    @Query("SELECT * FROM userDb")
    List<Users> loadAllUsers();

    @Query("SELECT * FROM userDb WHERE Role = :role")
    List<Users> loadAllUsersByRole(String role);

    @Query("SELECT FCM_Token FROM userDb WHERE FCM_Token IS NOT null")
    List<String> loadFCMTokens();
}
