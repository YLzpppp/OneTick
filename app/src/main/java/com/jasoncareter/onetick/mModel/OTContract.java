package com.jasoncareter.onetick.mModel;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class OTContract {
    /**
     *  uris , table entry , content type
     */
    public final static String AUTHORITY = "com.jasoncareter.onetick" ;

    public final static String TABLE_ACCOUNT = "onetick_account";
    public final static String TABLE_DATE = "onetick_date";

    public final static Uri BASE_URI = Uri.parse("content://"+AUTHORITY );

    public final static Uri ACCOUNT_CONTENT_URI = Uri.withAppendedPath( BASE_URI , TABLE_ACCOUNT );
    public final static Uri DATE_CONTENT_URI =Uri.withAppendedPath(BASE_URI , TABLE_DATE );

    // part of @account database
    public final static String ACCOUNT_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" +AUTHORITY +"/" +TABLE_ACCOUNT ;
    public final static String ACCOUNT_CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + TABLE_ACCOUNT ;

    public static class ACCOUNT_ENTRY implements BaseColumns {
        public static final String ACCOUNT_ID = BaseColumns._ID ;
        public static final String ACCOUNT_PLATFORM = "platfrom" ;
        public static final String ACCOUNT_USERNAME ="username";
        public static final String ACCOUNT_PASSWORD = "password" ;
    }
    //part of @date database
    public final static String DATE_CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE +"/" + AUTHORITY + "/" + TABLE_DATE ;
    public final static String DATE_CONTENT_LIST_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + AUTHORITY + "/" + TABLE_DATE ;

    public static class DATE_ENTRY implements BaseColumns {
        public static final String DATE_ID = BaseColumns._ID ;
        public static final String DATE_DATETYPE="datetype";
        public static final String DATE__TIME = "time" ;
        public static final String DATE_OPTIONAL_DESCRIPTION = "optional_description" ;
    }
}
