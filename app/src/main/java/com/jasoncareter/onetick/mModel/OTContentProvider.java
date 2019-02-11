package com.jasoncareter.onetick.mModel;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;



public class OTContentProvider extends ContentProvider {

    private final static int Account = 100 ;
    private final static int AccountID = 101 ;

    private static final String LOG_TAG = OTContentProvider.class.getName() ;

    private final static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(OTContract.AUTHORITY , OTContract.TABLE_ACCOUNT, Account );
        uriMatcher.addURI(OTContract.AUTHORITY , OTContract.TABLE_ACCOUNT , AccountID );
    }
    private OTAccountSQLiteHelper onetickDBHelper ;

    @Override
    public boolean onCreate() {
        onetickDBHelper = new OTAccountSQLiteHelper(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri , String[] projection ,String selection ,String[]selectionArgs ,String sortOrder){
        Cursor cursor ;
        SQLiteDatabase database = onetickDBHelper.getReadableDatabase();
        int match = uriMatcher.match(uri) ;
        switch ( match ){
            case Account :
                cursor = database.query(OTContract.TABLE_ACCOUNT ,projection ,selection ,selectionArgs , null ,null ,null);
                break;
            case AccountID :
                selection = OTContract.ACCOUNT_ENTRY.ACCOUNT_ID + "=?";
                selectionArgs = new String[]{ String.valueOf (ContentUris.parseId(uri ) )} ;
                cursor = database.query(OTContract.TABLE_ACCOUNT ,projection ,selection ,selectionArgs ,null ,null ,sortOrder);
                break;
            default:
                throw new IllegalArgumentException("unkown uri format : "+uri );
        }
        return cursor ;
    }

    @Override
    public Uri insert( Uri uri, ContentValues values) {
        int match = uriMatcher.match(uri ) ;
        switch (match ){
            case Account :
                // 传入的URI匹配的是整个表格项，插入操作不能指定ID，因此这里的URI为PATH
                return insertvalues(uri , values);
            default:
                throw new IllegalArgumentException(" cannot asign the specific ID : "+uri);
        }
    }
    private Uri insertvalues (Uri uri ,ContentValues values){
        SQLiteDatabase database = onetickDBHelper.getWritableDatabase() ;
        Long id = database.insert(OTContract.TABLE_ACCOUNT , OTContract.ACCOUNT_ENTRY.ACCOUNT_USERNAME , values);
        if (id == -1){
            Log.e(LOG_TAG, "wrong uri  , check out : "+uri );
            return null ;
        }else {
            return ContentUris.withAppendedId(uri , id);
        }
    }

    @Override
    public int delete( Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase database = onetickDBHelper.getWritableDatabase();
        int match = uriMatcher.match(uri);
        switch (match) {
            case Account:
                return database.delete(OTContract.TABLE_ACCOUNT, selection, selectionArgs);
            case AccountID:
                selection = OTContract.ACCOUNT_ENTRY.ACCOUNT_ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                return database.delete(OTContract.TABLE_ACCOUNT, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }
    }

    @Override
    public int update( Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int mache = uriMatcher.match(uri);
        switch (mache){
            case Account:
                return updateaccount(uri, values, selection, selectionArgs);
            case AccountID:
                selection = OTContract.ACCOUNT_ENTRY.ACCOUNT_ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateaccount(uri, values, selection, selectionArgs);
            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }
    private int updateaccount(Uri uri, ContentValues values, String selection, String[] selectionArgs) {

        if (values.containsKey(OTContract.ACCOUNT_ENTRY.ACCOUNT_USERNAME)) {
            Integer gender = values.getAsInteger(OTContract.ACCOUNT_ENTRY.ACCOUNT_USERNAME);
            if (gender == null ) {
                throw new IllegalArgumentException("requires username");
            }
        }
        if (values.containsKey(OTContract.ACCOUNT_ENTRY.ACCOUNT_PASSWORD)) {
            String password = values.getAsString(OTContract.ACCOUNT_ENTRY.ACCOUNT_PASSWORD);
            if (password == null ) {
                throw new IllegalArgumentException("requires password");
            }
        }
        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = onetickDBHelper.getWritableDatabase();

        return database.update(OTContract.TABLE_ACCOUNT , values, selection, selectionArgs);
    }

    @Override
    public String getType( Uri uri) {
        int match = uriMatcher.match(uri);
        switch (match ){
            case Account :
                return OTContract.ACCOUNT_CONTENT_LIST_TYPE ;
            case AccountID :
                return  OTContract.ACCOUNT_CONTENT_ITEM_TYPE ;
            default:
                throw new IllegalArgumentException( "unknown uri format : "+uri);
        }
    }
}
