# one tick  project  -- created by jasoncareter@icloud.com

# PROJECT   SUMMARY
# part of Datas
#Analysis
        one database for dates
        one database for accounts
        one JSON file to store reminders

                      type : SQLiteDatabase , Json
        total of Files  : database x2 , JsonFile x1

# SQLiteDatabase  :
OneTickContract :
        cpnyains constant strings  , uri , table entry , content type ( item , list)

OneTickSQLiteHelper :
        a/ implements the interface SQLiteOpenHelper
        b/ database version , name fieds
        c/ @Override  onCreate() , onUpdate() method   |   execute SQLite DSL

OneTickContentProvider :
        a/ one more ids for UriMatcher
        b/ static { } , authority plus table and ids
        c/ initialize the custom *helper in the  boolean onCreate() method  , Cursor query() ,
        int delete() , int update() , Uri insert() , String getType()
