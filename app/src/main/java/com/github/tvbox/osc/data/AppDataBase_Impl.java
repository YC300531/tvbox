package com.github.tvbox.osc.data;

import androidx.annotation.NonNull;
import androidx.base.c3.b;
import androidx.base.c3.d;
import androidx.base.c3.f;
import androidx.base.c3.h;
import androidx.media3.extractor.text.ttml.TtmlNode;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomMasterTable;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
public final class AppDataBase_Impl extends AppDataBase {
    public static final int a = 0;

    public class a extends RoomOpenHelper.Delegate {
        public a(int i) {
            super(i);
        }

        @Override
        public void createAllTables(SupportSQLiteDatabase supportSQLiteDatabase) {
            supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `cache` (`key` TEXT NOT NULL, `data` BLOB, PRIMARY KEY(`key`))");
            supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `vodRecord` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `vodId` TEXT, `updateTime` INTEGER NOT NULL, `sourceKey` TEXT, `dataJson` TEXT)");
            supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `vodCollect` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `vodId` TEXT, `updateTime` INTEGER NOT NULL, `sourceKey` TEXT, `name` TEXT, `pic` TEXT)");
            supportSQLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS `storageDrive` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `type` INTEGER NOT NULL, `configJson` TEXT)");
            supportSQLiteDatabase.execSQL(RoomMasterTable.CREATE_QUERY);
            supportSQLiteDatabase.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'd2470d2304493a5cf8987b181ba1e601')");
        }

        @Override
        public void dropAllTables(SupportSQLiteDatabase supportSQLiteDatabase) {
            supportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `cache`");
            supportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `vodRecord`");
            supportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `vodCollect`");
            supportSQLiteDatabase.execSQL("DROP TABLE IF EXISTS `storageDrive`");
            AppDataBase_Impl appDataBase_Impl = AppDataBase_Impl.this;
            int i = AppDataBase_Impl.a;
            List<? extends RoomDatabase.Callback> list = appDataBase_Impl.mCallbacks;
            if (list != null) {
                int size = list.size();
                for (int i2 = 0; i2 < size; i2++) {
                    mCallbacks.get(i2).onDestructiveMigration(supportSQLiteDatabase);
                }
            }
        }

        @Override
        public void onCreate(SupportSQLiteDatabase supportSQLiteDatabase) {
            AppDataBase_Impl appDataBase_Impl = AppDataBase_Impl.this;
            int i = AppDataBase_Impl.a;
            List<? extends RoomDatabase.Callback> list = appDataBase_Impl.mCallbacks;
            if (list != null) {
                int size = list.size();
                for (int i2 = 0; i2 < size; i2++) {
                    mCallbacks.get(i2).onCreate(supportSQLiteDatabase);
                }
            }
        }

        @Override
        public void onOpen(SupportSQLiteDatabase supportSQLiteDatabase) {
            AppDataBase_Impl appDataBase_Impl = AppDataBase_Impl.this;
            int i = AppDataBase_Impl.a;
            appDataBase_Impl.mDatabase = supportSQLiteDatabase;
            internalInitInvalidationTracker(supportSQLiteDatabase);
            List<? extends RoomDatabase.Callback> list = mCallbacks;
            if (list != null) {
                int size = list.size();
                for (int i2 = 0; i2 < size; i2++) {
                    mCallbacks.get(i2).onOpen(supportSQLiteDatabase);
                }
            }
        }

        @Override
        public void onPostMigrate(SupportSQLiteDatabase supportSQLiteDatabase) {
        }

        @Override
        public void onPreMigrate(SupportSQLiteDatabase supportSQLiteDatabase) {
            DBUtil.dropFtsSyncTriggers(supportSQLiteDatabase);
        }

        @Override
        public RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase supportSQLiteDatabase) {
            HashMap hashMap = new HashMap(2);
            hashMap.put(androidx.base.a4.a.KEY, new TableInfo.Column(androidx.base.a4.a.KEY, "TEXT", true, 1, null, 1));
            hashMap.put("data", new TableInfo.Column("data", "BLOB", false, 0, null, 1));
            TableInfo tableInfo = new TableInfo("cache", hashMap, new HashSet(0), new HashSet(0));
            TableInfo read = TableInfo.read(supportSQLiteDatabase, "cache");
            if (!tableInfo.equals(read)) {
                return new RoomOpenHelper.ValidationResult(false, "cache(com.github.tvbox.osc.cache.Cache).\n Expected:\n" + tableInfo + "\n Found:\n" + read);
            }
            HashMap hashMap2 = new HashMap(5);
            hashMap2.put(TtmlNode.ATTR_ID, new TableInfo.Column(TtmlNode.ATTR_ID, "INTEGER", true, 1, null, 1));
            hashMap2.put("vodId", new TableInfo.Column("vodId", "TEXT", false, 0, null, 1));
            hashMap2.put("updateTime", new TableInfo.Column("updateTime", "INTEGER", true, 0, null, 1));
            hashMap2.put("sourceKey", new TableInfo.Column("sourceKey", "TEXT", false, 0, null, 1));
            hashMap2.put("dataJson", new TableInfo.Column("dataJson", "TEXT", false, 0, null, 1));
            TableInfo tableInfo2 = new TableInfo("vodRecord", hashMap2, new HashSet(0), new HashSet(0));
            TableInfo read2 = TableInfo.read(supportSQLiteDatabase, "vodRecord");
            if (!tableInfo2.equals(read2)) {
                return new RoomOpenHelper.ValidationResult(false, "vodRecord(com.github.tvbox.osc.cache.VodRecord).\n Expected:\n" + tableInfo2 + "\n Found:\n" + read2);
            }
            HashMap hashMap3 = new HashMap(6);
            hashMap3.put(TtmlNode.ATTR_ID, new TableInfo.Column(TtmlNode.ATTR_ID, "INTEGER", true, 1, null, 1));
            hashMap3.put("vodId", new TableInfo.Column("vodId", "TEXT", false, 0, null, 1));
            hashMap3.put("updateTime", new TableInfo.Column("updateTime", "INTEGER", true, 0, null, 1));
            hashMap3.put("sourceKey", new TableInfo.Column("sourceKey", "TEXT", false, 0, null, 1));
            hashMap3.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, 1));
            hashMap3.put("pic", new TableInfo.Column("pic", "TEXT", false, 0, null, 1));
            TableInfo tableInfo3 = new TableInfo("vodCollect", hashMap3, new HashSet(0), new HashSet(0));
            TableInfo read3 = TableInfo.read(supportSQLiteDatabase, "vodCollect");
            if (!tableInfo3.equals(read3)) {
                return new RoomOpenHelper.ValidationResult(false, "vodCollect(com.github.tvbox.osc.cache.VodCollect).\n Expected:\n" + tableInfo3 + "\n Found:\n" + read3);
            }
            HashMap hashMap4 = new HashMap(4);
            hashMap4.put(TtmlNode.ATTR_ID, new TableInfo.Column(TtmlNode.ATTR_ID, "INTEGER", true, 1, null, 1));
            hashMap4.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, 1));
            hashMap4.put("type", new TableInfo.Column("type", "INTEGER", true, 0, null, 1));
            hashMap4.put("configJson", new TableInfo.Column("configJson", "TEXT", false, 0, null, 1));
            TableInfo tableInfo4 = new TableInfo("storageDrive", hashMap4, new HashSet(0), new HashSet(0));
            TableInfo read4 = TableInfo.read(supportSQLiteDatabase, "storageDrive");
            if (tableInfo4.equals(read4)) {
                return new RoomOpenHelper.ValidationResult(true, null);
            }
            return new RoomOpenHelper.ValidationResult(false, "storageDrive(com.github.tvbox.osc.cache.StorageDrive).\n Expected:\n" + tableInfo4 + "\n Found:\n" + read4);
        }
    }

    @Override
    public void clearAllTables() {
        super.assertNotMainThread();
        SupportSQLiteDatabase writableDatabase = super.getOpenHelper().getWritableDatabase();
        try {
            super.beginTransaction();
            writableDatabase.execSQL("DELETE FROM `cache`");
            writableDatabase.execSQL("DELETE FROM `vodRecord`");
            writableDatabase.execSQL("DELETE FROM `vodCollect`");
            writableDatabase.execSQL("DELETE FROM `storageDrive`");
            super.setTransactionSuccessful();
        } finally {
            super.endTransaction();
            writableDatabase.query("PRAGMA wal_checkpoint(FULL)").close();
            if (!writableDatabase.inTransaction()) {
                writableDatabase.execSQL("VACUUM");
            }
        }
    }

    @Override
    public InvalidationTracker createInvalidationTracker() {
        return new InvalidationTracker(this, new HashMap(0), new HashMap(0), "cache", "vodRecord", "vodCollect", "storageDrive");
    }

    @Override
    public SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration databaseConfiguration) {
        return databaseConfiguration.sqliteOpenHelperFactory.create(SupportSQLiteOpenHelper.Configuration.builder(databaseConfiguration.context).name(databaseConfiguration.name).callback(new RoomOpenHelper(databaseConfiguration, new a(2), "d2470d2304493a5cf8987b181ba1e601", "812d3710c749fe4fb0e209f273257690")).build());
    }

    @Override
    public List<Migration> getAutoMigrations(@NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> map) {
        return Arrays.asList(new Migration[0]);
    }

    @Override
    public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
        return new HashSet();
    }

    @Override
    public Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
        HashMap hashMap = new HashMap();
        hashMap.put(b.class, Collections.emptyList());
        hashMap.put(h.class, Collections.emptyList());
        hashMap.put(f.class, Collections.emptyList());
        hashMap.put(d.class, Collections.emptyList());
        return hashMap;
    }
}
