package com.example.root.fitnessapp;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.root.fitnessapp.models.DailyIntake;
import com.example.root.fitnessapp.models.Patient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class Tab3Fragment extends Fragment implements AdapterView.OnItemSelectedListener {
    Spinner s3;
    double bmrk;
    TextView tv;
    TextView topping;
    TextView dl;
    TextView bmring;
    TextView bmr_in_tab3;

    LinearLayout daysIntakeCalories;

//    ArrayList<DailyIntake> SinglePatientIntake = new ArrayList<>();

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab3,container,false);
        s3= (Spinner) view.findViewById(R.id.spinner3);
        tv=(TextView) view.findViewById(R.id.bmr_in_tab3);
        bmring=(TextView) view.findViewById(R.id.bmring);
        bmr_in_tab3=(TextView) view.findViewById(R.id.bmr_in_tab3);
        Typeface customfont=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Green Avocado-bold.ttf");
        Typeface customfont2=Typeface.createFromAsset(getActivity().getAssets(),"fonts/FallingSkyExtOu.otf");
        Typeface customfont3=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Android Insomnia Regular.ttf");

        topping=(TextView) view.findViewById(R.id.toping);
        topping.setTypeface(customfont);
        bmr_in_tab3.setTypeface(customfont2);
        bmring.setTypeface(customfont3);
        daysIntakeCalories = (LinearLayout) view.findViewById(R.id.daysIntakeCalories);


        ArrayList<String> Users_Emails = new ArrayList<String>();
        final ArrayList<Patient> kk = LoginActivity.patients;
        for (int i = 0; i < kk.size(); i++) {
            if (kk.get(i).getPatient_doctor().equals(MainActivity.doctor.getUser_id())) {
                Users_Emails.add(kk.get(i).getUser_email());
            }
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, Users_Emails);
        s3.setAdapter(adapter);
        s3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                daysIntakeCalories.removeAllViews();
                for (int i=0;i<kk.size();i++){
                    String selected=s3.getSelectedItem().toString();
                    if(kk.get(i).getUser_email().equals(selected)) {

//                        getPatientIngredients(kk.get(i).getUser_id());

                        String g = kk.get(i).getGender();
                        double bmi = kk.get(i).getBMI();
                        double weight = kk.get(i).getWeight();
                        double height = kk.get(i).getHeight();
                        double age = kk.get(i).getAge();

                        if(g.equals("Male")){
                            //For man, BMR = 10 * weight(kg) + 6.25 * height(cm) - 5 * age(y) + 5
                            bmrk=10*weight+6.25*height -5*age+5;
                            bmrk=Math.round(bmrk);
                        }
                        else {
                            //For woman, BMR = 10 * weight(kg) + 6.25 * height(cm) - 5 * age(y) - 161    
                             bmrk=10*weight+6.25*height-5*age-162;
                             bmrk= Math.round(bmrk);
                        }

                        final int finalI = i;
                        FirebaseDatabase.getInstance().getReference("Fitness").child("DailyIntake").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Log.d("insider", "DailyIntake");
                                for (DataSnapshot dss : dataSnapshot.getChildren()) {
                                    DailyIntake in = dss.getValue(DailyIntake.class);
                                    assert in != null;
                                    if(in.getPatientID().equals(kk.get(finalI).getUser_id())){
                                        Log.d("Intake ",in.toString());
                                        View view1 = getLayoutInflater(savedInstanceState).inflate(R.layout.total_calories_intake_perday,null);
                                        //CalIn  is equal to the total summation of calarious intaken
                                        //per Day

                                        TextView day = (TextView) view1.findViewById(R.id.calorie_day);
                                        TextView total = (TextView) view1.findViewById(R.id.total_calrious);
                                        TextView consumed = (TextView) view1.findViewById(R.id.consumed_calrious);

                                        day.setText(in.getIntakeDay());
                                        total.setText(round(in.getCaloriesTotal(),2)+"");
                                        consumed.setText(round(in.getCaloriesConsumed(),2)+"");

                                        daysIntakeCalories.addView(view1);
                                    }
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

                        //Cal2lu intake  (Is Equal To BMR)
                        tv.setText(bmrk+"");
                        break;
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

//    public void getPatientIngredients(final String pid){
////        SinglePatientIntake.clear();
//        FirebaseDatabase.getInstance().getReference("Fitness").child("DailyIntake").addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.d("insider", "DailyIntake");
//                for (DataSnapshot dss : dataSnapshot.getChildren()) {
//                    DailyIntake in = dss.getValue(DailyIntake.class);
//                    assert in != null;
//                    if(in.getPatientID().equals(pid)){
////                        SinglePatientIntake.add(in);
//                        Log.d("Intake ",in.toString());
////                        Log.d("PatientDailyIntake size", SinglePatientIntake.size()+"");
//                    }
//                }
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//            }
//        });
//    }
//
}