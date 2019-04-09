package com.francegala.footballapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.francegala.footballapp.database.DatabaseHelper;

import java.io.IOException;

public class ActualMatch extends AppCompatActivity {
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    String qq;

    EditText editTextMatch;
    EditText editTextDate;
    EditText editTextAgainst;
    EditText editTextPosition;

    Button btnCreate;
    Cursor cursor;
    String content;
    String IDMatch;


    public void defineButtons() {
        editTextMatch=(EditText)findViewById(R.id.editMatch);
        editTextDate=(EditText)findViewById(R.id.editDate);
        editTextAgainst=(EditText)findViewById(R.id.editAgainst);
        editTextPosition=(EditText)findViewById(R.id.editPosition);


        findViewById(R.id.createButton).setOnClickListener(buttonClickListener);
        findViewById(R.id.dribbleButton).setOnClickListener(buttonClickListener);
        findViewById(R.id.assistButton).setOnClickListener(buttonClickListener);


    }

    private View.OnClickListener buttonClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.createButton:
                    CreateMatch();
                    break;

                case R.id.dribbleButton:
                    AddDribble();
                    break;

                case R.id.assistButton:
                    AddAssist();
                    break;

            }
        }
    };



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actual_match);

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


        defineButtons();
    }


    public void CreateMatch() {
        content = editTextMatch.getText().toString();
        qq = "SELECT * FROM 'Match' WHERE ID LIKE ?";
        cursor = mDb.rawQuery(qq, new String[]{content});
        if (cursor.getCount() == 0) {
            boolean createSuccessful = false;

            ContentValues valuesMatch = new ContentValues();
            valuesMatch.put("ID", content); // inserting a string
            valuesMatch.put("Date", editTextDate.getText().toString()); // inserting a string
            valuesMatch.put("Against",  editTextAgainst.getText().toString()); // inserting a string
            valuesMatch.put("Position",  editTextPosition.getText().toString()); // inserting a string
            createSuccessful = mDb.insert("Match", null, valuesMatch)>=0;

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
            if(createSuccessful == false){
                showMessage("Error", "Not Valid, try Again");}
            else{
                showMessage("Success", "" + createSuccessful +" "+ cursor.getCount());}
        }

        else {
            IDMatch = content;
            StringBuffer buffer = new StringBuffer();
            while (cursor.moveToNext()) {
                buffer.append("ID :"+ cursor.getString(0)+"\n");
                buffer.append("When :"+ cursor.getString(1)+"\n");
                buffer.append("Against :"+ cursor.getString(2)+"\n");
                buffer.append("Position :"+ cursor.getString(3)+"\n");


            }
            showMessage("Data",buffer.toString());
        }

    }

    public void AddDribble() {
        content = editTextMatch.getText().toString();
        qq = "SELECT * FROM 'Match' WHERE ID LIKE ?";
        cursor = mDb.rawQuery(qq, new String[]{content});
        if (cursor.getCount() == 0) {
            showMessage("Error","Match ID not found, create one first");
        }else{

            boolean dribbleSuccessful = false;

            ContentValues valuesDribble = new ContentValues();
            valuesDribble.put("MatchID", content); // inserting a string
            dribbleSuccessful = mDb.insert("Dribble", null, valuesDribble)>=0;

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
            if(dribbleSuccessful == false){
                showMessage("Error", "Dribble Not Recorded, try Again");}
            else{
                showMessage("Success", "Dribble Recorded: " + dribbleSuccessful);}
            /*qq = "SELECT * FROM Dribble WHERE MatchID LIKE ?";
            cursor = mDb.rawQuery(qq, new String[]{content});
            StringBuffer buffer = new StringBuffer();
            while (cursor.moveToNext()) {
                buffer.append("ID :"+ cursor.getString(0)+"\n");
                buffer.append("Time :"+ cursor.getInt(1 )+"\n");


            }
            showMessage("Data",buffer.toString());*/
        }


    }

    public void AddAssist() {
        content = editTextMatch.getText().toString();
        qq = "SELECT * FROM 'Match' WHERE ID LIKE ?";
        cursor = mDb.rawQuery(qq, new String[]{content});
        if (cursor.getCount() == 0) {
            showMessage("Error","Match ID not found, create one first");
        }else{

            boolean assistSuccessful = false;

            ContentValues valuesDribble = new ContentValues();
            valuesDribble.put("MatchID", content); // inserting a string
            assistSuccessful = mDb.insert("Dribble", null, valuesDribble)>=0;

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
            if(assistSuccessful == false){
                showMessage("Error", "Dribble Not Recorded, try Again");}
            else{
                showMessage("Success", "Dribble Recorded: " + assistSuccessful);}
            /*qq = "SELECT * FROM Dribble WHERE MatchID LIKE ?";
            cursor = mDb.rawQuery(qq, new String[]{content});
            StringBuffer buffer = new StringBuffer();
            while (cursor.moveToNext()) {
                buffer.append("ID :"+ cursor.getString(0)+"\n");
                buffer.append("Time :"+ cursor.getInt(1 )+"\n");


            }
            showMessage("Data",buffer.toString());*/
        }


    }



    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}