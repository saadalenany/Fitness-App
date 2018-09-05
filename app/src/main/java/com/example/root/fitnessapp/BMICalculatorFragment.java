package com.example.root.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.fitnessapp.models.Messages;
import com.example.root.fitnessapp.models.Patient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by root on 24/04/17.
 */

public class BMICalculatorFragment extends Fragment{

    Spinner weightspinner , heightspinner;
    EditText editText , editText2;
    Button calculate;
    TextView weightView , bodyType;
    Patient p;
    LinearLayout bodytype_layout2;
    Bundle bundle ;

    String curW , curH;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bmi_calculator, container, false);

        getActivity().setTitle("BMI Calculator Page");

        bundle = savedInstanceState;

        Bundle b = getArguments();

        weightspinner = (Spinner) v.findViewById(R.id.weightspinner);
        heightspinner = (Spinner) v.findViewById(R.id.heightspinner);

        editText = (EditText) v.findViewById(R.id.editText);
        editText2 = (EditText) v.findViewById(R.id.editText2);

        calculate = (Button) v.findViewById(R.id.Calculate);
        weightView = (TextView) v.findViewById(R.id.calculated_weight);
        bodyType = (TextView) v.findViewById(R.id.body_type);

        bodytype_layout2 = (LinearLayout) v.findViewById(R.id.bodytype_layout2);

        curH = "m";
        curW = "kg";
/*****************************************************************************************************/

        if(b.getInt("type") == 2){
            p = (Patient)(b.getSerializable("user"));
            assert p != null;
            editText.setText(round(p.getWeight(),2)+"");
            editText2.setText(p.getHeight()+"");
            weightView.setText(p.getBMI()+"");
            bodyType.setText(p.getBodytype());
        }

/*****************************************************************************************************/

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.weight, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        weightspinner.setAdapter(adapter);

        ArrayAdapter<CharSequence> ad = ArrayAdapter.createFromResource(getContext(), R.array.height, android.R.layout.simple_spinner_item);

        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        heightspinner.setAdapter(ad);

