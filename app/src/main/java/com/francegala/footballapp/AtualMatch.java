package com.francegala.footballapp;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.francegala.footballapp.database.DatabaseHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AtualMatch extends AppCompatActivity {
    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    String qq;
    EditText editTextMatch;
    Button btnCreate;




    public void showMessage(String title, String Message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_atual_match);

        editTextMatch=(EditText)findViewById(R.id.editMatch);
        btnCreate = (Button)findViewById(R.id.createButton);
        Workable();



        // this should be everywhere
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
    }


    public void Workable() {
        btnCreate.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        qq = "SELECT * FROM 'Match'";
                        Cursor cursor =
                                mDb.rawQuery(qq, null);
                        if(cursor.getCount() == 0) {
                            qq = "INSERT INTO 'Match' VALUES (?, null, null, null, null) ";
                            mDb.rawQuery(qq, new String[]{editTextMatch.getText().toString()});
                        } else{
                            StringBuffer buffer = new StringBuffer();
                            while (cursor.moveToNext()) {
                                buffer.append("Match ID :"+ cursor.getString(0)+"\n");
                                                            }
                            showMessage("Data",buffer.toString());
                        }



                    }
                }
        );
    }



    //https://codinginflow.com/tutorials/android/chronometer
}