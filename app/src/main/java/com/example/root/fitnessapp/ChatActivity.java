package com.example.root.fitnessapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.root.fitnessapp.models.Doctor;
import com.example.root.fitnessapp.models.Messages;
import com.example.root.fitnessapp.models.Neutrulist;
import com.example.root.fitnessapp.models.Patient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by root on 13/06/17.
 */

public class ChatActivity extends AppCompatActivity {

    EditText chatedittext;
    ImageButton send;
    LinearLayout chatcontent;

    int type;
    Doctor d;
    Patient p;
    Neutrulist n;
    ProgressBar progress_bar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chat);

        chatedittext = (EditText) findViewById(R.id.chatedittext);
        send = (ImageButton) findViewById(R.id.send);
        chatcontent = (LinearLayout) findViewById(R.id.chatcontent);
        progress_bar = (ProgressBar) findViewById(R.id.progress_bar);

        progress_bar.setVisibility(View.VISIBLE);

        type = getIntent().getExtras().getInt("type");

        if(type==1){            //sender doctor , receiver patient
            if (p == null){
                d = (Doctor) getIntent().getExtras().getSerializable("sender");
                p = (Patient) getIntent().getExtras().getSerializable("receiver");
            }
            assert p != null;
            Log.d("type_1_receiver",p.toString());
            Log.d("type_1_sender",d.toString());

            setTitle(p.getUser_name());

            syncChat(d.getUser_id(),p.getUser_id() , true);

            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy HH:mm:ss");
                    String formattedDate = df.format(c.getTime());

                    if(!chatedittext.getText().toString().equals("")){
                        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Fitness").child("Message").push();
                        Messages m = new Messages(mRef.getKey(),chatedittext.getText().toString(),d.getUser_id(),p.getUser_id(),formattedDate,false);
                        mRef.setValue(m);

                        syncChat(d.getUser_id(),p.getUser_id() , true);

                        chatedittext.setText("");
                    }
                }
            });
            progress_bar.setVisibility(View.GONE);
        }else if(type==2){      //sender patient , receiver doctor
            p = (Patient) getIntent().getExtras().getSerializable("sender");
            d = (Doctor) getIntent().getExtras().getSerializable("receiver");

            assert p != null;
            assert d != null;

            Log.d("type_2_sender",p.toString());
            Log.d("type_2_receiver",d.toString());

            setTitle(d.getUser_name());

            syncChat(p.getUser_id(),d.getUser_id() , false);

            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy HH:mm:ss");
                    String formattedDate = df.format(c.getTime());

                    if(!chatedittext.getText().toString().equals("")){

                        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Fitness").child("Message").push();

                        Messages m = new Messages(mRef.getKey(),chatedittext.getText().toString(),p.getUser_id(),d.getUser_id(),formattedDate,false);
                        mRef.setValue(m);

                        syncChat(p.getUser_id(),d.getUser_id() , false);

                        chatedittext.setText("");
                    }
                }
            });
            progress_bar.setVisibility(View.GONE);
        }else if(type==3){      //sender neutrulist , receiver patient
            n = (Neutrulist) getIntent().getExtras().getSerializable("sender");
            p = (Patient) getIntent().getExtras().getSerializable("receiver");

            assert p != null;
            assert n != null;

            Log.d("type_3_sender",n.toString());
            Log.d("type_3_receiver",p.toString());

            setTitle(p.getUser_name());

            syncChat(n.getUser_id(),p.getUser_id() , false);

            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy HH:mm:ss");
                    String formattedDate = df.format(c.getTime());

                    if(!chatedittext.getText().toString().equals("")){

                        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Fitness").child("Message").push();

                        Messages m = new Messages(mRef.getKey(),chatedittext.getText().toString(),n.getUser_id(),p.getUser_id(),formattedDate,false);
                        mRef.setValue(m);

                        syncChat(n.getUser_id(),p.getUser_id() , false);

                        chatedittext.setText("");
                    }
                }
            });
            progress_bar.setVisibility(View.GONE);
        }else if(type==4){      //sender patient , receiver neutrulist
            p = (Patient) getIntent().getExtras().getSerializable("sender");
            n = (Neutrulist) getIntent().getExtras().getSerializable("receiver");

            assert p != null;
            assert n != null;

            Log.d("type_4_sender",p.toString());
            Log.d("type_4_receiver",n.toString());

            setTitle(n.getUser_name());

            syncChat(p.getUser_id(),n.getUser_id() , false);

            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Calendar c = Calendar.getInstance();
                    SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy HH:mm:ss");
                    String formattedDate = df.format(c.getTime());

                    if(!chatedittext.getText().toString().equals("")){

                        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Fitness").child("Message").push();

                        Messages m = new Messages(mRef.getKey(),chatedittext.getText().toString(),p.getUser_id(),n.getUser_id(),formattedDate,false);
                        mRef.setValue(m);

                        syncChat(p.getUser_id(),n.getUser_id() , false);

                        chatedittext.setText("");
                    }
                }
            });
            progress_bar.setVisibility(View.GONE);
        }else{
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        d = (Doctor) intent.getExtras().getSerializable("sender");
        p = (Patient) intent.getExtras().getSerializable("receiver");
    }

    public void syncChat(final String sender , final String receiver , final boolean makeItSeen){
        FirebaseDatabase.getInstance().getReference("Fitness").child("Message").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatcontent.removeAllViews();
                for(DataSnapshot dss : dataSnapshot.getChildren()) {
                    Messages m = dss.getValue(Messages.class);
                    if (m != null && sender != null && receiver != null){
                        if (m.getSender().equals(sender) && m.getReceiver().equals(receiver)) {
                            //message written by the sender
                            View view = getLayoutInflater().inflate(R.layout.messagesent_content, null);

                            Log.d("MessageContent", m.toString());

                            TextView mess = (TextView) view.findViewById(R.id.sent);
                            TextView date = (TextView) view.findViewById(R.id.messagesent_date);

                            if (m.getMessage_content().equals("Severe Thinness") || m.getMessage_content().equals("Moderate Thinness") || m.getMessage_content().equals("Mild Thinness") ||
                                    m.getMessage_content().equals("Normal") || m.getMessage_content().equals("Overweight") || m.getMessage_content().equals("Obese class I") ||
                                    m.getMessage_content().equals("Obese class II") || m.getMessage_content().equals("Obese class III") || m.getMessage_content().contains("Plan")) {
                                FrameLayout fl = (FrameLayout) view.findViewById(R.id.frame1);
                                fl.setBackgroundResource(R.drawable.special);
                            }
                            mess.setText(m.getMessage_content());
                            date.setText(m.getMessage_date());

                            chatcontent.addView(view);

                        } else if (m.getSender().equals(receiver) && m.getReceiver().equals(sender)) {
                            //message written by the sender
                            View view = getLayoutInflater().inflate(R.layout.messagereceived_content, null);

                            TextView mess = (TextView) view.findViewById(R.id.received);
                            TextView date = (TextView) view.findViewById(R.id.messagereceived_date);
                            if (m.getMessage_content().equals("Severe Thinness") || m.getMessage_content().equals("Moderate Thinness") || m.getMessage_content().equals("Mild Thinness") ||
                                    m.getMessage_content().equals("Normal") || m.getMessage_content().equals("Overweight") || m.getMessage_content().equals("Obese class I") ||
                                    m.getMessage_content().equals("Obese class II") || m.getMessage_content().equals("Obese class III")) {
                                FrameLayout fl = (FrameLayout) view.findViewById(R.id.frame2);
                                fl.setBackgroundResource(R.drawable.special);
                            }

                            mess.setText(m.getMessage_content());
                            date.setText(m.getMessage_date());

                            chatcontent.addView(view);
                        }
                        if (makeItSeen) {
                            FirebaseDatabase.getInstance().getReference("Fitness").child("Message").child(m.getMessageID()).child("seen").setValue(true);
                        }
                    }
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });

    }

}
