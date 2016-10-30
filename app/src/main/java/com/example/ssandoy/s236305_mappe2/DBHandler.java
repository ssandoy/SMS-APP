package com.example.ssandoy.s236305_mappe2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Calendar;

/**
 * Created by ssandoy on 09.10.2016.
 */
public class DBHandler {

    Context context;

    static final String DATABASE_NAME = "BirthdayBase";
    static final String TABLE_CONTACTS = "Contacts";
    static final String KEY_ID = "_ID";
    static final String KEY_FIRSTNAME = "firstName";
    static final String KEY_LASTNAME = "lastName";
    static final String KEY_PH_NO = "phoneNumber";
    static final String KEY_YEAR = "birthYear";
    static final String KEY_MONTH = "birthMonth";
    static final String KEY_DAY = "birthDay";

    static final int DATABASE_VERSION = 3;
    private DatabaseHelper DBHelper;
    private SQLiteDatabase db;

    public DBHandler(Context context) {
        this.context = context;
        DBHelper = new DatabaseHelper(context);
    }

    private static class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createTable = "CREATE TABLE " + TABLE_CONTACTS +
                    "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + KEY_FIRSTNAME + " TEXT, "
                    + KEY_LASTNAME + " TEXT, "
                    + KEY_PH_NO + " TEXT, "
                    + KEY_DAY + " INT, "
                    + KEY_MONTH + " INT, "
                    + KEY_YEAR + " INT"
                    + ")";
            db.execSQL(createTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
            onCreate(db);
        }
    } //END OF HELPER

    public DBHandler open() throws SQLException {
        db = DBHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        DBHelper.close();
    }

    public void reopen() throws SQLException{
        close();
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    public void insert(ContentValues cv)
    {
        db.insert(TABLE_CONTACTS,null,cv);
    }

    public Cursor findAll(){
        String	selectQuery	=	"SELECT * FROM "+ TABLE_CONTACTS + " ORDER BY " + KEY_FIRSTNAME;
        Cursor cursor = db.rawQuery(selectQuery,null);
        return cursor;
    }

    public boolean deletePerson(int id) {
        return db.delete(TABLE_CONTACTS, KEY_ID + "='" + id +"'", null) > 0;
    }

    public boolean update(String firstName, String lastName, String phoneNr, int bYear, int bMonth, int bDay, int id) {
        ContentValues cv = new ContentValues(6);

        cv.put(KEY_FIRSTNAME, firstName);
        cv.put(KEY_LASTNAME, lastName);
        cv.put(KEY_PH_NO, phoneNr);
        cv.put(KEY_YEAR, bYear);
        cv.put(KEY_MONTH, bMonth);
        cv.put(KEY_DAY, bDay);
        return db.update(TABLE_CONTACTS,cv, KEY_ID +  "='" + id +"'",null) > 0;
    }

    public Cursor findPersonByID(int id) {

        String[] columns = {KEY_ID,KEY_FIRSTNAME,KEY_LASTNAME,KEY_PH_NO, KEY_YEAR, KEY_MONTH, KEY_DAY};
        Cursor cur = db.query(TABLE_CONTACTS, columns, KEY_ID + "==" + String.valueOf(id), null, null, null, KEY_ID);
        return cur;

    }

    public Cursor hasBirthday(int month, int day) {
        Cursor cur;
        String[] columns = {KEY_ID,KEY_FIRSTNAME,KEY_LASTNAME,KEY_PH_NO, KEY_YEAR, KEY_MONTH, KEY_DAY};
        cur = db.query( TABLE_CONTACTS, columns, KEY_DAY + " = '" + day + "' and " + KEY_MONTH + " = '"
                + month + "'",null,null,null,KEY_ID);
        return cur;
    }

    public Calendar setPersonBirthDate(int day, int month, int year){
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
        return calendar;
    }



}
