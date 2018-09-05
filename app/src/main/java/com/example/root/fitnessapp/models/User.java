package com.example.root.fitnessapp.models;

import java.io.Serializable;

/**
 * Created by root on 09/06/17.
 */

public class User implements Serializable{

    String user_id;
    String user_name;
    String user_password;
    int user_type;
    String user_email;
    String gender;
    int age;
    String phone;

    public User() {
    }

    public User(String user_id, String user_name, String user_password, int user_type, String user_email, String gender, int age, String phone) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_password = user_password;
        this.user_type = user_type;
        this.user_email = user_email;
        this.gender = gender;
        this.age = age;
        this.phone = phone;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public int getUser_type() {
        return user_type;
    }

    public void setUser_type(int user_type) {
        this.user_type = user_type;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "user_id='" + user_id + '\'' +
                ", user_name='" + user_name + '\'' +
                ", user_password='" + user_password + '\'' +
                ", user_type=" + user_type +
                ", user_email='" + user_email + '\'' +
                ", gender='" + gender + '\'' +
                ", age=" + age +
                ", phone='" + phone + '\'' +
                '}';
    }
}
