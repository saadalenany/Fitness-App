package com.example.root.fitnessapp.models;

/**
 * Created by root on 12/06/17.
 */

public class Neutrulist extends User{

    String doctor_id;

    public Neutrulist() {
    }

    public Neutrulist(String user_id, String user_name, String user_password, int user_type, String user_email, String gender, int age, String phone, String doctor_id) {
        super(user_id, user_name, user_password, user_type, user_email, gender, age, phone);
        this.doctor_id = doctor_id;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    @Override
    public String toString() {
        return "Nutritionist{" +
                "user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_password='" + user_password + '\'' +
                ", user_type=" + user_type +
                ", user_email='" + user_email + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                ", doctor_id='" + doctor_id + '\'' +
                '}';
    }
}
