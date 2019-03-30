package com.example.gym.activities.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.gym.R;
import com.example.gym.activities.recycler_adapters.DayAdapter;
import com.example.gym.activities.recycler_adapters.RoutineAdapter;
import com.example.gym.global.StaticVariables;
import com.example.gym.global.Statics;
import com.example.gym.libraries.MyJSONObject;
import com.example.gym.libraries.MySingleton;
import com.example.gym.models.Day;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;

public class HistoryFragment extends Fragment {

    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_history, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("History");
        recyclerView = getView().findViewById(R.id.history_recycler_view);
        loadDays();

    }

    public void loadDays(){
        Statics.startProgressDialog("Loading history", getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Statics.getDaysURL(),
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        StaticVariables.dayList = new ArrayList<>();
                        MyJSONObject myJSONObject = new MyJSONObject(Statics.parseJSON(response));
                        JSONArray jsonArray = myJSONObject.getArray("data");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            try {
                                MyJSONObject temp = new MyJSONObject(jsonArray.getJSONObject(i));
                                StaticVariables.dayList.add(new Day(
                                        temp.get("id"),
                                        temp.get("user_id"),
                                        temp.get("note"),
                                        temp.get("date_created"),
                                        temp.get("day_no")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        loadRecyclerView();
                        Statics.stopProgressDialog();
                    }
                }, Statics.getErrorListener(getContext()));
        MySingleton.getInstance(getContext()).addToRequestQueue(stringRequest);
        
    }


    public void loadRecyclerView() {

        Collections.reverse(StaticVariables.dayList);
        DayAdapter adapter = new DayAdapter(StaticVariables.dayList, getActivity());
        recyclerView.setAdapter(adapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
