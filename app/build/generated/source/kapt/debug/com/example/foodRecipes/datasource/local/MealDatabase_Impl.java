package com.example.foodRecipes.datasource.local;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;

import com.example.foodRecipes.datasource.local.database.MealDao;
import com.example.foodRecipes.datasource.local.database.MealDatabase;

import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.annotation.processing.Generated;

@Generated("androidx.room.RoomProcessor")
@SuppressWarnings({"unchecked", "deprecation"})
public final class MealDatabase_Impl extends MealDatabase {
  private volatile MealDao _mealDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `MealEntity` (`name` TEXT NOT NULL, `category` TEXT NOT NULL, `region` TEXT NOT NULL, `instructions` TEXT NOT NULL, `image` TEXT NOT NULL, `youtubeUrl` TEXT, `sourceUrl` TEXT, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '475249af974d76a32d1340b406eecb21')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `MealEntity`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsMealEntity = new HashMap<String, TableInfo.Column>(8);
        _columnsMealEntity.put("name", new TableInfo.Column("name", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMealEntity.put("category", new TableInfo.Column("category", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMealEntity.put("region", new TableInfo.Column("region", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMealEntity.put("instructions", new TableInfo.Column("instructions", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMealEntity.put("image", new TableInfo.Column("image", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMealEntity.put("youtubeUrl", new TableInfo.Column("youtubeUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMealEntity.put("sourceUrl", new TableInfo.Column("sourceUrl", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsMealEntity.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysMealEntity = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesMealEntity = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoMealEntity = new TableInfo("MealEntity", _columnsMealEntity, _foreignKeysMealEntity, _indicesMealEntity);
        final TableInfo _existingMealEntity = TableInfo.read(_db, "MealEntity");
        if (! _infoMealEntity.equals(_existingMealEntity)) {
          return new RoomOpenHelper.ValidationResult(false, "MealEntity(com.example.foodRecipes.data.local.data.MealEntity).\n"
                  + " Expected:\n" + _infoMealEntity + "\n"
                  + " Found:\n" + _existingMealEntity);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "475249af974d76a32d1340b406eecb21", "eae19c6be27676263d606b872ea6c8ff");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "MealEntity");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `MealEntity`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(MealDao.class, MealDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
  }

  @Override
  public MealDao mealDao() {
    if (_mealDao != null) {
      return _mealDao;
    } else {
      synchronized(this) {
        if(_mealDao == null) {
          _mealDao = new MealDao_Impl(this);
        }
        return _mealDao;
      }
    }
  }
}
