package com.francegala.footballapp;


import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.francegala.footballapp.database.DatabaseHelper;

import java.io.IOException;
import java.util.ArrayList;
/**
 * Dear Programmer,
 *
 * When I wrote this code, only God and I knew how it worked.
 * Now, only God knows it !
 *
 * Therefore, if you are trying to optimize this routine and
 * it fails (most surely), please increase this counter
 * as a warning for the next person:
 *
 * total_hours_wasted_here: 6
 *
 * Yours sincerely,
 * Francesco Galassi
 **/
public class DataFromMatch extends AppCompatActivity {
    private static final String TAG = "DataFromMatch";
    EditText editTextMatch;
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    String qq;
    Cursor cursor;
    String content;

    private ArrayList<String> mDataMatch;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_from_match);

        // Create Local Database
        mDBHelper = new DatabaseHelper(this);
        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }

        Log.d(TAG,"onCreate: started.");
        editTextMatch=(EditText)findViewById(R.id.editMatch);

        findViewById(R.id.getDatabutton).setOnClickListener(buttonClickListener);
        findViewById(R.id.getMatches).setOnClickListener(buttonClickListener);

    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.getMatches:
                    mDataMatch = new ArrayList<>();


                    content = editTextMatch.getText().toString();
                    qq = "SELECT * FROM 'Match'";
                    cursor = mDb.rawQuery(qq, null);
                    if (cursor.getCount() == 0) {
                    }else{
                        while (cursor.moveToNext()) {
                            mDataMatch.add("ID :"+cursor.getString(0));
                                                  }

                    }

                    ContentInit();
                    break;

                case R.id.getDatabutton:
                    mDataMatch = new ArrayList<>();


                    content = editTextMatch.getText().toString();
                    qq = "SELECT * FROM 'Match' WHERE ID LIKE ?";
                    cursor = mDb.rawQuery(qq, new String[]{content});
                   if (cursor.getCount() == 0) {
                    }else{
                        while (cursor.moveToNext()) {
                            mDataMatch.add("ID :"+cursor.getString(0));
                             mDataMatch.add("When :"+ cursor.getString(1));
                            mDataMatch.add("Against :"+ cursor.getString(2));
                            mDataMatch.add("Position :"+ cursor.getString(3));


                        }

                    }

                    ContentInit();
                    break;



            }
        }
    };
    /**
     *  Hello there!
     *
     *     Nice to see you. I didn't expect you here and
     *     I'm sorry, there is no cake.
     *
     *     Feel free to look and learn,
     *     but please don't steal the whole thing.
     *
     *     Send me a message if you have questions.
     *
     *     Sincerely,
     *     Francesco :)
     *
     **/

    private void ContentInit(){
            Log.d(TAG, "ContentInit: initialising content");
            InitRecyclerView();
    }


    private void InitRecyclerView(){
        Log.d(TAG, "InitRecyclerView:  initialising recycle view");
        RecyclerView recyclerView = findViewById(R.id.matchView);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(mDataMatch,this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}

