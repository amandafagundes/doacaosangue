package com.teste.elleve.doacaosangue.presenter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.teste.elleve.doacaosangue.R;
import com.teste.elleve.doacaosangue.model.Request;

import java.util.ArrayList;

public class RequestsAdapater extends BaseAdapter {


    private ArrayList<Request> requests;
    private Context context;
    private static LayoutInflater inflater = null;

    public RequestsAdapater(ArrayList<Request> requests, Context context) {
        this.requests = requests;
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return requests.size();
    }

    @Override
    public Object getItem(int position) {
        return requests.get(position);
    }

    @Override
    public long getItemId(int position) {
        return requests.get(position).getId();
    }

    public class Holder {
        private TextView mPatient, mHospital, mBlood;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Holder holder = new Holder();
        convertView = inflater.inflate(R.layout.list_item, null);

        holder.mPatient = (TextView) convertView.findViewById(R.id.patient);
        holder.mHospital = (TextView) convertView.findViewById(R.id.hospital);
        holder.mBlood = (TextView) convertView.findViewById(R.id.blood);

        holder.mPatient.setText(requests.get(position).getPatient());
        holder.mHospital.setText(requests.get(position).getHospital());
        holder.mBlood.setText(requests.get(position).getBloodType());

        return convertView;
    }
}
