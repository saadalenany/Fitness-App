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
import com.example.root.fitnessapp.Controllers.NeutrulistAdapter;
import com.example.root.fitnessapp.models.Neutrulist;
import com.example.root.fitnessapp.models.Patient;

import java.util.ArrayList;

/**
 * Created by root on 21/06/17.
 */

public class NeutrulistPatients extends Fragment {

    EditText searchpatient , searchage;
    Spinner searchgender;
    Button go;
    ListView patientslist;
    ArrayList<Patient> patients = new ArrayList<>();
    Neutrulist n;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.neutrulist_patients , container,false);

        getActivity().setTitle("Patients Food Calories");

        searchpatient = (EditText) v.findViewById(R.id.neutrulist_searchpatient);
        searchage = (EditText) v.findViewById(R.id.neutrulist_searchage);
        searchgender = (Spinner) v.findViewById(R.id.neutrulist_searchgender);
        go = (Button) v.findViewById(R.id.neutrulist_go);

        n = (Neutrulist) getArguments().getSerializable("user");

        patients.clear();
        // Create an ArrayAdapter using the string array and a default gender_spinner layout
        ArrayAdapter<CharSequence> gadapter = ArrayAdapter.createFromResource(getContext(), R.array.gender,android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        gadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        searchgender.setAdapter(gadapter);

        patientslist = (ListView) v.findViewById(R.id.neutrulist_patientslist);

        getNeutrulistPatients();

        NeutrulistAdapter na = new NeutrulistAdapter(getContext(),patients,n);
        patientslist.setAdapter(na);

        go.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!searchpatient.getText().toString().equals("")){Log.d("NeutrulistPatients","Name searched "+searchpatient.getText().toString());
                    patientslist.setAdapter(null);

                    ArrayList<Patient> ps = new ArrayList<Patient>();
                    for(int i=0 ; i<patients.size() ; i++){
                        if(patients.get(i).getUser_name().contains(searchpatient.getText().toString())){
                            ps.add(patients.get(i));
                        }
                    }
                    NeutrulistAdapter na = new NeutrulistAdapter(getContext(),ps,n);
                    patientslist.setAdapter(na);
                }else if(!searchage.getText().toString().equals("")){
                    patientslist.setAdapter(null);

                    ArrayList<Patient> ps = new ArrayList<Patient>();
                    for(int i=0 ; i<patients.size() ; i++){
                        if(patients.get(i).getAge() == Integer.parseInt(searchage.getText().toString())){
                            ps.add(patients.get(i));
                        }
                    }
                    NeutrulistAdapter na = new NeutrulistAdapter(getContext(),ps,n);
                    patientslist.setAdapter(na);
                }else if(!searchgender.getSelectedItem().toString().equals("")){
                    patientslist.setAdapter(null);

                    ArrayList<Patient> ps = new ArrayList<Patient>();
                    for(int i=0 ; i<patients.size() ; i++){
                        if(patients.get(i).getGender().equals(searchgender.getSelectedItem().toString())){
                            ps.add(patients.get(i));
                        }
                    }
                    NeutrulistAdapter na = new NeutrulistAdapter(getContext(),ps,n);
                    patientslist.setAdapter(na);
                }
            }
        });
        return v;
    }

    public void getNeutrulistPatients(){
        for(int i=0 ; i<LoginActivity.patients.size() ; i++){
            if(LoginActivity.patients.get(i).getPatient_doctor().equals(n.getDoctor_id())){
                Log.d("Neutrulist patients",LoginActivity.patients.get(i).toString());
                patients.add(LoginActivity.patients.get(i));
            }
        }
    }
}
