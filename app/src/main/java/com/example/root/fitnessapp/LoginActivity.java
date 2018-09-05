package com.example.root.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.root.fitnessapp.models.Doctor;
import com.example.root.fitnessapp.models.Neutrulist;
import com.example.root.fitnessapp.models.Patient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by root on 08/06/17.
 */

public class LoginActivity extends AppCompatActivity {

    Button login , signup;
    EditText email, password;

    public static ArrayList<Doctor> doctors = new ArrayList<>();
    public static ArrayList<Patient> patients = new ArrayList<>();
    public static ArrayList<Neutrulist> neutrulists = new ArrayList<>();

    boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_form);

        doctors.clear();
        patients.clear();
        neutrulists.clear();

        login = (Button) findViewById(R.id.login);
        signup = (Button) findViewById(R.id.signup);

        email = (EditText) findViewById(R.id.logemail);
        password = (EditText) findViewById(R.id.password);

        flag = false;

        getAllDoctors();
        getAllPatients();
        getAllNeutrulists();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String un = email.getText().toString();
                String pw = password.getText().toString();

                if(un.equals("") || pw.equals("")){
                    Toast.makeText(LoginActivity.this,"Mismatched Email or password!",Toast.LENGTH_SHORT).show();
                }else{
                    for(Doctor d : doctors){
//                        Log.d("status","doc email "+d.getUser_email()+" ,pass "+d.getUser_password());
                        if(d.getUser_email().equals(un) && d.getUser_password().equals(pw)){
                            Intent i = new Intent(LoginActivity.this,MainActivity.class);
                            i.putExtra("type",1);
                            i.putExtra("user",d);
                            startActivity(i);
                            flag = true;
                            break;
                        }
                    }
                    for(Patient p : patients){
//                        Log.d("status","pat email "+p.getUser_email()+" ,pass "+p.getUser_password());
                        if(p.getUser_email().equals(un) && p.getUser_password().equals(pw)){
                            Intent i = new Intent(LoginActivity.this,MainActivity.class);
                            i.putExtra("type",2);
                            i.putExtra("user",p);
                            startActivity(i);
                            flag = true;
                            break;
                        }
                    }
                    for(Neutrulist n : neutrulists){
//                        Log.d("status","pat email "+p.getUser_email()+" ,pass "+p.getUser_password());
                        if(n.getUser_email().equals(un) && n.getUser_password().equals(pw)){
                            Intent i = new Intent(LoginActivity.this,MainActivity.class);
                            i.putExtra("type",3);
                            i.putExtra("user",n);
                            startActivity(i);
                            flag = true;
                            break;
                        }
                    }
                    if(!flag){
                        Log.d("status","false flag");
                        Toast.makeText(LoginActivity.this,"Mismatched Username or password!",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this,Registration.class);
                startActivity(i);
            }
        });

    }

    public static void getAllDoctors(){
        Log.d("outside method","doctors");
        FirebaseDatabase.getInstance().getReference("Fitness").child("Physician").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("inside method","doctors");
                for(DataSnapshot dss : dataSnapshot.getChildren()){
                    Log.d("inside method loop","doctors");
                    Doctor doctor = dss.getValue(Doctor.class);
                    assert doctor != null;
                    Log.d("Doctors ",doctor.toString());
                    doctors.add(doctor);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public static void getAllPatients(){
        Log.d("outside method","patients");
        FirebaseDatabase.getInstance().getReference("Fitness").child("Patient").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("inside method","patients");
                for(DataSnapshot dss : dataSnapshot.getChildren()){
                    Log.d("inside method loop","patients");
                    try{
                        Patient patient = dss.getValue(Patient.class);
                        assert patient != null;
                        Log.d("Patients ",patient.toString());
                        patients.add(patient);
                    }catch (Exception e){
                        Log.d("Exception_state",e.getMessage());
                        Log.d("Exception_state",e.getLocalizedMessage());
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public static void getAllNeutrulists(){
        Log.d("outside method","Neutrulists");
        FirebaseDatabase.getInstance().getReference("Fitness").child("Neutrulist").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("inside method","Neutrulist");
                for(DataSnapshot dss : dataSnapshot.getChildren()){
                    Log.d("inside method loop","Neutrulist");
                    try{
                        Neutrulist n = dss.getValue(Neutrulist.class);
                        assert n != null;
                        Log.d("Neutrulists ",n.toString());
                        neutrulists.add(n);
                    }catch (Exception e){
                        Log.d("Exception_state",e.getMessage());
                        Log.d("Exception_state",e.getLocalizedMessage());
                        e.printStackTrace();
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

}
