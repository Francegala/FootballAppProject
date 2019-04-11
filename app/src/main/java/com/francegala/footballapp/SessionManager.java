package com.francegala.footballapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.HashMap;
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
 * total_hours_wasted_here: 3
 *
 * Yours sincerely,
 * Francesco Galassi
 **/
public class SessionManager {

    SharedPreferences sharedPreferences;
    public SharedPreferences.Editor editor;
    public Context context;
    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "LOGIN";
    private static final String LOGIN = "IS_LOGIN";
    public static final String NAME = "NAME";
    public static final String EMAIL = "EMAIL";
    public static final String ID = "ID";


    public SessionManager(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(PREF_NAME , PRIVATE_MODE);
        editor = sharedPreferences.edit();

    }


    public void createSession(String name, String email, String id){
        editor.putBoolean(LOGIN, true);
        editor.putString(NAME, name);
        editor.putString(EMAIL, email);
        editor.putString(ID, id);
        editor.apply();

   }

    public boolean isLoggin(){
        return sharedPreferences.getBoolean(LOGIN, false);

    }

    public void checkLogin(){
        if(!this.isLoggin()){
            Intent i = new Intent(context, LoginActivity.class);
            context.startActivity(i);
            ((HomeActivity) context).finish();
        }
    }

    public HashMap<String, String> getUserDetail(){
        HashMap<String, String> user = new HashMap<>();
        user.put(NAME, sharedPreferences.getString(NAME, null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL, null));
        user.put(ID, sharedPreferences.getString(ID, null));
        return user;
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
     public void logout(){
        editor.clear();
        editor.commit();

         Intent i = new Intent(context, LoginActivity.class);
         context.startActivity(i);
         ((HomeActivity) context).finish();

    }


}
