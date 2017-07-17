package joansanchez.jediapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

import joansanchez.jediapp.Contact;

import static joansanchez.jediapp.database.MyDataBaseContract.Table1.COLUMN_ADRESS;
import static joansanchez.jediapp.database.MyDataBaseContract.Table1.COLUMN_BEST;
import static joansanchez.jediapp.database.MyDataBaseContract.Table1.COLUMN_CITY;
import static joansanchez.jediapp.database.MyDataBaseContract.Table1.COLUMN_NOTI;
import static joansanchez.jediapp.database.MyDataBaseContract.Table1.COLUMN_PASS;
import static joansanchez.jediapp.database.MyDataBaseContract.Table1.COLUMN_PHOTO;
import static joansanchez.jediapp.database.MyDataBaseContract.Table1.COLUMN_TIPONOTI;
import static joansanchez.jediapp.database.MyDataBaseContract.Table1.COLUMN_USER;

/**
 * Created by JoanPad on 05/07/2017.
 */

public class MyDataBaseHelper extends SQLiteOpenHelper{
    private final String TAG = "MyDataBaseHelper";

    public static final int DATABASE_VERSION = 9;
    public static final String DATABASE_NAME = "MyDataBase.db";

    private static final String SQL_CREATE_TABLE1 =
            "CREATE TABLE " + MyDataBaseContract.Table1.TABLE_NAME + " (" +
                    MyDataBaseContract.Table1._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    MyDataBaseContract.Table1.COLUMN_USER + " TEXT UNIQUE, " + MyDataBaseContract.Table1.COLUMN_PASS + " TEXT, " +
                    MyDataBaseContract.Table1.COLUMN_PHOTO + " TEXT, " +  MyDataBaseContract.Table1.COLUMN_ADRESS + " TEXT, " + MyDataBaseContract.Table1.COLUMN_TIPONOTI + " TEXT, " +
                    MyDataBaseContract.Table1.COLUMN_CITY + " TEXT, " + MyDataBaseContract.Table1.COLUMN_BEST + " TEXT, " +
                    MyDataBaseContract.Table1.COLUMN_NOTI + " TEXT )";

    private static final String SQL_CREATE_TABLE2 =
            "CREATE TABLE " + MyDataBaseContract.Table2.TABLE_NAME1 + " (" +
                    MyDataBaseContract.Table2._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                    MyDataBaseContract.Table2.COLUMN_USER1 + " TEXT, "  + MyDataBaseContract.Table2.COLUMN_BEST1 + " TEXT )";

    private static final String SQL_DELETE_TABLE1 =
            "DROP TABLE IF EXISTS " + MyDataBaseContract.Table1.TABLE_NAME;

