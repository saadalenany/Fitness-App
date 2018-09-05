package com.example.root.fitnessapp;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.fitnessapp.models.Doctor;
import com.example.root.fitnessapp.models.Neutrulist;
import com.example.root.fitnessapp.models.Patient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ProfileActivity extends Fragment {

    EditText textusername , textpassword , textphone , textage;
    TextView textemail , texttype;
    Spinner gender;
    Button update;

    Doctor d;
    Patient p;
    Neutrulist n;

    LinearLayout bodytype_layout;
    LinearLayout profile_color;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.activity_profile, container, false);

        getActivity().setTitle("Profile");
        Bundle b = getArguments();
        if(b.getInt("type") == 1){
            d = (Doctor) b.getSerializable("user");
            getActivity().setTitle(d.getUser_name()+" Profile");
        }else if(b.getInt("type") == 2){
            p = (Patient) b.getSerializable("user");
            getActivity().setTitle(p.getUser_name()+" Profile");
        }else if(b.getInt("type") == 3){
            n = (Neutrulist) b.getSerializable("user");
            getActivity().setTitle(n.getUser_name()+" Profile");
        }

        textusername = (EditText) v.findViewById(R.id.textusername);
        textage = (EditText) v.findViewById(R.id.textage);
        textpassword = (EditText) v.findViewById(R.id.textpassword);
        textphone = (EditText) v.findViewById(R.id.textphone);
        texttype = (TextView) v.findViewById(R.id.texttype);

        textemail = (TextView) v.findViewById(R.id.textemail);
        gender = (Spinner) v.findViewById(R.id.textgender_spinner);

        update = (Button) v.findViewById(R.id.update);

        bodytype_layout = (LinearLayout) v.findViewById(R.id.bodytype_layout);
        profile_color = (LinearLayout) v.findViewById(R.id.profile_color);

/***************************************************************************************************/
        // Create an ArrayAdapter using the string array and a default gender_spinner layout
        final ArrayAdapter<CharSequence> gadapter = ArrayAdapter.createFromResource(getContext(), R.array.gender,android.R.layout.simple_spinner_item);

        // Specify the layout to use when the list of choices appears
        gadapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // Apply the adapter to the gender spinner
        gender.setAdapter(gadapter);
