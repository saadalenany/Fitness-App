package com.example.root.fitnessapp.models;

import java.io.Serializable;

/**
 * Created by root on 09/06/17.
 */

public class Doctor extends User implements Serializable {

    public Doctor() {
    }

    public Doctor(String user_id, String user_name, String user_password, int user_type, String user_email, String gender, int age, String phone) {
        super(user_id, user_name, user_password, user_type, user_email, gender, age, phone);
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "user_id='" + user_id + '\'' +
                "user_name='" + user_name + '\'' +
                ", user_password='" + user_password + '\'' +
                ", user_type=" + user_type +
                ", user_email='" + user_email + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                '}';
    }
}
