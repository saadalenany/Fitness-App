package com.example.root.fitnessapp.models;

import java.io.Serializable;

/**
 * Created by root on 09/06/17.
 */

public class Patient extends User implements Serializable{

    String bodytype;
    int bmi;
    double weight;
    double height;
    String patient_doctor;
    String patient_illness;
    int plandays;

    public Patient() {
    }

    public Patient(String user_id, String user_name, String user_password, int user_type, String user_email, String gender, int age, String phone, String bodytype, double weight, double height, String patient_doctor, String patient_illness,int plandays) {
        super(user_id, user_name, user_password, user_type, user_email, gender, age, phone);
        this.bodytype = bodytype;
        this.weight = weight;
        this.height = height;
        this.patient_doctor = patient_doctor;
        this.patient_illness = patient_illness;
        this.plandays =  plandays;
    }

    public Patient(String user_id, String user_name, String user_password, int user_type, String user_email, String gender, int age, String phone, String bodytype, int bmi, double weight, double height, String patient_doctor, String patient_illness,int  plandays) {
        super(user_id, user_name, user_password, user_type, user_email, gender, age, phone);
        this.bodytype = bodytype;
        this.bmi = bmi;
        this.weight = weight;
        this.height = height;
        this.patient_doctor = patient_doctor;
        this.patient_illness = patient_illness;
        this.plandays =  plandays;
    }

    public String getBodytype() {
        return bodytype;
    }

    public void setBodytype(String bodytype) {
        this.bodytype = bodytype;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public String getPatient_doctor() {
        return patient_doctor;
    }

    public void setPatient_doctor(String patient_doctor) {
        this.patient_doctor = patient_doctor;
    }

    public String getPatient_illness() {
        return patient_illness;
    }

    public void setPatient_illness(String patient_illness) {
        this.patient_illness = patient_illness;
    }

    public int getBMI() {
        return bmi;
    }

    public void setBMI(int BMI) {
        this.bmi = BMI;
    }

    public int getPlandays() {
        return plandays;
    }

    public void setPlandays(int plandays) {
        this.plandays = plandays;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_password='" + user_password + '\'' +
                ", user_type=" + user_type +
                ", user_email='" + user_email + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", bodytype='" + bodytype + '\'' +
                ", weight=" + weight +
                ", height=" + height +
                ", patient_doctor='" + patient_doctor + '\'' +
                ", patient_illness='" + patient_illness + '\'' +
                ", BMI='" + bmi + '\'' +
                '}';
    }

}
