package com.teste.elleve.doacaosangue.presenter;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.teste.elleve.doacaosangue.R;
import com.teste.elleve.doacaosangue.helpers.DatabaseHelper;
import com.teste.elleve.doacaosangue.model.Request;

import java.util.List;

public class NewRequestActivity extends AppCompatActivity {

    EditText mPatient, mHospital;
    Spinner mBloodType;

    ArrayAdapter<String> adapter;
    String[] list = {"A+", "A-", "B+","B-","AB+","AB-","O+","O-"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_request);

        mPatient = (EditText) findViewById(R.id.patient);
        mHospital = (EditText) findViewById(R.id.hospital);
        mBloodType = (Spinner) findViewById(R.id.blood_type);

        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, list);

        mBloodType.setAdapter(adapter);
    }
    
    public void saveRequest(View view){
        if(validateAndSave()){
            Request request = new Request(mHospital.getText().toString(), mPatient.getText().toString(), mBloodType.getSelectedItem().toString());
            if(DatabaseHelper.getInstance(getContext()).createRequest(request)){
                Toast.makeText(getContext(), getResources().getString(R.string.request_saved), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getContext(), getResources().getString(R.string.request_failed), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private Context getContext() {
        return this;
    }

    private boolean validateAndSave() {
        mPatient.setError(null);
        mHospital.setError(null);

        if (mPatient.getText().toString().isEmpty()) {
            mPatient.setError(getResources().getString(R.string.required_field));
            return false;
        }

        if (mHospital.getText().toString().isEmpty()) {
            mHospital.setError(getResources().getString(R.string.required_field));
            return false;
        }
        return true;
    }
}
