<<<<<<< HEAD
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
=======
# one tick  project  -- created by jasoncareter@icloud.com

# PROJECT   SUMMARY

# one newest feature including the backdrop( the Material Design haven't provided available way to implement this yet)




# part of Datas
# Analysis
        one database for dates
        one database for accounts
        one JSON file to store reminders

                      type : SQLiteDatabase , Json
        total of Files  : database x2 , JsonFile x1

# SQLiteDatabase  :
OneTickContract :
        constant strings  , uri , table entry , content type ( item , list)
OneTickSQLiteHelper :
        a/ implements the interface SQLiteOpenHelper
        b/ database version , name fieds
        c/ @Override  onCreate() , onUpdate() method   |   execute SQLite DSL
OneTickContentProvider :
        a/ one more ids for UriMatcher
        b/ static { } , authority plus table and ids
        c/ initialize the custom *helper in the  boolean onCreate() method  , Cursor query() ,
        int delete() , int update() , Uri insert() , String getType()
>>>>>>> 3b0f08c91245cf075170745a594b3e5083a97ceb
