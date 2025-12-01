package com.example.otams;

import android.database.Cursor;
import androidx.annotation.NonNull;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class SessionDao_Impl implements SessionDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<SessionEntity> __insertionAdapterOfSessionEntity;

  private final EntityDeletionOrUpdateAdapter<SessionEntity> __deletionAdapterOfSessionEntity;

  private final EntityDeletionOrUpdateAdapter<SessionEntity> __updateAdapterOfSessionEntity;

  public SessionDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSessionEntity = new EntityInsertionAdapter<SessionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `sessions` (`id`,`studentId`,`tutorId`,`date`,`time`,`courseCode`,`status`,`rating`,`feedback`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final SessionEntity entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.studentId);
        statement.bindLong(3, entity.tutorId);
        if (entity.date == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.date);
        }
        if (entity.time == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.time);
        }
        if (entity.courseCode == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.courseCode);
        }
        if (entity.status == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.status);
        }
        statement.bindDouble(8, entity.rating);
        if (entity.feedback == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.feedback);
        }
      }
    };
    this.__deletionAdapterOfSessionEntity = new EntityDeletionOrUpdateAdapter<SessionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `sessions` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final SessionEntity entity) {
        statement.bindLong(1, entity.id);
      }
    };
    this.__updateAdapterOfSessionEntity = new EntityDeletionOrUpdateAdapter<SessionEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "UPDATE OR ABORT `sessions` SET `id` = ?,`studentId` = ?,`tutorId` = ?,`date` = ?,`time` = ?,`courseCode` = ?,`status` = ?,`rating` = ?,`feedback` = ? WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final SessionEntity entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.studentId);
        statement.bindLong(3, entity.tutorId);
        if (entity.date == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.date);
        }
        if (entity.time == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.time);
        }
        if (entity.courseCode == null) {
          statement.bindNull(6);
        } else {
          statement.bindString(6, entity.courseCode);
        }
        if (entity.status == null) {
          statement.bindNull(7);
        } else {
          statement.bindString(7, entity.status);
        }
        statement.bindDouble(8, entity.rating);
        if (entity.feedback == null) {
          statement.bindNull(9);
        } else {
          statement.bindString(9, entity.feedback);
        }
        statement.bindLong(10, entity.id);
      }
    };
  }

  @Override
  public long insert(final SessionEntity session) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfSessionEntity.insertAndReturnId(session);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final SessionEntity session) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfSessionEntity.handle(session);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void update(final SessionEntity session) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __updateAdapterOfSessionEntity.handle(session);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<SessionEntity> getSessionsForStudent(final int studentId) {
    final String _sql = "SELECT * FROM sessions WHERE studentId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, studentId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
      final int _cursorIndexOfTutorId = CursorUtil.getColumnIndexOrThrow(_cursor, "tutorId");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
      final int _cursorIndexOfCourseCode = CursorUtil.getColumnIndexOrThrow(_cursor, "courseCode");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
      final int _cursorIndexOfFeedback = CursorUtil.getColumnIndexOrThrow(_cursor, "feedback");
      final List<SessionEntity> _result = new ArrayList<SessionEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final SessionEntity _item;
        _item = new SessionEntity();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        _item.studentId = _cursor.getInt(_cursorIndexOfStudentId);
        _item.tutorId = _cursor.getInt(_cursorIndexOfTutorId);
        if (_cursor.isNull(_cursorIndexOfDate)) {
          _item.date = null;
        } else {
          _item.date = _cursor.getString(_cursorIndexOfDate);
        }
        if (_cursor.isNull(_cursorIndexOfTime)) {
          _item.time = null;
        } else {
          _item.time = _cursor.getString(_cursorIndexOfTime);
        }
        if (_cursor.isNull(_cursorIndexOfCourseCode)) {
          _item.courseCode = null;
        } else {
          _item.courseCode = _cursor.getString(_cursorIndexOfCourseCode);
        }
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _item.status = null;
        } else {
          _item.status = _cursor.getString(_cursorIndexOfStatus);
        }
        _item.rating = _cursor.getFloat(_cursorIndexOfRating);
        if (_cursor.isNull(_cursorIndexOfFeedback)) {
          _item.feedback = null;
        } else {
          _item.feedback = _cursor.getString(_cursorIndexOfFeedback);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<SessionEntity> getSessionsForTutor(final int tutorId) {
    final String _sql = "SELECT * FROM sessions WHERE tutorId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, tutorId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
      final int _cursorIndexOfTutorId = CursorUtil.getColumnIndexOrThrow(_cursor, "tutorId");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
      final int _cursorIndexOfCourseCode = CursorUtil.getColumnIndexOrThrow(_cursor, "courseCode");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
      final int _cursorIndexOfFeedback = CursorUtil.getColumnIndexOrThrow(_cursor, "feedback");
      final List<SessionEntity> _result = new ArrayList<SessionEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final SessionEntity _item;
        _item = new SessionEntity();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        _item.studentId = _cursor.getInt(_cursorIndexOfStudentId);
        _item.tutorId = _cursor.getInt(_cursorIndexOfTutorId);
        if (_cursor.isNull(_cursorIndexOfDate)) {
          _item.date = null;
        } else {
          _item.date = _cursor.getString(_cursorIndexOfDate);
        }
        if (_cursor.isNull(_cursorIndexOfTime)) {
          _item.time = null;
        } else {
          _item.time = _cursor.getString(_cursorIndexOfTime);
        }
        if (_cursor.isNull(_cursorIndexOfCourseCode)) {
          _item.courseCode = null;
        } else {
          _item.courseCode = _cursor.getString(_cursorIndexOfCourseCode);
        }
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _item.status = null;
        } else {
          _item.status = _cursor.getString(_cursorIndexOfStatus);
        }
        _item.rating = _cursor.getFloat(_cursorIndexOfRating);
        if (_cursor.isNull(_cursorIndexOfFeedback)) {
          _item.feedback = null;
        } else {
          _item.feedback = _cursor.getString(_cursorIndexOfFeedback);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public SessionEntity getSessionById(final int id) {
    final String _sql = "SELECT * FROM sessions WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, id);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
      final int _cursorIndexOfTutorId = CursorUtil.getColumnIndexOrThrow(_cursor, "tutorId");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
      final int _cursorIndexOfCourseCode = CursorUtil.getColumnIndexOrThrow(_cursor, "courseCode");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
      final int _cursorIndexOfFeedback = CursorUtil.getColumnIndexOrThrow(_cursor, "feedback");
      final SessionEntity _result;
      if (_cursor.moveToFirst()) {
        _result = new SessionEntity();
        _result.id = _cursor.getInt(_cursorIndexOfId);
        _result.studentId = _cursor.getInt(_cursorIndexOfStudentId);
        _result.tutorId = _cursor.getInt(_cursorIndexOfTutorId);
        if (_cursor.isNull(_cursorIndexOfDate)) {
          _result.date = null;
        } else {
          _result.date = _cursor.getString(_cursorIndexOfDate);
        }
        if (_cursor.isNull(_cursorIndexOfTime)) {
          _result.time = null;
        } else {
          _result.time = _cursor.getString(_cursorIndexOfTime);
        }
        if (_cursor.isNull(_cursorIndexOfCourseCode)) {
          _result.courseCode = null;
        } else {
          _result.courseCode = _cursor.getString(_cursorIndexOfCourseCode);
        }
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _result.status = null;
        } else {
          _result.status = _cursor.getString(_cursorIndexOfStatus);
        }
        _result.rating = _cursor.getFloat(_cursorIndexOfRating);
        if (_cursor.isNull(_cursorIndexOfFeedback)) {
          _result.feedback = null;
        } else {
          _result.feedback = _cursor.getString(_cursorIndexOfFeedback);
        }
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<SessionEntity> getAllSessions() {
    final String _sql = "SELECT * FROM sessions";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfStudentId = CursorUtil.getColumnIndexOrThrow(_cursor, "studentId");
      final int _cursorIndexOfTutorId = CursorUtil.getColumnIndexOrThrow(_cursor, "tutorId");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfTime = CursorUtil.getColumnIndexOrThrow(_cursor, "time");
      final int _cursorIndexOfCourseCode = CursorUtil.getColumnIndexOrThrow(_cursor, "courseCode");
      final int _cursorIndexOfStatus = CursorUtil.getColumnIndexOrThrow(_cursor, "status");
      final int _cursorIndexOfRating = CursorUtil.getColumnIndexOrThrow(_cursor, "rating");
      final int _cursorIndexOfFeedback = CursorUtil.getColumnIndexOrThrow(_cursor, "feedback");
      final List<SessionEntity> _result = new ArrayList<SessionEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final SessionEntity _item;
        _item = new SessionEntity();
        _item.id = _cursor.getInt(_cursorIndexOfId);
        _item.studentId = _cursor.getInt(_cursorIndexOfStudentId);
        _item.tutorId = _cursor.getInt(_cursorIndexOfTutorId);
        if (_cursor.isNull(_cursorIndexOfDate)) {
          _item.date = null;
        } else {
          _item.date = _cursor.getString(_cursorIndexOfDate);
        }
        if (_cursor.isNull(_cursorIndexOfTime)) {
          _item.time = null;
        } else {
          _item.time = _cursor.getString(_cursorIndexOfTime);
        }
        if (_cursor.isNull(_cursorIndexOfCourseCode)) {
          _item.courseCode = null;
        } else {
          _item.courseCode = _cursor.getString(_cursorIndexOfCourseCode);
        }
        if (_cursor.isNull(_cursorIndexOfStatus)) {
          _item.status = null;
        } else {
          _item.status = _cursor.getString(_cursorIndexOfStatus);
        }
        _item.rating = _cursor.getFloat(_cursorIndexOfRating);
        if (_cursor.isNull(_cursorIndexOfFeedback)) {
          _item.feedback = null;
        } else {
          _item.feedback = _cursor.getString(_cursorIndexOfFeedback);
        }
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @NonNull
  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
