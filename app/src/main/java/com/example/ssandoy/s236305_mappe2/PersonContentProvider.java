package com.example.ssandoy.s236305_mappe2;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;

/**
 * Created by ssandoy on 18.10.2016.
 */
public class PersonContentProvider extends ContentProvider { //FIXME



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
    SQLiteDatabase db;

    public final static String PROVIDER = "com.example.ssandoy.s236305_mappe2.contentprovider";
    private static final int CONTACT =1;
    private static final int MCONTACT=2;

    public static final Uri CONTENT_URI =Uri.parse("content://"+ PROVIDER + "/contact");
    private static final UriMatcher uriMatcher;
    static{
        uriMatcher = new
                UriMatcher(UriMatcher.NO_MATCH);
        uriMatcher.addURI(PROVIDER, "contact",MCONTACT);
        uriMatcher.addURI(PROVIDER, "contact/#",CONTACT);
    }


    private static class DatabaseHelper extends SQLiteOpenHelper {

        DatabaseHelper(Context context) {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
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
            Log.d("CONTENTPROVIDER", createTable);
            db.execSQL(createTable);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("drop table if exists " + TABLE_CONTACTS);
            Log.d("CONTENTPROVIDER","updated");
            onCreate(db);
        }
    }//SLUTT PÅ HELPERCLASS


    @Override
    public boolean onCreate() {
        DBHelper = new DatabaseHelper(getContext());
        db = DBHelper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)){
            case MCONTACT:return
                    "vnd.android.cursor.dir/vnd.com.example.ssandoy.s236305_mappe2";
            case CONTACT:return
                    "vnd.android.cursor.item/vnd.com.example.ssandoy.s236305_mappe2";
            default: throw new
                    IllegalArgumentException("Ugyldig URI" +
                    uri);}
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {

        SQLiteDatabase db = DBHelper.getWritableDatabase();
        db.insert(TABLE_CONTACTS,null, values);

        Cursor c= db.query(TABLE_CONTACTS, null, null, null, null,
                null, null);

        c.moveToLast();
        long minid=c.getLong(0);
        getContext().getContentResolver().notifyChange(uri, null);

        return ContentUris.withAppendedId(uri, minid);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
