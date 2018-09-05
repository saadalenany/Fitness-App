package com.example.root.fitnessapp.models;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by root on 22/06/17.
 */

public class DailyIntake implements Serializable{

    String dayID;
    String patientID;
    String intakeDay;
    ArrayList<Ingredient> breakfast = new ArrayList<>();
    ArrayList<Ingredient> lunch = new ArrayList<>();
    ArrayList<Ingredient> dinner = new ArrayList<>();
    ArrayList<Ingredient> snacks = new ArrayList<>();
    double caloriesTotal;
    double caloriesConsumed;
    boolean taken;

    public DailyIntake() {
    }

    public DailyIntake(String dayID, String patientID, String intakeDay, ArrayList<Ingredient> breakfast, ArrayList<Ingredient> lunch, ArrayList<Ingredient> dinner, ArrayList<Ingredient> snacks, double caloriesTotal, double caloriesConsumed, boolean taken) {
        this.dayID = dayID;
        this.patientID = patientID;
        this.intakeDay = intakeDay;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
        this.snacks = snacks;
        this.caloriesTotal = caloriesTotal;
        this.caloriesConsumed = caloriesConsumed;
        this.taken = taken;
    }

    public String getDayID() {
        return dayID;
    }

    public void setDayID(String dayID) {
        this.dayID = dayID;
    }

    public String getPatientID() {
        return patientID;
    }

    public void setPatientID(String patientID) {
        this.patientID = patientID;
    }

    public String getIntakeDay() {
        return intakeDay;
    }

    public void setIntakeDay(String intakeDay) {
        this.intakeDay = intakeDay;
    }

    public ArrayList<Ingredient> getBreakfast() {
        return breakfast;
    }

    public void setBreakfast(ArrayList<Ingredient> breakfast) {
        this.breakfast = breakfast;
    }

    public ArrayList<Ingredient> getLunch() {
        return lunch;
    }

    public void setLunch(ArrayList<Ingredient> lunch) {
        this.lunch = lunch;
    }

    public ArrayList<Ingredient> getDinner() {
        return dinner;
    }

    public void setDinner(ArrayList<Ingredient> dinner) {
        this.dinner = dinner;
    }

    public ArrayList<Ingredient> getSnacks() {
        return snacks;
    }

    public void setSnacks(ArrayList<Ingredient> snacks) {
        this.snacks = snacks;
    }

    public double getCaloriesTotal() {
        return caloriesTotal;
    }

    public void setCaloriesTotal(double caloriesTotal) {
        this.caloriesTotal = caloriesTotal;
    }

    public double getCaloriesConsumed() {
        return caloriesConsumed;
    }

    public void setCaloriesConsumed(double caloriesConsumed) {
        this.caloriesConsumed = caloriesConsumed;
    }

    public boolean isTaken() {
        return taken;
    }

    public void setTaken(boolean taken) {
        this.taken = taken;
    }

    @Override
    public String toString() {
        return "DailyIntake{" +
                "dayID='" + dayID + '\'' +
                ", patientID='" + patientID + '\'' +
                ", intakeDay='" + intakeDay + '\'' +
                ", breakfast=" + breakfast +
                ", lunch=" + lunch +
                ", dinner=" + dinner +
                ", snacks=" + snacks +
                ", caloriesTotal=" + caloriesTotal +
                ", caloriesConsumed=" + caloriesConsumed +
                ", taken=" + taken +
                '}';
    }
}