    private static final String SQL_DELETE_TABLE2 =
            "DROP TABLE IF EXISTS " + MyDataBaseContract.Table2.TABLE_NAME1;

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
        db.execSQL(SQL_CREATE_TABLE2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_TABLE1);
        db.execSQL(SQL_DELETE_TABLE2);
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
        values.put(MyDataBaseContract.Table1.COLUMN_PASS,p);
        String aux = "dirección sin determinar";
        values.put(MyDataBaseContract.Table1.COLUMN_ADRESS, aux);
        aux = "ciudad sin determinar";
        values.put(MyDataBaseContract.Table1.COLUMN_CITY, aux);
        values.put(MyDataBaseContract.Table1.COLUMN_BEST, "no games");
        values.put(MyDataBaseContract.Table1.COLUMN_PHOTO, "no photo");
        values.put(MyDataBaseContract.Table1.COLUMN_TIPONOTI, "toast");
        values.put(MyDataBaseContract.Table1.COLUMN_NOTI, "sin notificaciones");
        long newId = writable.insert(MyDataBaseContract.Table1.TABLE_NAME,null,values);
        return newId;
    }

    public long insertarpuntuacion(String u, String points) {
        ContentValues values = new ContentValues();
        values.put(MyDataBaseContract.Table2.COLUMN_USER1,u);
        values.put(MyDataBaseContract.Table2.COLUMN_BEST1, points);
        long newId = writable.insert(MyDataBaseContract.Table2.TABLE_NAME1,null,values);
        return newId;
    }

    public long query(String u, String p) {
        Cursor c;
        c = readable.query(MyDataBaseContract.Table1.TABLE_NAME,
                new String[] {COLUMN_PASS},
                MyDataBaseContract.Table1.COLUMN_USER + " = ? AND " + MyDataBaseContract.Table1.COLUMN_PASS + " = ?",
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
    public static long existe(String u){
        Cursor c;
        String TAG = "MyDataBaseHelper";
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
    public static long createRowGoogle(String u) {
        ContentValues values = new ContentValues();
        values.put(MyDataBaseContract.Table1.COLUMN_USER,u);
        String aux = "dirección sin determinar";
        values.put(MyDataBaseContract.Table1.COLUMN_ADRESS, aux);
        aux = "ciudad sin determinar";
        values.put(MyDataBaseContract.Table1.COLUMN_CITY, aux);
        values.put(MyDataBaseContract.Table1.COLUMN_BEST, "no games");
        values.put(MyDataBaseContract.Table1.COLUMN_PHOTO, "no photo");
        values.put(MyDataBaseContract.Table1.COLUMN_TIPONOTI, "toast");
        values.put(MyDataBaseContract.Table1.COLUMN_NOTI, "sin notificaciones");
        long newId = writable.insert(MyDataBaseContract.Table1.TABLE_NAME,null,values);
        return newId;
    }

    public String direccion(String nomuse) {
        Cursor c;
        String TAG = "MyDataBaseHelper";
        c = readable.query(MyDataBaseContract.Table1.TABLE_NAME,
                new String[] {COLUMN_ADRESS},
                MyDataBaseContract.Table1.COLUMN_USER + " = ? ",
                new String[] {nomuse},
                null,
                null,
                null);
        String retvalue = "nada";
        if (c.moveToFirst()) {
            do {
                Log.v(TAG, "entra");
                retvalue = c.getString(c.getColumnIndex(MyDataBaseContract.Table1.COLUMN_ADRESS));
            } while (c.moveToNext());
        }
        c.close();

        return retvalue;
    }

    public String ciudad(String nomuse) {
        Cursor c;
        String TAG = "MyDataBaseHelper";
        c = readable.query(MyDataBaseContract.Table1.TABLE_NAME,
                new String[] {COLUMN_CITY},
                MyDataBaseContract.Table1.COLUMN_USER + " = ? ",
                new String[] {nomuse},
                null,
                null,
                null);
        String retvalue = "nada";
        if (c.moveToFirst()) {
            do {
                Log.v(TAG, "entra");
                retvalue = c.getString(c.getColumnIndex(MyDataBaseContract.Table1.COLUMN_CITY));
            } while (c.moveToNext());
        }
        c.close();

        return retvalue;
    }

    public String bestpoints(String nomuse) {
        Cursor c;
        String TAG = "MyDataBaseHelper";
        c = readable.query(MyDataBaseContract.Table1.TABLE_NAME,
                new String[] {COLUMN_BEST},
                MyDataBaseContract.Table1.COLUMN_USER + " = ? ",
                new String[] {nomuse},
                null,
                null,
                null);
        String retvalue = "sin partidas jugadas";
        if (c.moveToFirst()) {
            do {
                Log.v(TAG, "entra");
                retvalue = c.getString(c.getColumnIndex(MyDataBaseContract.Table1.COLUMN_BEST));
            } while (c.moveToNext());
        }
        c.close();

        return retvalue;
    }

    public String lastnotifi(String nomuse) {
        Cursor c;
        String TAG = "MyDataBaseHelper";
        c = readable.query(MyDataBaseContract.Table1.TABLE_NAME,
                new String[] {COLUMN_NOTI},
                MyDataBaseContract.Table1.COLUMN_USER + " = ? ",
                new String[] {nomuse},
                null,
                null,
                null);
        String retvalue = "0";
        if (c.moveToFirst()) {
            do {
                Log.v(TAG, "entra");
                retvalue = c.getString(c.getColumnIndex(MyDataBaseContract.Table1.COLUMN_NOTI));
            } while (c.moveToNext());
        }
        c.close();

        return retvalue;
    }

    public void updatenoti(String noti, String user) {
        ContentValues values = new ContentValues();
        values.put(MyDataBaseContract.Table1.COLUMN_NOTI, noti);
        int rows_afected = readable.update(MyDataBaseContract.Table1.TABLE_NAME,    //Table name
                values,                                                             //New value for columns
                MyDataBaseContract.Table1.COLUMN_USER + " LIKE ? ",                 //Selection args
                new String[] {user});                                                  //Selection values


    }

    public String tipodenoti(String user) {
        Cursor c;
        String TAG = "MyDataBaseHelper";
        c = readable.query(MyDataBaseContract.Table1.TABLE_NAME,
                new String[] {COLUMN_TIPONOTI},
                MyDataBaseContract.Table1.COLUMN_USER + " = ? ",
                new String[] {user},
                null,
                null,
                null);
        String retvalue = "toast";
        if (c.moveToFirst()) {
            do {
                Log.v(TAG, "entra");
                retvalue = c.getString(c.getColumnIndex(MyDataBaseContract.Table1.COLUMN_TIPONOTI));
            } while (c.moveToNext());
        }
        c.close();

        return retvalue;
    }
    public String getphoto(String nomuser){
        Cursor c;
        String TAG = "MyDataBaseHelper";
        c = readable.query(MyDataBaseContract.Table1.TABLE_NAME,
                new String[] {COLUMN_PHOTO},
                MyDataBaseContract.Table1.COLUMN_USER + " = ? ",
                new String[] {nomuser},
                null,
                null,
                null);
        String retvalue = "no photo";
        if (c.moveToFirst()) {
            do {
                Log.v(TAG, "entra");
                retvalue = c.getString(c.getColumnIndex(MyDataBaseContract.Table1.COLUMN_PHOTO));
            } while (c.moveToNext());
        }
        c.close();

        return retvalue;
    }
    public String getpoints(String nomuser){
        Cursor c;
        String TAG = "MyDataBaseHelper";
        c = readable.query(MyDataBaseContract.Table1.TABLE_NAME,
                new String[] {COLUMN_BEST},
                MyDataBaseContract.Table1.COLUMN_USER + " = ? ",
                new String[] {nomuser},
                null,
                null,
                null);
        String retvalue = "no games";
        if (c.moveToFirst()) {
            do {
                Log.v(TAG, "entra");
                retvalue = c.getString(c.getColumnIndex(MyDataBaseContract.Table1.COLUMN_BEST));
            } while (c.moveToNext());
        }
        c.close();

        return retvalue;
    }
    public String getpointst2(String nomuser){
        Cursor c;
        String TAG = "MyDataBaseHelper";
        c = readable.query(MyDataBaseContract.Table2.TABLE_NAME1,
                new String[] {MyDataBaseContract.Table2.COLUMN_BEST1},
                MyDataBaseContract.Table2.COLUMN_USER1 + " = ? ",
                new String[] {nomuser},
                null,
                null,
                null);
        String retvalue = "no users";
        if (c.moveToFirst()) {
            do {
                Log.v(TAG, "entra");
                retvalue = c.getString(c.getColumnIndex(MyDataBaseContract.Table2.COLUMN_BEST1));
            } while (c.moveToNext());
        }
        c.close();

        return retvalue;
    }


    public void updatetiponoti(String tiponot, String user) {
        ContentValues values = new ContentValues();
        values.put(MyDataBaseContract.Table1.COLUMN_TIPONOTI, tiponot);
        int rows_afected = readable.update(MyDataBaseContract.Table1.TABLE_NAME,    //Table name
                values,                                                             //New value for columns
                MyDataBaseContract.Table1.COLUMN_USER + " LIKE ? ",                 //Selection args
                new String[] {user});                                                  //Selection values

    }

    public void updatedireccion(String nomuse, String d) {
        ContentValues values = new ContentValues();
        values.put(MyDataBaseContract.Table1.COLUMN_ADRESS, d);
        int rows_afected = readable.update(MyDataBaseContract.Table1.TABLE_NAME,    //Table name
                values,                                                             //New value for columns
                MyDataBaseContract.Table1.COLUMN_USER + " LIKE ? ",                 //Selection args
                new String[] {nomuse});
    }

    public void updatecity(String nomuse, String c) {
        ContentValues values = new ContentValues();
        values.put(MyDataBaseContract.Table1.COLUMN_CITY, c);
        int rows_afected = readable.update(MyDataBaseContract.Table1.TABLE_NAME,    //Table name
                values,                                                             //New value for columns
                MyDataBaseContract.Table1.COLUMN_USER + " LIKE ? ",                 //Selection args
                new String[] {nomuse});
    }

    public void updatephoto(String nomuse, String url){
        ContentValues values = new ContentValues();
        values.put(MyDataBaseContract.Table1.COLUMN_PHOTO, url);
        int rows_afected = readable.update(MyDataBaseContract.Table1.TABLE_NAME,    //Table name
                values,                                                             //New value for columns
                MyDataBaseContract.Table1.COLUMN_USER + " LIKE ? ",                 //Selection args
                new String[] {nomuse});
    }
    public void updatepoints (String nomuse, String points){
        ContentValues values = new ContentValues();
        values.put(MyDataBaseContract.Table1.COLUMN_BEST, points);
        int rows_afected = readable.update(MyDataBaseContract.Table1.TABLE_NAME,    //Table name
                values,                                                             //New value for columns
                MyDataBaseContract.Table1.COLUMN_USER + " LIKE ? ",                 //Selection args
                new String[] {nomuse});
    }
    public void updatepointst2 (String nomuse, String points){
        ContentValues values = new ContentValues();
        values.put(MyDataBaseContract.Table2.COLUMN_BEST1, points);
        int rows_afected = readable.update(MyDataBaseContract.Table2.TABLE_NAME1,    //Table name
                values,                                                             //New value for columns
                MyDataBaseContract.Table2.COLUMN_USER1 + " LIKE ? ",                 //Selection args
                new String[] {nomuse});
    }


    public List<Contact> getpuntuaciones(List<Contact> contactos) {
        Cursor c;
        String TAG = "MyDataBaseHelper";
        c = readable.query(MyDataBaseContract.Table2.TABLE_NAME1,
                null,
                null,
                null,
                null,
                null,
                null);
        String retvalue = "no games";
        Log.v(TAG, "llega hasta aquí");
        if (c.moveToFirst()) {
            do {
                Log.v(TAG, "entra");
                String name = c.getString(c.getColumnIndex(MyDataBaseContract.Table2.COLUMN_USER1));
                Log.v(TAG, name);
                retvalue = c.getString(c.getColumnIndex(MyDataBaseContract.Table2.COLUMN_BEST1));
                Log.v(TAG, retvalue);
                if(!retvalue.equals("no games"))contactos.add(new Contact(0, name, retvalue));
            } while (c.moveToNext());
        }
        c.close();

        return contactos;
    }

    public void deletetable() {
        writable.execSQL(SQL_DELETE_TABLE2);
        writable.execSQL(SQL_CREATE_TABLE2);
    }
}




























