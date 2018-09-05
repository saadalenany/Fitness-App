package com.example.root.fitnessapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.root.fitnessapp.Controllers.ChartFragmentDialog;
import com.example.root.fitnessapp.Controllers.FileChooser;
import com.example.root.fitnessapp.models.Doctor;
import com.example.root.fitnessapp.models.Ingredient;
import com.example.root.fitnessapp.models.Patient;
import com.example.root.fitnessapp.models.SensoryChart;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by root on 19/06/17.
 */

public class UploadSensoryFile extends Fragment{

    Button uploadsensorydatafile;
    LinearLayout sensory_data_layout;
    ProgressBar progress;
    EditText sugar;
    EditText blood;

    Patient p ;
    Context con;

    ArrayList<String> times = new ArrayList<>();
    ArrayList<String> val1 = new ArrayList<>();
    ArrayList<String> val2 = new ArrayList<>();

    public static ArrayList<DataPoint[]> dp1 = new ArrayList<>();
    public static ArrayList<DataPoint[]> dp2 = new ArrayList<>();
    public static ArrayList<String> sugarText = new ArrayList<>();
    public static ArrayList<String> bloodText = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.upload_sensory_file, container, false);

        con = getContext();

        getActivity().setTitle("Sensory Data");

        uploadsensorydatafile = (Button) v.findViewById(R.id.uploadsensorydatafile);
        sugar = (EditText) v.findViewById(R.id.sugar);
        blood = (EditText) v.findViewById(R.id.blood);
        sensory_data_layout = (LinearLayout) v.findViewById(R.id.sensory_data_layout);
        progress = (ProgressBar) v.findViewById(R.id.progress);
        progress.setVisibility(View.VISIBLE);

        Bundle b = getArguments();
        if(b.getInt("type") == 2){
            p = (Patient) b.getSerializable("user");
            FirebaseDatabase.getInstance().getReference("Fitness").child("Sensory").orderByChild("sensory_patientID").equalTo(p.getUser_id()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    int x=0;
                    for(DataSnapshot dss : dataSnapshot.getChildren()){
                        SensoryChart sc = dss.getValue(SensoryChart.class);
                        View v = LayoutInflater.from(con).inflate(R.layout.chart_item, null);

                        final Button date = (Button) v.findViewById(R.id.chart_item_date);

                        date.setText(sc.getSensory_uploaded());
                        final int finalX = x;
                        date.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ChartFragmentDialog cfd = new ChartFragmentDialog();
                                Bundle b1 = new Bundle();
                                b1.putInt("index",finalX);
                                cfd.setArguments(b1);
                                cfd.show(getFragmentManager(),"Chart for "+date.getText());
                            }
                        });

                        dp1.add(new DataPoint[sc.getVal1().size()]);
                        dp2.add(new DataPoint[sc.getVal2().size()]);
                        sugarText.add(sc.getSugar_pressure());
                        bloodText.add(sc.getBlood_pressure());
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

                        sensory_data_layout.addView(v);
                        x++;
                    }
                    progress.setVisibility(View.GONE);
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }

        uploadsensorydatafile.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onClick(View v) {
                if(sugar.getText().toString().equals("") || blood.getText().toString().equals("") ){
                    Toast.makeText(getContext(),"Insert Sugar & Blood Pressure first",Toast.LENGTH_SHORT).show();
                }else{
/**************************open both for drive & Storage "Requires an installed File Explorer"****************/
//                    Intent intentTxt = new Intent(Intent.ACTION_GET_CONTENT);
//                    intentTxt.setType("text/plain");
//                    intentTxt.addCategory(Intent.CATEGORY_OPENABLE);
//                    startActivityForResult(intentTxt,1);
                    Intent intent = new Intent(getContext(), FileChooser.class);
                    ArrayList<String> extensions = new ArrayList<>();
                    extensions.add(".txt");
                    intent.putStringArrayListExtra("filterFileExtension", extensions);
                    startActivityForResult(intent, 1);
                }
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data != null){
            String fileSelected = data.getStringExtra("fileSelected");
            Toast.makeText(getContext(), fileSelected, Toast.LENGTH_SHORT).show();
            Log.d("onActivityResult",fileSelected);
            File file = new File(fileSelected);

            try {
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                int lineNumber = 0;

                while ((line = br.readLine()) != null) {
                    if(lineNumber >= 2){
                        String[] splited = line.split("\\s+");
                        times.add(splited[1]);
                        val1.add(splited[2]);
                        val2.add(splited[3]);
                    }
                    lineNumber++;
                }

                Calendar c = Calendar.getInstance();
                SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy HH:mm:ss");
                String formattedDate = df.format(c.getTime());

                SensoryChart sc = new SensoryChart(p.getUser_id(),times,val1,val2,formattedDate,blood.getText().toString(),sugar.getText().toString());
                DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Fitness").child("Sensory").push();
                mRef.setValue(sc);

                Toast.makeText(con,"Data uploaded successfully",Toast.LENGTH_SHORT).show();
                br.close();
            }
            catch (IOException e) {
                //You'll need to add proper error handling here
            }
            super.onActivityResult(requestCode, resultCode, data);

            Intent i = new Intent(con,MainActivity.class);
            i.putExtra("type",2);
            i.putExtra("user",p);
            startActivity(i);
        }
    }
}
