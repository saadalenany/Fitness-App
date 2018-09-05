package com.example.root.fitnessapp.Controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.root.fitnessapp.ChatActivity;
import com.example.root.fitnessapp.NeutrulistIntakeFragment;
import com.example.root.fitnessapp.PatientCaloriesActivity;
import com.example.root.fitnessapp.R;
import com.example.root.fitnessapp.models.Neutrulist;
import com.example.root.fitnessapp.models.Patient;

import java.util.ArrayList;

/**
 * Created by root on 21/06/17.
 */

public class NeutrulistAdapter extends BaseAdapter {

    Bundle b;
    Context con;
    ArrayList<Patient> pats;
    Neutrulist n;

    public NeutrulistAdapter(Context con, ArrayList<Patient> pats, Neutrulist n) {
        this.con = con;
        this.pats = pats;
        this.n = n;
        b = ((FragmentActivity)(con)).getIntent().getExtras();
    }

    @Override
    public int getCount() {
        return pats.size();
    }

    @Override
    public Patient getItem(int position) {
        return pats.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;
        if(v == null){
            v = LayoutInflater.from(con).inflate(R.layout.neutrulist_patient_item , null);
        }

        TextView patientName = (TextView) v.findViewById(R.id.patientName);
        TextView patientStatus = (TextView) v.findViewById(R.id.patientStatus);
        TextView patientgender = (TextView) v.findViewById(R.id.patientgender);
        TextView patientage = (TextView) v.findViewById(R.id.patientage);
        ImageButton patient_history = (ImageButton) v.findViewById(R.id.patient_history);
        ImageButton patient_chat = (ImageButton) v.findViewById(R.id.patient_chat);

        Patient p = getItem(position);
        patientName.setText(p.getUser_name());
        patientStatus.setText(p.getBodytype());
        patientgender.setText(p.getGender());
        patientage.setText(p.getAge()+"");

        final int pos = position;
        patient_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Patient patient = getItem(pos);

                NeutrulistIntakeFragment nif = new NeutrulistIntakeFragment();
                b.putSerializable("user",patient);
                b.putSerializable("neu",n);
                nif.setArguments(b);
                nif.show(((FragmentActivity)(con)).getSupportFragmentManager(),"Patient Daily Intake");
            }
        });

        patient_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Patient pat = getItem(pos);
                Intent i = new Intent(con, ChatActivity.class);
                i.putExtra("type", 3);
                i.putExtra("sender",n);
                i.putExtra("receiver",pat);
                con.startActivity(i);
            }
        });

        return v;
    }
}
