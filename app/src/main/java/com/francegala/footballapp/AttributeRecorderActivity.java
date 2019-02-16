package com.francegala.footballapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AttributeRecorderActivity extends AppCompatActivity {
    private Button btn_home, btn_newmatch;
    private TextView tagid, stime, against, matches_list;
    SessionManager sessionManager;
    String getId;



    //private static String URL_NEWMATCH ="http://192.168.1.68/android_register_login/newmatch.php";
    private static String URL_NEWMATCH ="https://apptestingfrancegala.000webhostapp.com/newmatch.php";
    //private static String URL_GAMES ="http://192.168.1.68/android_register_login/matcheslist.php";
    private static String URL_GAMES ="https://apptestingfrancegala.000webhostapp.com/matcheslist.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attribute_recorder);

        btn_home = findViewById(R.id.btn_home);
        btn_newmatch = findViewById(R.id.btn_newmatch);
        tagid = findViewById(R.id.tagid);
        stime = findViewById(R.id.stime);
        against = findViewById(R.id.against);
        matches_list = findViewById(R.id.matches_list);
        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();
        HashMap<String, String> user = sessionManager.getUserDetail();
        getId = user.get(sessionManager.ID);

        btn_newmatch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               newMatch();
            }
        });


        btn_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResume();
               /* Intent intent = new Intent(AttributeRecorderActivity.this, HomeActivity.class);
                startActivity(intent);*/
            }
        });


    }


    @Override
    protected void onResume() {
        super.onResume();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_GAMES,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString("success");
                            JSONArray jsonArray = jsonObject.getJSONArray("read");
                            if (success.equals("1")){
                                for(int i=0;i<jsonArray.length();i++){
                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String strName = object.getString("name").trim();
                                    matches_list.setText("");
                                    matches_list.append(strName);

                                }
                            }
                        } catch (JSONException e) {

                            e.printStackTrace();
                           // Toast.makeText(AttributeRecorderActivity.this,"Error Reading Detail: "+"1"+e.toString(), Toast.LENGTH_SHORT).show();

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                     //   Toast.makeText(AttributeRecorderActivity.this,"Error Reading Detail: "+"2"+error.toString(), Toast.LENGTH_SHORT).show();

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id",getId);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);



    }

    private void newMatch(){

        final String tagid = this.tagid.getText().toString().trim();
        final String stime = this.stime.getText().toString().trim();
        final String against = this.against.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_NEWMATCH,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String success = jsonObject.getString( "success");
                            if(success.equals("1")){
                                Toast.makeText( AttributeRecorderActivity.this, "Register Success",Toast.LENGTH_SHORT).show();
                                                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                            Toast.makeText( AttributeRecorderActivity.this, "Register Error" + e.toString() ,Toast.LENGTH_SHORT).show();
                                                   }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText( AttributeRecorderActivity.this, "Register Error" + error.toString() ,Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("taguser", getId);
                params.put("tagid", tagid);
                params.put("against", against);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this );
        requestQueue.add(stringRequest);
    }
}
