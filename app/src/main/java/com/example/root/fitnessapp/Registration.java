package com.example.root.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.example.root.fitnessapp.models.Doctor;
import com.example.root.fitnessapp.models.Messages;
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

/**
 * Created by root on 08/06/17.
 */

public class Registration extends AppCompatActivity {

    Button register;
    EditText username , email, password , age , phone;
    Spinner user_spinner , gender_spinner;
    LinearLayout registrationlayout;

/************************************patients-data*******************************************************/
    Spinner doctorsspinner;
    EditText illnessField;
    EditText heightField;
    EditText weightField;

    ArrayAdapter<String> dadapter;
    ArrayList<String> emails = new ArrayList<>();
    ArrayList<String> docsIDs = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);

        emails.clear();
        docsIDs.clear();

        register = (Button) findViewById(R.id.register);
        username = (EditText) findViewById(R.id.regusername);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.regpassword);
        age = (EditText) findViewById(R.id.age);
        phone = (EditText) findViewById(R.id.phone);

        user_spinner = (Spinner) findViewById(R.id.user_spinner);
        gender_spinner = (Spinner) findViewById(R.id.gender_spinner);

        registrationlayout = (LinearLayout) findViewById(R.id.registrationlayout);

/***********************************************************************************************************/

        // Create an ArrayAdapter using the string array and a default user_spinner layout
        final ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.users,android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the user_spinner
        user_spinner.setAdapter(adapter);
/***********************************************************************************************************/

        // Create an ArrayAdapter using the string array and a default gender_spinner layout
        final ArrayAdapter<CharSequence> gadapter = ArrayAdapter.createFromResource(this, R.array.gender,android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        gadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the gender_spinner
        gender_spinner.setAdapter(gadapter);
/***********************************************************************************************************/
        // Create an ArrayAdapter using the string array and a default doctors_spinner layout
        dadapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, getDoctorsNames());
        dadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
