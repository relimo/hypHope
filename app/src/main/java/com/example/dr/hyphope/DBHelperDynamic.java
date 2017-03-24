package com.example.dr.hyphope;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created on 17/07/2016.
 */
public class DBHelperDynamic extends SQLiteOpenHelper {

    private final String TAG = getClass().getSimpleName();
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "learning.db";

    public DBHelperDynamic(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.v(TAG,"DBHelperDynamic constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        //TODO: delere it!!!
//        db.execSQL("DROP TABLE IF EXIST " + ContractDynamic.TABLE_NAME_1);
   //     Log.v("on///create","after drop table");
        //creating the first table - the table which contains data of the types of the events
        db.execSQL(
                "CREATE TABLE " + ContractDynamic.TABLE_NAME_1 + " ( " +
                        ContractDynamic.COLUMN_ID_1+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        ContractDynamic.COLUMN_NAME_1 + " TEXT, UNIQUE (" +ContractDynamic.COLUMN_NAME_1+") " +
                        ");"
        );



        Log.v(TAG, "after create the first table");
        //creating the second table that contains the records
        db.execSQL(
                "CREATE TABLE " + ContractDynamic.TABLE_NAME_2 + " ( " +
                        ContractDynamic.COLUMN_ID_2+ " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        ContractDynamic.COLUMN_HOUR_2 + " INTEGER, " +
                        ContractDynamic.COLUMN_MINUTE_2 + " INTEGER, " +
                        ContractDynamic.COLUMN_DATE_2 + " TEXT, " +
                        ContractDynamic.COLUMN_DAY_IN_WEEK_2 + " TEXT, " +
                        ContractDynamic.COLUMN_EVENT_TYPE_ID_2 + " LONG, " +
                        ContractDynamic.COLUMN_VALUE_2 + " TEXT, " +
                        ContractDynamic.COLUMN_IS_DEVIATION_2 + " INTEGER " +

                        ");"
        );
        Log.v(TAG, "after create the second table");
    }//onCreate
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST " + ContractDynamic.TABLE_NAME_1);
        db.execSQL("DROP TABLE IF EXIST " + ContractDynamic.TABLE_NAME_2);
        onCreate(db);
    }
}