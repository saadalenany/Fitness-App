package com.example.root.fitnessapp.Controllers;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.root.fitnessapp.R;
import com.example.root.fitnessapp.models.Doctor;
import com.example.root.fitnessapp.models.Patient;

import java.util.List;

/**
 * Created by root on 18/06/17.
 */

public class SensoryListAdapter extends BaseAdapter{

    Context con;
    Doctor d ;
    List<Patient> listofvalues;
    Patient p;

    public SensoryListAdapter(Context context, Doctor d , List<Patient> items) {
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(con);
            v = vi.inflate(R.layout.sensory_list_item, null);
        }

        p = getItem(position);
        if (p != null) {

            TextView name = (TextView) v.findViewById(R.id.patientName);
            TextView status = (TextView) v.findViewById(R.id.patientStatus);

            ImageButton sensory = (ImageButton) v.findViewById(R.id.patient_sensory);
            ImageButton activity = (ImageButton) v.findViewById(R.id.add_activity);

            name.setText(p.getUser_name());
            status.setText(p.getBodytype());

            final int pos = position;
            sensory.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    p = getItem(pos);
                    Bundle b = new Bundle();
                    b.putString("patientID",p.getUser_id());
                    SensoryPopUpDialog spud = new SensoryPopUpDialog();
                    spud.setArguments(b);
                    spud.show(((FragmentActivity) con).getSupportFragmentManager(),p.getUser_name()+" Sensory Data");
                }
            });

            activity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //open activities conversation activity
                    p = getItem(pos);
                    Bundle b = new Bundle();
                    b.putSerializable("patient",p);
                    b.putSerializable("doctor",d);
                    ActivitiesFragment af = new ActivitiesFragment();
                    af.setArguments(b);
                    af.show(((FragmentActivity) con).getSupportFragmentManager(),p.getUser_name()+" Activities");
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

        return  v;
    }
}
