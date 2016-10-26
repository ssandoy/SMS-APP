package com.example.ssandoy.s236305_mappe2;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by ssandoy on 18.10.2016.
 */
public class PersonContentProvider extends ContentProvider {

    //TODO: WHAT TO DO WHEN DATABASE EXISTS? LEGGE TIL DBHANDLER?

    //TODO: FIX URIMATCHER

    private DBHandler dbHandler;
    SQLiteDatabase db;

    @Override
    public boolean onCreate() {
        dbHandler = new DBHandler(getContext());
         //db = ?? FIXME HENTE UT PRIVAT KLASSE?... KALLE PÅ OPEN()?
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
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
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
