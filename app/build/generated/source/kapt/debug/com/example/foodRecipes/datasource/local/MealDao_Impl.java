package com.example.foodRecipes.datasource.local;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.CoroutinesRoom;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.example.foodRecipes.datasource.local.data.MealEntity;
import com.example.foodRecipes.datasource.local.database.MealDao;

import java.lang.Class;
import java.lang.Exception;
import java.lang.Object;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import javax.annotation.processing.Generated;
import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class MealDao_Impl implements MealDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<MealEntity> __insertionAdapterOfMealEntity;

  private final EntityDeletionOrUpdateAdapter<MealEntity> __deletionAdapterOfMealEntity;

  public MealDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfMealEntity = new EntityInsertionAdapter<MealEntity>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `MealEntity` (`name`,`category`,`region`,`instructions`,`image`,`youtubeUrl`,`sourceUrl`,`id`) VALUES (?,?,?,?,?,?,?,nullif(?, 0))";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MealEntity value) {
        if (value.getName() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getName());
        }
        if (value.getCategory() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getCategory());
        }
        if (value.getRegion() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getRegion());
        }
        if (value.getInstructions() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getInstructions());
        }
        if (value.getImage() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getImage());
        }
        if (value.getYoutubeUrl() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getYoutubeUrl());
        }
        if (value.getSourceUrl() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getSourceUrl());
        }
        stmt.bindLong(8, value.getId());
      }
    };
    this.__deletionAdapterOfMealEntity = new EntityDeletionOrUpdateAdapter<MealEntity>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `MealEntity` WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, MealEntity value) {
        stmt.bindLong(1, value.getId());
      }
    };
  }

  @Override
  public Object insertMeal(final MealEntity meal, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __insertionAdapterOfMealEntity.insert(meal);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public Object deleteMeal(final MealEntity meal, final Continuation<? super Unit> continuation) {
    return CoroutinesRoom.execute(__db, true, new Callable<Unit>() {
      @Override
      public Unit call() throws Exception {
        __db.beginTransaction();
        try {
          __deletionAdapterOfMealEntity.handle(meal);
          __db.setTransactionSuccessful();
          return Unit.INSTANCE;
        } finally {
          __db.endTransaction();
        }
      }
    }, continuation);
  }

  @Override
  public LiveData<List<MealEntity>> getAllMeals() {
    final String _sql = "SELECT * FROM MealEntity ORDER BY name";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return __db.getInvalidationTracker().createLiveData(new String[]{"MealEntity"}, false, new Callable<List<MealEntity>>() {
      @Override
      public List<MealEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfRegion = CursorUtil.getColumnIndexOrThrow(_cursor, "region");
          final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
          final int _cursorIndexOfImage = CursorUtil.getColumnIndexOrThrow(_cursor, "image");
          final int _cursorIndexOfYoutubeUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "youtubeUrl");
          final int _cursorIndexOfSourceUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceUrl");
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final List<MealEntity> _result = new ArrayList<MealEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final MealEntity _item;
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final String _tmpRegion;
            if (_cursor.isNull(_cursorIndexOfRegion)) {
              _tmpRegion = null;
            } else {
              _tmpRegion = _cursor.getString(_cursorIndexOfRegion);
            }
            final String _tmpInstructions;
            if (_cursor.isNull(_cursorIndexOfInstructions)) {
              _tmpInstructions = null;
            } else {
              _tmpInstructions = _cursor.getString(_cursorIndexOfInstructions);
            }
            final String _tmpImage;
            if (_cursor.isNull(_cursorIndexOfImage)) {
              _tmpImage = null;
            } else {
              _tmpImage = _cursor.getString(_cursorIndexOfImage);
            }
            final String _tmpYoutubeUrl;
            if (_cursor.isNull(_cursorIndexOfYoutubeUrl)) {
              _tmpYoutubeUrl = null;
            } else {
              _tmpYoutubeUrl = _cursor.getString(_cursorIndexOfYoutubeUrl);
            }
            final String _tmpSourceUrl;
            if (_cursor.isNull(_cursorIndexOfSourceUrl)) {
              _tmpSourceUrl = null;
            } else {
              _tmpSourceUrl = _cursor.getString(_cursorIndexOfSourceUrl);
            }
            _item = new MealEntity(_tmpName,_tmpCategory,_tmpRegion,_tmpInstructions,_tmpImage,_tmpYoutubeUrl,_tmpSourceUrl);
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  @Override
  public LiveData<List<MealEntity>> getMealsByName(final String search) {
    final String _sql = "SELECT * FROM MealEntity WHERE name LIKE '%' || ? || '%' ORDER BY name";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (search == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, search);
    }
    return __db.getInvalidationTracker().createLiveData(new String[]{"MealEntity"}, false, new Callable<List<MealEntity>>() {
      @Override
      public List<MealEntity> call() throws Exception {
        final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
        try {
          final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
          final int _cursorIndexOfCategory = CursorUtil.getColumnIndexOrThrow(_cursor, "category");
          final int _cursorIndexOfRegion = CursorUtil.getColumnIndexOrThrow(_cursor, "region");
          final int _cursorIndexOfInstructions = CursorUtil.getColumnIndexOrThrow(_cursor, "instructions");
          final int _cursorIndexOfImage = CursorUtil.getColumnIndexOrThrow(_cursor, "image");
          final int _cursorIndexOfYoutubeUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "youtubeUrl");
          final int _cursorIndexOfSourceUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "sourceUrl");
          final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
          final List<MealEntity> _result = new ArrayList<MealEntity>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final MealEntity _item;
            final String _tmpName;
            if (_cursor.isNull(_cursorIndexOfName)) {
              _tmpName = null;
            } else {
              _tmpName = _cursor.getString(_cursorIndexOfName);
            }
            final String _tmpCategory;
            if (_cursor.isNull(_cursorIndexOfCategory)) {
              _tmpCategory = null;
            } else {
              _tmpCategory = _cursor.getString(_cursorIndexOfCategory);
            }
            final String _tmpRegion;
            if (_cursor.isNull(_cursorIndexOfRegion)) {
              _tmpRegion = null;
            } else {
              _tmpRegion = _cursor.getString(_cursorIndexOfRegion);
            }
            final String _tmpInstructions;
            if (_cursor.isNull(_cursorIndexOfInstructions)) {
              _tmpInstructions = null;
            } else {
              _tmpInstructions = _cursor.getString(_cursorIndexOfInstructions);
            }
            final String _tmpImage;
            if (_cursor.isNull(_cursorIndexOfImage)) {
              _tmpImage = null;
            } else {
              _tmpImage = _cursor.getString(_cursorIndexOfImage);
            }
            final String _tmpYoutubeUrl;
            if (_cursor.isNull(_cursorIndexOfYoutubeUrl)) {
              _tmpYoutubeUrl = null;
            } else {
              _tmpYoutubeUrl = _cursor.getString(_cursorIndexOfYoutubeUrl);
            }
            final String _tmpSourceUrl;
            if (_cursor.isNull(_cursorIndexOfSourceUrl)) {
              _tmpSourceUrl = null;
            } else {
              _tmpSourceUrl = _cursor.getString(_cursorIndexOfSourceUrl);
            }
            _item = new MealEntity(_tmpName,_tmpCategory,_tmpRegion,_tmpInstructions,_tmpImage,_tmpYoutubeUrl,_tmpSourceUrl);
            final int _tmpId;
            _tmpId = _cursor.getInt(_cursorIndexOfId);
            _item.setId(_tmpId);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    });
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}
