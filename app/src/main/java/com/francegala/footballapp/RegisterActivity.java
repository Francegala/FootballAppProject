package com.francegala.footballapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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

public class RegisterActivity extends AppCompatActivity {

    private EditText name, email, password, c_password;
    private Button btn_regist;
    private ProgressBar loading;
    private TextView link_login;
    private static String URL_REGIST ="http://192.168.1.68/android_register_login/register.php";
    //private static String URL_REGIST ="https://apptestingfrancegala.000webhostapp.com/register.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loading = findViewById(R.id.loading);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        c_password = findViewById(R.id.c_password);
        btn_regist = findViewById(R.id.btn_regist);
        link_login = findViewById(R.id.link_login);


        btn_regist.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Regist();
            }

        });

        link_login.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,  LoginActivity.class ));
            }
        });
    }

    private void Regist(){

        loading.setVisibility(View.VISIBLE);
        btn_regist.setVisibility(View.GONE);
        final String name = this.name.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_REGIST,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString( "success");
                        if(success.equals("1")){
                            Toast.makeText( RegisterActivity.this, "Register Success",Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);

                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                        Toast.makeText( RegisterActivity.this, "Register Error" + e.toString() ,Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);
                        btn_regist.setVisibility(View.VISIBLE);

                    }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText( RegisterActivity.this, "Register Error" + error.toString() ,Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.GONE);
                btn_regist.setVisibility(View.VISIBLE);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("name", name);
                params.put("email", email);
                params.put("password", password);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this );
        requestQueue.add(stringRequest);
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