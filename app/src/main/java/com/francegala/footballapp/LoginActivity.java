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
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
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
 * total_hours_wasted_here: 10
 *
 * Yours sincerely,
 * Francesco Galassi
 **/

public class LoginActivity extends AppCompatActivity {

    private EditText email, password;
    private Button btn_login;
    private Button btn_skip;
    private TextView link_regist;
    private ProgressBar loading;
    private static String URL_LOGIN = "http://192.168.1.68/android_register_login/login.php";
    //private static String URL_LOGIN = "https://apptestingfrancegala.000webhostapp.com/login.php";
    SessionManager sessionManager;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);

        loading = findViewById(R.id.loading);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btn_login = findViewById(R.id.btn_login);
        btn_skip = findViewById(R.id.btn_Skip);
        link_regist = findViewById(R.id.link_regist);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mEmail = email.getText().toString().trim();
                String mPass = password.getText().toString().trim();
                if(!mEmail.isEmpty() || !mPass.isEmpty()){
                    Login(mEmail,mPass);
                }else{
                    email.setError("Please insert Email");
                    password.setError("Please insert Password");

                }
            }
        });

        link_regist.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class ));
            }
        });

        btn_skip.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ActualMatch.class ));
            }
        });

    }

    private void Login(final String email, final String password) {
        loading.setVisibility(View.VISIBLE);
        btn_login.setVisibility(View.GONE);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                    try{
                        JSONObject jsonObject = new JSONObject(response);
                        String success = jsonObject.getString("success" );
                        JSONArray   jsonArray = jsonObject.getJSONArray("login");
                        if(success.equals("1")){
                            for(int i = 0; i< jsonArray.length(); i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                 String name = object.getString("name").trim();
                                String email = object.getString("email").trim();
                                String id = object.getString("id").trim();

                                sessionManager.createSession(name, email, id);

                              Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                              intent.putExtra("name", name);
                              intent.putExtra("email", email);
                              startActivity(intent);
                              finish();

                                btn_login.setVisibility(View.VISIBLE);
                                loading.setVisibility(View.GONE);
                            }
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                        loading.setVisibility(View.GONE);
                        btn_login.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginActivity.this, "Error"+e.toString(), Toast.LENGTH_SHORT).show();

                    }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        loading.setVisibility(View.GONE);
                        btn_login.setVisibility(View.VISIBLE);
                        Toast.makeText(LoginActivity.this, "Error"+error.toString(), Toast.LENGTH_SHORT).show();


                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
               Map<String, String> params = new HashMap<>();
               params.put("email", email);
                params.put("password", password);

                return params;
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
        stringRequest.setRetryPolicy(new RetryPolicy() {
            @Override
            public int getCurrentTimeout() {
                return 30000;
            }

            @Override
            public int getCurrentRetryCount() {
                return 30000;
            }

            @Override
            public void retry(VolleyError error) throws VolleyError {

            }
        });


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}
