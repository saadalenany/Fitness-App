package com.example.root.fitnessapp;

import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.root.fitnessapp.models.Comment;
import com.example.root.fitnessapp.models.Doctor;
import com.example.root.fitnessapp.models.Like;
import com.example.root.fitnessapp.models.Neutrulist;
import com.example.root.fitnessapp.models.Patient;
import com.example.root.fitnessapp.models.Post;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by root on 20/06/17.
 */

public class CommunityFragment extends Fragment {

    EditText post_content;
    Button save_post;
    LinearLayout post_container;
    int type;
    Doctor d;
    Patient p;
    Neutrulist n;

    ArrayList<Post> posts = MainActivity.posts;
    Bundle b;

    ArrayList<Like> likers = MainActivity.likes;

    ArrayList<Comment> commentators = MainActivity.comments;

    Bundle sis;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sis = savedInstanceState;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.community_fragment,container,false);

        getActivity().setTitle("Social Community");

        post_content = (EditText) view.findViewById(R.id.post_content);
        save_post = (Button) view.findViewById(R.id.save_post);
        post_container = (LinearLayout) view.findViewById(R.id.post_container);

        b = getArguments();
        type = getArguments().getInt("type");

        if(type==1) {
            d = (Doctor) getArguments().getSerializable("user");

            assert d != null;
            inflatePosts(d.getUser_id());
            save_post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!post_content.getText().toString().equals("")){
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy HH:mm:ss");
                        String formattedDate = df.format(c.getTime());

                        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Fitness").child("Post").push();
                        Post post = new Post(mRef.getKey(),d.getUser_id(),formattedDate,post_content.getText().toString());
                        mRef.setValue(post);

                        post_content.setText("");

                        Intent i = new Intent(getContext(),MainActivity.class);
                        i.putExtra("type",1);
                        i.putExtra("user",d);
                        startActivity(i);
                    }
                }
            });

        }else if(type==2){
            p = (Patient) getArguments().getSerializable("user");

            assert p != null;
            inflatePosts(p.getUser_id());
            save_post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!post_content.getText().toString().equals("")){
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy HH:mm:ss");
                        String formattedDate = df.format(c.getTime());

                        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Fitness").child("Post").push();
                        Post post = new Post(mRef.getKey(),p.getUser_id(),formattedDate,post_content.getText().toString());
                        mRef.setValue(post);

                        post_content.setText("");

                        Intent i = new Intent(getContext(),MainActivity.class);
                        i.putExtra("type",2);
                        i.putExtra("user",p);
                        startActivity(i);
                    }
                }
            });
        }else if(type==3){
            n = (Neutrulist) getArguments().getSerializable("user");

            assert n != null;
            inflatePosts(n.getUser_id());
            save_post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(!post_content.getText().toString().equals("")){
                        Calendar c = Calendar.getInstance();
                        SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy HH:mm:ss");
                        String formattedDate = df.format(c.getTime());

                        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Fitness").child("Post").push();
                        Post post = new Post(mRef.getKey(),p.getUser_id(),formattedDate,post_content.getText().toString());
                        mRef.setValue(post);

                        post_content.setText("");

                        Intent i = new Intent(getContext(),MainActivity.class);
                        i.putExtra("type",3);
                        i.putExtra("user",n);
                        startActivity(i);
                    }
                }
            });
        }

        return view;
    }

    public void inflatePosts(final String userID){
        for(int i=posts.size()-1 ; i>=0 ; i--){
            View v = getLayoutInflater(b).inflate(R.layout.post_item,null);
            final LinearLayout poster = (LinearLayout) v.findViewById(R.id.poster);
            TextView post_owner = (TextView) v.findViewById(R.id.post_owner);
            TextView post_date = (TextView) v.findViewById(R.id.post_date);
            final TextView post_data = (TextView) v.findViewById(R.id.post_data);
            final ImageButton like_post = (ImageButton) v.findViewById(R.id.like_post);
            final ImageButton comment_post = (ImageButton) v.findViewById(R.id.comment_post);
            final TextView numOfLikers = (TextView) v.findViewById(R.id.likers);
            final TextView numOfCommentators = (TextView) v.findViewById(R.id.commentators);

            for(int j=0 ; j<LoginActivity.doctors.size() ; j++){
                if(LoginActivity.doctors.get(j).getUser_id().equals(posts.get(i).getPost_owner())){
                    post_owner.setText(LoginActivity.doctors.get(j).getUser_name());
                    break;
                }
            }
            for(int j=0 ; j<LoginActivity.patients.size() ; j++){
                if(LoginActivity.patients.get(j).getUser_id().equals(posts.get(i).getPost_owner())){
                    post_owner.setText(LoginActivity.patients.get(j).getUser_name());
                    break;
                }
            }

            post_date.setText(posts.get(i).getPost_date());
            post_data.setText(posts.get(i).getPost_content());

            final int finalI1 = i;
            DatabaseReference likeref = FirebaseDatabase.getInstance().getReference("Fitness").child("Like");
            Query query = likeref.orderByChild("post_id").equalTo(posts.get(i).getPost_id());
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("Firebase","Post "+ finalI1 +" likers => "+dataSnapshot.getChildrenCount());
                    numOfLikers.setText(dataSnapshot.getChildrenCount()+"");
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            DatabaseReference fdb2 = FirebaseDatabase.getInstance().getReference("Fitness").child("Comment");
            Query query2 = fdb2.orderByChild("post_id").equalTo(posts.get(i).getPost_id());
            query2.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Log.d("Firebase","Post "+ finalI1 +" commentators => "+dataSnapshot.getChildrenCount());
                    numOfCommentators.setText(dataSnapshot.getChildrenCount()+"");
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            if(numOfLikers.getText().toString().equals("")){
                numOfLikers.setText("0");
            }

            if(numOfCommentators.getText().toString().equals("")){
                numOfCommentators.setText("0");
            }

            for(int j=0 ; j<likers.size() ; j++){
                if(likers.get(j).getPost_id().equals(posts.get(i).getPost_id())){
                    if(likers.get(j).getLiker_id().equals(userID)){
                        like_post.setImageResource(android.R.color.transparent);
                        like_post.setBackgroundResource(R.drawable.like);
                        like_post.setTag("like");
                        numOfLikers.setText((Integer.parseInt(numOfLikers.getText().toString())+1)+"");
                        break;
                    }else{
                        like_post.setImageResource(android.R.color.transparent);
                        like_post.setBackgroundResource(R.drawable.dislike);
                        like_post.setTag("dislike");
                        numOfLikers.setText((Integer.parseInt(numOfLikers.getText().toString())-1)+"");
                    }
                }
            }

            if(like_post.getTag() == null){
                like_post.setTag("dislike");
            }

            final int finalI = i;
            like_post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (like_post.getTag().equals("like")){
                        like_post.setImageResource(android.R.color.transparent);
                        like_post.setBackgroundResource(R.drawable.dislike);
                        like_post.setTag("dislike");

                        DatabaseReference fdb = FirebaseDatabase.getInstance().getReference("Fitness").child("Like");
                        Query query = fdb.orderByChild("liker_id").equalTo(userID);
                        query.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                for (DataSnapshot appleSnapshot: dataSnapshot.getChildren()) {
                                    appleSnapshot.getRef().removeValue();
                                }
                            }
                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });

                    }else{
                        like_post.setImageResource(android.R.color.transparent);
                        like_post.setBackgroundResource(R.drawable.like);
                        like_post.setTag("like");

                        DatabaseReference fdb = FirebaseDatabase.getInstance().getReference("Fitness").child("Like").push();
                        Like l = new Like(userID,posts.get(finalI).getPost_id(),false);
                        fdb.setValue(l);
                    }
                    DatabaseReference likeref = FirebaseDatabase.getInstance().getReference("Fitness").child("Like");
                    likeref.orderByChild("post_id").equalTo(posts.get(finalI1).getPost_id()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Log.d("Firebase","Post "+ finalI1 +" likers => "+dataSnapshot.getChildrenCount());
                            numOfLikers.setText(dataSnapshot.getChildrenCount()+"");
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }
            });

            comment_post.setTag("uncomment");
            comment_post.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(comment_post.getTag().equals("uncomment")) {
                        final View vv = getLayoutInflater(sis).inflate(R.layout.type_comment,null);

                        final EditText editText = (EditText) vv.findViewById(R.id.comment_area);
                        ImageButton ib = (ImageButton) vv.findViewById(R.id.type_comment);

                        ib.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (!editText.getText().toString().equals("")) {

                                    Calendar c = Calendar.getInstance();
                                    SimpleDateFormat df = new SimpleDateFormat("EEE, MMM d, yyyy HH:mm:ss");
                                    String formattedDate = df.format(c.getTime());

                                    DatabaseReference fdb = FirebaseDatabase.getInstance().getReference("Fitness").child("Comment").push();
                                    Comment com = new Comment(userID,posts.get(finalI).getPost_id(),editText.getText().toString(),formattedDate,false);
                                    fdb.setValue(com);

                                    comment_post.setTag("uncomment");
                                    poster.removeView(vv);

                                    Intent i = new Intent(getContext(),MainActivity.class);
                                    if(type==1){
                                        i.putExtra("type",1);
                                        i.putExtra("user",d);
                                    }else if(type==2){
                                        i.putExtra("type",2);
                                        i.putExtra("user",p);
                                    }else if(type==3){
                                        i.putExtra("type",3);
                                        i.putExtra("user",n);
                                    }
                                    startActivity(i);

                                }
                            }
                        });

                        poster.addView(vv);
                        comment_post.setTag("commented");

                        for(int j=0 ; j<commentators.size() ; j++){
                            if(commentators.get(j).getPost_id().equals(posts.get(finalI).getPost_id())){
                                View view = getLayoutInflater(sis).inflate(R.layout.comment_item,null);
                                TextView name = (TextView) view.findViewById(R.id.commentator_name);
                                TextView date = (TextView) view.findViewById(R.id.comment_date);
                                TextView content = (TextView) view.findViewById(R.id.comment_content);

                                for(int x=0 ; x<LoginActivity.doctors.size() ; x++){
                                    if(LoginActivity.doctors.get(x).getUser_id().equals(commentators.get(j).getCommentator_id())){
                                        name.setText(LoginActivity.doctors.get(x).getUser_name());
                                        break;
                                    }
                                }
                                for(int x=0 ; x<LoginActivity.patients.size() ; x++){
                                    if(LoginActivity.patients.get(x).getUser_id().equals(commentators.get(j).getCommentator_id())){
                                        name.setText(LoginActivity.patients.get(x).getUser_name());
                                        break;
                                    }
                                }

                                date.setText(commentators.get(j).getComment_date());
                                content.setText(commentators.get(j).getComment_content());

                                View separator = new View(getContext());
                                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1);
                                lp.setMargins(10,2,10,2);
                                separator.setLayoutParams(lp);
                                separator.setBackgroundColor(getResources().getColor(R.color.obese_iii));

                                poster.addView(separator);
                                poster.addView(view);
                            }
                        }
                    }
                }
            });

            View separator = new View(getContext());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,1);
            lp.setMargins(0,5,0,5);
            separator.setLayoutParams(lp);
            separator.setBackgroundColor(getResources().getColor(android.R.color.transparent));

            post_container.addView(separator);
            post_container.addView(v);
        }
    }
}
