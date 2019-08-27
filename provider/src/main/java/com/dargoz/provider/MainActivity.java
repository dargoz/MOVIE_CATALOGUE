package com.dargoz.provider;

import android.content.ContentValues;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import static com.dargoz.provider.db.DatabaseContract.*;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ContentValues values = new ContentValues();
        //TODO : Create here ...
        getContentResolver().insert(CONTENT_URI,values);
    }
}