/************************************************************************************************************/

        getDoctorsEmails();
        getPatientsEmails();
        getNeutrilistsEmails();

        user_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(user_spinner.getSelectedItem().equals("Patient")){

                    registrationlayout.removeAllViews();
                    doctorsspinner = new Spinner(getApplicationContext());
                    doctorsspinner.setAdapter(dadapter);

                    TextView tv = new TextView(getApplicationContext());
                    tv.setTextColor(getResources().getColor(R.color.white));
                    tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,15);
                    tv.setText("Register with Physician");
                    tv.setGravity(Gravity.CENTER);
                    registrationlayout.addView(tv);

                    /********************* illness Field ******************************/
                    TextInputLayout til1 = (TextInputLayout) getLayoutInflater().inflate(R.layout.edittext_sample, null);
                    illnessField = til1.getEditText();

                    til1.setHint("Your Illness");

                    /********************* height Field ******************************/
                    TextInputLayout til2 = (TextInputLayout) getLayoutInflater().inflate(R.layout.edittext_sample, null);
                    heightField = til2.getEditText();
                    assert heightField != null;
                    heightField.setInputType(InputType.TYPE_CLASS_NUMBER);

                    til2.setHint("Your Height in cm");

                    /********************* weight Field ******************************/
                    TextInputLayout til3 = (TextInputLayout) getLayoutInflater().inflate(R.layout.edittext_sample, null);
                    weightField = til3.getEditText();
                    assert weightField != null;
                    weightField.setInputType(InputType.TYPE_CLASS_NUMBER);

                    til3.setHint("Your Weight in kg");

                    registrationlayout.addView(doctorsspinner);
                    registrationlayout.addView(til1);
                    registrationlayout.addView(til2);
                    registrationlayout.addView(til3);

                }else if(user_spinner.getSelectedItem().equals("Nutritionist")) {

                    registrationlayout.removeAllViews();
                    doctorsspinner = new Spinner(getApplicationContext());
                    doctorsspinner.setAdapter(dadapter);
                    registrationlayout.addView(doctorsspinner);

                }else{
                    registrationlayout.removeAllViews();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String un = username.getText().toString();
                String em = email.getText().toString();
                String pw = password.getText().toString();
                String ag = age.getText().toString();
                String ph = phone.getText().toString();
                if(un.equals("") || em.equals("") || pw.equals("") || ag.equals("") || ph.equals("")){
                    Toast.makeText(Registration.this, "Please fill out all the fields!", Toast.LENGTH_SHORT).show();
                }else{

                    boolean flag = false;
                    for(int i=0 ; i<emails.size() ; i++){
                        if(em.equalsIgnoreCase(emails.get(i))){
                            Toast.makeText(Registration.this, "This email already exists, try to login!", Toast.LENGTH_SHORT).show();
                            flag = true;
                            break;
                        }
                    }

                    if(!flag){
                        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Fitness");
                        if(user_spinner.getSelectedItem().toString().equals("Patient")){
                            String ill = illnessField.getText().toString();
                            String h = heightField.getText().toString();
                            String w = weightField.getText().toString();
                            if(ill.equals("") || h.equals("") || w.equals("") || doctorsspinner.getSelectedItem() == null){
                                Toast.makeText(Registration.this, "Please fill out all the fields!", Toast.LENGTH_SHORT).show();
                            }else if(getNeutrulistObject(docsIDs.get(doctorsspinner.getSelectedItemPosition())) == null){
                                Toast.makeText(Registration.this, "This Doctor has no Nutritionist yet!", Toast.LENGTH_LONG).show();
                                Toast.makeText(Registration.this, "Register later!", Toast.LENGTH_SHORT).show();
                            }else{

                                int doc = doctorsspinner.getSelectedItemPosition();
                                docsIDs.get(doc);

                                Double BMIh = Double.parseDouble(h);
                                Double BMIw = Double.parseDouble(w);

                                BMIh /= 100;

                                int result = (int) (BMIw/(BMIh*BMIh));

                                String bodyType = "";
                                if(result < 16){
                                    bodyType = "Severe Thinness";
                                }else if(result >= 16 && result < 17){
                                    bodyType ="Moderate Thinness";
                                }else if(result >= 17 && result < 18.5){
                                    bodyType = "Mild Thinness";
                                }else if(result >= 18.5 && result < 25){
                                    bodyType = "Normal";
                                }else if(result >= 25 && result < 30){
                                    bodyType = "Overweight";
                                }else if(result >= 30 && result < 35){
                                    bodyType = "Obese class I";
                                }else if(result >= 35 && result < 40){
                                    bodyType = "Obese class II";
                                }else if(result >= 40){
                                    bodyType = "Obese class III";
                                }

                                int diff=0;
                                int idealW = (int) (22* (BMIh*BMIh));
                                if(idealW > BMIw ){
                                    diff = (int) (idealW - BMIw);
                                }else if(idealW < BMIw){
                                    diff = (int) (BMIw - idealW);
                                }

                                DatabaseReference patientRef = myRef.child("Patient").push();
                                Patient patient = new Patient(patientRef.getKey(),un,pw,2,em,gender_spinner.getSelectedItem().toString(),Integer.parseInt(ag),ph,bodyType,BMIw,BMIh,docsIDs.get(doc),ill,diff);
                                patient.setBMI(result);
                                patientRef.setValue(patient);

                                String message = "Plan: To reach you Ideal weight, You have to consume 1000 calories from your food intake per week within "+diff+" weeks";

                                Calendar c = Calendar.getInstance();
                                SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy HH:mm:ss");
                                String formattedDate = df.format(c.getTime());

                                DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Fitness").child("Message").push();

                                Messages m = new Messages(mRef.getKey(), message,getNeutrulist(patient.getPatient_doctor()),patient.getUser_id(),formattedDate,false);
                                mRef.setValue(m);

//                                DatabaseReference doctorRef = myRef.child("Physician").push();
//                                doctorRef.child(docsIDs.get(doc)).child("numberofpatients").setValue(nop.get(doc)+1);

                                Intent i = new Intent(Registration.this,LoginActivity.class);
                                i.putExtra("type",2);
                                i.putExtra("user",patient);
                                startActivity(i);
                            }
                        }else if(user_spinner.getSelectedItem().toString().equals("Physician")){
                            DatabaseReference doctorRef = myRef.child("Physician").push();
                            Doctor doctor = new Doctor(doctorRef.getKey(),un,pw,1,em,gender_spinner.getSelectedItem().toString(),Integer.parseInt(ag),ph);
                            doctorRef.setValue(doctor);

                            Intent i = new Intent(Registration.this,LoginActivity.class);
                            i.putExtra("type",1);
                            i.putExtra("user",doctor);
                            startActivity(i);
                        }else if(user_spinner.getSelectedItem().toString().equals("Nutritionist")){
                            int doc = doctorsspinner.getSelectedItemPosition();
                            docsIDs.get(doc);

                            DatabaseReference doctorRef = myRef.child("Neutrulist").push();
                            Neutrulist n = new Neutrulist(doctorRef.getKey(),un,pw,1,em,gender_spinner.getSelectedItem().toString(),Integer.parseInt(ag),ph,docsIDs.get(doc));
                            doctorRef.setValue(n);

                            Intent i = new Intent(Registration.this,LoginActivity.class);
                            i.putExtra("type",3);
                            i.putExtra("user",n);
                            startActivity(i);
                        }
                    }
                }
            }
        });
    }

    public ArrayList<String> getDoctorsNames(){
        final ArrayList<String> docs = new ArrayList<>();
        FirebaseDatabase.getInstance().getReference("Fitness").child("Physician").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dss : dataSnapshot.getChildren()){
                    Doctor doctor = dss.getValue(Doctor.class);
                    assert doctor != null;
                    Log.d("Doctor names ",doctor.getUser_name());
                    Log.d("Doctor IDS ",FirebaseDatabase.getInstance().getReference("Fitness").child("Physician").push().getKey());
                    docsIDs.add(doctor.getUser_id());
                    docs.add(doctor.getUser_name());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
        return docs;
    }

    private String getNeutrulist(String patient_doctor) {
        for(int i=0 ; i<LoginActivity.neutrulists.size() ; i++){
            if(LoginActivity.neutrulists.get(i).getDoctor_id().equals(patient_doctor)){
                Log.d("Neutrulist_found",LoginActivity.neutrulists.get(i).toString());
                return LoginActivity.neutrulists.get(i).getUser_id();
            }
        }
        return null;
    }

    public void getDoctorsEmails(){
        FirebaseDatabase.getInstance().getReference("Fitness").child("Physician").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dss : dataSnapshot.getChildren()){
                    Doctor doctor = dss.getValue(Doctor.class);
                    assert doctor != null;
                    Log.d("Doctor emails ",doctor.getUser_email());
                    emails.add(doctor.getUser_email());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void getPatientsEmails(){
        FirebaseDatabase.getInstance().getReference("Fitness").child("Patient").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dss : dataSnapshot.getChildren()){
                    Patient p = dss.getValue(Patient.class);
                    assert p != null;
                    Log.d("Patient emails ",p.getUser_email());
                    emails.add(p.getUser_email());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void getNeutrilistsEmails(){
        FirebaseDatabase.getInstance().getReference("Fitness").child("Neutrulist").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dss : dataSnapshot.getChildren()){
                    Neutrulist n = dss.getValue(Neutrulist.class);
                    assert n != null;
                    Log.d("Neutrulist emails ",n.getUser_email());
                    emails.add(n.getUser_email());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public Neutrulist getNeutrulistObject(String patient_doctor) {
        for(int i=0 ; i<LoginActivity.neutrulists.size() ; i++){
            if(LoginActivity.neutrulists.get(i).getDoctor_id().equals(patient_doctor)){
                Log.d("Neutrulist_found",LoginActivity.neutrulists.get(i).toString());
                return LoginActivity.neutrulists.get(i);
            }
        }
        return null;
    }

}
