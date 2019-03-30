package com.example.gym.models;

public class Day {
    public String id;
    public String user_id;
    public String note;
    public String date_created;
    public String day_no;

    public Day(String id, String user_id, String note, String date_created, String day_no) {
        this.id = id;
        this.user_id = user_id;
        this.note = note;
        this.date_created = date_created;
        this.day_no = day_no;
    }
}
