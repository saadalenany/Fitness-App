package com.example.root.fitnessapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.fitnessapp.models.ActivityTable;
import com.example.root.fitnessapp.models.Doctor;
import com.example.root.fitnessapp.models.Patient;
import com.example.root.fitnessapp.models.SingleActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by root on 20/06/17.
 */

public class PatientActivities extends Fragment {

    LinearLayout activities_container;
    Patient p ;

    Bundle b ;
    ProgressBar progress_bar;

    HashMap<String , Double> activitiesMap125 = new HashMap<>();
    HashMap<String , Double> activitiesMap155 = new HashMap<>();
    HashMap<String , Double> activitiesMap185 = new HashMap<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View v = inflater.inflate(R.layout.patient_activities, container, false);

        activitiesMap125.put("Golf",210.0);
        activitiesMap125.put("Walk",240.0);
        activitiesMap125.put("Kayaking",300.0);
        activitiesMap125.put("Softball/Baseball",300.0);
        activitiesMap125.put("Swimming",360.0);
        activitiesMap125.put("Tennis",420.0);
        activitiesMap125.put("Running",480.0);
        activitiesMap125.put("Bicycling",480.0);
        activitiesMap125.put("Basketball",480.0);
        activitiesMap125.put("Soccer",480.0);

/****************************************************************/
        activitiesMap155.put("Golf",260.0);
        activitiesMap155.put("Walk",300.0);
        activitiesMap155.put("Kayaking",370.0);
        activitiesMap155.put("Softball/Baseball",370.0);
        activitiesMap155.put("Swimming",440.0);
        activitiesMap155.put("Tennis",520.0);
        activitiesMap155.put("Running",600.0);
        activitiesMap155.put("Bicycling",600.0);
        activitiesMap155.put("Basketball",600.0);
        activitiesMap155.put("Soccer",600.0);

/****************************************************************/
        activitiesMap185.put("Golf",310.0);
        activitiesMap185.put("Walk",360.0);
        activitiesMap185.put("Kayaking",440.0);
        activitiesMap185.put("Softball/Baseball",440.0);
        activitiesMap185.put("Swimming",530.0);
        activitiesMap185.put("Tennis",620.0);
        activitiesMap185.put("Running",710.0);
        activitiesMap185.put("Bicycling",710.0);
        activitiesMap185.put("Basketball",710.0);
        activitiesMap185.put("Soccer",710.0);

/****************************************************************/

        activities_container = (LinearLayout) v.findViewById(R.id.activities_container);
        progress_bar = (ProgressBar) v.findViewById(R.id.progress_bar);
        progress_bar.setVisibility(View.VISIBLE);

        b = getArguments();
        if(getArguments().getInt("type") == 2){
            p = (Patient) getArguments().getSerializable("user");
            getActivity().setTitle(p.getUser_name()+" Activities");
            retrieveActivities(p.getPatient_doctor(),p.getUser_id());
        }
        progress_bar.setVisibility(View.GONE);

