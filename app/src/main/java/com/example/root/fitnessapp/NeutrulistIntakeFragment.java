package com.example.root.fitnessapp;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.fitnessapp.Controllers.AddDailyIntakeFragment;
import com.example.root.fitnessapp.models.DailyIntake;
import com.example.root.fitnessapp.models.Ingredient;
import com.example.root.fitnessapp.models.Neutrulist;
import com.example.root.fitnessapp.models.Patient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by root on 26/06/17.
 */

public class NeutrulistIntakeFragment extends DialogFragment {

    TextView weight;
    TextView bmr;
    TextView bmi;
    Button addtodayintake;
    ArrayList<DailyIntake> patientDailyIntake = new ArrayList<>();
    LinearLayout patient_calories_container;
    ProgressBar progress_bar;

    Neutrulist n;
    Patient p;
    Bundle b;

    boolean[] clicked;
    boolean todayFlag = false;

    ArrayList<String> food = new ArrayList<>();
    HashMap<String,Integer> ing = new HashMap<>();

    ArrayAdapter<String> dadapter;
    List<String> indexes;

    ArrayList<Ingredient> BreakfastData = new ArrayList<>();
    ArrayList<Ingredient> LunchData = new ArrayList<>();
    ArrayList<Ingredient> DinnerData = new ArrayList<>();
    ArrayList<Ingredient> SnacksData = new ArrayList<>();

    double totalCalories;
    double consumedCalories;

    HashMap<Spinner,EditText> breakfastfields = new HashMap<>();
    HashMap<Spinner,EditText> lunchfields = new HashMap<>();
    HashMap<Spinner,EditText> dinnerfields = new HashMap<>();
    HashMap<Spinner,EditText> snacksfields = new HashMap<>();

    EditText plan;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.neutrulist_intake_fragment , container,false);
        weight = (TextView) v.findViewById(R.id.weight);
        bmi = (TextView) v.findViewById(R.id.bmi);
        plan = (EditText) v.findViewById(R.id.plan);

        food.add("Apple");
        food.add("Banana");
        food.add("Grape");
        food.add("Mango");
        food.add("Orange");
        food.add("Peach");
        food.add("Pineapple");
        food.add("Strawberry");
        food.add("Asparagus");
        food.add("Bean curd");
        food.add("Broccoli");
        food.add("Carrots");
        food.add("Cucumber");
        food.add("Eggplant");
        food.add("Lettuce");
        food.add("Tomato");
        food.add("Beef, regular, cooked");
        food.add("Chicken, cooked");
        food.add("Egg");
        food.add("Fish, Catfish, cooked");
        food.add("Pork, cooked");
        food.add("Shrimp, cooked");
        food.add("Bread, regular");
        food.add("Butter");
        food.add("Caesar salad");
        food.add("Cheeseburger");
        food.add("Chocolate");
        food.add("Corn");
        food.add("Hamburger");
        food.add("Pizza");
        food.add("Potato (uncooked)");
        food.add("Rice, cooked");
        food.add("Sandwich");
        food.add("Beer, regular");
        food.add("Coca-Cola Classic");
        food.add("Diet Coke");
        food.add("Milk, low-fat (1%)");
        food.add("Milk, low-fat (2%)");
        food.add("Milk, whole");
        food.add("Orange Juice / Apple Cider");
        food.add("Yogurt, low-fat");
        food.add("Yogurt, non-fat");
