package com.example.iet_events.database;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.iet_events.models.Users;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unchecked")
public final class UserDao_Impl implements UserDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfUsers;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfUsers;

  private final SharedSQLiteStatement __preparedStmtOfClearDb;

  public UserDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfUsers = new EntityInsertionAdapter<Users>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `userDb`(`UserID`,`primary_key`,`Name`,`Role`,`checkBoxTask`) VALUES (?,nullif(?, 0),?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Users value) {
        if (value.UserID == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.UserID);
        }
        stmt.bindLong(2, value.getPrimary_key());
        if (value.getName() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getName());
        }
        if (value.getRole() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getRole());
        }
        final int _tmp;
        _tmp = value.isCheckBoxTask() ? 1 : 0;
        stmt.bindLong(5, _tmp);
      }
    };
    this.__deletionAdapterOfUsers = new EntityDeletionOrUpdateAdapter<Users>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `userDb` WHERE `primary_key` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Users value) {
        stmt.bindLong(1, value.getPrimary_key());
      }
    };
    this.__preparedStmtOfClearDb = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM userDb";
        return _query;
      }
    };
  }

  @Override
  public void insertUser(Users user) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfUsers.insert(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteUser(Users user) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfUsers.handle(user);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void clearDb() {
    final SupportSQLiteStatement _stmt = __preparedStmtOfClearDb.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfClearDb.release(_stmt);
    }
  }

  @Override
  public List<Users> loadAllUsers() {
    final String _sql = "SELECT * FROM userDb";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfUserID = _cursor.getColumnIndexOrThrow("UserID");
      final int _cursorIndexOfPrimaryKey = _cursor.getColumnIndexOrThrow("primary_key");
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("Name");
      final int _cursorIndexOfRole = _cursor.getColumnIndexOrThrow("Role");
      final int _cursorIndexOfCheckBoxTask = _cursor.getColumnIndexOrThrow("checkBoxTask");
      final List<Users> _result = new ArrayList<Users>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Users _item;
        _item = new Users();
        _item.UserID = _cursor.getString(_cursorIndexOfUserID);
        final int _tmpPrimary_key;
        _tmpPrimary_key = _cursor.getInt(_cursorIndexOfPrimaryKey);
        _item.setPrimary_key(_tmpPrimary_key);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _item.setName(_tmpName);
        final String _tmpRole;
        _tmpRole = _cursor.getString(_cursorIndexOfRole);
        _item.setRole(_tmpRole);
        final boolean _tmpCheckBoxTask;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfCheckBoxTask);
        _tmpCheckBoxTask = _tmp != 0;
        _item.setCheckBoxTask(_tmpCheckBoxTask);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Users> loadAllUsersByRole(String role) {
    final String _sql = "SELECT * FROM userDb WHERE Role = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (role == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, role);
    }
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfUserID = _cursor.getColumnIndexOrThrow("UserID");
      final int _cursorIndexOfPrimaryKey = _cursor.getColumnIndexOrThrow("primary_key");
      final int _cursorIndexOfName = _cursor.getColumnIndexOrThrow("Name");
      final int _cursorIndexOfRole = _cursor.getColumnIndexOrThrow("Role");
      final int _cursorIndexOfCheckBoxTask = _cursor.getColumnIndexOrThrow("checkBoxTask");
      final List<Users> _result = new ArrayList<Users>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Users _item;
        _item = new Users();
        _item.UserID = _cursor.getString(_cursorIndexOfUserID);
        final int _tmpPrimary_key;
        _tmpPrimary_key = _cursor.getInt(_cursorIndexOfPrimaryKey);
        _item.setPrimary_key(_tmpPrimary_key);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _item.setName(_tmpName);
        final String _tmpRole;
        _tmpRole = _cursor.getString(_cursorIndexOfRole);
        _item.setRole(_tmpRole);
        final boolean _tmpCheckBoxTask;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfCheckBoxTask);
        _tmpCheckBoxTask = _tmp != 0;
        _item.setCheckBoxTask(_tmpCheckBoxTask);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
