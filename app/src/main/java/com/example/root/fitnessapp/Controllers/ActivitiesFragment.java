package com.example.root.fitnessapp.Controllers;

import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.root.fitnessapp.R;
import com.example.root.fitnessapp.models.ActivityTable;
import com.example.root.fitnessapp.models.Doctor;
import com.example.root.fitnessapp.models.Patient;
import com.example.root.fitnessapp.models.SingleActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * Created by root on 20/06/17.
 */

public class ActivitiesFragment extends DialogFragment {

    LinearLayout activities_container;
    ImageButton send;

    Doctor d;
    Patient p;

    Bundle b;

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activities_fragment,container,false);

        activities_container = (LinearLayout) view.findViewById(R.id.activities_container);
        send = (ImageButton) view.findViewById(R.id.send_activity);

        b = getArguments();
        d = (Doctor) getArguments().getSerializable("doctor");
        p = (Patient) getArguments().getSerializable("patient");

        assert p != null;
        Log.d("Activity Patient ",p.toString());
        Log.d("Activity Doctor ",d.toString());

        getDialog().setTitle(p.getUser_name()+" activities");

        retrieveActivities(d.getUser_id(),p.getUser_id());

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addActivityTableDialog atd = new addActivityTableDialog();
                atd.setArguments(getArguments());
                atd.show(((FragmentActivity) getContext()).getSupportFragmentManager(),"Activity for "+p.getUser_name());
            }
        });

        return view;
    }

    public void retrieveActivities(final String sender , final String receiver){
        FirebaseDatabase.getInstance().getReference("Fitness").child("Activity").addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                activities_container.removeAllViews();
                for(DataSnapshot dss : dataSnapshot.getChildren()) {
                    ActivityTable at = dss.getValue(ActivityTable.class);
                    if(at.getReceiver().equals(receiver) && at.getSender().equals(sender)){
//                        View view = getLayoutInflater(b).inflate(R.layout.table_item,null);
//                        LinearLayout table_item_layout = (LinearLayout) view.findViewById(R.id.table_item_layout);
//                        TextView tv = (TextView) view.findViewById(R.id.table_item_date);
//                        tv.setText(at.getActivity_date());
//                        ImageView checked = (ImageView) view.findViewById(R.id.checked);

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
                        tv.setTextSize(TypedValue.COMPLEX_UNIT_DIP,13);

                        table_item_layout.addView(tv);

                        ImageView checked = new ImageView(getContext());
                        if(at.isCompleted()){
                            checked.setImageResource(R.drawable.check);
                        }else{
                            checked.setImageResource(R.drawable.uncheck);
                        }

                        container.addView(checked);

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

                            tv1.setTextColor(getResources().getColor(R.color.colorCalories));
                            tv2.setTextColor(getResources().getColor(R.color.moderate));
                            tv3.setTextColor(getResources().getColor(R.color.mild));

                            tv1.setGravity(Gravity.CENTER);
                            tv2.setGravity(Gravity.CENTER);
                            tv3.setGravity(Gravity.CENTER);

                            tv1.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);
                            tv2.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);
                            tv3.setTextSize(TypedValue.COMPLEX_UNIT_DIP,12);

                            tv1.setText(sa.getActivity_name());
                            tv2.setText(sa.getActivity_duration()+" Hour(s)");
                            tv3.setText(sa.getNumberOfTimesPerDay()+" Per Day");

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
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
}