/***************************************************************/
        ing.put("Apple",80);
        ing.put("Banana",101);
        ing.put("Grape",2);
        ing.put("Mango",135);
        ing.put("Orange",71);
        ing.put("Peach",38);
        ing.put("Pineapple",80);
        ing.put("Strawberry",53);
        ing.put("Watermelon",45);
        ing.put("Asparagus",36);
        ing.put("Bean curd",81);
        ing.put("Broccoli",40);
        ing.put("Carrots",45);
        ing.put("Cucumber",30);
        ing.put("Eggplant",38);
        ing.put("Lettuce",7);
        ing.put("Tomato",29);
        ing.put("Beef, regular, cooked",120);
        ing.put("Chicken, cooked",95);
        ing.put("Egg",79);
        ing.put("Fish, Catfish, cooked",80);
        ing.put("Pork, cooked",130);
        ing.put("Shrimp, cooked",70);
        ing.put("Bread, regular",75);
        ing.put("Butter",102);
        ing.put("Caesar salad",360);
        ing.put("Cheeseburger",360);
        ing.put("Chocolate",150);
        ing.put("Corn",140);
        ing.put("Hamburger",280);
        ing.put("Pizza",180);
        ing.put("Potato (uncooked)",120);
        ing.put("Rice, cooked",225);
        ing.put("Sandwich",310);
        ing.put("Beer, regular",150);
        ing.put("Coca-Cola Classic",97);
        ing.put("Diet Coke",3);
        ing.put("Milk, low-fat (1%)",104);
        ing.put("Milk, low-fat (2%)",121);
        ing.put("Milk, whole",150);
        ing.put("Orange Juice / Apple Cider",115);
        ing.put("Yogurt, low-fat",200);
        ing.put("Yogurt, non-fat",150);
/***************************************************************/

        indexes = new ArrayList<String>(ing.keySet());

        b = savedInstanceState;

        p = (Patient) getArguments().getSerializable("user");
        n = (Neutrulist) getArguments().getSerializable("neu");

        bmr = (TextView) v.findViewById(R.id.bmr);
        addtodayintake = (Button) v.findViewById(R.id.addtodayintake);
        patient_calories_container = (LinearLayout) v.findViewById(R.id.patient_calories_container);
        progress_bar = (ProgressBar) v.findViewById(R.id.progress_bar);
        progress_bar.setVisibility(View.VISIBLE);

        double BMR;
        if(p.getGender().equals("Male")){
            BMR = (10 * p.getWeight())+(6.25*p.getHeight()) - ((5 * p.getAge())+5);
        }else{
            BMR = (10 * p.getWeight())+(6.25*p.getHeight()) - ((5 * p.getAge())-161);
        }

        dadapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, food);

        getDialog().setTitle("Calories Intake");

        bmr.setText(BMR+"");
        weight.setText(round(p.getWeight(),2)+"");
        addtodayintake.setText("Add daily food for "+p.getUser_name());
        bmi.setText(p.getBMI()+"");
        plan.setText(p.getPlandays()+"");

