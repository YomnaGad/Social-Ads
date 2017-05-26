package com.example.android.socialads.data.local;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.socialads.utils.GlobalEntities;

/**
 * Created by Yomna on 5/11/2017.
 */

public class DbOpenHelper  extends SQLiteOpenHelper {
    private static DbOpenHelper dbOpenHelper;

    public static final String DATABASE_NAME = "imgSearch.db";
    public static final int DATABASE_VERSION = 1;

    private DbOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    public static DbOpenHelper getInstance(Context context){
        if(dbOpenHelper == null){
            dbOpenHelper = new DbOpenHelper(context);
        }

        return dbOpenHelper;
    }

    public static boolean isNull(){
        return dbOpenHelper==null;
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        Log.i(GlobalEntities.DB_OPEN_HELPER, "Configure database");
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.i(GlobalEntities.DB_OPEN_HELPER, "Creating database");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        Log.i(GlobalEntities.DB_OPEN_HELPER, "Upgrading database");
    }
}
