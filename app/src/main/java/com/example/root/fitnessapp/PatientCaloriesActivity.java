package com.example.root.fitnessapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.root.fitnessapp.models.DailyIntake;
import com.example.root.fitnessapp.models.Patient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by root on 17/06/17.
 */

public class PatientCaloriesActivity extends AppCompatActivity {

    Patient p ;
    LinearLayout patient_calories_container;
    TextView patient_calories_status , patient_calories_name;
    ArrayList<DailyIntake> patientDailyIntake = new ArrayList<>();
    ProgressBar progress_bar;
    TextView bmr ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_calories);

        p = (Patient) getIntent().getExtras().getSerializable("user");
        bmr = (TextView) findViewById(R.id.bmr);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        progress_bar.setVisibility(View.VISIBLE);

        setTitle("");

        double BMR = 0;
        if(p.getGender().equals("Male")){
            BMR = (10 * p.getWeight())+(6.25*p.getHeight()) - ((5 * p.getAge())+5);
        }else{
            BMR = (10 * p.getWeight())+(6.25*p.getHeight()) - ((5 * p.getAge())-161);
        }

        bmr.setText(BMR+"");

        patientDailyIntake.clear();
        FirebaseDatabase.getInstance().getReference("Fitness").child("DailyIntake").orderByChild("patientID").equalTo(p.getUser_id()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("insider", "Ingredient");
                for (DataSnapshot dss : dataSnapshot.getChildren()) {
                    DailyIntake in = dss.getValue(DailyIntake.class);
                    assert in != null;
                    patientDailyIntake.add(in);
                    Log.d("Intake ",in.toString());
                    Log.d("PatientDailyIntake size", patientDailyIntake.size()+"");
                }
                progress_bar.setVisibility(View.GONE);

                patient_calories_container = (LinearLayout) findViewById(R.id.patient_calories_container);
                patient_calories_name = (TextView) findViewById(R.id.patient_calories_name);
                patient_calories_status = (TextView) findViewById(R.id.patient_calories_status);

                assert p != null;
                setTitle(p.getUser_name()+" Calories");
                patient_calories_name.setText(p.getUser_name());
                patient_calories_status.setText(p.getBodytype());

                if(p.getBodytype().equals("Severe Thinness")){
                    patient_calories_status.setTextColor(getResources().getColor(R.color.sever));
                }else if(p.getBodytype().equals("Moderate Thinness")){
                    patient_calories_status.setTextColor(getResources().getColor(R.color.moderate));
                }else if(p.getBodytype().equals("Mild Thinness")){
                    patient_calories_status.setTextColor(getResources().getColor(R.color.mild));
                }else if(p.getBodytype().equals("Normal")){
                    patient_calories_status.setTextColor(getResources().getColor(R.color.normal));
                }else if(p.getBodytype().equals("Overweight")){
                    patient_calories_status.setTextColor(getResources().getColor(R.color.overweight));
                }else if(p.getBodytype().equals("Obese class I")){
                    patient_calories_status.setTextColor(getResources().getColor(R.color.obese_i));
                }else if(p.getBodytype().equals("Obese class II")){
                    patient_calories_status.setTextColor(getResources().getColor(R.color.obese_ii));
                }else if(p.getBodytype().equals("Obese class III")){
                    patient_calories_status.setTextColor(getResources().getColor(R.color.obese_iii));
                }else{
                }

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy");
                String formattedDate = df.format(c.getTime());

                for(int i=patientDailyIntake.size()-1 ; i>=0 ; i--){
                    View v1 = getLayoutInflater().inflate(R.layout.day_item, null);
                    View BreakfastV = getLayoutInflater().inflate(R.layout.day_intake_item, null);
                    View LunchV = getLayoutInflater().inflate(R.layout.day_intake_item, null);
                    View DinnerV = getLayoutInflater().inflate(R.layout.day_intake_item, null);
                    View SnackV = getLayoutInflater().inflate(R.layout.day_intake_item, null);

                    LinearLayout daily = (LinearLayout) v1.findViewById(R.id.daily);
                    TextView day = (TextView) v1.findViewById(R.id.today_date);

                    if (patientDailyIntake.get(i).getIntakeDay().equals(formattedDate)) {
                        day.setText("Today");
                    } else {
                        day.setText(patientDailyIntake.get(i).getIntakeDay());
                    }

                    TextView breakfast = (TextView) BreakfastV.findViewById(R.id.meal);
                    breakfast.setText("Breakfast");

                    TextView lunch = (TextView) LunchV.findViewById(R.id.meal);
                    lunch.setText("Lunch");

                    TextView dinner = (TextView) DinnerV.findViewById(R.id.meal);
                    dinner.setText("Dinner");

                    TextView snacks = (TextView) SnackV.findViewById(R.id.meal);
                    snacks.setText("Snacks");

                    LinearLayout breakfastContainer = (LinearLayout) BreakfastV.findViewById(R.id.ingredients_container);
                    LinearLayout lunchContainer = (LinearLayout) LunchV.findViewById(R.id.ingredients_container);
                    LinearLayout dinnerContainer = (LinearLayout) DinnerV.findViewById(R.id.ingredients_container);
                    LinearLayout snacksContainer = (LinearLayout) SnackV.findViewById(R.id.ingredients_container);

                    for (int j = 0; j < patientDailyIntake.get(i).getBreakfast().size(); j++) {
                        View ingV = getLayoutInflater().inflate(R.layout.ingredient_item, null);

                        TextView name = (TextView) ingV.findViewById(R.id.ingredient_name);
                        TextView quantity = (TextView) ingV.findViewById(R.id.ingredient_quantity);
                        TextView calories = (TextView) ingV.findViewById(R.id.ingredient_calories);

                        name.setText(patientDailyIntake.get(i).getBreakfast().get(j).getIngredient_name());
                        quantity.setText(patientDailyIntake.get(i).getBreakfast().get(j).getQuantity() + "");
                        calories.setText(patientDailyIntake.get(i).getBreakfast().get(j).getCalories() + "");

                        breakfastContainer.addView(ingV);
                    }

                    for (int j = 0; j < patientDailyIntake.get(i).getLunch().size(); j++) {
                        View ingV = getLayoutInflater().inflate(R.layout.ingredient_item, null);

                        TextView name = (TextView) ingV.findViewById(R.id.ingredient_name);
                        TextView quantity = (TextView) ingV.findViewById(R.id.ingredient_quantity);
                        TextView calories = (TextView) ingV.findViewById(R.id.ingredient_calories);

                        name.setText(patientDailyIntake.get(i).getLunch().get(j).getIngredient_name());
                        quantity.setText(patientDailyIntake.get(i).getLunch().get(j).getQuantity() + "");
                        calories.setText(patientDailyIntake.get(i).getLunch().get(j).getCalories() + "");

                        lunchContainer.addView(ingV);
                    }

                    for (int j = 0; j < patientDailyIntake.get(i).getDinner().size(); j++) {
                        View ingV = getLayoutInflater().inflate(R.layout.ingredient_item, null);

                        TextView name = (TextView) ingV.findViewById(R.id.ingredient_name);
                        TextView quantity = (TextView) ingV.findViewById(R.id.ingredient_quantity);
                        TextView calories = (TextView) ingV.findViewById(R.id.ingredient_calories);

                        name.setText(patientDailyIntake.get(i).getDinner().get(j).getIngredient_name());
                        quantity.setText(patientDailyIntake.get(i).getDinner().get(j).getQuantity() + "");
                        calories.setText(patientDailyIntake.get(i).getDinner().get(j).getCalories() + "");

                        dinnerContainer.addView(ingV);
                    }

                    for (int j = 0; j < patientDailyIntake.get(i).getSnacks().size(); j++) {
                        View ingV = getLayoutInflater().inflate(R.layout.ingredient_item, null);

                        TextView name = (TextView) ingV.findViewById(R.id.ingredient_name);
                        TextView quantity = (TextView) ingV.findViewById(R.id.ingredient_quantity);
                        TextView calories = (TextView) ingV.findViewById(R.id.ingredient_calories);

                        name.setText(patientDailyIntake.get(i).getSnacks().get(j).getIngredient_name());
                        quantity.setText(patientDailyIntake.get(i).getSnacks().get(j).getQuantity() + "");
                        calories.setText(patientDailyIntake.get(i).getSnacks().get(j).getCalories() + "");

                        snacksContainer.addView(ingV);
                    }

                    daily.addView(BreakfastV);
                    daily.addView(LunchV);
                    daily.addView(DinnerV);
                    daily.addView(SnackV);

                    View tc = getLayoutInflater().inflate(R.layout.calories_total_consumed, null);
                    TextView total = (TextView) tc.findViewById(R.id.total_calories);
                    TextView consumed = (TextView) tc.findViewById(R.id.consumed_calories);

                    total.setText(patientDailyIntake.get(i).getCaloriesTotal() + "");
                    consumed.setText(patientDailyIntake.get(i).getCaloriesConsumed() + "");

                    daily.addView(tc);

                    View separator = new View(getApplicationContext());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1);
                    lp.setMargins(0,5,0,5);
                    separator.setLayoutParams(lp);
                    separator.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    patient_calories_container.addView(separator);
                    patient_calories_container.addView(daily);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Firebase","you don't have permission");
            }
        });
    }
}
