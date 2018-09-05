package com.example.root.fitnessapp.models;

import java.io.Serializable;

/**
 * Created by root on 20/06/17.
 */

public class Comment implements Serializable{
    String commentator_id;
    String post_id;
    String comment_content;
    String comment_date;
    boolean comment_notified;

    public Comment() {
    }

    public Comment(String commentator_id, String post_id, String comment_content, String comment_date, boolean comment_notified) {
        this.commentator_id = commentator_id;
        this.post_id = post_id;
        this.comment_content = comment_content;
        this.comment_date = comment_date;
        this.comment_notified = comment_notified;
    }

    public String getCommentator_id() {
        return commentator_id;
    }

    public void setCommentator_id(String commentator_id) {
        this.commentator_id = commentator_id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }

    public String getComment_content() {
        return comment_content;
    }

    public void setComment_content(String comment_content) {
        this.comment_content = comment_content;
    }

    public String getComment_date() {
        return comment_date;
    }

    public void setComment_date(String comment_date) {
        this.comment_date = comment_date;
    }

    public boolean isComment_notified() {
        return comment_notified;
    }

    public void setComment_notified(boolean comment_notified) {
        this.comment_notified = comment_notified;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentator_id='" + commentator_id + '\'' +
                ", post_id='" + post_id + '\'' +
                ", comment_content='" + comment_content + '\'' +
                ", comment_date='" + comment_date + '\'' +
                ", comment_notified=" + comment_notified +
                '}';
    }
}
