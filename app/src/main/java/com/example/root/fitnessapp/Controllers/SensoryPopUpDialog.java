package com.example.root.fitnessapp.Controllers;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.root.fitnessapp.R;
import com.example.root.fitnessapp.models.SensoryChart;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.series.DataPoint;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by root on 19/06/17.
 */

public class SensoryPopUpDialog extends DialogFragment {

    LinearLayout patient_sensory_layout;
    ProgressBar popup;
    String PID;

    public static ArrayList<DataPoint[]> dp1 = new ArrayList<>();
    public static ArrayList<DataPoint[]> dp2 = new ArrayList<>();
    public static ArrayList<String> sugarText = new ArrayList<>();
    public static ArrayList<String> bloodText = new ArrayList<>();

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.sensory_popup, container, false);

        getDialog().setTitle("Sensory Data");

        patient_sensory_layout = (LinearLayout) v.findViewById(R.id.patient_sensory_layout);
        popup = (ProgressBar) v.findViewById(R.id.popup_progress);
        popup.setVisibility(View.VISIBLE);

        if(getArguments() != null){
            PID = getArguments().getString("patientID");
            FirebaseDatabase.getInstance().getReference("Fitness").child("Sensory").orderByChild("sensory_patientID").equalTo(PID).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("PopUp Fragment","Inside loop");
                    int x=0;
                    for(DataSnapshot dss : dataSnapshot.getChildren()){
                        SensoryChart sc = dss.getValue(SensoryChart.class);
                        View view = LayoutInflater.from(getContext()).inflate(R.layout.chart_item, null);
                        sugarText.add(sc.getSugar_pressure());
                        bloodText.add(sc.getBlood_pressure());

                        final Button date = (Button) view.findViewById(R.id.chart_item_date);

                        date.setText(sc.getSensory_uploaded());
                        final int finalX = x;
                        date.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ChartFragmentDialog cfd = new ChartFragmentDialog();
                                Bundle b1 = new Bundle();
                                b1.putInt("index", finalX);
                                cfd.setArguments(b1);
                                cfd.show(getFragmentManager(),"Chart for "+date.getText());
                            }
                        });

                        dp1.add(new DataPoint[sc.getVal1().size()]);
                        dp2.add(new DataPoint[sc.getVal2().size()]);
                        for(int i=0 ; i<sc.getTimes().size() ; i++){
                            SimpleDateFormat sdf = new SimpleDateFormat("m:ss.SSS");
                            Date dt = null;
                            try {
                                dt = sdf.parse(sc.getTimes().get(i));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            assert dt != null;
                            dp1.get(x)[i] = new DataPoint(dt,Double.parseDouble(sc.getVal1().get(i)));
                            dp2.get(x)[i] = new DataPoint(dt,Double.parseDouble(sc.getVal2().get(i)));
                        }

                        patient_sensory_layout.addView(view);
                        x++;
                    }
                    popup.setVisibility(View.GONE);
                    if (patient_sensory_layout.getChildCount() == 1){
                        popup.setVisibility(View.GONE);
                        TextView tv = new TextView(getContext());
                        tv.setText("Nothing recorded yet!");
                        patient_sensory_layout.addView(tv);
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        return v;
    }

}
