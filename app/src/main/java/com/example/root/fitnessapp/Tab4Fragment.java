package com.example.root.fitnessapp;


import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class Tab4Fragment extends Fragment  {
    TextView v;
    TextView v2;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.tab4, container, false);
        v=(TextView) view.findViewById(R.id.text1);
        v2=(TextView) view.findViewById(R.id.text2);
        Typeface t=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Green Avocado-bold.ttf");
        Typeface t2=Typeface.createFromAsset(getActivity().getAssets(),"fonts/Green Avocado-thin.ttf");

             v2.setTypeface(t2);
             v.setTypeface(t);



        return view;


    }
}
