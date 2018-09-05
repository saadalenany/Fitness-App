package com.example.root.fitnessapp.Controllers;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.root.fitnessapp.ChatActivity;
import com.example.root.fitnessapp.PatientCaloriesActivity;
import com.example.root.fitnessapp.R;
import com.example.root.fitnessapp.models.Doctor;
import com.example.root.fitnessapp.models.Ingredient;
import com.example.root.fitnessapp.models.Patient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 26/04/17.
 */

public class ListAdapter extends BaseAdapter{

    Context con;
    Doctor d ;
    List<Patient> listofvalues;
    Patient p;

    public ListAdapter(Context context, Doctor d , List<Patient> items) {
        super();
        con = context;
        this.d = d;
        listofvalues = items;
    }

    @Override
    public int getCount() {
        return listofvalues.size();
    }

    @Override
    public Patient getItem(int position) {
        return listofvalues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(con);
            v = vi.inflate(R.layout.list_item, null);
        }

        p = getItem(position);

        if (p != null) {

            TextView name = (TextView) v.findViewById(R.id.patientName);
            TextView status = (TextView) v.findViewById(R.id.patientStatus);
            TextView gender = (TextView) v.findViewById(R.id.patientgender);
            TextView age = (TextView) v.findViewById(R.id.patientage);
            TextView calories = (TextView) v.findViewById(R.id.patient_calories);

            ImageButton message = (ImageButton) v.findViewById(R.id.message);

            name.setText(p.getUser_name());
            status.setText(p.getBodytype());
            gender.setText(p.getGender());
            age.setText(p.getAge()+"");

            final int pos = position;
            calories.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    p = getItem(pos);
                    Log.d("pressed patient",p.toString());
                    Intent i = new Intent(con, PatientCaloriesActivity.class);
                    i.putExtra("user",p);
                    con.startActivity(i);
                }
            });

            message.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    p = getItem(pos);
                    Intent i = new Intent(con, ChatActivity.class);
                    i.putExtra("type", 1);
                    i.putExtra("sender",d);
                    i.putExtra("receiver",p);
                    con.startActivity(i);
                }
            });

            switch (p.getBodytype()) {
                case "Severe Thinness":
                    status.setTextColor(con.getResources().getColor(R.color.sever));
                    break;
                case "Moderate Thinness":
                    status.setTextColor(con.getResources().getColor(R.color.moderate));
                    break;
                case "Mild Thinness":
                    status.setTextColor(con.getResources().getColor(R.color.mild));
                    break;
                case "Normal":
                    status.setTextColor(con.getResources().getColor(R.color.normal));
                    break;
                case "Overweight":
                    status.setTextColor(con.getResources().getColor(R.color.overweight));
                    break;
                case "Obese class I":
                    status.setTextColor(con.getResources().getColor(R.color.obese_i));
                    break;
                case "Obese class II":
                    status.setTextColor(con.getResources().getColor(R.color.obese_ii));
                    break;
                case "Obese class III":
                    status.setTextColor(con.getResources().getColor(R.color.obese_iii));
                    break;
                default:
                    break;
            }

        }

        return v;
    }

}
