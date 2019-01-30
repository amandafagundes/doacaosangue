package com.teste.elleve.doacaosangue.presenter;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.teste.elleve.doacaosangue.R;
import com.teste.elleve.doacaosangue.helpers.DatabaseHelper;
import com.teste.elleve.doacaosangue.model.Request;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    TextView mNoRequests;
    ListView mRequests;
    FloatingActionButton mFab;
    ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mRequests = (ListView) findViewById(R.id.requests);
        mFab = (FloatingActionButton) findViewById(R.id.fab);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mNoRequests = (TextView) findViewById(R.id.no_requests);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, NewRequestActivity.class);
                startActivity(intent);
            }
        });

        GetRequestsTask getRequestsTask = new GetRequestsTask();
        getRequestsTask.execute();
    }

    @Override
    protected void onResume() {
        super.onResume();
        GetRequestsTask getRequestsTask = new GetRequestsTask();
        getRequestsTask.execute();
    }

    private Context getContext() {
        return this;
    }

    public class GetRequestsTask extends AsyncTask<Void, Void, ArrayList<Request>> {

        @Override
        protected void onPreExecute() {
            mProgressBar.setVisibility(View.VISIBLE);
            mRequests.setVisibility(View.GONE);
        }

        @Override
        protected ArrayList<Request> doInBackground(Void... voids) {
            ArrayList<Request> requests = DatabaseHelper.getInstance(getContext()).getRequests();
            return requests;
        }

        @Override
        protected void onPostExecute(final ArrayList<Request> requests) {
            mProgressBar.setVisibility(View.GONE);
            if (!requests.isEmpty()) {
                RequestsAdapater adapater = new RequestsAdapater(requests, getContext());
                mRequests.setAdapter(adapater);
                mRequests.setVisibility(View.VISIBLE);
                mNoRequests.setVisibility(View.GONE);
            } else if (requests.isEmpty()) {
                mNoRequests.setVisibility(View.VISIBLE);
                mRequests.setVisibility(View.GONE);
            }

        }
    }

}
