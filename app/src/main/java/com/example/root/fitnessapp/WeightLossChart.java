package com.example.root.fitnessapp;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.fitnessapp.models.Patient;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.ArrayList;

public class WeightLossChart extends AppCompatActivity  implements AdapterView.OnItemSelectedListener {

    LineGraphSeries<DataPoint> series;

    /////////////////////////////////////////////////////
    // Array list for Data
    static ArrayList<Integer> arr_date = new ArrayList<>();
    static ArrayList<Double> arr_weight = new ArrayList<>();
    static ArrayList<String> arr_pulusandminus = new ArrayList<>();
    static ArrayList<Double> arr_bmi = new ArrayList<>();

    int one[] = {1, 2, 3, 4, 5, 6};
    int two[] = {2, 3, 5453, 2, 432, 2};
    int date_jj = 0;
    int duration;


    /////////////////////////////////////////////////////
    Spinner sp6;

    TextView start_weight;
    TextView height;
    TextView BMI;
    TextView goalWeight;
    TextView start_bmi;
    TextView goal_bmi;
    EditText edit;
    TableLayout tl;
    TableRow tr;
    TextView date, weight;
    TextView un;
    TextView deux;
    TextView trois;
    TextView quater;
    TextView cinq;
    TextView sept;
    TextView neuf;
    TextView top_center;
    TextView pm;
    TextView bmr_view;
    EditText starting, ending;
    TextView info;
    Button bb;
    TextView infoo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_loss_chart);
        un = (TextView) findViewById(R.id.un);
        top_center = (TextView) findViewById(R.id.top_center);
        deux = (TextView) findViewById(R.id.deux);
        trois = (TextView) findViewById(R.id.trois);
        quater = (TextView) findViewById(R.id.quater);
        cinq = (TextView) findViewById(R.id.cinq);
        sept = (TextView) findViewById(R.id.sept);
        neuf = (TextView) findViewById(R.id.neuf);
        info=(TextView) findViewById(R.id.info);
        final GraphView graph = (GraphView) findViewById(R.id.graph1);
        infoo=(TextView) findViewById(R.id.infoo);
        Typeface customfont = Typeface.createFromAsset(getAssets(), "fonts/Green Avocado-bold.ttf");
        Typeface customfont2 = Typeface.createFromAsset(getAssets(), "fonts/FallingSkyExtOu.otf");
        Typeface customfont3 = Typeface.createFromAsset(getAssets(), "fonts/Android Insomnia Regular.ttf");
        top_center.setTypeface(customfont);
        un.setTypeface(customfont3);
        deux.setTypeface(customfont3);
        trois.setTypeface(customfont3);
        quater.setTypeface(customfont3);
        cinq.setTypeface(customfont3);
        sept.setTypeface(customfont3);
        neuf.setTypeface(customfont3);
        info.setTypeface(customfont2);


        starting = (EditText) findViewById(R.id.starting);
        ending = (EditText) findViewById(R.id.ending);
        tl = (TableLayout) findViewById(R.id.table_view);
        start_weight = (TextView) findViewById(R.id.start_weight);
        start_weight.setTypeface(customfont2);
        goalWeight = (TextView) findViewById(R.id.goalWeight);
        goalWeight.setTypeface(customfont2);
        sp6 = (Spinner) findViewById(R.id.spinner6);
        height = (TextView) findViewById(R.id.height);
        height.setTypeface(customfont2);
        goal_bmi = (TextView) findViewById(R.id.bmi_data_goal);
        bb=(Button)findViewById(R.id.clear);
        goal_bmi.setTypeface(customfont2);
        start_bmi = (TextView) findViewById(R.id.bmi_data_start);
        start_bmi.setTypeface(customfont2);
        edit = (EditText) findViewById(R.id.bmi_data_start);
        edit.setTypeface(customfont2);
        starting.setTypeface(customfont2);
        ending.setTypeface(customfont2);

        //adding headers


        //Get Users Data
        ArrayList<String> Users_Emails = new ArrayList<String>();
        final ArrayList<Patient> kk = LoginActivity.patients;
        for (int i = 0; i < kk.size(); i++) {
            if (kk.get(i).getPatient_doctor().equals(MainActivity.doctor.getUser_id())) {
                Users_Emails.add(kk.get(i).getUser_email());
            }
        }

        //////////////////////////////////////////////////////////





        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, Users_Emails);
        sp6.setAdapter(adapter);
        sp6.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                for (int i = 0; i < kk.size(); i++) {
                    String selected = sp6.getSelectedItem().toString();
                    if (kk.get(i).getUser_email().equals(selected)) {
                        //Weight
                        double weight = kk.get(i).getWeight();
                        weight = Math.round(weight);
                        start_weight.setText(weight + "");
                        //Height
                        double heightt = kk.get(i).getHeight();

                        heightt = Math.round(heightt);
                        height.setText(heightt + "");

                        double real_bmi = kk.get(i).getBMI();
                        real_bmi = Math.round(real_bmi);
                        edit.setText(real_bmi + "");

                        String name=kk.get(i).getUser_name();
                        info.setText("Selected patient is : "+" "+name);

                        //get Height in meter
                        double h = heightt / 100;
                        double hh = h * h;

                        //Goal Weight
                        double GoalWeight = Math.round(22 * (hh));
                        goalWeight.setText(GoalWeight + "");


                        double my_weight = weight;
                        double my_weight_lose = weight;

                        double size_data = (weight - GoalWeight) * 2;
                        for (int loop = 0; loop < 100; loop++) {

                            if (GoalWeight < my_weight) {

                                my_weight -= 1;
                                date_jj = date_jj + 1;
                                arr_weight.add(my_weight);
                                double bmie = Math.round(my_weight / (h * h));
                                arr_bmi.add(bmie);
                                arr_date.add(date_jj);
                                arr_pulusandminus.add("-");


                            } else if (my_weight_lose < GoalWeight) {
                                my_weight_lose += 1;
                                date_jj = date_jj + 1;
                                arr_weight.add(my_weight_lose);
                                double bmiee = Math.round(my_weight_lose / (h * h));
                                arr_bmi.add(bmiee);
                                arr_date.add(date_jj);
                                arr_pulusandminus.add("+");
                            } else {
                                starting.setText(arr_date.get(0) + "W");
                                ending.setText(arr_date.size() + "W");

                                if(125 < weight && weight>155) {
                                    double foodball_week_carious = .5*7;
                                    int weeks = arr_date.size();
                                    double duration_to_lose = foodball_week_carious * weeks;

                                    infoo.setText(name + " , " + "you have to make" +
                                            " " + duration_to_lose + " " + "hours" + " " +
                                            "physical activities"+","+ " "+"provided that "+
                                            foodball_week_carious+" "+"hours"+ " "+"per week till "+arr_date.size()+"weeks");                                  duration = arr_date.size() - 1;
                                }
                                else if(155<weight && weight>185){

                                    double foodball_week_carious = .75*7;
                                    int weeks = arr_date.size();
                                    double duration_to_lose = foodball_week_carious * weeks;

                                    infoo.setText(name + " , " + "you have to make" +
                                            " " + duration_to_lose + " " + "hours" + " " +
                                            "physical activities"+","+ " "+"Provided that "+
                                            foodball_week_carious+" "+"hours"+ " "+"per week till "+arr_date.size()+"weeks");
                                    duration = arr_date.size() - 1;

                                }else {
                                    double foodball_week_carious = .5*7;
                                    int weeks = arr_date.size();
                                    double duration_to_lose = foodball_week_carious * weeks;

                                    infoo.setText(name + " , " + "you have to make" +
                                            " " + duration_to_lose + " " + "hours" + " " +
                                            "physical activities"+","+ " "+"provided that "+
                                            foodball_week_carious+" "+"hours"+ " "+"per week till "+arr_date.size()+"weeks");
                                    duration = arr_date.size() - 1;
                                }

                                break;
                            }
                        }


                        //Goal BMI
                        double goalBmi = 22;

                        goal_bmi.setText(goalBmi + "");


                        double y, x;
                        double bmi_graph;


                        series = new LineGraphSeries<DataPoint>();
                        for(int io =0; io<arr_weight.size(); io++) {
                            x = arr_date.get(io);
                            y = arr_weight.get(io);
                            bmi_graph=arr_bmi.get(io);
                            series.appendData(new DataPoint(x, y), true, 200);
                            //series.appendData(new DataPoint(22,bmi_graph),true,200);
                        }

                        graph.addSeries(series);
                        series.setDrawDataPoints(true);
                        series.setDataPointsRadius(1);


                    }

                }
                bb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        graph.removeAllSeries();

                    }
                });
                addHeaders();
                addData();
                date_jj=0;


                arr_date.clear();
                arr_weight.clear();
                arr_pulusandminus.clear();

                graph.clearFocus();
                arr_bmi.clear();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });

             arr_date.clear();
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }




    /** This function add the headers to the table **/
    public void addHeaders(){

        /** Create a TableRow dynamically **/
        tr = new TableRow(this);
        tr.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));


        /** Creating a TextView to add to the row **/
        TextView companyTV = new TextView(this);
        companyTV.setText("Weight");
        companyTV.setTextColor(Color.GRAY);
        companyTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        companyTV.setPadding(45, 5, 5, 0);
        tr.addView(companyTV);  // Adding textView to tablerow.


        /** Creating another textview **/
        TextView valueTV = new TextView(this);
        valueTV.setText("Date");
        valueTV.setTextColor(Color.GRAY);
        valueTV.setPadding(58, 5, 5, 0);
        valueTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(valueTV); // Adding textView to tablerow.


        TextView plusOrMinus = new TextView(this);
        plusOrMinus.setText("+/-");
        plusOrMinus.setTextColor(Color.GRAY);
        plusOrMinus.setPadding(20, 5, 5, 0);
        plusOrMinus.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(plusOrMinus);




        TextView bmii = new TextView(this);
        bmii.setText("BMI");
        bmii.setTextColor(Color.GRAY);
        bmii.setPadding(20, 5, 5, 0);
        bmii.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(bmii);

        // Add the TableRow to the TableLayout
        tl.addView(tr, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        // we are adding two textviews for the divider because we have two columns
        tr = new TableRow(this);
        tr.setLayoutParams(new LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));

        /** Creating another textview **/
        TextView divider = new TextView(this);
        divider.setText("-----------------");
        divider.setTextColor(Color.GREEN);
        divider.setPadding(5, 0, 0, 0);
        divider.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(divider); // Adding textView to tablerow.

        TextView divider2 = new TextView(this);
        divider2.setText("-----------------");
        divider2.setTextColor(Color.GREEN);
        divider2.setPadding(5, 0, 0, 0);
        divider2.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(divider2); // Adding textView to tablerow.

        TextView divider3 = new TextView(this);
        divider3.setText("-----------------");
        divider3.setTextColor(Color.GREEN);
        divider3.setPadding(5, 0, 0, 0);
        divider3.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(divider3);


        TextView divider4 = new TextView(this);
        divider4.setText("-----------------");
        divider4.setTextColor(Color.GREEN);
        divider4.setPadding(5, 0, 0, 0);
        divider4.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(divider4);

        // Add the TableRow to the TableLayout
        tl.addView(tr, new TableLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT));
    }



    /** This function add the data to the table **/
    public void addData(){

        for (int i = 0; i < arr_weight.size(); i++)
        {
            /** Create a TableRow dynamically **/
            tr = new TableRow(this);
            tr.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));

            /** Creating a TextView to add to the row **/
            date = new TextView(this);
            String ll=arr_weight.get(i).toString();

            Toast.makeText(getApplicationContext(),ll + " ", Toast.LENGTH_SHORT).show();
            date.setText(ll);
