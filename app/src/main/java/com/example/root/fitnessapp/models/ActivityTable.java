package com.example.root.fitnessapp.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by root on 18/06/17.
 */

public class ActivityTable implements Serializable{

    String activityID;
    String sender;
    String receiver;
    String activity_date;
    ArrayList<SingleActivity> activities = new ArrayList<>();
    boolean completed;

    public ActivityTable() {
    }

    public ActivityTable(String activityID, String sender, String receiver, String activity_date, ArrayList<SingleActivity> activities, boolean completed) {
        this.activityID = activityID;
        this.sender = sender;
        this.receiver = receiver;
        this.activity_date = activity_date;
        this.activities = activities;
        this.completed = completed;
    }

    public String getActivityID() {
        return activityID;
    }

    public void setActivityID(String activityID) {
        this.activityID = activityID;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getActivity_date() {
        return activity_date;
    }

    public void setActivity_date(String activity_date) {
        this.activity_date = activity_date;
    }

    public ArrayList<SingleActivity> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<SingleActivity> activities) {
        this.activities = activities;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "ActivityTable{" +
                "activityID='" + activityID + '\'' +
                ", sender='" + sender + '\'' +
                ", receiver='" + receiver + '\'' +
                ", activity_date='" + activity_date + '\'' +
                ", activities=" + activities +
                ", completed=" + completed +
                '}';
    }
}
