package com.teste.elleve.doacaosangue.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.teste.elleve.doacaosangue.R;
import com.teste.elleve.doacaosangue.helpers.DatabaseHelper;

public class LoginActivity extends AppCompatActivity {

    EditText mEmail, mPassword;
    Button mSignIn;
    TextView mSignUp;

    String userEmail, userPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEmail = (EditText) findViewById(R.id.user_email);
        mPassword = (EditText) findViewById(R.id.user_password);
        mSignIn = (Button) findViewById(R.id.signin_button);
        mSignUp = (TextView) findViewById(R.id.signin_text);

        mSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
                startActivity(intent);
            }
        });
    }

    public void signIn(View view) {
        if (validateAndSave()) {
            userEmail = mEmail.getText().toString();
            userPassword = mPassword.getText().toString();
            if (DatabaseHelper.getInstance(getContext()).auth(userEmail, userPassword)) {
                SharedPreferences pref = getContext().getSharedPreferences("preferences", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("logged_in", true);
                editor.commit();
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), getResources().getString(R.string.not_registered), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateAndSave() {
        mEmail.setError(null);
        mPassword.setError(null);

        if (mEmail.getText().toString().isEmpty()) {
            mEmail.setError(getResources().getString(R.string.required_field));
            return false;
        }

        if (mPassword.getText().toString().isEmpty()) {
            mPassword.setError(getResources().getString(R.string.required_field));
            return false;
        }
        return true;
    }

    private Context getContext() {
        return this;
    }
}