//        int diff=0;
//        int idealW = (int) (22* (p.getHeight()*p.getHeight()));
//        if(idealW > p.getWeight() ){
//            diff = (int) (idealW - p.getWeight());
//        }else if(idealW < p.getWeight()){
//            diff = (int) (p.getWeight() - idealW);
//        }else{
//        }

        patientDailyIntake.clear();
        FirebaseDatabase.getInstance().getReference("Fitness").child("DailyIntake").orderByChild("patientID").equalTo(p.getUser_id()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("insider", "Ingredient");

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy");
                String formattedDate = df.format(c.getTime());

                for (DataSnapshot dss : dataSnapshot.getChildren()) {
                    DailyIntake in = dss.getValue(DailyIntake.class);
                    assert in != null;
                    patientDailyIntake.add(in);
                    Log.d("Intake ",in.toString());
                    Log.d("PatientDailyIntake size", patientDailyIntake.size()+"");
                }

                progress_bar.setVisibility(View.GONE);
                clicked = new boolean[patientDailyIntake.size()];
                for(int i=0 ; i<patientDailyIntake.size() ; i++){
                    clicked[i] = true;
                }

                for(int i=patientDailyIntake.size()-1 ; i>=0 ; i--){
                    final View v1 = getLayoutInflater(b).inflate(R.layout.day_item, null);
                    View BreakfastV = getLayoutInflater(b).inflate(R.layout.day_intake_item, null);
                    View LunchV = getLayoutInflater(b).inflate(R.layout.day_intake_item, null);
                    View DinnerV = getLayoutInflater(b).inflate(R.layout.day_intake_item, null);
                    View SnackV = getLayoutInflater(b).inflate(R.layout.day_intake_item, null);

                    final LinearLayout daily = (LinearLayout) v1.findViewById(R.id.daily);
                    TextView day = (TextView) v1.findViewById(R.id.today_date);

                    if(patientDailyIntake.get(i).getIntakeDay().equals(formattedDate)){
                        day.setText("Today");
                        addtodayintake.setText("This patient is done for today!");
                        addtodayintake.setEnabled(false);
                        todayFlag = true;
                    }else{
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
                        View ingV = getLayoutInflater(b).inflate(R.layout.ingredient_item, null);

                        TextView name = (TextView) ingV.findViewById(R.id.ingredient_name);
                        TextView quantity = (TextView) ingV.findViewById(R.id.ingredient_quantity);
                        TextView calories = (TextView) ingV.findViewById(R.id.ingredient_calories);

                        name.setText(patientDailyIntake.get(i).getBreakfast().get(j).getIngredient_name());
                        quantity.setText(patientDailyIntake.get(i).getBreakfast().get(j).getQuantity() + "");
                        calories.setText(patientDailyIntake.get(i).getBreakfast().get(j).getCalories() + "");

                        breakfastContainer.addView(ingV);
                    }

                    for (int j = 0; j < patientDailyIntake.get(i).getLunch().size(); j++) {
                        View ingV = getLayoutInflater(b).inflate(R.layout.ingredient_item, null);

                        TextView name = (TextView) ingV.findViewById(R.id.ingredient_name);
                        TextView quantity = (TextView) ingV.findViewById(R.id.ingredient_quantity);
                        TextView calories = (TextView) ingV.findViewById(R.id.ingredient_calories);

                        name.setText(patientDailyIntake.get(i).getLunch().get(j).getIngredient_name());
                        quantity.setText(patientDailyIntake.get(i).getLunch().get(j).getQuantity() + "");
                        calories.setText(patientDailyIntake.get(i).getLunch().get(j).getCalories() + "");

                        lunchContainer.addView(ingV);
                    }

                    for (int j = 0; j < patientDailyIntake.get(i).getDinner().size(); j++) {
                        View ingV = getLayoutInflater(b).inflate(R.layout.ingredient_item, null);

                        TextView name = (TextView) ingV.findViewById(R.id.ingredient_name);
                        TextView quantity = (TextView) ingV.findViewById(R.id.ingredient_quantity);
                        TextView calories = (TextView) ingV.findViewById(R.id.ingredient_calories);

                        name.setText(patientDailyIntake.get(i).getDinner().get(j).getIngredient_name());
                        quantity.setText(patientDailyIntake.get(i).getDinner().get(j).getQuantity() + "");
                        calories.setText(patientDailyIntake.get(i).getDinner().get(j).getCalories() + "");

                        dinnerContainer.addView(ingV);
                    }

                    for (int j = 0; j < patientDailyIntake.get(i).getSnacks().size(); j++) {
                        View ingV = getLayoutInflater(b).inflate(R.layout.ingredient_item, null);

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

                    View tc = getLayoutInflater(b).inflate(R.layout.calories_total_consumed, null);
                    TextView total = (TextView) tc.findViewById(R.id.total_calories);
                    TextView consumed = (TextView) tc.findViewById(R.id.consumed_calories);

                    tot = patientDailyIntake.get(i).getCaloriesTotal();
                    total.setText(patientDailyIntake.get(i).getCaloriesTotal() + "");
                    consumed.setText(patientDailyIntake.get(i).getCaloriesConsumed() + "");

                    daily.addView(tc);

                    View separator = new View(getContext());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1);
                    lp.setMargins(0,5,0,5);
                    separator.setLayoutParams(lp);
                    separator.setBackgroundColor(getResources().getColor(android.R.color.transparent));

                    if(clicked[i]) {
                        if (!todayFlag) {
                            final int finalI = i;
                            daily.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    clicked[finalI] = false;
                                    setEditable(daily, finalI);
                                }
                            });
                        }
                    }

                    patient_calories_container.addView(separator);
                    patient_calories_container.addView(v1);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("Firebase","you don't have permission");
            }
        });

        if(todayFlag){
            addtodayintake.setEnabled(false);
            addtodayintake.setText("This patient is up for today");
        }else{
            addtodayintake.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AddDailyIntakeFragment addDailyIntakeFragment = new AddDailyIntakeFragment();
                    Bundle b1 = getArguments();
                    b1.putBoolean("taken",false);
                    b1.putSerializable("user",p);
                    b1.putSerializable("neu",n);
                    addDailyIntakeFragment.setArguments(b1);
                    addDailyIntakeFragment.show(getFragmentManager(),"Add Daily Intake");
                }
            });
        }

        return v;
    }

    double tot;
    public void setEditable(LinearLayout daily , int i){
        daily.removeAllViews();
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy");
        final String formattedDate = df.format(c.getTime());

        View BreakfastV = getLayoutInflater(b).inflate(R.layout.modified_day_intake_item, null);
        View LunchV = getLayoutInflater(b).inflate(R.layout.modified_day_intake_item, null);
        View DinnerV = getLayoutInflater(b).inflate(R.layout.modified_day_intake_item, null);
        View SnackV = getLayoutInflater(b).inflate(R.layout.modified_day_intake_item, null);

        TextView breakfast = (TextView) BreakfastV.findViewById(R.id.meal);
        breakfast.setText("Breakfast");

        TextView lunch = (TextView) LunchV.findViewById(R.id.meal);
        lunch.setText("Lunch");

        TextView dinner = (TextView) DinnerV.findViewById(R.id.meal);
        dinner.setText("Dinner");

        TextView snacks = (TextView) SnackV.findViewById(R.id.meal);
        snacks.setText("Snacks");

        final LinearLayout breakfastContainer = (LinearLayout) BreakfastV.findViewById(R.id.ingredients_container);
        final LinearLayout lunchContainer = (LinearLayout) LunchV.findViewById(R.id.ingredients_container);
        final LinearLayout dinnerContainer = (LinearLayout) DinnerV.findViewById(R.id.ingredients_container);
        final LinearLayout snacksContainer = (LinearLayout) SnackV.findViewById(R.id.ingredients_container);

        breakfastfields.clear();
        lunchfields.clear();
        dinnerfields.clear();
        snacksfields.clear();

        for (int j = 0; j < patientDailyIntake.get(i).getBreakfast().size(); j++) {
            View ingV = getLayoutInflater(b).inflate(R.layout.modified_ingredient_item, null);

            Spinner name = (Spinner) ingV.findViewById(R.id.ingredient_name);
            EditText quantity = (EditText) ingV.findViewById(R.id.ingredient_quantity);

            name.setAdapter(dadapter);
            name.setSelection(indexes.indexOf(patientDailyIntake.get(i).getBreakfast().get(j).getIngredient_name()));
            quantity.setText(patientDailyIntake.get(i).getBreakfast().get(j).getQuantity() + "");

            breakfastfields.put(name,quantity);

            breakfastContainer.addView(ingV);
        }

        for (int j = 0; j < patientDailyIntake.get(i).getLunch().size(); j++) {
            View ingV = getLayoutInflater(b).inflate(R.layout.modified_ingredient_item, null);

            Spinner name = (Spinner) ingV.findViewById(R.id.ingredient_name);
            EditText quantity = (EditText) ingV.findViewById(R.id.ingredient_quantity);

            name.setAdapter(dadapter);
            name.setSelection(indexes.indexOf(patientDailyIntake.get(i).getLunch().get(j).getIngredient_name()));
            quantity.setText(patientDailyIntake.get(i).getLunch().get(j).getQuantity() + "");

            lunchfields.put(name,quantity);

            lunchContainer.addView(ingV);
        }

        for (int j = 0; j < patientDailyIntake.get(i).getDinner().size(); j++) {
            View ingV = getLayoutInflater(b).inflate(R.layout.modified_ingredient_item, null);

            Spinner name = (Spinner) ingV.findViewById(R.id.ingredient_name);
            EditText quantity = (EditText) ingV.findViewById(R.id.ingredient_quantity);

            name.setAdapter(dadapter);
            name.setSelection(indexes.indexOf(patientDailyIntake.get(i).getDinner().get(j).getIngredient_name()));
            quantity.setText(patientDailyIntake.get(i).getDinner().get(j).getQuantity() + "");

            dinnerfields.put(name,quantity);

            dinnerContainer.addView(ingV);
        }

        for (int j = 0; j < patientDailyIntake.get(i).getSnacks().size(); j++) {
            View ingV = getLayoutInflater(b).inflate(R.layout.modified_ingredient_item, null);

            Spinner name = (Spinner) ingV.findViewById(R.id.ingredient_name);
            EditText quantity = (EditText) ingV.findViewById(R.id.ingredient_quantity);

            name.setAdapter(dadapter);
            name.setSelection(indexes.indexOf(patientDailyIntake.get(i).getSnacks().get(j).getIngredient_name()));
            quantity.setText(patientDailyIntake.get(i).getSnacks().get(j).getQuantity() + "");

            snacksfields.put(name,quantity);

            snacksContainer.addView(ingV);
        }

        daily.addView(BreakfastV);
        daily.addView(LunchV);
        daily.addView(DinnerV);
        daily.addView(SnackV);

        Button newbreakfast = (Button) BreakfastV.findViewById(R.id.newmeal);
        Button newlunch = (Button) LunchV.findViewById(R.id.newmeal);
        Button newdinner = (Button) DinnerV.findViewById(R.id.newmeal);
        Button newsnacks = (Button) SnackV.findViewById(R.id.newmeal);

        Button delbreakfast = (Button) BreakfastV.findViewById(R.id.delmeal);
        Button dellunch = (Button) LunchV.findViewById(R.id.delmeal);
        Button deldinner = (Button) DinnerV.findViewById(R.id.delmeal);
        Button delsnacks = (Button) SnackV.findViewById(R.id.delmeal);

        View tc = getLayoutInflater(b).inflate(R.layout.calories_total_consumed, null);
        final TextView total = (TextView) tc.findViewById(R.id.total_calories);
        final TextView consumed = (TextView) tc.findViewById(R.id.consumed_calories);
        consumed.setVisibility(View.GONE);

        newbreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tot>(7716.17/7)){
                    Toast.makeText(getContext(),"Total exceeded daily losing calories",Toast.LENGTH_LONG).show();
                }else{
                    add(breakfastContainer, breakfastfields,total,consumed);
                }
            }
        });

        newlunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tot>(7716.17/7)){
                    Toast.makeText(getContext(),"Total exceeded daily losing calories",Toast.LENGTH_LONG).show();
                }else {
                    add(lunchContainer, lunchfields, total, consumed);
                }
            }
        });

        newdinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tot>(7716.17/7)){
                    Toast.makeText(getContext(),"Total exceeded daily losing calories",Toast.LENGTH_LONG).show();
                }else {
                    add(dinnerContainer, dinnerfields, total, consumed);
                }
            }
        });

        newsnacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tot>(7716.17/7)){
                    Toast.makeText(getContext(),"Total exceeded daily losing calories",Toast.LENGTH_LONG).show();
                }else {
                    add(snacksContainer, snacksfields, total, consumed);
                }
            }
        });

        delbreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(breakfastContainer, breakfastfields,total,consumed);
            }
        });

        dellunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(lunchContainer, lunchfields,total,consumed);
            }
        });

        deldinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(dinnerContainer, dinnerfields,total,consumed);
            }
        });

        delsnacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(snacksContainer, snacksfields,total,consumed);
            }
        });

        Button submit = new Button(getContext());
        submit.setText("Submit New Intake");
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0,5,0,5);

        submit.setLayoutParams(lp);
        submit.setTextSize(TypedValue.COMPLEX_UNIT_SP,15f);
        submit.setTextColor(getResources().getColor(R.color.image_button_color));
        submit.setBackgroundResource(R.drawable.table_background);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert p != null;
                String patientID = p.getUser_id();

                if(breakfastfields.size() > 0){
                    BreakfastData = loopThrough(breakfastfields);
                }
                if(lunchfields.size() > 0){
                    LunchData = loopThrough(lunchfields);
                }
                if(dinnerfields.size() > 0){
                    DinnerData = loopThrough(dinnerfields);
                }
                if(snacksfields.size() > 0){
                    SnacksData = loopThrough(snacksfields);
                }

                double BMR;
                if(p.getGender().equals("Male")){
                    BMR = (10 * p.getWeight())+(6.25*p.getHeight()) - ((5 * p.getAge())+5);
                }else{
                    BMR = (10 * p.getWeight())+(6.25*p.getHeight()) - ((5 * p.getAge())-161);
                }

                consumedCalories = BMR - totalCalories;

                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Fitness");
                DatabaseReference ingredientref = myRef.child("DailyIntake").push();

                String dayID = ingredientref.getKey();
                DailyIntake di = new DailyIntake(dayID,patientID,formattedDate,BreakfastData,LunchData,DinnerData,SnacksData,totalCalories,consumedCalories,false);

                ingredientref.setValue(di);

                dismiss();

                Intent i = new Intent(getContext(),MainActivity.class);
                i.putExtra("type",3);
                i.putExtra("user",n);
                startActivity(i);
            }
        });

        total.setText(patientDailyIntake.get(i).getCaloriesTotal() + "");