        return v;
    }

    public void retrieveActivities(final String sender , final String receiver){
        FirebaseDatabase.getInstance().getReference("Fitness").child("Activity").addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                activities_container.removeAllViews();
                for(DataSnapshot dss : dataSnapshot.getChildren()) {
                    final ActivityTable at = dss.getValue(ActivityTable.class);
                    Log.d("PatientActivities ",at.toString());
                    if(at.getReceiver().equals(receiver) && at.getSender().equals(sender)){
                        LinearLayout container = new LinearLayout(getContext());
                        container.setOrientation(LinearLayout.HORIZONTAL);

                        LinearLayout table_item_layout = new LinearLayout(getContext());

//                        final int sdk = android.os.Build.VERSION.SDK_INT;
//                        if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
//                            table_item_layout.setBackgroundDrawable( getResources().getDrawable(R.drawable.background));
//                        } else {
//                            table_item_layout.setBackground( getResources().getDrawable(R.drawable.background));
//                        }

                        table_item_layout.setBackgroundResource(R.drawable.table_background);
                        table_item_layout.setOrientation(LinearLayout.VERTICAL);

                        DisplayMetrics metrics = getContext().getResources().getDisplayMetrics();
                        float dp = 11f;
                        float fpixels = metrics.density * dp;
                        int pixels = (int) (fpixels + 0.5f);

                        int dpValue = 5; // margin in dips
                        float d = getContext().getResources().getDisplayMetrics().density;
                        int margin = (int)(dpValue * d); // margin in pixels

                        LinearLayout.LayoutParams lip = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        lip.setMargins(0,margin,0,margin);
                        table_item_layout.setLayoutParams(lip);

                        LinearLayout.LayoutParams lip2 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                        lip2.setMargins(0,margin,0,margin);

                        TextView tv = new TextView(getContext());
                        tv.setText(at.getActivity_date());
                        tv.setLayoutParams(lip2);
                        tv.setGravity(Gravity.CENTER);
                        tv.setTextColor(getResources().getColor(R.color.white));
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,14);

                        table_item_layout.addView(tv);

                        final ImageView checked = new ImageView(getContext());
                        if(at.isCompleted()){
                            checked.setImageResource(R.drawable.check);
                        }else{
                            checked.setImageResource(R.drawable.uncheck);
                        }

                        container.addView(checked);

                        final ArrayList<String> takenActivities = new ArrayList<String>();

                        for(SingleActivity sa : at.getActivities()){
//                            View v2 = LayoutInflater.from(getContext()).inflate(R.layout.fixed_row,null);
//                            TextView tv1 = (TextView) v2.findViewById(R.id.fixed_activity);
//                            TextView tv2 = (TextView) v2.findViewById(R.id.fixed_duration);
//                            TextView tv3 = (TextView) v2.findViewById(R.id.fixed_times);
//
////                            tv1.setTextColor(getResources().getColor(R.color.colorCalories));
////                            tv2.setTextColor(getResources().getColor(R.color.moderate));
////                            tv3.setTextColor(getResources().getColor(R.color.mild));
//
//                            tv1.setText(sa.getActivity_name());
//                            tv2.setText(sa.getActivity_duration()+" Hour(s)");
//                            tv3.setText(sa.getNumberOfTimesPerDay()+" Per Day");
//
//                            Log.d("PatientsActivities",sa.toString());
//
//                            table_item_layout.addView(v2);

                            TextView tv1 = new TextView(getContext());
                            TextView tv2 = new TextView(getContext());
                            TextView tv3 = new TextView(getContext());

                            tv1.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                            tv2.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));
                            tv3.setLayoutParams(new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f));

                            tv1.setGravity(Gravity.CENTER);
                            tv2.setGravity(Gravity.CENTER);
                            tv3.setGravity(Gravity.CENTER);

                            tv1.setTextColor(getResources().getColor(R.color.colorCalories));
                            tv2.setTextColor(getResources().getColor(R.color.moderate));
                            tv3.setTextColor(getResources().getColor(R.color.mild));

                            tv1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,13);
                            tv2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,13);
                            tv3.setTextSize(TypedValue.COMPLEX_UNIT_DIP,13);

                            tv1.setText(sa.getActivity_name());
                            tv2.setText(sa.getActivity_duration()+" Hour(s)");
                            tv3.setText(sa.getNumberOfTimesPerDay()+" times/day");

                            takenActivities.add(sa.getActivity_name());
                            LinearLayout ll = new LinearLayout(getContext());
                            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            lp.setMargins(margin,margin,margin,margin);

                            ll.setLayoutParams(lp);
                            ll.addView(tv1);
                            ll.addView(tv2);
                            ll.addView(tv3);

                            table_item_layout.addView(ll);

                            LinearLayout.LayoutParams lpp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 2); // or set height to any fixed value you want
                            lpp.setMargins(margin*2, 0, margin*2, 0);

                            View separator = new View(getContext());
                            separator.setLayoutParams(lpp);
                            separator.setBackgroundColor(getResources().getColor(R.color.colorPrimary));

                            table_item_layout.addView(separator);

                        }
                        table_item_layout.removeViewAt(table_item_layout.getChildCount()-1);
                        container.addView(table_item_layout);
                        activities_container.addView(container);

                        checked.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(!at.isCompleted()){
                                    checked.setImageResource(R.drawable.check);
                                    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Fitness").child("Activity").child(at.getActivityID());
                                    mRef.child("completed").setValue(true);

                                    double consumed = 0;
                                    if(p.getWeight() > 56.699 && p.getWeight() < 70.3068){      //his weight between 125lb & 155 lb
                                        for(int x=0 ; x<takenActivities.size() ; x++){
                                            for (int xx=0 ; xx<activitiesMap125.size() ; xx++){
                                                if(activitiesMap125.containsKey(takenActivities.get(x))){
                                                    consumed += activitiesMap125.get(takenActivities.get(x));
                                                }
                                            }
                                        }
                                    }else if(p.getWeight() > 70.3068 && p.getWeight() < 83.9146){      //his weight between 155lb & 185 lb
                                        for(int x=0 ; x<takenActivities.size() ; x++){
                                            for (int xx=0 ; xx<activitiesMap155.size() ; xx++){
                                                if(activitiesMap155.containsKey(takenActivities.get(x))){
                                                    consumed += activitiesMap155.get(takenActivities.get(x));
                                                }
                                            }
                                        }
                                    }else if(p.getWeight() > 83.9146){      //his weight higher than 185lb
                                        for(int x=0 ; x<takenActivities.size() ; x++){
                                            for (int xx=0 ; xx<activitiesMap185.size() ; xx++){
                                                if(activitiesMap185.containsKey(takenActivities.get(x))){
                                                    consumed += activitiesMap185.get(takenActivities.get(x));
                                                }
                                            }
                                        }
                                    }else{
                                    }
                                    // 3500 calories = 1 pound , 2.20462 pounds = 1 kg
                                    // 3500 * 2.20462 = 7716.17
                                    double newWeight = round(p.getWeight() + (consumed / (7716.17)),2);
                                    p.setWeight(newWeight);
                                    FirebaseDatabase.getInstance().getReference("Fitness").child("Patient").child(p.getUser_id()).child("weight").setValue(newWeight);

                                    Toast.makeText(getContext(),"Calories Consumed "+consumed,Toast.LENGTH_LONG).show();
                                    Toast.makeText(getContext(),"New Weight "+newWeight,Toast.LENGTH_LONG).show();

                                    Intent i = new Intent(getContext(),MainActivity.class);
                                    i.putExtra("type",2);
                                    i.putExtra("user",p);
                                    startActivity(i);
                                }
                            }
                        });

                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }
}