/*****************************************************************************************************/

        weightspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(weightspinner.getSelectedItem().toString().equals("kg")){
                    if(curW.equals("pounds")){
                        double w = Double.parseDouble(editText.getText().toString());
                        editText.setText((w/2.20462)+"");
                        curW = "kg";
                    }
                }else if(weightspinner.getSelectedItem().toString().equals("pounds")){
                    if(curW.equals("kg")){
                        double w = Double.parseDouble(editText.getText().toString());
                        editText.setText((w*2.20462)+"");
                        curW = "pounds";
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        heightspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(heightspinner.getSelectedItem().toString().equals("m")){
                    if(curH.equals("cm")){
                        double h = Double.parseDouble(editText2.getText().toString());
                        editText2.setText((h/100)+"");
                        curH = "m";
                    }
                }else if(heightspinner.getSelectedItem().toString().equals("cm")){
                    if(curH.equals("m")){
                        double h = Double.parseDouble(editText2.getText().toString());
                        editText2.setText((h*100)+"");
                        curW = "cm";
                    }
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editText.getText().toString().equals("") || editText2.getText().toString().equals("") ){
                    Toast.makeText(getContext(),"please fill out the fields!",Toast.LENGTH_SHORT).show();
                }else{
                    double w = Double.parseDouble(editText.getText().toString());
                    double h = Double.parseDouble(editText2.getText().toString());

                    if(curW.equals("pounds")){
                        w /= 2.20462;
                        curW = "kg";
                    }

                    if(curH.equals("cm")){
                        h /= 100;
                        curH = "m";
                    }

                    int result = (int) (w/(h*h));
                    Log.d("result",result+"");

                    weightView.setText(result+"");

                    if(result < 16){
                        bodyType.setText("Severe Thinness");
                    }else if(result >= 16 && result < 17){
                        bodyType.setText("Moderate Thinness");
                    }else if(result >= 17 && result < 18.5){
                        bodyType.setText("Mild Thinness");
                    }else if(result >= 18.5 && result < 25){
                        bodyType.setText("Normal");
                    }else if(result >= 25 && result < 30){
                        bodyType.setText("Overweight");
                    }else if(result >= 30 && result < 35){
                        bodyType.setText("Obese class I");
                    }else if(result >= 35 && result < 40){
                        bodyType.setText("Obese class II");
                    }else if(result >= 40){
                        bodyType.setText("Obese class III");
                    }

                    View view = getLayoutInflater(bundle).inflate(R.layout.bodytype_sample,null);
                    TextView body_typetext = (TextView) view.findViewById(R.id.body_typetext);

                    View severe_thinness = view.findViewById(R.id.severe_thinness);
                    View moderate_thinness = view.findViewById(R.id.moderate_thinness);
                    View mild_thinness = view.findViewById(R.id.mild_thinness);
                    View normal = view.findViewById(R.id.normal);
                    View overweight = view.findViewById(R.id.overweight);
                    View obese_i = view.findViewById(R.id.obese_i);
                    View obese_ii = view.findViewById(R.id.obese_ii);
                    View obese_iii = view.findViewById(R.id.obese_iii);

                    int hw = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 60, getResources().getDisplayMetrics());
                    LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(hw,hw);

                    body_typetext.setText(p.getBodytype());
                    if(p.getBodytype().equals("Severe Thinness")){
                        body_typetext.setTextColor(getResources().getColor(R.color.sever));
                        severe_thinness.setLayoutParams(lp);
                    }else if(p.getBodytype().equals("Moderate Thinness")){
                        body_typetext.setTextColor(getResources().getColor(R.color.moderate));
                        moderate_thinness.setLayoutParams(lp);
                    }else if(p.getBodytype().equals("Mild Thinness")){
                        body_typetext.setTextColor(getResources().getColor(R.color.mild));
                        mild_thinness.setLayoutParams(lp);
                    }else if(p.getBodytype().equals("Normal")){
                        body_typetext.setTextColor(getResources().getColor(R.color.normal));
                        normal.setLayoutParams(lp);
                    }else if(p.getBodytype().equals("Overweight")){
                        body_typetext.setTextColor(getResources().getColor(R.color.overweight));
                        overweight.setLayoutParams(lp);
                    }else if(p.getBodytype().equals("Obese class I")){
                        body_typetext.setTextColor(getResources().getColor(R.color.obese_i));
                        obese_i.setLayoutParams(lp);
                    }else if(p.getBodytype().equals("Obese class II")){
                        body_typetext.setTextColor(getResources().getColor(R.color.obese_ii));
                        obese_ii.setLayoutParams(lp);
                    }else if(p.getBodytype().equals("Obese class III")){
                        body_typetext.setTextColor(getResources().getColor(R.color.obese_iii));
                        obese_iii.setLayoutParams(lp);
                    }else{
                    }

                    DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Fitness");
                    DatabaseReference patientRef = myRef.child("Patient");
                    patientRef.child(p.getUser_id()).child("bodytype").setValue(bodyType.getText().toString());

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy HH:mm:ss");
                    String formattedDate = df.format(c.getTime());

                    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Fitness").child("Message").push();

                    Messages m = new Messages(mRef.getKey(),bodyType.getText().toString(),p.getUser_id(),p.getPatient_doctor(),formattedDate,false);
                    mRef.setValue(m);

                    if (curW.equals("kg")){
                        patientRef.child(p.getUser_id()).child("weight").setValue(w);
                    }else{
                        w /= 2.20462;
                        patientRef.child(p.getUser_id()).child("weight").setValue(w);
                    }

                    if(curH.equals("m")){
                        h *= 100;
                        patientRef.child(p.getUser_id()).child("height").setValue(h);
                    }else{
                        patientRef.child(p.getUser_id()).child("height").setValue(h);
                    }

                    p.setBodytype(bodyType.getText().toString());
                    p.setWeight(round(w,2));
                    p.setHeight(h);

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent i = new Intent(getContext(),MainActivity.class);
                            i.putExtra("type",2);
                            i.putExtra("user",p);
                            startActivity(i);
                        }
                    }, 5000);
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

}
