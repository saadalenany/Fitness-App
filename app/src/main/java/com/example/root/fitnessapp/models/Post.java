package com.example.root.fitnessapp.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by root on 20/06/17.
 */

public class Post implements Serializable {

    String post_id;
    String post_owner;
    String post_date;
    String post_content;

    public Post() {
    }

    public Post(String post_id, String post_owner, String post_date, String post_content) {
        this.post_id = post_id;
        this.post_owner = post_owner;
        this.post_date = post_date;
        this.post_content = post_content;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getPost_owner() {
        return post_owner;
    }

    public void setPost_owner(String post_owner) {
        this.post_owner = post_owner;
    }

    public String getPost_date() {
        return post_date;
    }

    public void setPost_date(String post_date) {
        this.post_date = post_date;
    }

    public String getPost_content() {
        return post_content;
    }

    public void setPost_content(String post_content) {
        this.post_content = post_content;
    }

    @Override
    public String toString() {
        return "Post{" +
                "post_id='" + post_id + '\'' +
                ", post_owner='" + post_owner + '\'' +
                ", post_date='" + post_date + '\'' +
                ", post_content='" + post_content + '\'' +
                '}';
    }
}
