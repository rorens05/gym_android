package com.example.gym.global;

import com.example.gym.models.Day;
import com.example.gym.models.Routine;
import com.example.gym.models.User;

import java.util.ArrayList;
import java.util.List;

public class StaticVariables {
    public static User user;
    public static List<Routine> routineList = new ArrayList<>();

    public static List<Day> dayList = new ArrayList<>();

    public static String user_id = "1";
}
