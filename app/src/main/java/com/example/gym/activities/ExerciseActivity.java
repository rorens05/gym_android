package com.example.gym.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.gym.R;
import com.example.gym.activities.recycler_adapters.ExerciseAdapter;
import com.example.gym.activities.recycler_adapters.RoutineAdapter;
import com.example.gym.global.ConstantVariables;
import com.example.gym.global.StaticVariables;
import com.example.gym.global.Statics;
import com.example.gym.libraries.MyJSONObject;
import com.example.gym.libraries.MySingleton;
import com.example.gym.models.Exercise;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;

public class ExerciseActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise);
        recyclerView = findViewById(R.id.exercise_recycler_view);
        loadRoutineDetails();
        loadExercises();
    }

    public void loadRoutineDetails(){
        ImageView image = findViewById(R.id.rd_image);
        TextView name= findViewById(R.id.rd_name);
        TextView description = findViewById(R.id.rd_description);

        if (StaticVariables.selectedRoutine.image.equalsIgnoreCase("")) {
        }else{
            Picasso.get()
                    .load(StaticVariables.selectedRoutine.image)
                    .resize(600, 300)
                    .centerCrop()
                    .into(image);
        }

        name.setText(StaticVariables.selectedRoutine.name);
        description.setText(StaticVariables.selectedRoutine.description);
    }


    public void loadExercises(){
        Statics.startProgressDialog("Loading Exercises", this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Statics.getExerciseURL(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        MyJSONObject myJSONObject = new MyJSONObject(Statics.parseJSON(response));
                        StaticVariables.exerciseList = new ArrayList<>();
                        JSONArray jsonArray = myJSONObject.getArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                MyJSONObject temp = new MyJSONObject(jsonArray.getJSONObject(i));
                                StaticVariables.exerciseList.add(new Exercise(
                                        temp.get("id"),
                                        temp.get("name"),
                                        temp.get("description"),
                                        temp.get("usage"),
                                        temp.get("routine_id"),
                                        temp.get("image")
                                        ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        loadRecyclerView();
                        Statics.stopProgressDialog();
                    }
                }, Statics.getErrorListener(this));
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void loadRecyclerView() {

        Collections.reverse(StaticVariables.exerciseList);
        ExerciseAdapter adapter = new ExerciseAdapter(StaticVariables.exerciseList, this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
