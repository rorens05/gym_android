package com.example.gym.models;

public class Exercise {
   public String id;
   public String name;
   public String description;
   public String usage;
   public String routine_id;
   public String image;

    public Exercise(String id, String name, String description, String usage, String routine_id, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.routine_id = routine_id;
        this.image = image;
    }
}
