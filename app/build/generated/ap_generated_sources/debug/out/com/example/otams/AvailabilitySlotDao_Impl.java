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
public final class AvailabilitySlotDao_Impl implements AvailabilitySlotDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<AvailabilitySlotEntity> __insertionAdapterOfAvailabilitySlotEntity;

  private final EntityDeletionOrUpdateAdapter<AvailabilitySlotEntity> __deletionAdapterOfAvailabilitySlotEntity;

  public AvailabilitySlotDao_Impl(@NonNull final RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAvailabilitySlotEntity = new EntityInsertionAdapter<AvailabilitySlotEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "INSERT OR ABORT INTO `availability_slots` (`id`,`tutorId`,`date`,`startTime`,`endTime`,`autoApprove`) VALUES (nullif(?, 0),?,?,?,?,?)";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final AvailabilitySlotEntity entity) {
        statement.bindLong(1, entity.id);
        statement.bindLong(2, entity.tutorId);
        if (entity.date == null) {
          statement.bindNull(3);
        } else {
          statement.bindString(3, entity.date);
        }
        if (entity.startTime == null) {
          statement.bindNull(4);
        } else {
          statement.bindString(4, entity.startTime);
        }
        if (entity.endTime == null) {
          statement.bindNull(5);
        } else {
          statement.bindString(5, entity.endTime);
        }
        final int _tmp = entity.autoApprove ? 1 : 0;
        statement.bindLong(6, _tmp);
      }
    };
    this.__deletionAdapterOfAvailabilitySlotEntity = new EntityDeletionOrUpdateAdapter<AvailabilitySlotEntity>(__db) {
      @Override
      @NonNull
      protected String createQuery() {
        return "DELETE FROM `availability_slots` WHERE `id` = ?";
      }

      @Override
      protected void bind(@NonNull final SupportSQLiteStatement statement,
          final AvailabilitySlotEntity entity) {
        statement.bindLong(1, entity.id);
      }
    };
  }

  @Override
  public long insert(final AvailabilitySlotEntity slot) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      final long _result = __insertionAdapterOfAvailabilitySlotEntity.insertAndReturnId(slot);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void delete(final AvailabilitySlotEntity slot) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __deletionAdapterOfAvailabilitySlotEntity.handle(slot);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<AvailabilitySlotEntity> getSlotsByTutor(final int tutorId) {
    final String _sql = "SELECT * FROM availability_slots WHERE tutorId = ? ORDER BY date, startTime";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, tutorId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTutorId = CursorUtil.getColumnIndexOrThrow(_cursor, "tutorId");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
      final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
      final int _cursorIndexOfAutoApprove = CursorUtil.getColumnIndexOrThrow(_cursor, "autoApprove");
      final List<AvailabilitySlotEntity> _result = new ArrayList<AvailabilitySlotEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final AvailabilitySlotEntity _item;
        final int _tmpTutorId;
        _tmpTutorId = _cursor.getInt(_cursorIndexOfTutorId);
        final String _tmpDate;
        if (_cursor.isNull(_cursorIndexOfDate)) {
          _tmpDate = null;
        } else {
          _tmpDate = _cursor.getString(_cursorIndexOfDate);
        }
        final String _tmpStartTime;
        if (_cursor.isNull(_cursorIndexOfStartTime)) {
          _tmpStartTime = null;
        } else {
          _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
        }
        final String _tmpEndTime;
        if (_cursor.isNull(_cursorIndexOfEndTime)) {
          _tmpEndTime = null;
        } else {
          _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
        }
        final boolean _tmpAutoApprove;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfAutoApprove);
        _tmpAutoApprove = _tmp != 0;
        _item = new AvailabilitySlotEntity(_tmpTutorId,_tmpDate,_tmpStartTime,_tmpEndTime,_tmpAutoApprove);
        _item.id = _cursor.getInt(_cursorIndexOfId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<AvailabilitySlotEntity> getOverlappingSlots(final int tutorId, final String date,
      final String startTime, final String endTime) {
    final String _sql = "SELECT * FROM availability_slots WHERE tutorId = ? AND date = ? AND ((startTime <= ? AND endTime > ?) OR (startTime < ? AND endTime >= ?) OR (startTime >= ? AND endTime <= ?))";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 8);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, tutorId);
    _argIndex = 2;
    if (date == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, date);
    }
    _argIndex = 3;
    if (startTime == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, startTime);
    }
    _argIndex = 4;
    if (startTime == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, startTime);
    }
    _argIndex = 5;
    if (endTime == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, endTime);
    }
    _argIndex = 6;
    if (endTime == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, endTime);
    }
    _argIndex = 7;
    if (startTime == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, startTime);
    }
    _argIndex = 8;
    if (endTime == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, endTime);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTutorId = CursorUtil.getColumnIndexOrThrow(_cursor, "tutorId");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
      final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
      final int _cursorIndexOfAutoApprove = CursorUtil.getColumnIndexOrThrow(_cursor, "autoApprove");
      final List<AvailabilitySlotEntity> _result = new ArrayList<AvailabilitySlotEntity>(_cursor.getCount());
      while (_cursor.moveToNext()) {
        final AvailabilitySlotEntity _item;
        final int _tmpTutorId;
        _tmpTutorId = _cursor.getInt(_cursorIndexOfTutorId);
        final String _tmpDate;
        if (_cursor.isNull(_cursorIndexOfDate)) {
          _tmpDate = null;
        } else {
          _tmpDate = _cursor.getString(_cursorIndexOfDate);
        }
        final String _tmpStartTime;
        if (_cursor.isNull(_cursorIndexOfStartTime)) {
          _tmpStartTime = null;
        } else {
          _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
        }
        final String _tmpEndTime;
        if (_cursor.isNull(_cursorIndexOfEndTime)) {
          _tmpEndTime = null;
        } else {
          _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
        }
        final boolean _tmpAutoApprove;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfAutoApprove);
        _tmpAutoApprove = _tmp != 0;
        _item = new AvailabilitySlotEntity(_tmpTutorId,_tmpDate,_tmpStartTime,_tmpEndTime,_tmpAutoApprove);
        _item.id = _cursor.getInt(_cursorIndexOfId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public AvailabilitySlotEntity getSlotById(final int slotId) {
    final String _sql = "SELECT * FROM availability_slots WHERE id = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, slotId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTutorId = CursorUtil.getColumnIndexOrThrow(_cursor, "tutorId");
      final int _cursorIndexOfDate = CursorUtil.getColumnIndexOrThrow(_cursor, "date");
      final int _cursorIndexOfStartTime = CursorUtil.getColumnIndexOrThrow(_cursor, "startTime");
      final int _cursorIndexOfEndTime = CursorUtil.getColumnIndexOrThrow(_cursor, "endTime");
      final int _cursorIndexOfAutoApprove = CursorUtil.getColumnIndexOrThrow(_cursor, "autoApprove");
      final AvailabilitySlotEntity _result;
      if (_cursor.moveToFirst()) {
        final int _tmpTutorId;
        _tmpTutorId = _cursor.getInt(_cursorIndexOfTutorId);
        final String _tmpDate;
        if (_cursor.isNull(_cursorIndexOfDate)) {
          _tmpDate = null;
        } else {
          _tmpDate = _cursor.getString(_cursorIndexOfDate);
        }
        final String _tmpStartTime;
        if (_cursor.isNull(_cursorIndexOfStartTime)) {
          _tmpStartTime = null;
        } else {
          _tmpStartTime = _cursor.getString(_cursorIndexOfStartTime);
        }
        final String _tmpEndTime;
        if (_cursor.isNull(_cursorIndexOfEndTime)) {
          _tmpEndTime = null;
        } else {
          _tmpEndTime = _cursor.getString(_cursorIndexOfEndTime);
        }
        final boolean _tmpAutoApprove;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfAutoApprove);
        _tmpAutoApprove = _tmp != 0;
        _result = new AvailabilitySlotEntity(_tmpTutorId,_tmpDate,_tmpStartTime,_tmpEndTime,_tmpAutoApprove);
        _result.id = _cursor.getInt(_cursorIndexOfId);
      } else {
        _result = null;
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
