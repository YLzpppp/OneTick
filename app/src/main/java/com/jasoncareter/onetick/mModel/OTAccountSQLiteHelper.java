package com.jasoncareter.onetick.mModel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class OTAccountSQLiteHelper extends SQLiteOpenHelper {

    private final static String DATABASE_NAME = "accountDataBase.db" ;
    private final static int DATABASE_VERSION = 1 ;

    public OTAccountSQLiteHelper(Context context){
        super(context , DATABASE_NAME , null , DATABASE_VERSION );
    }

    private static final String CREATE_ENTRY_ACCOUNT = "CREATE  TABLE "+ OTContract.TABLE_ACCOUNT +" ("
            + OTContract.ACCOUNT_ENTRY.ACCOUNT_ID + " INTEGER" + " PRIMARY KEY AUTOINCREMENT "+","
            + OTContract.ACCOUNT_ENTRY.ACCOUNT_PLATFORM + " TEXT" + ","
            + OTContract.ACCOUNT_ENTRY.ACCOUNT_USERNAME + " TEXT" + ","
            + OTContract.ACCOUNT_ENTRY.ACCOUNT_PASSWORD + " TEXT" + ")" ;

    private static final String DELETE_TABLE = "DROP TABLE IF EXISTS " + OTContract.TABLE_ACCOUNT ;

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ENTRY_ACCOUNT );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL( DELETE_TABLE );
    }
}
