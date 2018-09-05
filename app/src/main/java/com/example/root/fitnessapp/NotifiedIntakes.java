package com.example.root.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.root.fitnessapp.models.DailyIntake;
import com.example.root.fitnessapp.models.Doctor;
import com.example.root.fitnessapp.models.Patient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by root on 27/06/17.
 */

public class NotifiedIntakes extends AppCompatActivity {

    TextView patient_calories_name;
    TextView patient_calories_status;
    TextView bmr;
    ProgressBar progress_bar;
    LinearLayout patient_calories_container;

    public ArrayList<DailyIntake> patientDailyIntake = new ArrayList<>();

    Patient p;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_calories);

        patient_calories_container = (LinearLayout) findViewById(R.id.patient_calories_container);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);
        bmr = (TextView) findViewById(R.id.bmr);
        patient_calories_name = (TextView) findViewById(R.id.patient_calories_name);
        patient_calories_status = (TextView) findViewById(R.id.patient_calories_status);

        p = (Patient) getIntent().getExtras().getSerializable("user");

        double BMR;
        if(p.getGender().equals("Male")){
            BMR = (10 * p.getWeight())+(6.25*p.getHeight()) - ((5 * p.getAge())+5);
        }else{
            BMR = (10 * p.getWeight())+(6.25*p.getHeight()) - ((5 * p.getAge())-161);
        }

        bmr.setText(BMR+"");

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy");
        String formattedDate = df.format(c.getTime());

        for (int i = patientDailyIntake.size() - 1; i >= 0; i--) {
            View v1 = getLayoutInflater().inflate(R.layout.notified_day_item, null);
            View BreakfastV = getLayoutInflater().inflate(R.layout.day_intake_item, null);
            View LunchV = getLayoutInflater().inflate(R.layout.day_intake_item, null);
            View DinnerV = getLayoutInflater().inflate(R.layout.day_intake_item, null);
            View SnackV = getLayoutInflater().inflate(R.layout.day_intake_item, null);

            LinearLayout daily = (LinearLayout) v1.findViewById(R.id.daily);
            final ImageButton check = (ImageButton) v1.findViewById(R.id.check);
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
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
            lp.setMargins(0, 5, 0, 5);
            separator.setLayoutParams(lp);
            separator.setBackgroundColor(getResources().getColor(android.R.color.transparent));

            patient_calories_container.addView(separator);
            patient_calories_container.addView(v1);

            if(!patientDailyIntake.get(i).isTaken()){
                check.setImageResource(R.drawable.check);
            }else{
                check.setImageResource(R.drawable.uncheck);
            }

            final int finalI = i;
            check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!patientDailyIntake.get(finalI).isTaken()){
                        check.setImageResource(R.drawable.check);
                        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Fitness").child("DailyIntake").child(patientDailyIntake.get(finalI).getDayID());
                        mRef.child("taken").setValue(true);
                    }else{
                        check.setImageResource(R.drawable.uncheck);
                        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Fitness").child("DailyIntake").child(patientDailyIntake.get(finalI).getDayID());
                        mRef.child("taken").setValue(false);
                    }
                }
            });
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        p = (Patient) intent.getExtras().getSerializable("user");
    }

}
