package com.example.root.fitnessapp.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by root on 19/06/17.
 */

public class SensoryChart implements Serializable{

    String sensory_patientID;
    ArrayList<String> times = new ArrayList<>();
    ArrayList<String> val1 = new ArrayList<>();
    ArrayList<String> val2 = new ArrayList<>();
    String sensory_uploaded;
    String blood_pressure;
    String sugar_pressure;

    public SensoryChart() {
    }

    public SensoryChart(String sensory_patientID, ArrayList<String> times, ArrayList<String> val1, ArrayList<String> val2, String sensory_uploaded, String blood_pressure, String sugar_pressure) {
        this.sensory_patientID = sensory_patientID;
        this.times = times;
        this.val1 = val1;
        this.val2 = val2;
        this.sensory_uploaded = sensory_uploaded;
        this.blood_pressure = blood_pressure;
        this.sugar_pressure = sugar_pressure;
    }

    public String getSensory_patientID() {
        return sensory_patientID;
    }

    public void setSensory_patientID(String sensory_patientID) {
        this.sensory_patientID = sensory_patientID;
    }

    public ArrayList<String> getTimes() {
        return times;
    }

    public void setTimes(ArrayList<String> times) {
        this.times = times;
    }

    public ArrayList<String> getVal1() {
        return val1;
    }

    public void setVal1(ArrayList<String> val1) {
        this.val1 = val1;
    }

    public ArrayList<String> getVal2() {
        return val2;
    }

    public void setVal2(ArrayList<String> val2) {
        this.val2 = val2;
    }

    public String getSensory_uploaded() {
        return sensory_uploaded;
    }

    public void setSensory_uploaded(String sensory_uploaded) {
        this.sensory_uploaded = sensory_uploaded;
    }

    public String getBlood_pressure() {
        return blood_pressure;
    }

    public void setBlood_pressure(String blood_pressure) {
        this.blood_pressure = blood_pressure;
    }

    public String getSugar_pressure() {
        return sugar_pressure;
    }

    public void setSugar_pressure(String sugar_pressure) {
        this.sugar_pressure = sugar_pressure;
    }
}
