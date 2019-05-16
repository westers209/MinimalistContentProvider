package com.example.alicia.minimalistcontentprovider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import static java.lang.Integer.parseInt;

public class MiniContentProvider extends ContentProvider {

    private static final String TAG = MiniContentProvider.class.getSimpleName();
    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    public String[] data;

    @Override
    public boolean onCreate() {
        Context context = getContext();
        data = context.getResources().getStringArray(R.array.words);
        initializeUriMatching();
        return true;
    }

    private void initializeUriMatching() {
        uriMatcher.addURI(Contract.AUTHORITY, Contract.CONTENT_PATH + "/#", 1);
        uriMatcher.addURI(Contract.AUTHORITY, Contract.CONTENT_PATH, 0);

    }

    @NonNull
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {

        int id = -1;
        //Match the Uri. Each case switches on the returned code.
        switch (uriMatcher.match(uri)) {
            case 0:
                id = Contract.ALL_ITEMS;
                if (selection != null) {
                    id = parseInt(selectionArgs[0]);
                }
                break;
            case 1:
                id = parseInt(uri.getLastPathSegment());
                break;
            case UriMatcher.NO_MATCH:
                Log.d(TAG, "NO MATCH FOR THIS URI IN SCHEME.");
                id = -1;
                break;
            default:
                Log.d(TAG, "INVALID URI - URI NOT RECOGNIZED.");
                id = -1;
        }
        //Process the arguments and build a query for the back end.
        Log.d(TAG, "Query: " + id);

        //Return the cursor.
        return populateCursor(id);
    }

    //Receives the id extracted from the URI
    private Cursor populateCursor(int id) {
        //Creates a MatrixCursor to store received data.
        MatrixCursor cursor = new MatrixCursor(new String[]{Contract.CONTENT_PATH});

        //Creates and executes a query. IF query is valid, execute and add result to cursor.
        //.addRow adds the result to the cursor.
        if (id == Contract.ALL_ITEMS) {
            for (int i = 0; i < data.length; i++) {
                String word = data[i];
                cursor.addRow(new Object[]{word});
            }
        } else if (id >= 0) {
            String word = data[id];
            cursor.addRow(new Object[]{word});
        }
        return cursor;
    }


    @Override
    public String getType(Uri uri) {
        switch (uriMatcher.match(uri)) {
            case 0:
                return Contract.MULTIPLE_RECORD_MIME_TYPE;
            case 1:
                return Contract.SINGLE_RECORD_MIME_TYPE;
            default:
                return null;
        }
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        Log.e(TAG, "Not implemented: update uri: " + uri.toString());
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        Log.e(TAG, "Not implemented: update uri: " + uri.toString());
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        Log.e(TAG, "Not implemented: update uri: " + uri.toString());
        return 0;
    }
}
