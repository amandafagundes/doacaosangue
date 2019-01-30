package com.teste.elleve.doacaosangue.presenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.teste.elleve.doacaosangue.R;
import com.teste.elleve.doacaosangue.helpers.DatabaseHelper;
import com.teste.elleve.doacaosangue.model.User;

public class SignUpActivity extends AppCompatActivity {

    EditText input_email, input_password;
    Button signup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        input_email = (EditText) findViewById(R.id.user_email);
        input_password = (EditText) findViewById(R.id.user_password);
        signup = (Button) findViewById(R.id.signup_button);
    }

    public void signUp(View view) {
        if (validateAndSave()) {
            User user = new User(input_email.getText().toString(), input_password.getText().toString());
            if (DatabaseHelper.getInstance(getContext()).createUser(user)) {
                SharedPreferences pref = getContext().getSharedPreferences("preferences", 0);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("logged_in", true);
                editor.commit();
                Intent intent = new Intent(SignUpActivity.this, HomeActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(getContext(), getResources().getString(R.string.signup_error), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean validateAndSave() {
        input_email.setError(null);
        input_password.setError(null);

        if (input_email.getText().toString().isEmpty()) {
            input_email.setError(getResources().getString(R.string.required_field));
            return false;
        } else {
            if (!android.util.Patterns.EMAIL_ADDRESS.matcher(input_email.getText().toString()).matches()) {
                input_email.setError(getResources().getString(R.string.invalid_email));
                return false;
            }
        }
        if (input_password.getText().toString().isEmpty()) {
            input_password.setError(getResources().getString(R.string.required_field));
            return false;
        }
        return true;
    }

    private Context getContext() {
        return this;
    }
}
