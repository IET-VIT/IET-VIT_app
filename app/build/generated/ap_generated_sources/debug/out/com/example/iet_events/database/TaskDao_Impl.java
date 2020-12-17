package com.example.iet_events.database;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.iet_events.models.Task;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class TaskDao_Impl implements TaskDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfTask;

  private final SharedSQLiteStatement __preparedStmtOfClearTaskDb;

  private final SharedSQLiteStatement __preparedStmtOfUpdateTaskDone;

  public TaskDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfTask = new EntityInsertionAdapter<Task>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `taskDb`(`TaskId`,`primary_key`,`Description`,`Status`) VALUES (?,nullif(?, 0),?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Task value) {
        if (value.TaskId == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.TaskId);
        }
        stmt.bindLong(2, value.getPrimary_key());
        if (value.getDescription() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getDescription());
        }
        if (value.getStatus() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getStatus());
        }
      }
    };
    this.__preparedStmtOfClearTaskDb = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM taskDb";
        return _query;
      }
    };
    this.__preparedStmtOfUpdateTaskDone = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE taskDb SET Status = 'Done' WHERE primary_key = ?";
        return _query;
      }
    };
  }

  @Override
  public void insertTask(Task task) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfTask.insert(task);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void clearTaskDb() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfClearTaskDb.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfClearTaskDb.release(_stmt);
    }
  }

  @Override
  public void updateTaskDone(int key) {
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdateTaskDone.acquire();
    __db.beginTransaction();
    try {
      int _argIndex = 1;
      _stmt.bindLong(_argIndex, key);
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdateTaskDone.release(_stmt);
    }
  }

  @Override
  public List<Task> loadAllTasks() {
    final String _sql = "SELECT * FROM taskDb";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfTaskId = _cursor.getColumnIndexOrThrow("TaskId");
      final int _cursorIndexOfPrimaryKey = _cursor.getColumnIndexOrThrow("primary_key");
      final int _cursorIndexOfDescription = _cursor.getColumnIndexOrThrow("Description");
      final int _cursorIndexOfStatus = _cursor.getColumnIndexOrThrow("Status");
      final List<Task> _result = new ArrayList<Task>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Task _item;
        _item = new Task();
        _item.TaskId = _cursor.getString(_cursorIndexOfTaskId);
        final int _tmpPrimary_key;
        _tmpPrimary_key = _cursor.getInt(_cursorIndexOfPrimaryKey);
        _item.setPrimary_key(_tmpPrimary_key);
        final String _tmpDescription;
        _tmpDescription = _cursor.getString(_cursorIndexOfDescription);
        _item.setDescription(_tmpDescription);
        final String _tmpStatus;
        _tmpStatus = _cursor.getString(_cursorIndexOfStatus);
        _item.setStatus(_tmpStatus);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int countUndoneTasks() {
    final String _sql = "SELECT COUNT(*) FROM taskDb WHERE Status = 'Not Done'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
