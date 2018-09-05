package com.example.root.fitnessapp;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.root.fitnessapp.models.Comment;
import com.example.root.fitnessapp.models.DailyIntake;
import com.example.root.fitnessapp.models.Doctor;
import com.example.root.fitnessapp.models.Like;
import com.example.root.fitnessapp.models.Messages;
import com.example.root.fitnessapp.models.Neutrulist;
import com.example.root.fitnessapp.models.Patient;
import com.example.root.fitnessapp.models.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    Patient patient;
    Neutrulist neutrulist;
    static Doctor doctor;

    public static ArrayList<Post> posts = new ArrayList<>();
    public static ArrayList<Like> likes = new ArrayList<>();
    public static ArrayList<Comment> comments = new ArrayList<>();

    Bundle bundle = new Bundle();

    public static ArrayList<DailyIntake> PatientDailyIntake;
    public static ArrayList<Patient> patients;

    int type ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        PatientDailyIntake = new ArrayList<DailyIntake>();
        patients = new ArrayList<Patient>();

        PatientDailyIntake.clear();
        patients.clear();
        posts.clear();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View navHeaderView= navigationView.getHeaderView(0);
        TextView user_header = (TextView) navHeaderView.findViewById(R.id.user_header);
        TextView usertype_header = (TextView) navHeaderView.findViewById(R.id.usertype_header);

        type = getIntent().getExtras().getInt("type");
        if(type == 1){
            doctor = (Doctor) getIntent().getExtras().getSerializable("user");

            assert doctor != null;
            user_header.setText(doctor.getUser_name());
            usertype_header.setText("Physician");

/*************************changing navigation bar for doctor*************************************/

            navigationView.getMenu().findItem(R.id.message_neutrulist).setVisible(false);
            navigationView.getMenu().findItem(R.id.social_network_data).setVisible(false);
            navigationView.getMenu().findItem(R.id.upload_sensoryFile).setVisible(false);
            navigationView.getMenu().findItem(R.id.patient_activities).setVisible(false);
            navigationView.getMenu().findItem(R.id.message_doctor).setVisible(true);
            navigationView.getMenu().findItem(R.id.message_doctor).setTitle("ECG Mining");
            navigationView.getMenu().findItem(R.id.message_doctor).setIcon(R.drawable.charticon);
            navigationView.getMenu().findItem(R.id.calculate_data).setVisible(true);
            navigationView.getMenu().findItem(R.id.calculate_data).setTitle("Calories Calculations");
            navigationView.getMenu().findItem(R.id.profile).setVisible(true);
            navigationView.getMenu().findItem(R.id.graph).setVisible(true);
            navigationView.getMenu().findItem(R.id.tabs).setVisible(true);

            getDoctorPatients(doctor.getUser_id());

            bundle.putInt("type", 1);
            bundle.putSerializable("user",doctor);

            getUnReadableMessages(doctor);
        }else if(type == 2){
            patient = (Patient) getIntent().getExtras().getSerializable("user");

            assert patient != null;
            user_header.setText(patient.getUser_name());
            usertype_header.setText("Patient");

/*************************changing navigation bar for patient*************************************/
            navigationView.getMenu().findItem(R.id.message_neutrulist).setVisible(true);
            navigationView.getMenu().findItem(R.id.social_network_data).setVisible(true);
            navigationView.getMenu().findItem(R.id.upload_sensoryFile).setVisible(true);
            navigationView.getMenu().findItem(R.id.message_doctor).setVisible(true);
            navigationView.getMenu().findItem(R.id.calculate_data).setVisible(true);
            navigationView.getMenu().findItem(R.id.profile).setVisible(true);
            navigationView.getMenu().findItem(R.id.patient_activities).setVisible(true);
            navigationView.getMenu().findItem(R.id.graph).setVisible(false);
            navigationView.getMenu().findItem(R.id.tabs).setVisible(false);

            getPatientIngredients();

            bundle.putInt("type", 2);
            bundle.putSerializable("user",patient);
        }else if(type == 3) {
            neutrulist = (Neutrulist) getIntent().getExtras().getSerializable("user");

            assert neutrulist != null;
            user_header.setText(neutrulist.getUser_name());
            usertype_header.setText("Nutritionist");

/*************************changing navigation bar for neutrulist*************************************/

            navigationView.getMenu().findItem(R.id.message_neutrulist).setVisible(false);
            navigationView.getMenu().findItem(R.id.calculate_data).setVisible(true);
            navigationView.getMenu().findItem(R.id.calculate_data).setTitle("Patients Food Calories");
            navigationView.getMenu().findItem(R.id.profile).setVisible(true);
            navigationView.getMenu().findItem(R.id.message_doctor).setVisible(false);
            navigationView.getMenu().findItem(R.id.social_network_data).setVisible(false);
            navigationView.getMenu().findItem(R.id.upload_sensoryFile).setVisible(false);
            navigationView.getMenu().findItem(R.id.patient_activities).setVisible(false);
            navigationView.getMenu().findItem(R.id.graph).setVisible(false);
            navigationView.getMenu().findItem(R.id.tabs).setVisible(false);

            bundle.putInt("type", 3);
            bundle.putSerializable("user", neutrulist);
        }
        posts.clear();
        likes.clear();
        comments.clear();

        getAllPosts();
        getAllLikes();
        getAllComments();

        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();

        ProfileActivity pro = new ProfileActivity();
        pro.setArguments(bundle);

        ft.replace(R.id.content_main,pro).commitAllowingStateLoss();

    }

    public void getUnReadableMessages(final Doctor d){
        final Patient[] pat = new Patient[1];
        FirebaseDatabase.getInstance().getReference("Fitness").child("Message").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dss : dataSnapshot.getChildren()){
                    final Messages m = dss.getValue(Messages.class);
                    if(!m.isSeen()){

                        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this);

                        for(int i=0 ; i<LoginActivity.patients.size() ; i++){
                            if(LoginActivity.patients.get(i).getPatient_doctor().equals(d.getUser_id())){
                                if(LoginActivity.patients.get(i).getUser_id().equals(m.getSender())){
                                    pat[0] = LoginActivity.patients.get(i);
                                    break;
                                }
                            }
                        }

                        if(pat[0] == null){return;}

                        if(m.getMessage_content().equals("Severe Thinness") || m.getMessage_content().equals("Moderate Thinness") || m.getMessage_content().equals("Mild Thinness") ||
                                m.getMessage_content().equals("Normal") || m.getMessage_content().equals("Overweight") || m.getMessage_content().equals("Obese class I") ||
                                m.getMessage_content().equals("Obese class II") || m.getMessage_content().equals("Obese class III")){
                            mBuilder.setSmallIcon(R.drawable.urgent);
                            mBuilder.setContentTitle("Urgent case!");
                        }else {
                            mBuilder.setSmallIcon(R.drawable.thumb);
                            mBuilder.setContentTitle("New Message!");
                        }

                        assert pat[0] != null;
                        mBuilder.setContentText(pat[0].getUser_name() +" "+m.getMessage_content());

                        Intent intent = new Intent(MainActivity.this, ChatActivity.class);
                        intent.putExtra("type", 1);
                        intent.putExtra("sender",d);
                        intent.putExtra("receiver", pat[0]);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                        PendingIntent i=PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                        mBuilder.setContentIntent(i);
                        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                        // notificationID allows you to update the notification later on.
                        mNotificationManager.notify(0, mBuilder.build());

                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void getAllPosts(){
        FirebaseDatabase.getInstance().getReference("Fitness").child("Post").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dss : dataSnapshot.getChildren()) {
                    posts.add(dss.getValue(Post.class));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void getAllLikes(){
        FirebaseDatabase.getInstance().getReference("Fitness").child("Like").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dss : dataSnapshot.getChildren()) {
                    likes.add(dss.getValue(Like.class));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void getAllComments(){
        FirebaseDatabase.getInstance().getReference("Fitness").child("Comment").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dss : dataSnapshot.getChildren()) {
                    comments.add(dss.getValue(Comment.class));
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void getDoctorPatients(final String drID){
        FirebaseDatabase.getInstance().getReference("Fitness").child("Patient").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot dss : dataSnapshot.getChildren()){
                    Patient p = dss.getValue(Patient.class);
                    assert p != null;
                    if(p.getPatient_doctor().equals(drID)){
                        Log.d("Patient ",p.toString());
                        patients.add(p);
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    public void getPatientIngredients(){
        FirebaseDatabase.getInstance().getReference("Fitness").child("DailyIntake").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("insider", "DailyIntake");
                for (DataSnapshot dss : dataSnapshot.getChildren()) {
                    DailyIntake in = dss.getValue(DailyIntake.class);
                    assert in != null;
                    if(in.getPatientID().equals(patient.getUser_id())){
                        PatientDailyIntake.add(in);
                        Log.d("Intake ",in.toString());
                        Log.d("PatientDailyIntake size", PatientDailyIntake.size()+"");
                        if(!in.isTaken()){
                            Log.d("MainActivity",in.toString());
                            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this);
                            mBuilder.setSmallIcon(R.drawable.food);
                            mBuilder.setContentTitle("New Intakes");
                            mBuilder.setContentText("tap to see!");

                            Intent intent = new Intent(MainActivity.this, NotifiedIntakes.class);
                            intent.putExtra("user",patient);

                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

                            PendingIntent i= PendingIntent.getActivity(MainActivity.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_ONE_SHOT);

                            mBuilder.setContentIntent(i);
                            NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                            // notificationID allows you to update the notification later on.
                            mNotificationManager.notify(0, mBuilder.build());
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentManager fm = getSupportFragmentManager();

        FragmentTransaction ft = fm.beginTransaction();

        BMICalculatorFragment bmi = new BMICalculatorFragment();
        CalorieCalculator calorie = new CalorieCalculator();
        ProfileActivity profile = new ProfileActivity();
        DoctorPatients pd = new DoctorPatients();
        PatientsSensoryData psd = new PatientsSensoryData();
        UploadSensoryFile usf = new UploadSensoryFile();
        PatientActivities pa = new PatientActivities();
        CommunityFragment cf = new CommunityFragment();
        NeutrulistPatients np = new NeutrulistPatients();

        ft.remove(profile);
        ft.remove(calorie);
        ft.remove(bmi);
        ft.remove(pd);
        ft.remove(psd);
        ft.remove(usf);
        ft.remove(pa);
        ft.remove(cf);
        ft.remove(np);

        if (id == R.id.calculate_data) {
            if(type == 2){
                bmi.setArguments(bundle);
                ft.replace(R.id.content_main,bmi).commit();
            }else if (type == 1){
                pd.setArguments(bundle);
                ft.replace(R.id.content_main,pd).commit();
            }else if(type == 3){
                np.setArguments(bundle);
                ft.replace(R.id.content_main,np).commit();
            }
        } else if (id == R.id.profile) {
            profile.setArguments(bundle);
            ft.replace(R.id.content_main, profile).commit();
        } else if (id == R.id.social_network_data) {
            calorie.setArguments(bundle);
            ft.replace(R.id.content_main, calorie).commit();
        }else if (id == R.id.tabs) {
            Intent i=new Intent(MainActivity.this,WeightLossChart.class);
            i.putExtra("doctor",doctor);
            startActivity(i);
        }else if (id == R.id.graph) {
            Intent i=new Intent(MainActivity.this,physician.class);
            startActivity(i);
        }else if (id == R.id.message_neutrulist) {
            Intent i = new Intent(MainActivity.this , ChatActivity.class);
            i.putExtra("type", 4);
            i.putExtra("sender",patient);
            i.putExtra("receiver",getNeutrulist(patient.getPatient_doctor()));
            startActivity(i);
        } else if (id == R.id.message_doctor) {
            if(type == 1){
                psd.setArguments(bundle);
                ft.replace(R.id.content_main,psd).commit();
            } else if (type == 2) {
                Intent i = new Intent(MainActivity.this , ChatActivity.class);
                i.putExtra("type", 2);
                i.putExtra("sender",patient);
                i.putExtra("receiver",getDoctor(patient.getPatient_doctor()));
                startActivity(i);
            }
        } else if (id == R.id.upload_sensoryFile) {
            usf.setArguments(bundle);
            ft.replace(R.id.content_main,usf).commit();
        } else if (id == R.id.patient_activities) {
            pa.setArguments(bundle);
            ft.replace(R.id.content_main,pa).commit();
        } else if (id == R.id.community) {
            cf.setArguments(bundle);
            ft.replace(R.id.content_main, cf).commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private Neutrulist getNeutrulist(String patient_doctor) {
        for(int i=0 ; i<LoginActivity.neutrulists.size() ; i++){
            if(LoginActivity.neutrulists.get(i).getDoctor_id().equals(patient_doctor)){
                Log.d("Neutrulist_found",LoginActivity.neutrulists.get(i).toString());
                return LoginActivity.neutrulists.get(i);
            }
        }
        return null;
    }

    public Doctor getDoctor(final String drID){
        for(int i=0 ; i<LoginActivity.doctors.size() ; i++){
            if(LoginActivity.doctors.get(i).getUser_id().equals(drID)){
                Log.d("Doctor_found",LoginActivity.doctors.get(i).toString());
                return LoginActivity.doctors.get(i);
            }
        }
        return null;
    }

    public Patient getPatient() {
        return patient;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public ArrayList<DailyIntake> getIngredients() {
        return PatientDailyIntake;
    }

}
