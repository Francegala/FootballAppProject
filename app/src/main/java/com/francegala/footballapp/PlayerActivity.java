package com.francegala.footballapp;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Toast;

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
 * total_hours_wasted_here: 7
 *
 * Yours sincerely,
 * Francesco Galassi
 **/


public class PlayerActivity extends AppCompatActivity {


    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    String qq;
    Cursor cursor;
    private ArrayList<String> mDataGame;

public void showGame(String id){

    mDataGame = new ArrayList<>();


    qq = "SELECT * FROM 'Assists' WHERE MatchID LIKE ?";
    cursor = mDb.rawQuery(qq, new String[]{id});
    if (cursor.getCount() == 0) {
        mDataGame.add("No Assists Found");
    }else{
        while (cursor.moveToNext()) {
            mDataGame.add("Assist= ID: "+ cursor.getString(0)+" Time: "+String.valueOf(cursor.getInt(1)));
        }
    }

    qq = "SELECT * FROM 'Dribbles' WHERE MatchID LIKE ?";
    cursor = mDb.rawQuery(qq, new String[]{id});
    if (cursor.getCount() == 0) {
        mDataGame.add("No Dribbles Found");
    }else{
        while (cursor.moveToNext()) {
            mDataGame.add("Dribbles= ID: "+ cursor.getString(0)+" Time: "+String.valueOf(cursor.getInt(1)));
        }
    }

    qq = "SELECT * FROM 'Goals' WHERE MatchID LIKE ?";
    cursor = mDb.rawQuery(qq, new String[]{id});
    if (cursor.getCount() == 0) {
        mDataGame.add("No Goals Found");
    }else{
        while (cursor.moveToNext()) {
            mDataGame.add("Goals= ID: "+ cursor.getString(0)+" Time: "+String.valueOf(cursor.getInt(1))+" Type: "+ cursor.getString(2));
        }
    }

    qq = "SELECT * FROM 'Passes' WHERE MatchID LIKE ?";
    cursor = mDb.rawQuery(qq, new String[]{id});
    if (cursor.getCount() == 0) {
        mDataGame.add("No Passes Found");
    }else{
        while (cursor.moveToNext()) {
            mDataGame.add("Passes= ID: "+ cursor.getString(0)+" Time: "+String.valueOf(cursor.getInt(1)));
        }
    }

    qq = "SELECT * FROM 'Saves' WHERE MatchID LIKE ?";
    cursor = mDb.rawQuery(qq, new String[]{id});
    if (cursor.getCount() == 0) {
        mDataGame.add("No Saves Found");
    }else{
        while (cursor.moveToNext()) {
            mDataGame.add("Saves= ID: "+ cursor.getString(0)+" Time: "+String.valueOf(cursor.getInt(1))+" Type: "+ cursor.getString(2));
        }
    }

    qq = "SELECT * FROM 'Tackles' WHERE MatchID LIKE ?";
    cursor = mDb.rawQuery(qq, new String[]{id});
    if (cursor.getCount() == 0) {
        mDataGame.add("No Tackles Won Found");
    }else{
        while (cursor.moveToNext()) {
            mDataGame.add("Tackles= ID: "+ cursor.getString(0)+" Time: "+String.valueOf(cursor.getInt(1)));
        }
    }
    ContentInit();


}

    private void ContentInit(){
        InitRecyclerView();
    }


    private void InitRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.gameView);
        RecyclerViewAdapter recyclerViewAdapter = new RecyclerViewAdapter(mDataGame,this);
        recyclerView.setAdapter(recyclerViewAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Intent intent = getIntent();
        String id = intent.getStringExtra("MatchID");
        Toast.makeText(this, "Playermode: " + id, Toast.LENGTH_SHORT).show();
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

        showGame(id);
    }
}
