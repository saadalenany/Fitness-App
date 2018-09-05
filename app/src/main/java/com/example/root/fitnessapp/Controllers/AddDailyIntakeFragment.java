package com.example.root.fitnessapp.Controllers;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.root.fitnessapp.MainActivity;
import com.example.root.fitnessapp.R;
import com.example.root.fitnessapp.models.DailyIntake;
import com.example.root.fitnessapp.models.Ingredient;
import com.example.root.fitnessapp.models.Neutrulist;
import com.example.root.fitnessapp.models.Patient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by root on 12/05/17.
 */

public class AddDailyIntakeFragment extends DialogFragment {

    Bundle bundel;

    Button newbreakfast , delbreakfast;
    Button newlunch , dellunch;
    Button newdinner , deldinner;
    Button newsnacks , delsnacks;

    LinearLayout addbreakfast , addlunch , adddinner , addsnacks;

    ArrayList<String> food = new ArrayList<>();
    HashMap<String,Integer> ing = new HashMap<>();

    ArrayAdapter<String> dadapter;

    HashMap<Spinner,EditText> breakfastfields = new HashMap<>();
    HashMap<Spinner,EditText> lunchfields = new HashMap<>();
    HashMap<Spinner,EditText> dinnerfields = new HashMap<>();
    HashMap<Spinner,EditText> snacksfields = new HashMap<>();

    ArrayList<Ingredient> BreakfastData = new ArrayList<>();
    ArrayList<Ingredient> LunchData = new ArrayList<>();
    ArrayList<Ingredient> DinnerData = new ArrayList<>();
    ArrayList<Ingredient> SnacksData = new ArrayList<>();

    double totalCalories;
    double consumedCalories;

    Button submitFood;
    Patient p;
    Neutrulist n;
    boolean taken;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.addintake_fragment, container, false);

        getDialog().setCanceledOnTouchOutside(true);

        p = (Patient) getArguments().getSerializable("user");
        taken = getArguments().getBoolean("taken");

        submitFood = (Button) v.findViewById(R.id.submitFood);

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

        newbreakfast = (Button) v.findViewById(R.id.newbreakfast);
        newlunch = (Button) v.findViewById(R.id.newlunch);
        newdinner = (Button) v.findViewById(R.id.newdinner);
        newsnacks = (Button) v.findViewById(R.id.newsnacks);

        delbreakfast = (Button) v.findViewById(R.id.delbreakfast);
        dellunch = (Button) v.findViewById(R.id.dellunch);
        deldinner = (Button) v.findViewById(R.id.deldinner);
        delsnacks = (Button) v.findViewById(R.id.delsnacks);

        addbreakfast = (LinearLayout) v.findViewById(R.id.addbreakfast);
        addlunch = (LinearLayout) v.findViewById(R.id.addlunch);
        adddinner = (LinearLayout) v.findViewById(R.id.adddinner);
        addsnacks = (LinearLayout) v.findViewById(R.id.addsnacks);

        bundel = savedInstanceState;

        dadapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, food);

        getDialog().setTitle("Food Intake/day");

        newbreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add(addbreakfast, breakfastfields);
            }
        });

        newlunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add(addlunch, lunchfields);
            }
        });

        newdinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add(adddinner, dinnerfields);
            }
        });

        newsnacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add(addsnacks, snacksfields);
            }
        });

        delbreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(addbreakfast, breakfastfields);
            }
        });

        dellunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(addlunch, lunchfields);
            }
        });

        deldinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(adddinner, dinnerfields);
            }
        });

        delsnacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                remove(addsnacks, snacksfields);
            }
        });

        submitFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy");
                String formattedDate = df.format(c.getTime());

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

                double BMR = 0;
                if(p.getGender().equals("Male")){
                    BMR = (10 * p.getWeight())+(6.25*p.getHeight()) - ((5 * p.getAge())+5);
                }else{
                    BMR = (10 * p.getWeight())+(6.25*p.getHeight()) - ((5 * p.getAge())-161);
                }

                consumedCalories = BMR - totalCalories;

                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Fitness");
                DatabaseReference ingredientref = myRef.child("DailyIntake").push();

                String dayID = ingredientref.getKey();
                DailyIntake di = new DailyIntake(dayID,patientID,formattedDate,BreakfastData,LunchData,DinnerData,SnacksData,totalCalories,consumedCalories,taken);

                ingredientref.setValue(di);

                if(taken) {
                    // 3500 calories = 1 pound , 2.20462 pounds = 1 kg
                    // 3500 * 2.20462 = 7716.17
                    double newWeight = round(p.getWeight() + (consumedCalories / (7716.17)),2);
                    p.setWeight(newWeight);
                    FirebaseDatabase.getInstance().getReference("Fitness").child("Patient").child(p.getUser_id()).child("weight").setValue(newWeight);

                    dismiss();

                    Intent i = new Intent(getContext(),MainActivity.class);
                    i.putExtra("type",2);
                    i.putExtra("user",p);
                    startActivity(i);
                }else{
                    n = (Neutrulist) getArguments().getSerializable("neu");

                    dismiss();

                    Intent i = new Intent(getContext(),MainActivity.class);
                    i.putExtra("type",3);
                    i.putExtra("user",n);
                    startActivity(i);
                }
            }
        });

        return v;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
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

    public void add(LinearLayout ln , HashMap<Spinner,EditText> data){
        LinearLayout ll = new LinearLayout(getContext());
        ll.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT); // or set height to any fixed value you want
        lp.setMargins(0,5,0,5);
        ll.setLayoutParams(lp);

        Spinner spin = new Spinner(getContext());
        spin.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,1f));
        spin.setAdapter(dadapter);

        TextInputLayout til = (TextInputLayout) getLayoutInflater(bundel).inflate(R.layout.sample_numberinput,null);
        LinearLayout.LayoutParams textInputLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        til.setLayoutParams(textInputLayoutParams);
        til.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,1f));

        EditText et = til.getEditText();
        assert et != null;
        et.setTextColor(getResources().getColor(R.color.colorPrimary));
        til.setHint("Quantity");

        ll.addView(spin);
        ll.addView(til);

        ln.addView(ll);

        assert et != null;
        data.put(spin,et);
    }

    public void remove(LinearLayout ln , HashMap<Spinner , EditText> data){
        if(ln.getChildCount() > 0){
            LinearLayout ll = (LinearLayout) ln.getChildAt(ln.getChildCount()-1);
            Spinner s = (Spinner) ll.getChildAt(ll.getChildCount()-2);
            data.remove(s);
            ln.removeViewAt(ln.getChildCount()-1);
        }
    }

}
