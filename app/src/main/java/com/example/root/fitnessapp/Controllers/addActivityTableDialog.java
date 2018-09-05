package com.example.root.fitnessapp.Controllers;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.fitnessapp.MainActivity;
import com.example.root.fitnessapp.R;
import com.example.root.fitnessapp.models.ActivityTable;
import com.example.root.fitnessapp.models.Doctor;
import com.example.root.fitnessapp.models.Patient;
import com.example.root.fitnessapp.models.SingleActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by root on 20/06/17.
 */

public class addActivityTableDialog extends DialogFragment {

    TextView activity_date;
    Button newactivity , delactivity;
    LinearLayout table_container;
    Button submit_table;

    Patient p;
    Doctor d;

    ArrayList<String> activities = new ArrayList<>();

    ArrayList<Spinner> spinners = new ArrayList<>();
    ArrayList<EditText> durations = new ArrayList<>();
    ArrayList<EditText> times = new ArrayList<>();

    ArrayAdapter adapter;

    String curDate;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.add_activity_table,container,false);

        activity_date = (TextView) v.findViewById(R.id.activity_date);
        newactivity = (Button) v.findViewById(R.id.newactivity);
        delactivity = (Button) v.findViewById(R.id.delactivity);
        table_container = (LinearLayout) v.findViewById(R.id.table_container);
        submit_table = (Button) v.findViewById(R.id.submit_table);

        d = (Doctor) getArguments().getSerializable("doctor");
        p = (Patient) getArguments().getSerializable("patient");

        getDialog().setTitle("Activity for "+p.getUser_name());

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy");
        curDate = df.format(c.getTime());

        activity_date.setText(curDate);

        activities.add("Golf");
        activities.add("Walk");
        activities.add("Kayaking");
        activities.add("Softball/Baseball");
        activities.add("Swimming");
        activities.add("Tennis");
        activities.add("Running");
        activities.add("Bicycling");
        activities.add("Basketball");
        activities.add("Soccer");

        // Create an ArrayAdapter using the string array and a default activity_spinner layout
        adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, activities);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        Log.d("AddActivityTable","table size "+table_container.getChildCount());

        newactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view = LayoutInflater.from(getContext()).inflate(R.layout.activity_row,null);

                Spinner spin = (Spinner) view.findViewById(R.id.activity_spinner);
                spin.setAdapter(adapter);
                EditText duration = (EditText) view.findViewById(R.id.duration);
                EditText time = (EditText) view.findViewById(R.id.times);

                spinners.add(spin);
                durations.add(duration);
                times.add(time);

                table_container.addView(view);
                Log.d("AddActivityTable","table size "+table_container.getChildCount());
            }
        });

        delactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinners.size() > 0){
                    spinners.remove(spinners.size()-1);
                    durations.remove(durations.size()-1);
                    times.remove(times.size()-1);

                    table_container.removeViewAt(table_container.getChildCount()-1);
                }
            }
        });

        submit_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinners.size() > 0) {
                    ArrayList<SingleActivity> arr = new ArrayList<SingleActivity>();
                    for(int i=0 ; i<spinners.size() ; i++){
                        if(durations.get(i).getText().equals("") || times.get(i).getText().equals("")){
                            Toast.makeText(getContext(), "please fill the empty fields!", Toast.LENGTH_SHORT).show();
                        }else{
                            SingleActivity sa = new SingleActivity(spinners.get(i).getSelectedItem().toString(),Double.parseDouble(durations.get(i).getText().toString()),Integer.parseInt(times.get(i).getText().toString()));
                            arr.add(sa);
                        }
                    }
                    if(arr.size() > 0){
                        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Fitness").child("Activity").push();
                        ActivityTable at = new ActivityTable(mRef.getKey(),d.getUser_id(),p.getUser_id(),curDate,arr,false);
                        mRef.setValue(at);

                        Intent i = new Intent(getContext(),MainActivity.class);
                        i.putExtra("type",1);
                        i.putExtra("user",d);
                        startActivity(i);
                    }
                }
            }
        });

        return v;
    }

}
