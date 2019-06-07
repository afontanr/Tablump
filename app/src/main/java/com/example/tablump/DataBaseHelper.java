package com.example.tablump;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
public class DataBaseHelper extends SQLiteOpenHelper {
    public DataBaseHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    // Called when no database exists in disk and the helper class needs
    // to create a new one.
    @Override
    public void onCreate(SQLiteDatabase _db) {
        try {
            _db.execSQL(TablumpDatabaseAdapter.DATABASE_CREATE_USERS);
            _db.execSQL(TablumpDatabaseAdapter.DATABASE_CREATE_POSTS);
            _db.execSQL(TablumpDatabaseAdapter.DATABASE_CREATE_NOTIFICATIONS);
            _db.execSQL(TablumpDatabaseAdapter.DATABASE_CREATE_LIKES);
            _db.execSQL(TablumpDatabaseAdapter.DATABASE_CREATE_COMENTARIOS);
        }catch(Exception er){
            Log.e("Error","exception");
        }
    }
    // Called when there is a database version mismatch meaning that the version
    // of the database on disk needs to be upgraded to the current version.
    @Override
    public void onUpgrade(SQLiteDatabase _db, int _oldVersion, int _newVersion)
    {
        // Log the version upgrade.
        Log.w("TaskDBAdapter", "Upgrading from version " +_oldVersion + " to " +_newVersion + ", which will destroy all old data");
        // Upgrade the existing database to conform to the new version. Multiple
        // previous versions can be handled by comparing _oldVersion and _newVersion
        // values.
        // The simplest case is to drop the old table and create a new one.
        _db.execSQL("DROP TABLE IF EXISTS " + "USER");
        _db.execSQL("DROP TABLE IF EXISTS " + "POST");
        _db.execSQL("DROP TABLE IF EXISTS " + "NOTIFICATION");
        _db.execSQL("DROP TABLE IF EXISTS " + "LIKEUSER");
        _db.execSQL("DROP TABLE IF EXISTS " + "COMMENTS");

        // Create a new one.
        onCreate(_db);
    }
}