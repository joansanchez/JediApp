package joansanchez.jediapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static joansanchez.jediapp.database.MyDataBaseContract.Table1.COLUMN_PASS;
import static joansanchez.jediapp.database.MyDataBaseContract.Table1.COLUMN_USER;

/**
 * Created by JoanPad on 05/07/2017.
 */

public class MyDataBaseHelper extends SQLiteOpenHelper{
    private final String TAG = "MyDataBaseHelper";

    public static final int DATABASE_VERSION = 4;
    public static final String DATABASE_NAME = "MyDataBase.db";

    private static final String SQL_CREATE_TABLE1 =
            "CREATE TABLE " + MyDataBaseContract.Table1.TABLE_NAME + " (" +
                    MyDataBaseContract.Table1._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    MyDataBaseContract.Table1.COLUMN_USER + " TEXT UNIQUE, " + COLUMN_PASS + " TEXT, " +
                    MyDataBaseContract.Table1.COLUMN_PHOTO + " TEXT, " + MyDataBaseContract.Table1.COLUMN_ADRESS + " TEXT )";

    private static final String SQL_DELETE_TABLE1 =
            "DROP TABLE IF EXISTS " + MyDataBaseContract.Table1.TABLE_NAME;

    private static MyDataBaseHelper instance;
    private static SQLiteDatabase writable;
    private static SQLiteDatabase readable;

    //We will use this method instead the default constructor to get a reference.
    //With this we will use all the time the same reference.
    public static MyDataBaseHelper getInstance(Context c){
        if(instance == null){
            instance = new MyDataBaseHelper(c);
            if (writable == null) writable = instance.getWritableDatabase();
            if (readable == null) readable = instance.getReadableDatabase();
        }
        return instance;
    }

    private MyDataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE1);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE1);
        onCreate(db);
    }


    @Override
    public synchronized void close() {
        super.close();
        //Always close the SQLiteDatabase
        instance = null;
        writable.close();
        readable.close();
        Log.v(TAG,"close()");
        writable = readable = null;
    }

    public long createRow(String u, String p) {
        ContentValues values = new ContentValues();
        values.put(MyDataBaseContract.Table1.COLUMN_USER,u);
        values.put(COLUMN_PASS,p);
        long newId = writable.insert(MyDataBaseContract.Table1.TABLE_NAME,null,values);
        return newId;
    }

    public long query(String u, String p) {
        Cursor c;
        c = readable.query(MyDataBaseContract.Table1.TABLE_NAME,
                new String[] {COLUMN_PASS},
                MyDataBaseContract.Table1.COLUMN_USER + " = ? AND " + COLUMN_PASS + " = ?",
                new String[] {u, p},
                null,
                null,
                null);
        long retvalue = -1;
        if (c.moveToFirst()) {
            do {
                Log.v(TAG, "entra");
                retvalue = 1;
            } while (c.moveToNext());
        }
        c.close();

        return retvalue;
    }
    public long existe(String u){
        Cursor c;
        c = readable.query(MyDataBaseContract.Table1.TABLE_NAME,
                new String[] {COLUMN_USER},
                MyDataBaseContract.Table1.COLUMN_USER + " = ? ",
                new String[] {u},
                null,
                null,
                null);
        long retvalue = -1;
        if (c.moveToFirst()) {
            do {
                Log.v(TAG, "entra");
                retvalue = 1;
            } while (c.moveToNext());
        }
        c.close();

        return retvalue;
    }
    public long createRowGoogle(String u) {
        ContentValues values = new ContentValues();
        values.put(MyDataBaseContract.Table1.COLUMN_USER,u);
        long newId = writable.insert(MyDataBaseContract.Table1.TABLE_NAME,null,values);
        return newId;
    }
}




























