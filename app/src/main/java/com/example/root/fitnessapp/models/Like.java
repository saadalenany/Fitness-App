package com.example.root.fitnessapp.models;

import java.io.Serializable;

/**
 * Created by root on 20/06/17.
 */

public class Like implements Serializable{

    String liker_id;
    String post_id;
    boolean like_notified;

    public Like() {
    }

    public Like(String liker_id, String post_id, boolean like_notified) {
        this.liker_id = liker_id;
        this.post_id = post_id;
        this.like_notified = like_notified;
    }

    public String getLiker_id() {
        return liker_id;
    }

    public void setLiker_id(String liker_id) {
        this.liker_id = liker_id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public boolean isLike_notified() {
        return like_notified;
    }

    public void setLike_notified(boolean like_notified) {
        this.like_notified = like_notified;
    }

    @Override
    public String toString() {
        return "Like{" +
                "liker_id='" + liker_id + '\'' +
                ", post_id='" + post_id + '\'' +
                ", like_notified=" + like_notified +
                '}';
    }
}
