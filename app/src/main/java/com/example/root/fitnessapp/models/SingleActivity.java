package com.example.root.fitnessapp.models;

/**
 * Created by root on 18/06/17.
 */

public class SingleActivity {

    String activity_name;
    double activity_duration;
    int numberOfTimesPerDay;

    public SingleActivity() {
    }

    public SingleActivity(String activity_name, double activity_duration, int numberOfTimesPerDay) {
        this.activity_name = activity_name;
        this.activity_duration = activity_duration;
        this.numberOfTimesPerDay = numberOfTimesPerDay;
    }

    public String getActivity_name() {
        return activity_name;
    }

    public void setActivity_name(String activity_name) {
        this.activity_name = activity_name;
    }

    public double getActivity_duration() {
        return activity_duration;
    }

    public void setActivity_duration(double activity_duration) {
        this.activity_duration = activity_duration;
    }

    public int getNumberOfTimesPerDay() {
        return numberOfTimesPerDay;
    }

    public void setNumberOfTimesPerDay(int numberOfTimesPerDay) {
        this.numberOfTimesPerDay = numberOfTimesPerDay;
    }

    @Override
    public String toString() {
        return "SingleActivity{" +
                "activity_name='" + activity_name + '\'' +
                ", activity_duration=" + activity_duration +
                ", numberOfTimesPerDay=" + numberOfTimesPerDay +
                '}';
    }
}

