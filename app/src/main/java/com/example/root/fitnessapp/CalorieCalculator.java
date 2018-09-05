package com.example.root.fitnessapp;

import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.root.fitnessapp.Controllers.AddDailyIntakeFragment;
import com.example.root.fitnessapp.models.DailyIntake;
import com.example.root.fitnessapp.models.Patient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by root on 26/04/17.
 */

public class CalorieCalculator extends Fragment {

    LinearLayout calorie_data_container;
    Button addtodayintake;
    TextView bmr;

    public ArrayList<DailyIntake> patientDailyIntake = new ArrayList<>();

    Patient p;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View vv = inflater.inflate(R.layout.calorie_calculator, container, false);

        getActivity().setTitle("Calorie Calculator");

        final Bundle b = getArguments();

        p = (Patient) b.getSerializable("user");
        patientDailyIntake = MainActivity.PatientDailyIntake;

        bmr = (TextView) vv.findViewById(R.id.bmr);
        calorie_data_container = (LinearLayout) vv.findViewById(R.id.calorie_data_container);
        addtodayintake = (Button) vv.findViewById(R.id.addtodayintake);

        double BMR = 0;
        if(p.getGender().equals("Male")){
            BMR = (10 * p.getWeight())+(6.25*p.getHeight()) - ((5 * p.getAge())+5);
        }else{
            BMR = (10 * p.getWeight())+(6.25*p.getHeight()) - ((5 * p.getAge())-161);
        }

        if(bmr == null){
            Log.d("CalorieCalculator","Object bmr was null");
            bmr = (TextView) vv.findViewById(R.id.bmr);
        }

        try {
            bmr.setText(round(BMR,2) + "");
        }catch(NullPointerException ex){
            Log.d("CalorieCalculator",ex.getLocalizedMessage());
        }

        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy");
        String formattedDate = df.format(c.getTime());

