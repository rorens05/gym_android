package com.example.gym.global;

import com.example.gym.models.Day;
import com.example.gym.models.Exercise;
import com.example.gym.models.HealthStatus;
import com.example.gym.models.Routine;
import com.example.gym.models.User;

import java.util.ArrayList;
import java.util.List;

public class GlobalVariables {
    public static User user;

    public static List<Routine> routineList = new ArrayList<>();
    public static Routine selectedRoutine = null;

    public static List<Day> dayList = new ArrayList<>();
    public static Day selectedDay = null;

    public static List<Exercise> exerciseList = new ArrayList<>();
    public static Exercise selectedExercise = null;

    public static String user_id = "1";

    public static HealthStatus time_in = null;
    public static HealthStatus time_out = null;

}
