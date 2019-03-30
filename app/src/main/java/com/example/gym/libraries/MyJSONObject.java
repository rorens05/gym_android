package com.example.gym.libraries;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyJSONObject {
    private JSONObject jsonObject;

    public MyJSONObject(JSONObject jsonObject){
        this.jsonObject = jsonObject;
    }

    public boolean isSuccess(){
        try {
            if(jsonObject.getString("status").equalsIgnoreCase("success")){
                return true;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String get(String key){
        try {
            String message = jsonObject.getString(key);
            return message;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "no data found";
    }

    public JSONArray getArray(String key){
        try {
            JSONArray jsonArray = jsonObject.getJSONArray(key);
            return jsonArray;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getJSON(String key){
        try {
            JSONObject jsonArray = jsonObject.getJSONObject(key);
            return jsonArray;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getMessage() {
        try {
            return jsonObject.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "no message found";
    }
}
