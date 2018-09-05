package com.example.root.fitnessapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import com.example.root.fitnessapp.Controllers.ListAdapter;
import com.example.root.fitnessapp.Controllers.SensoryListAdapter;
import com.example.root.fitnessapp.models.Doctor;
import com.example.root.fitnessapp.models.Patient;

import java.util.ArrayList;

/**
 * Created by root on 18/06/17.
 */

public class PatientsSensoryData extends Fragment {

    EditText search_patient;
    Button search_sensory;
    ListView patients_sensory_list;
    ArrayList<Patient> pats = new ArrayList<>();
    Doctor d;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.patients_sensory_data, container, false);

        getActivity().setTitle("ECG Data");

        search_patient = (EditText) v.findViewById(R.id.search_patient);
        search_sensory = (Button) v.findViewById(R.id.search_sensory);
        patients_sensory_list = (ListView) v.findViewById(R.id.patients_sensory_list);

        Bundle b = getArguments();
        if(b.getInt("type") == 1){
            d = (Doctor) b.getSerializable("user");
        }
        assert d != null;
        Log.d("Logged Doctor ",d.getUser_name());

        pats = MainActivity.patients;

        SensoryListAdapter sla = new SensoryListAdapter(getContext(),d,pats);
        patients_sensory_list.setAdapter(sla);

        search_sensory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!search_patient.getText().toString().equals("")){
                    patients_sensory_list.setAdapter(null);

                    ArrayList<Patient> ps = new ArrayList<Patient>();
                    for(int i=0 ; i<pats.size() ; i++){
                        if(pats.get(i).getUser_name().contains(search_patient.getText().toString())){
                            ps.add(pats.get(i));
                        }
                    }
                    SensoryListAdapter la = new SensoryListAdapter(getContext(),d,ps);
                    patients_sensory_list.setAdapter(la);
                }
            }
        });

        return v;
    }
}
