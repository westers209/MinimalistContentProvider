package com.example.alicia.minimalistcontentprovider;

import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textView);
    }

    public void onClickDisplayEntries(View view) {
        String queryUri = Contract.CONTENT_URI.toString(); //Identify provider and table. Get URI info from contract
        String[] projection = new String[]{Contract.CONTENT_PATH}; // names of columns to return.
        String selectionClause; //which rows to return, SQL's WHERE
        String selectionArgs[]; //values that match the selection criteria. Always separate selection and its args.
        String sortOrder = null; // SQL's ORDER BY, null means default or no order.

        switch (view.getId()) {
            case R.id.button_display_all:
                //Both to null means return everything.
                selectionClause = null;
                selectionArgs = null;
                break;
            case R.id.button_display_first:
                //First word's ID is 0.
                selectionClause = Contract.WORD_ID + " = ?";
                selectionArgs = new String[]{"0"};
                break;
            default:
                selectionClause = null;
                selectionArgs = null;
        }

        Cursor cursor = getContentResolver().query(Uri.parse(queryUri), projection, selectionClause, selectionArgs, sortOrder);

        //If the cursor isn't empty
        if(cursor != null){
            //And the cursor has at least one thing in it.
            if (cursor.getCount() > 0){
                //Set the cursor to the first item
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(projection[0]);
                do{
                    //Then go through the cursor adding each word to the text view in a new line.
                    String word = cursor.getString(columnIndex);
                    textView.append(word + "\n");
                    //Keep doing this until there are no more words in the cursor
                } while(cursor.moveToNext());
            } else{
                //If the cursor is null, just say that it's null and append it to the text view.
                Log.d(TAG,"onClickDisplayEntries " + "Cursor is null.");
                textView.append("Cursor is null." + "\n");
            }
        }
    }
}
