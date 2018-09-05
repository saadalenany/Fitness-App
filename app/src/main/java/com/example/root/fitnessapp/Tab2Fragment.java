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

import com.example.root.fitnessapp.models.Patient;

import java.util.ArrayList;

public class Tab2Fragment extends Fragment implements AdapterView.OnItemSelectedListener {

    Spinner s2;
    TextView t23;
    TextView showAdvice;
    TextView header;
    TextView first_article;
    TextView second_article;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        ArrayList<String> Users_Emails = new ArrayList<String>();
        final ArrayList<Patient> kk = LoginActivity.patients;
        for (int i = 0; i < kk.size(); i++) {

            if (kk.get(i).getPatient_doctor().equals(MainActivity.doctor.getUser_id())) {

                Users_Emails.add(kk.get(i).getUser_email());
            }
        }

        View view = inflater.inflate(R.layout.tab2, container, false);
        s2 = (Spinner) view.findViewById(R.id.spinner2);
        t23 = (TextView) view.findViewById(R.id.second_article);
        showAdvice=(TextView) view.findViewById(R.id.last_article);
        header=(TextView) view.findViewById(R.id.header);
        first_article=(TextView) view.findViewById(R.id.first_article);

        second_article=(TextView) view.findViewById(R.id.second_article);
        Typeface customfont=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Green Avocado-bold.ttf");
        Typeface customfont2=Typeface.createFromAsset(getActivity().getAssets(),"fonts/FallingSkyExtOu.otf");
        Typeface customfont4=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Rurable_PersonalUseOnly.ttf");

        header.setTypeface(customfont4);
                 first_article.setTypeface(customfont);
                 second_article.setTypeface(customfont);
                 showAdvice.setTypeface(customfont2);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Users_Emails);
        s2.setAdapter(adapter);
        s2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                for (int i=0;i<kk.size();i++){
                    String selected=s2.getSelectedItem().toString();
                    if(kk.get(i).getUser_email().equals(selected)) {

                        double body = kk.get(i).getBMI();
                        t23.setText(body+"");

                        if (kk.get(i).getBodytype().equals("Normal")) {
                            showAdvice.setText("This patient Appear very Good" +
                                    " "+"His Height is Good With His Weight" +
                                    "Advice: Keep Fitness");
                        }

                        else  if(kk.get(i).getBodytype().equals("Severe Thinness")){
                            showAdvice.setText("This Patient Appear very" +
                                    "Thiness" +
                                    "Advice: a. Try not to lower your calorie intake by more than 1,000 calories per day.");

                        }

                        else  if(kk.get(i).getBodytype().equals("over_weight")){
                            showAdvice.setText("This Patient Appear very" +
                                    "Over in Weight" +
                                    "Advice: "
                                    +"c. Try to maintain your level of fiber intake and balance your other nutritional needs ");
                        }

                        else
                            showAdvice.setText("b. Try to lower your calorie intake gradually.");
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