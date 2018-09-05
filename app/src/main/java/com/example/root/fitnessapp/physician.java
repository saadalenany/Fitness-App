package com.example.root.fitnessapp;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.root.fitnessapp.models.Doctor;

public class physician extends AppCompatActivity {



    private SectionsPageAdapter mSectionsPageAdapter;

    private ViewPager mViewPager;
    public static Doctor d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pysician_taps);

        mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.containerr);
        setupViewPager(mViewPager);
        d= (Doctor) getIntent().getSerializableExtra("doctor");

        TabLayout tabLayout = (TabLayout) findViewById(R.id.table);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());
        Tab1Fragment tab1Fragment=new Tab1Fragment();
        tab1Fragment.setDoctor(d);
        adapter.addFragment(new Tab1Fragment(), "BMR");
        adapter.addFragment(new Tab2Fragment(), "Ideal Weight");
        adapter.addFragment(new Tab3Fragment(), "Losing Calories");
        adapter.addFragment(new Tab4Fragment(), "Habits");


        viewPager.setAdapter(adapter);
    }

}

