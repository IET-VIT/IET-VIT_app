package com.example.iet_events.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.iet_events.models.Task;

import java.util.List;

@Dao
public interface TaskDao {

    @Insert
    void insertTask(Task task);

    @Query("DELETE FROM taskDb")
    void clearTaskDb();

    @Query("SELECT * FROM taskDb")
    List<Task> loadAllTasks();

    @Query("SELECT COUNT(*) FROM taskDb WHERE Status = 'Not Done'")
    int countUndoneTasks();

    @Query("UPDATE taskDb SET Status = 'Done' WHERE primary_key = :key")
    void updateTaskDone(int key);
}
