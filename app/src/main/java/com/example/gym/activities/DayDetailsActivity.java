package com.example.gym.activities;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.gym.R;
import com.example.gym.activities.recycler_adapters.RoutineAdapter;
import com.example.gym.activities.recycler_adapters.UserRoutineAdapter;
import com.example.gym.global.ConstantVariables;
import com.example.gym.global.GlobalVariables;
import com.example.gym.global.Statics;
import com.example.gym.libraries.MyJSONObject;
import com.example.gym.libraries.MySingleton;
import com.example.gym.models.HealthStatus;
import com.example.gym.models.UserRoutine;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class DayDetailsActivity extends AppCompatActivity {

    TextView day;
    TextView date;
    TextView note;

    Button edit_time_in;
    Button edit_time_out;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_details);
        day = findViewById(R.id.dd_day);
        date = findViewById(R.id.dd_date);
        note = findViewById(R.id.dd_note);

        String sNote = GlobalVariables.selectedDay.note.equalsIgnoreCase("null") ? "" : GlobalVariables.selectedDay.note;
        day.setText("Day " + GlobalVariables.selectedDay.day_no);
        date.setText(Statics.getDate(Statics.stringToDate(GlobalVariables.selectedDay.date_created)));
        note.setText("Note: " + sNote);

        edit_time_in = findViewById(R.id.btn_edit_time_in);
        edit_time_out = findViewById(R.id.button_edit_time_out);

        edit_time_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.selectedHealthStatus = GlobalVariables.time_in;
                startActivityForResult(new Intent(DayDetailsActivity.this, EditHealthActivity.class), 1);
            }
        });

        edit_time_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.selectedHealthStatus = GlobalVariables.time_out;
                startActivityForResult(new Intent(DayDetailsActivity.this, EditHealthActivity.class), 1);
            }
        });

        getHealthStatus();

    }

    public void getHealthStatus(){
        Statics.startProgressDialog("Loading day", this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Statics.getHealthURL(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        MyJSONObject myJSONObject = new MyJSONObject(Statics.parseJSON(response));
                        JSONArray jsonArray = myJSONObject.getArray("data");

                        try {
                            MyJSONObject temp = new MyJSONObject(jsonArray.getJSONObject(0));
                            GlobalVariables.time_in = new HealthStatus(
                                    temp.get("id"),
                                    temp.get("day_id"),
                                    temp.get("height"),
                                    temp.get("weight"),
                                    temp.get("blood_pressure"),
                                    temp.get("sugar"),
                                    temp.get("waist_line"),
                                    temp.get("created_at"),
                                    temp.get("updated_at"),
                                    temp.get("image")

                                    );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        try {
                            MyJSONObject temp = new MyJSONObject(jsonArray.getJSONObject(1));
                            GlobalVariables.time_out = new HealthStatus(
                                    temp.get("id"),
                                    temp.get("day_id"),
                                    temp.get("height"),
                                    temp.get("weight"),
                                    temp.get("blood_pressure"),
                                    temp.get("sugar"),
                                    temp.get("waist_line"),
                                    temp.get("created_at"),
                                    temp.get("updated_at"),
                                    temp.get("image")
                            );
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        showHealth();
                        //Toast.makeText(DayDetailsActivity.this, "health loaded", Toast.LENGTH_SHORT).show();
                        loadUserRoutines();
                    }
                }, Statics.getErrorListener(this));
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    public void showHealth(){
        ImageView ti_image = findViewById(R.id.time_in_image);
        TextView ti_height = findViewById(R.id.time_in_height);
        TextView ti_weight = findViewById(R.id.time_in_weight);
        TextView ti_bp = findViewById(R.id.time_in_blood_pressure);
        TextView ti_sugar = findViewById(R.id.time_in_sugar);
        TextView ti_wl = findViewById(R.id.time_in_waste_line);

        if (GlobalVariables.time_in.image.equalsIgnoreCase("")) {
        }else{
            Picasso.get()
                    .load(GlobalVariables.time_in.image)
                    .resize(600, 600)
                    .centerCrop()
                    .into(ti_image);
        }
        ti_height.setText(GlobalVariables.time_in.height + " kg");
        ti_weight.setText(GlobalVariables.time_in.weight + " cm");
        ti_bp.setText(GlobalVariables.time_in.blood_pressure);
        ti_sugar.setText(GlobalVariables.time_in.sugar);
        ti_wl.setText(GlobalVariables.time_in.waist_line + " cm");

        ImageView to_image = findViewById(R.id.time_out_image);
        TextView to_height = findViewById(R.id.time_out_height);
        TextView to_weight = findViewById(R.id.time_out_weight);
        TextView to_bp = findViewById(R.id.time_out_bp);
        TextView to_sugar = findViewById(R.id.time_out_sugar);
        TextView to_wl = findViewById(R.id.to_waist_line);

        if (GlobalVariables.time_out.image.equalsIgnoreCase("")) {
        }else{
            Picasso.get()
                    .load(GlobalVariables.time_out.image)
                    .resize(600, 600)
                    .centerCrop()
                    .into(to_image);
        }
        to_height.setText(GlobalVariables.time_out.height + " kg");
        to_weight.setText(GlobalVariables.time_out.weight + " cm");
        to_bp.setText(GlobalVariables.time_out.blood_pressure);
        to_sugar.setText(GlobalVariables.time_out.sugar);
        to_wl.setText(GlobalVariables.time_out.waist_line + " cm");

    }

    public void loadUserRoutines(){
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                Statics.getUserRoutineURL(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                GlobalVariables.userRoutineList = new ArrayList<>();
                MyJSONObject myJSONObject = new MyJSONObject(Statics.parseJSON(response));
                JSONArray data = myJSONObject.getArray("data");
                for (int i = 0; i < data.length(); i++) {
                    try {
                        MyJSONObject temp = new MyJSONObject(data.getJSONObject(i));
                        UserRoutine userRoutine = new UserRoutine(
                                temp.get("id"),
                                temp.get("day_id"),
                                temp.get("routine_id")
                        );
                        GlobalVariables.userRoutineList.add(userRoutine);
                        System.out.println(userRoutine.toString());
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
        RecyclerView recyclerView = findViewById(R.id.dd_recycler_view);
        UserRoutineAdapter adapter = new UserRoutineAdapter(GlobalVariables.userRoutineList, this);
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        getHealthStatus();
    }
}
