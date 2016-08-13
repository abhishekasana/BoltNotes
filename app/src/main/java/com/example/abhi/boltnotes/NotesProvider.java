package com.example.abhi.boltnotes;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by abhi on 8/2/2016.
 */
public class NotesProvider extends ContentProvider {
    private static final String AUTHORITY ="com.example.abhi.boltnotes.notesprovider";
    private static final String BASE_PATH ="notes";
    static final Uri CONTENT_URI =Uri.parse("content://"+AUTHORITY+"/"+BASE_PATH);

    //constants to identify the requested operation
    private static final int NOTES = 1;//..give me the notes
    private static final int NOTES_ID=2;//give the specific row

    public static final String CONTENT_ITEM_TYPE="NOTE";
    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    //this code will execute first time whenver anything is called from this class
    static
    {
        uriMatcher.addURI(AUTHORITY,BASE_PATH,NOTES);
        uriMatcher.addURI(AUTHORITY,BASE_PATH+"/#",NOTES_ID);
    }
    private SQLiteDatabase database;
    @Override
    public boolean onCreate() {

        DBOpenHelper helper  = new DBOpenHelper(getContext());
        database = helper.getWritableDatabase();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        if(uriMatcher.match(uri)==NOTES_ID){
            selection=DBOpenHelper.NOTE_ID + "=" + uri.getLastPathSegment();
        }
        return database.query(DBOpenHelper.TABLE_NOTES,DBOpenHelper.ALL_COLUMNS,selection,null,null,null,DBOpenHelper.NOTE_CREATED + " DESC");
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values)
    {
        long id = database.insert(DBOpenHelper.TABLE_NOTES,null,values);
        return Uri.parse(BASE_PATH+"/"+id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs)
    {
        return database.delete(DBOpenHelper.TABLE_NOTES,selection,selectionArgs);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return database.update(DBOpenHelper.TABLE_NOTES,values,selection,selectionArgs);
    }
}
