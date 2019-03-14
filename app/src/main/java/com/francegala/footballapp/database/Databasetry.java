package com.francegala.footballapp.database;
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

import com.francegala.footballapp.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Databasetry extends AppCompatActivity {




    private DatabaseHelper mDBHelper;
    private SQLiteDatabase mDb;
    String qq;

    Button btnviewAll;
    TextView txtview;
    EditText edtEditText;
    EditText edtEditPerson;
    EditText edtEditSecond;
    String content;
    List<String> output = new ArrayList<String>();

    public void viewAll() {
        btnviewAll.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        if(edtEditText.getText().toString()!=null){
                            content = edtEditText.getText().toString() + "%";
                            qq = "SELECT FirstName, SecondName FROM Staff Where ID IN (SELECT ID FROM Occupancy WHERE RoomNumber LIKE ?)";
                        }else {
                           if(edtEditSecond.getText().toString()==null){
                            content = edtEditPerson.getText().toString() + "%";
                            qq = "SELECT * FROM Rooms WHERE RoomNumber = (SELECT RoomNumber From Occupancy WHere IDStaff = (SELECT ID FROM Staff WHERE FirstName = ? or SecondName=?))";
                        }else{
                               content = edtEditSecond.getText().toString() + "%";
                               qq = "SELECT * FROM Rooms WHERE RoomNumber = (SELECT RoomNumber From Occupancy WHere IDStaff = (SELECT ID FROM Staff WHERE FirstName = ? or SecondName=?))";
                           }
                        }
                        Cursor cursor =
                                mDb.rawQuery(qq, new String[] {content});
                        if(cursor.getCount() == 0) {
                            showMessage("Error","Nothing found");
                            return;
                        }

                        StringBuffer buffer = new StringBuffer();
                        while (cursor.moveToNext()) {
                           buffer.append("Room Number :"+ cursor.getString(0)+"\n");
                            //buffer.append("Floor :"+ cursor.getInt(0)+"\n");
                            buffer.append("Name :"+ cursor.getString(1)+"\n\n");
                            // buffer.append("Room :"+ res.getString(2)+"\n");

                            output.add(cursor.getString(0));
                            output.add(cursor.getString(1));



                        }
                        showMessage("Data",buffer.toString());
                    }
                }
        );
    }


    public void showMessage(String title,String Message){
        txtview.setText("Resident:"+output.get(0)+output.get(1));
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_databasetry);
        edtEditText=(EditText)findViewById(R.id.editTextRoom);
        edtEditPerson=(EditText)findViewById(R.id.editTextPerson);
        edtEditSecond=(EditText)findViewById(R.id.editTextSecond);
        btnviewAll = (Button)findViewById(R.id.databutton);
        txtview = (TextView)findViewById(R.id.text);
        viewAll();

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



}