        for (int i = patientDailyIntake.size() - 1; i >= 0; i--) {
            View v1 = getLayoutInflater(savedInstanceState).inflate(R.layout.day_item, null);
            View BreakfastV = getLayoutInflater(savedInstanceState).inflate(R.layout.day_intake_item, null);
            View LunchV = getLayoutInflater(savedInstanceState).inflate(R.layout.day_intake_item, null);
            View DinnerV = getLayoutInflater(savedInstanceState).inflate(R.layout.day_intake_item, null);
            View SnackV = getLayoutInflater(savedInstanceState).inflate(R.layout.day_intake_item, null);

            LinearLayout daily = (LinearLayout) v1.findViewById(R.id.daily);
            final ImageButton check = new ImageButton(getContext());
            TextView day = (TextView) v1.findViewById(R.id.today_date);

            if (patientDailyIntake.get(i).getIntakeDay().equals(formattedDate)) {
                day.setText("Today");
                addtodayintake.setText("You're done for today!");
                addtodayintake.setEnabled(false);
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
                View ingV = getLayoutInflater(savedInstanceState).inflate(R.layout.ingredient_item, null);

                TextView name = (TextView) ingV.findViewById(R.id.ingredient_name);
                TextView quantity = (TextView) ingV.findViewById(R.id.ingredient_quantity);
                TextView calories = (TextView) ingV.findViewById(R.id.ingredient_calories);

                name.setText(patientDailyIntake.get(i).getBreakfast().get(j).getIngredient_name());
                quantity.setText(patientDailyIntake.get(i).getBreakfast().get(j).getQuantity() + "");
                calories.setText(patientDailyIntake.get(i).getBreakfast().get(j).getCalories() + "");

                breakfastContainer.addView(ingV);
            }

            for (int j = 0; j < patientDailyIntake.get(i).getLunch().size(); j++) {
                View ingV = getLayoutInflater(savedInstanceState).inflate(R.layout.ingredient_item, null);

                TextView name = (TextView) ingV.findViewById(R.id.ingredient_name);
                TextView quantity = (TextView) ingV.findViewById(R.id.ingredient_quantity);
                TextView calories = (TextView) ingV.findViewById(R.id.ingredient_calories);

                name.setText(patientDailyIntake.get(i).getLunch().get(j).getIngredient_name());
                quantity.setText(patientDailyIntake.get(i).getLunch().get(j).getQuantity() + "");
                calories.setText(patientDailyIntake.get(i).getLunch().get(j).getCalories() + "");

                lunchContainer.addView(ingV);
            }

            for (int j = 0; j < patientDailyIntake.get(i).getDinner().size(); j++) {
                View ingV = getLayoutInflater(savedInstanceState).inflate(R.layout.ingredient_item, null);

                TextView name = (TextView) ingV.findViewById(R.id.ingredient_name);
                TextView quantity = (TextView) ingV.findViewById(R.id.ingredient_quantity);
                TextView calories = (TextView) ingV.findViewById(R.id.ingredient_calories);

                name.setText(patientDailyIntake.get(i).getDinner().get(j).getIngredient_name());
                quantity.setText(patientDailyIntake.get(i).getDinner().get(j).getQuantity() + "");
                calories.setText(patientDailyIntake.get(i).getDinner().get(j).getCalories() + "");

                dinnerContainer.addView(ingV);
            }

            for (int j = 0; j < patientDailyIntake.get(i).getSnacks().size(); j++) {
                View ingV = getLayoutInflater(savedInstanceState).inflate(R.layout.ingredient_item, null);

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

            View tc = getLayoutInflater(savedInstanceState).inflate(R.layout.calories_total_consumed, null);
            TextView total = (TextView) tc.findViewById(R.id.total_calories);
            TextView consumed = (TextView) tc.findViewById(R.id.consumed_calories);

            total.setText(patientDailyIntake.get(i).getCaloriesTotal() + "");
            consumed.setText(patientDailyIntake.get(i).getCaloriesConsumed() + "");

            daily.addView(tc);

            View separator = new View(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
            lp.setMargins(0, 5, 0, 5);
            separator.setLayoutParams(lp);
            separator.setBackgroundColor(getResources().getColor(android.R.color.transparent));

            LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout horizontal_container = new LinearLayout(getContext());
            horizontal_container.setOrientation(LinearLayout.HORIZONTAL);
            horizontal_container.setLayoutParams(llp);

            horizontal_container.addView(check);
            horizontal_container.addView(daily);

            calorie_data_container.addView(separator);
            calorie_data_container.addView(horizontal_container);

            check.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            check.setBackgroundColor(getResources().getColor(R.color.transparent_button_color));

            if(!patientDailyIntake.get(i).isTaken()){
                check.setImageResource(R.drawable.uncheck);
            }else{
                check.setImageResource(R.drawable.check);
            }

            final int finalI = i;
            check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!patientDailyIntake.get(finalI).isTaken()){
                        check.setImageResource(R.drawable.check);
                        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Fitness").child("DailyIntake").child(patientDailyIntake.get(finalI).getDayID());
                        mRef.child("taken").setValue(true);
                        patientDailyIntake.get(finalI).setTaken(true);

                        // 3500 calories = 1 pound , 2.20462 pounds = 1 kg
                        // 3500 * 2.20462 = 7716.17
                        double newWeight = p.getWeight() + (patientDailyIntake.get(finalI).getCaloriesConsumed() / (7716.17));
                        p.setWeight(newWeight);
                        FirebaseDatabase.getInstance().getReference("Fitness").child("Patient").child(p.getUser_id()).child("weight").setValue(newWeight);
                    }
                }
            });
        }

        if(addtodayintake == null){
            Log.d("CalorieCalculator","Object addtodayintake was null");
            addtodayintake = (Button) vv.findViewById(R.id.addtodayintake);
        }

        try {
            addtodayintake.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddDailyIntakeFragment addDailyIntakeFragment = new AddDailyIntakeFragment();
                    b.putBoolean("taken", true);
                    addDailyIntakeFragment.setArguments(b);
                    addDailyIntakeFragment.show(getFragmentManager(), "Add Daily Intake");
                }
            });
        }catch(NullPointerException ex){}

        return vv;
    }

}
