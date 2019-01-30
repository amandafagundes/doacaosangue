package com.teste.elleve.doacaosangue.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.teste.elleve.doacaosangue.R;

public class RootActivity extends AppCompatActivity {

    Intent intent;
    boolean firstAccess = false;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    boolean loggedIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_root);

        pref = getContext().getSharedPreferences("preferences", 0);
        editor = pref.edit();

        loggedIn = pref.getBoolean("logged_in", true);

        if (pref.getBoolean("firstAccess", true)) {
            firstAccess = true;
            editor.putBoolean("firstAccess", false);
            editor.putBoolean("logged_in", false);
            editor.commit();
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(firstAccess){
                    intent = new Intent(RootActivity.this, LoginActivity.class);
                }else{
                    if (loggedIn) {
                        intent = new Intent(RootActivity.this, HomeActivity.class);
                    } else {
                        intent = new Intent(RootActivity.this, LoginActivity.class);
                    }
                }
                startActivity(intent);
            }
        }, 1000);

    }

    private Context getContext() {
        return this;
    }
}
