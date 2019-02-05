package com.jasoncareter.onetick.mModel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class OneTickAccountSQLiteHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "accountDataBase.db" ;
    private final static int DATABASE_VERSION = 1 ;

    public OneTickAccountSQLiteHelper(Context context){
        super(context , DATABASE_NAME , null , DATABASE_VERSION );
    }

    private static final String CREATE_ENTRY_ACCOUNT = "CREATE  TABLE "+OneTickContract.TABLE_ACCOUNT +" ("
            +OneTickContract.ACCOUNT_ENTRY.ACCOUNT_ID + " INTEGER" + " PRIMARY KEY AUTOINCREMENT "+","
            +OneTickContract.ACCOUNT_ENTRY.ACCOUNT_PLATFORM + " TEXT" + ","
            +OneTickContract.ACCOUNT_ENTRY.ACCOUNT_USERNAME + " TEXT" + ","
            +OneTickContract.ACCOUNT_ENTRY.ACCOUNT_PASSWORD + " TEXT" + ")" ;

    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + OneTickContract.TABLE_ACCOUNT ;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ENTRY_ACCOUNT );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( DELETE_TABLE );
    }
}
