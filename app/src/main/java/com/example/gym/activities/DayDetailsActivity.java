package com.example.gym.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.gym.R;
import com.example.gym.global.ConstantVariables;
import com.example.gym.global.GlobalVariables;
import com.example.gym.global.Statics;
import com.example.gym.libraries.MyJSONObject;
import com.example.gym.libraries.MySingleton;
import com.example.gym.models.HealthStatus;

import org.json.JSONArray;
import org.json.JSONException;

public class DayDetailsActivity extends AppCompatActivity {

    TextView day;
    TextView date;
    TextView note;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_day_details);
        day = findViewById(R.id.dd_day);
        date = findViewById(R.id.dd_date);
        note = findViewById(R.id.dd_note);

        day.setText("Day " + GlobalVariables.selectedDay.day_no);
        date.setText(Statics.getDate(Statics.stringToDate(GlobalVariables.selectedDay.date_created)));
        note.setText("Note: " + GlobalVariables.selectedDay.note);

        getHealthStatus();

    }

    private void getHealthStatus(){
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
                        Toast.makeText(DayDetailsActivity.this, "health loaded", Toast.LENGTH_SHORT).show();

                    }
                }, Statics.getErrorListener(this));
        MySingleton.getInstance(this).addToRequestQueue(stringRequest);

    }

    public void showHealth(){

    }
}
