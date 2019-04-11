package com.francegala.footballapp;

import android.content.ContentValues;
import android.content.Intent;
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
public class ActualMatch extends AppCompatActivity {
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    String qq;

    EditText editTextMatch;
    EditText editTextDate;
    EditText editTextAgainst;
    EditText editTextPosition;
    EditText editTextGoal;
    EditText editTextSave;

    Cursor cursor;
    String content;


    public void defineButtons() {
        editTextMatch=(EditText)findViewById(R.id.editMatch);
        editTextDate=(EditText)findViewById(R.id.editDate);
        editTextAgainst=(EditText)findViewById(R.id.editAgainst);
        editTextPosition=(EditText)findViewById(R.id.editPosition);
        editTextGoal=(EditText)findViewById(R.id.editGoal);
        editTextSave=(EditText)findViewById(R.id.editSave);



        findViewById(R.id.createButton).setOnClickListener(buttonClickListener);
        findViewById(R.id.dribbleButton).setOnClickListener(buttonClickListener);
        findViewById(R.id.assistButton).setOnClickListener(buttonClickListener);
        findViewById(R.id.tackleButton).setOnClickListener(buttonClickListener);
        findViewById(R.id.passButton).setOnClickListener(buttonClickListener);
        findViewById(R.id.goalButton).setOnClickListener(buttonClickListener);
        findViewById(R.id.saveButton).setOnClickListener(buttonClickListener);
        findViewById(R.id.viewerButton).setOnClickListener(buttonClickListener);


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

                case R.id.tackleButton:
                    AddTackle();
                    break;

                case R.id.passButton:
                    AddPass();
                    break;

                case R.id.goalButton:
                    AddGoal();
                    break;

                case R.id.saveButton:
                    AddSave();
                    break;

                case R.id.viewerButton:
                    startActivity(new Intent(ActualMatch.this, DataFromMatch.class ));
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
            StringBuffer buffer = new StringBuffer();
            while (cursor.moveToNext()) {
                buffer.append("ID : "+ cursor.getString(0)+"\n");
                buffer.append("When : "+ cursor.getString(1)+"\n");
                buffer.append("Against : "+ cursor.getString(2)+"\n");
                buffer.append("Position : "+ cursor.getString(3)+"\n");


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
            dribbleSuccessful = mDb.insert("Dribbles", null, valuesDribble)>=0;

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

            ContentValues valuesAssist = new ContentValues();
            valuesAssist.put("MatchID", content); // inserting a string
            assistSuccessful = mDb.insert("Assists", null, valuesAssist)>=0;

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
                showMessage("Error", "Assist Not Recorded, try Again");}
            else{
                showMessage("Success", "Assist Recorded: " + assistSuccessful);}
                    }


    }

    public void AddTackle() {
        // Get the Match ID
        content = editTextMatch.getText().toString();
        // Query the DB if exist
        qq = "SELECT * FROM 'Match' WHERE ID LIKE ?";
        cursor = mDb.rawQuery(qq, new String[]{content});
        if (cursor.getCount() == 0) {
            showMessage("Error","Match ID not found, create one first");
        }else{

            boolean tackleSuccessful = false;
            //Insert the Tackle in the DB
            ContentValues valuesTackles = new ContentValues();
            valuesTackles.put("MatchID", content); // inserting a string
            tackleSuccessful = mDb.insert("Tackles", null, valuesTackles)>=0;

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
            if(tackleSuccessful == false){
                showMessage("Error", "Tackle Won Not Recorded, try Again");}
            else{
                showMessage("Success", "Tackle Won Recorded: " + tackleSuccessful);}
                    }


    }

    public void AddPass() {
        // Get the Match ID
        content = editTextMatch.getText().toString();
        // Query the DB if exist
        qq = "SELECT * FROM 'Match' WHERE ID LIKE ?";
        cursor = mDb.rawQuery(qq, new String[]{content});
        if (cursor.getCount() == 0) {
            showMessage("Error","Match ID not found, create one first");
        }else{

            boolean passSuccessful = false;
            //Insert the Pass in the DB
            ContentValues valuesPass = new ContentValues();
            valuesPass.put("MatchID", content); // inserting a string
            passSuccessful = mDb.insert("Passes", null, valuesPass)>=0;

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
            if(passSuccessful == false){
                showMessage("Error", "Pass Not Recorded, try Again");}
            else{
                showMessage("Success", "Pass Recorded: " + passSuccessful);}
                  }


    }

    public void AddGoal() {
        // Get the Match ID
        content = editTextMatch.getText().toString();
        String goalContent = editTextGoal.getText().toString();
        // Query the DB if exist
        qq = "SELECT * FROM 'Match' WHERE ID LIKE ?";
        cursor = mDb.rawQuery(qq, new String[]{content});
        if (cursor.getCount() == 0) {
            showMessage("Error","Match ID not found, create one first");
        }else{

            boolean goalSuccessful = false;
            //Insert the Goal in the DB
            ContentValues valuesGoal = new ContentValues();
            valuesGoal.put("MatchID", content); // inserting a string
            valuesGoal.put("Type", goalContent); // inserting a string
            goalSuccessful = mDb.insert("Goals", null, valuesGoal)>=0;

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
            if(goalSuccessful == false){
                showMessage("Error", "Goal Not Recorded, try Again");}
            else{
                showMessage("Success", "Goal Won Recorded: " + goalSuccessful);}
                   }


    }

    public void AddSave() {
        // Get the Match ID
        content = editTextMatch.getText().toString();
        String saveContent = editTextSave.getText().toString();
        // Query the DB if exist
        qq = "SELECT * FROM 'Match' WHERE ID LIKE ?";
        cursor = mDb.rawQuery(qq, new String[]{content});
        if (cursor.getCount() == 0) {
            showMessage("Error","Match ID not found, create one first");
        }else{

            boolean saveSuccessful = false;
            //Insert the Save in the DB
            ContentValues valuesSave = new ContentValues();
            valuesSave.put("MatchID", content); // inserting a string
            valuesSave.put("Type", saveContent); // inserting a string
            saveSuccessful = mDb.insert("Saves", null, valuesSave)>=0;

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
            if(saveSuccessful == false){
                showMessage("Error", "Save Not Recorded, try Again");}
            else{
                showMessage("Success", "Save Won Recorded: " + saveSuccessful);}
                 }


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



    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }
}