package com.example.alicia.minimalistcontentprovider;

import android.net.Uri;

public final class Contract {
    private Contract() {
    }

    public static final String AUTHORITY = "com.example.alicia.minimalistcontentprovider.provider";
    public static final String CONTENT_PATH = "words";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + CONTENT_PATH);
    static final int ALL_ITEMS = -2;
    static final String WORD_ID = "id";
    static final String SINGLE_RECORD_MIME_TYPE = "vnd.alicia.cursor.item/vnd.com.example.provider.words";
    static final String MULTIPLE_RECORD_MIME_TYPE = "vnd.alicia.cursor.dir/vnd.com.example.provider.words";

}

/*
Not working: I tried a number of set ups and these are the results I got



 */
