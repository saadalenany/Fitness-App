package com.example.root.fitnessapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.root.fitnessapp.Controllers.ListAdapter;
import com.example.root.fitnessapp.models.Doctor;
import com.example.root.fitnessapp.models.Patient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by root on 12/06/17.
 */

public class DoctorPatients extends Fragment {

    ListView lv;

    ArrayList<Patient> pats = new ArrayList<>();

    String drName ;
    EditText searchpatient , searchage;
    Spinner searchgender;
    Button go;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.patients, container, false);

        getActivity().setTitle("Patients Calories Page");

        searchpatient = (EditText) v.findViewById(R.id.searchpatient);
        searchage = (EditText) v.findViewById(R.id.searchage);
        searchgender = (Spinner) v.findViewById(R.id.searchgender);
        go = (Button) v.findViewById(R.id.go);

/***********************************************************************************************************/
        // Create an ArrayAdapter using the string array and a default gender spinner layout
        final ArrayAdapter<CharSequence> gadapter = ArrayAdapter.createFromResource(getContext(), R.array.gender,android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        gadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the gender spinner
        searchgender.setAdapter(gadapter);
/***********************************************************************************************************/

        Bundle b = getArguments();
        final Doctor d = (Doctor) b.getSerializable("user");
        assert d != null;
        drName = d.getUser_name();
        Log.d("Logged Doctor ",drName);

        pats = MainActivity.patients;

        lv = (ListView) v.findViewById(R.id.patientslist);
        ListAdapter la = new ListAdapter(getContext(),d,pats);
        lv.setAdapter(la);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!searchpatient.getText().toString().equals("")){
                    lv.setAdapter(null);

                    ArrayList<Patient> ps = new ArrayList<Patient>();
                    for(int i=0 ; i<pats.size() ; i++){
                        if(pats.get(i).getUser_name().contains(searchpatient.getText().toString())){
                            ps.add(pats.get(i));
                        }
                    }
                    ListAdapter la = new ListAdapter(getContext(),d,ps);
                    lv.setAdapter(la);
                }else if(!searchage.getText().toString().equals("")){
                    lv.setAdapter(null);

                    ArrayList<Patient> ps = new ArrayList<Patient>();
                    for(int i=0 ; i<pats.size() ; i++){
                        if(pats.get(i).getAge() == Integer.parseInt(searchage.getText().toString())){
                            ps.add(pats.get(i));
                        }
                    }
                    ListAdapter la = new ListAdapter(getContext(),d,ps);
                    lv.setAdapter(la);
                }else if(!searchgender.getSelectedItem().toString().equals("")){
                    lv.setAdapter(null);

                    ArrayList<Patient> ps = new ArrayList<Patient>();
                    for(int i=0 ; i<pats.size() ; i++){
                        if(pats.get(i).getGender().equals(searchgender.getSelectedItem().toString())){
                            ps.add(pats.get(i));
                        }
                    }
                    ListAdapter la = new ListAdapter(getContext(),d,ps);
                    lv.setAdapter(la);
                }
            }
        });

        return v;
    }
}