//            date.setText(one+"");
            date.setTextColor(Color.RED);
            date.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            date.setPadding(45, 5, 5, 5);
            tr.addView(date);  // Adding textView to tablerow.

            /** Creating another textview **/
            weight = new TextView(this);
            String kk=arr_date.get(i).toString();
            weight.setText(kk+" "+"Week");
//              weight.setText(two+"");
            weight.setTextColor(Color.BLUE);
            weight.setPadding(58, 5, 5, 5);
            weight.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            tr.addView(weight);

            // Adding textView to tablerow.


            pm = new TextView(this);
            String nn=arr_pulusandminus.get(i);
            pm.setText(nn);
//              weight.setText(two+"");
            pm.setTextColor(Color.BLUE);
            pm.setPadding(50, 5, 5, 5);
            pm.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            tr.addView(pm);

            bmr_view = new TextView(this);
            double ff=arr_bmi.get(i);
            bmr_view.setText(ff+"");
//              weight.setText(two+"");
            bmr_view.setTextColor(Color.BLACK);
            bmr_view.setPadding(10, 5, 5, 5);
            bmr_view.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            tr.addView(bmr_view);






            // Add the TableRow to the TableLayout
            tl.addView(tr, new TableLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT,
                    LayoutParams.WRAP_CONTENT));
        }

    }








}