//        consumed.setText(patientDailyIntake.get(i).getCaloriesConsumed() + "");

        daily.addView(tc);

        daily.addView(submit);
        clicked[i] = true;
    }

    public ArrayList<Ingredient> loopThrough(HashMap<Spinner,EditText> data){
        ArrayList<Ingredient> meal = new ArrayList<>();
        Iterator it = data.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            Spinner spin = (Spinner) pair.getKey();
            EditText et = (EditText) pair.getValue();

            String type = spin.getSelectedItem().toString();

            int quantity = Integer.parseInt(et.getText().toString());

            int calories = ing.get(type) * quantity;
            totalCalories += calories;

            Ingredient i = new Ingredient(type,quantity,calories);

            meal.add(i);
        }
        return meal;
    }

    public void add(LinearLayout ln , HashMap<Spinner,EditText> data, final TextView total, final TextView consumed){
        LinearLayout ll = new LinearLayout(getContext());
        ll.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); // or set height to any fixed value you want
        lp.setMargins(0,5,0,5);
        ll.setLayoutParams(lp);

        final Spinner spin = new Spinner(getContext());
        spin.setAdapter(dadapter);

        TextInputLayout til = (TextInputLayout) getLayoutInflater(b).inflate(R.layout.sample_numberinput,null);
        LinearLayout.LayoutParams textInputLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        til.setLayoutParams(textInputLayoutParams);

        final EditText et = til.getEditText();
        assert et != null;
        et.setTextColor(getResources().getColor(R.color.colorPrimary));
        til.setHint("Quantity");

        spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(spin.getSelectedItem().toString().equals(food.get(position))){
                    if(!et.getText().toString().equals("")) {
                        tot += Double.parseDouble(et.getText().toString()) * ing.get(spin.getSelectedItem().toString());
                        total.setText(tot+"");
                        consumed.setText((Double.parseDouble(bmr.getText().toString()) - tot)+"");
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() > 0){
                    tot += Double.parseDouble(et.getText().toString()) * ing.get(spin.getSelectedItem().toString());
                    total.setText(tot+"");
                    consumed.setText((Double.parseDouble(bmr.getText().toString()) - tot)+"");
                }
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        ll.addView(spin);
        ll.addView(til);

        ln.addView(ll);

        assert et != null;
        data.put(spin,et);
    }

    public void remove(LinearLayout ln , HashMap<Spinner , EditText> data, TextView total, TextView consumed){
        if(ln.getChildCount() > 0){
            LinearLayout ll = (LinearLayout) ln.getChildAt(ln.getChildCount()-1);
            Spinner spin = (Spinner) ll.getChildAt(ll.getChildCount()-2);
            TextInputLayout til = (TextInputLayout) ll.getChildAt(ll.getChildCount()-1);
            EditText et = til.getEditText();

            Log.d("NeurulistPatient","total before removing "+tot);

            assert et != null;
            if(!et.getText().toString().equals("")) {
                tot -= (Double.parseDouble(et.getText().toString()) * ing.get(spin.getSelectedItem().toString()));
                if(tot < 0){
                    tot = 0;
                    total.setText("0.0");
                    consumed.setText("0.0");
                }else{
                    total.setText(tot+"");
                    consumed.setText((Double.parseDouble(bmr.getText().toString()) - tot)+"");
                }
            }

            Log.d("NeurulistPatient","total after removing "+tot);

            data.remove(spin);
            ln.removeViewAt(ln.getChildCount()-1);
        }
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
