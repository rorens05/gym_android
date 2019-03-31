package com.example.gym.global;

import android.app.ProgressDialog;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Statics {
    private static ProgressDialog progressDialog;


    public static void startProgressDialog(String message, Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);
        progressDialog.setMessage(message);
        progressDialog.show();
    }

    public static void stopProgressDialog() {
        progressDialog.dismiss();
    }

    public static JSONObject parseJSON(String response){

        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(response);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }

    public static Response.ErrorListener getErrorListener(final Context context) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Cannot connect to the server", Toast.LENGTH_SHORT).show();
                stopProgressDialog();
            }
        };

    }

    public static String getDaysURL(){
        return ConstantVariables.GET_DAYS_URL + "?id=" + GlobalVariables.user_id;
    }

    public static String getExerciseURL(){
        return ConstantVariables.GET_EXERCISES_URL + "?id=" + GlobalVariables.selectedRoutine.id;
    }

    public static Date stringToDate(String sdate){
        try {
            return new SimpleDateFormat("yyyy-MM-dd").parse(sdate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getDay(Date date){
        DateFormat dateFormat = new SimpleDateFormat("EEEE");
        return dateFormat.format(date);
    }

    public static String getDate(Date date){
        DateFormat dateFormat = new SimpleDateFormat("MMMM dd, yyyy");
        return dateFormat.format(date);
    }

    public static String getHealthURL() {
        return ConstantVariables.GET_HEALTH + "?id=" + GlobalVariables.selectedDay.id;
    }

    public static String getUserRoutineURL() {
        return ConstantVariables.GET_USER_ROUTINES + "?id=" + GlobalVariables.selectedDay.id;
    }
}
