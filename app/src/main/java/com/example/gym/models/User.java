package com.example.gym.models;

public class User {
    public String name;
    public String email;
    public String contact;
    public String address;
    public String gender;
    public String birthday;
    public String image_url;


    public User(String name, String email, String contact, String address, String gender, String birthday, String image_url) {
        this.name = name;
        this.email = email;
        this.contact = contact;
        this.address = address;
        this.gender = gender;
        this.birthday = birthday;
        this.image_url = image_url;
    }
}