/***************************************************************************************************/

        if(p != null){
            textusername.setText(p.getUser_name());
            texttype.setText("Patient");
            textpassword.setText(p.getUser_password());
            textemail.setText(p.getUser_email());
            textphone.setText(p.getPhone());
            textage.setText(p.getAge()+"");
            if(p.getGender().equals("Male")){
                gender.setSelection(0);
                profile_color.setBackgroundColor(getResources().getColor(R.color.male_color));
            }else{
                gender.setSelection(1);
                profile_color.setBackgroundColor(getResources().getColor(R.color.female_color));
            }

            View view = getLayoutInflater(savedInstanceState).inflate(R.layout.bodytype_sample,null);

            TextView body_typetext = (TextView) view.findViewById(R.id.body_typetext);

            View severe_thinness = view.findViewById(R.id.severe_thinness);
            View moderate_thinness = view.findViewById(R.id.moderate_thinness);
            View mild_thinness = view.findViewById(R.id.mild_thinness);
            View normal = view.findViewById(R.id.normal);
            View overweight = view.findViewById(R.id.overweight);
            View obese_i = view.findViewById(R.id.obese_i);
            View obese_ii = view.findViewById(R.id.obese_ii);
            View obese_iii = view.findViewById(R.id.obese_iii);

            final float scale = getContext().getResources().getDisplayMetrics().density;
            int pixels = (int) (40 * scale + 0.5f);

            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(pixels,pixels);

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

            bodytype_layout.addView(view);

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(textusername.getText().toString().equals("") || textpassword.getText().toString().equals("") || textphone.getText().toString().equals("") || textage.getText().toString().equals("")){
                        Toast.makeText(getContext(),"Please, fill all the fields!",Toast.LENGTH_SHORT).show();
                    }else{
                        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Fitness");
                        DatabaseReference patientRef = myRef.child("Patient");
                        patientRef.child(p.getUser_id()).child("user_name").setValue(textusername.getText().toString());
                        patientRef.child(p.getUser_id()).child("user_password").setValue(textpassword.getText().toString());
                        patientRef.child(p.getUser_id()).child("phone").setValue(textphone.getText().toString());
                        patientRef.child(p.getUser_id()).child("gender").setValue(gender.getSelectedItem().toString());
                        patientRef.child(p.getUser_id()).child("age").setValue(Integer.parseInt(textage.getText().toString()));

                        p.setUser_name(textusername.getText().toString());
                        p.setUser_password(textpassword.getText().toString());
                        p.setPhone(textphone.getText().toString());
                        p.setGender(gender.getSelectedItem().toString());
                        p.setAge(Integer.parseInt(textage.getText().toString()));

                        Intent i = new Intent(getContext(),MainActivity.class);
                        i.putExtra("type",2);
                        i.putExtra("user",p);
                        startActivity(i);
                    }
                }
            });

        }else if(d != null){
            textusername.setText(d.getUser_name());
            texttype.setText("Physician");
            textpassword.setText(d.getUser_password());
            textemail.setText(d.getUser_email());
            textphone.setText(d.getPhone());
            textage.setText(d.getAge()+"");
            if(d.getGender().equals("Male")){
                gender.setSelection(0);
            }else{
                gender.setSelection(1);
            }

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(textusername.getText().toString().equals("") || textpassword.getText().toString().equals("") || textphone.getText().toString().equals("") || textage.getText().toString().equals("")){
                        Toast.makeText(getContext(),"Please, fill all the fields!",Toast.LENGTH_SHORT).show();
                    }else{
                        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Fitness");
                        DatabaseReference patientRef = myRef.child("Physician");
                        patientRef.child(d.getUser_id()).child("user_name").setValue(textusername.getText().toString());
                        patientRef.child(d.getUser_id()).child("user_password").setValue(textpassword.getText().toString());
                        patientRef.child(d.getUser_id()).child("phone").setValue(textphone.getText().toString());
                        patientRef.child(d.getUser_id()).child("gender").setValue(gender.getSelectedItem().toString());
                        patientRef.child(d.getUser_id()).child("age").setValue(Integer.parseInt(textage.getText().toString()));

                        d.setUser_name(textusername.getText().toString());
                        d.setUser_password(textpassword.getText().toString());
                        d.setPhone(textphone.getText().toString());
                        d.setGender(gender.getSelectedItem().toString());
                        d.setAge(Integer.parseInt(textage.getText().toString()));

                        Intent i = new Intent(getContext(),MainActivity.class);
                        i.putExtra("type",1);
                        i.putExtra("user",d);
                        startActivity(i);
                    }
                }
            });
        }else if(n != null){
            textusername.setText(n.getUser_name());
            texttype.setText("Nutritionist");
            textpassword.setText(n.getUser_password());
            textemail.setText(n.getUser_email());
            textphone.setText(n.getPhone());
            textage.setText(n.getAge()+"");
            if(n.getGender().equals("Male")){
                gender.setSelection(0);
            }else{
                gender.setSelection(1);
            }

            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(textusername.getText().toString().equals("") || textpassword.getText().toString().equals("") || textphone.getText().toString().equals("") || textage.getText().toString().equals("")){
                        Toast.makeText(getContext(),"Please, fill all the fields!",Toast.LENGTH_SHORT).show();
                    }else{
                        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("Fitness");
                        DatabaseReference patientRef = myRef.child("Neutrulist");
                        patientRef.child(n.getUser_id()).child("user_name").setValue(textusername.getText().toString());
                        patientRef.child(n.getUser_id()).child("user_password").setValue(textpassword.getText().toString());
                        patientRef.child(n.getUser_id()).child("phone").setValue(textphone.getText().toString());
                        patientRef.child(n.getUser_id()).child("gender").setValue(gender.getSelectedItem().toString());
                        patientRef.child(n.getUser_id()).child("age").setValue(Integer.parseInt(textage.getText().toString()));

                        n.setUser_name(textusername.getText().toString());
                        n.setUser_password(textpassword.getText().toString());
                        n.setPhone(textphone.getText().toString());
                        n.setGender(gender.getSelectedItem().toString());
                        n.setAge(Integer.parseInt(textage.getText().toString()));

                        Intent i = new Intent(getContext(),MainActivity.class);
                        i.putExtra("type",3);
                        i.putExtra("user",n);
                        startActivity(i);
                    }
                }
            });
        }

        return v;
    }
}
