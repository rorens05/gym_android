package com.example.gym.models;

import com.example.gym.global.GlobalVariables;

public class UserRoutine {
    public String id;
    public String day_id;
    public String routine_id;
    public Routine routine;

    public UserRoutine(String id, String day_id, String routine_id) {
        this.id = id;
        this.day_id = day_id;
        this.routine_id = routine_id;

        for(int i = 0; i < GlobalVariables.routineList.size(); i++){
            if(GlobalVariables.routineList.get(i).id.equalsIgnoreCase(routine_id)){
                routine = GlobalVariables.routineList.get(i);
            }
        }
    }

    @Override
    public String toString() {
        return "UserRoutine{" +
                "id='" + id + '\'' +
                ", day_id='" + day_id + '\'' +
                ", routine_id='" + routine_id + '\'' +
                ", routine=" + routine +
                '}';
    }
}
