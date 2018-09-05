package com.example.root.fitnessapp;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.fitnessapp.models.Doctor;
import com.example.root.fitnessapp.models.Patient;

import java.util.ArrayList;


public class Tab1Fragment extends Fragment implements AdapterView.OnItemSelectedListener {
     TextView e3;
     TextView e4;
     TextView t;
     TextView weight;
     TextView height;
     TextView gender;
     TextView bmr;
    TextView bm;
    TextView viewid;
    public  String g;
    public double age;
    public double k;
    public double h;

     public Doctor d;
     public void setDoctor(Doctor d){
         this.d=d;
     }
     public  Doctor getDoctor(){
        return d;
    }
     Spinner sp;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Get Array of Data From patient


        ArrayList<String> Users_Emails=new ArrayList<String>();
        Users_Emails.clear();
        final ArrayList<Patient> kk= LoginActivity.patients;
        for(int i=0;i<kk.size();i++){
            if(kk.get(i).getPatient_doctor().equals(MainActivity.doctor.getUser_id())) {
                Users_Emails.add(kk.get(i).getUser_email());
            }
        }

        View view = inflater.inflate(R.layout.tab1,container,false);
        e3=(TextView) view.findViewById(R.id.patient_weight_view);
        e4=(TextView) view.findViewById(R.id.patient_height_view);
        t=(TextView)view.findViewById(R.id.patient_gender_view);
        bmr=(TextView) view.findViewById(R.id.bmr_view);

        weight=(TextView)view.findViewById(R.id.patient_weight);
        height=(TextView)view.findViewById(R.id.patient_height);
        gender=(TextView)view.findViewById(R.id.patient_gender);
        viewid=(TextView)view.findViewById(R.id.viewid);
        bm=(TextView)view.findViewById(R.id.textView25);

        Typeface customfont=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Green Avocado-bold.ttf");
        Typeface customfont2=Typeface.createFromAsset(getActivity().getAssets(),"fonts/FallingSkyExtOu.otf");
        Typeface customfont3=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Android Insomnia Regular.ttf");

        weight.setTypeface(customfont);
        height.setTypeface(customfont);
        gender.setTypeface(customfont);
        e3.setTypeface(customfont2);
        e4.setTypeface(customfont2);
        t.setTypeface(customfont2);
        bmr.setTypeface(customfont2);
        viewid.setTypeface(customfont3);
        bm.setTypeface(customfont);

        sp=(Spinner) view.findViewById(R.id.spinner);

        ArrayAdapter<String> adapter= new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Users_Emails);
        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                for (int i=0;i<kk.size();i++){
                    String selected=sp.getSelectedItem().toString();
                    if(kk.get(i).getUser_email().equals(selected)) {

                         k= kk.get(i).getWeight();
                         k=Math.round(k);
                         h= kk.get(i).getHeight();

                         g=kk.get(i).getGender();
                         age=kk.get(i).getAge();
                         e3.setText(k+"");
                         e4.setText(h+"");
                         t.setText(g+"");

                        // Control on user Gender
                        if(g.equals("Male")){
                            //For man, BMR = 10 * weight(kg) + 6.25 * height(cm) - 5 * age(y) + 5
                            double bmrk=10*k+6.25*h -5*age+5;
                            bmrk=Math.round(bmrk);
                            bmr.setText(bmrk+"");
                            Toast.makeText(getContext()," BMR = 10 * weight(kg) + 6.25 * height(cm) - 5 * age(y) + 5",Toast.LENGTH_SHORT).show();
                        }
                        else {
                            //For woman, BMR = 10 * weight(kg) + 6.25 * height(cm) - 5 * age(y) - 161    
                            double bmrs=10*k+6.25*h-5*age-161;
                            bmrs=Math.round(bmrs);
                            bmr.setText(bmrs+"");
                            Toast.makeText(getContext()," BMR = 10 * weight(kg) + 6.25 * height(cm) - 5 * age(y) - 161 ",Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });




        return view;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }


}