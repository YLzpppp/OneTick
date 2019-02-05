package com.jasoncareter.onetick.mModel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class OneTickDateSQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dateDataBase.db" ;
    private static final int DATABASE_VERSION = 1 ;

    public OneTickDateSQLiteHelper(Context context){
        super(context , DATABASE_NAME , null , DATABASE_VERSION );
    }

    private static final String CREATE_ENTRY_DATE = "CREATE  TABLE "+OneTickContract.TABLE_DATE+" ("
            +OneTickContract.DATE_ENTRY.DATE_ID + " INTEGER" + " PRIMARY KEY AUTOINCREMENT "+","
            +OneTickContract.DATE_ENTRY.DATE_DATETYPE + " TEXT" + " NOT NULL"+","
            +OneTickContract.DATE_ENTRY.DATE__TIME + " TEXT" + " NOT NULL "+","
            +OneTickContract.DATE_ENTRY.DATE_OPTIONAL_DESCRIPTION + " TEXT" + ")" ;

    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + OneTickContract.TABLE_DATE ;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ENTRY_DATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //here we only delete the table
        db.execSQL(DELETE_TABLE );
    }
}
