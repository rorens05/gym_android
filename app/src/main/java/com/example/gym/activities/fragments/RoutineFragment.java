package com.example.gym.activities.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.gym.R;
import com.example.gym.activities.recycler_adapters.RoutineAdapter;
import com.example.gym.global.ConstantVariables;
import com.example.gym.global.StaticVariables;
import com.example.gym.global.Statics;
import com.example.gym.libraries.MyJSONObject;
import com.example.gym.libraries.MySingleton;
import com.example.gym.models.Routine;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.Collections;

public class RoutineFragment extends Fragment {

    RecyclerView recyclerView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_routine, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Routines");
        recyclerView = getView().findViewById(R.id.routine_recycler_view);
        loadRoutines();
    }

    private void loadRoutines(){
        Statics.startProgressDialog("Loading routines...", getContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                ConstantVariables.GET_ROUTINES_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                MyJSONObject myJSONObject = new MyJSONObject(Statics.parseJSON(response));
                Toast.makeText(getContext(), myJSONObject.getMessage(), Toast.LENGTH_SHORT).show();
                JSONArray jsonArray = myJSONObject.getArray("data");
                StaticVariables.routineList = new ArrayList<>();
                for (int i = 0; i < jsonArray.length(); i++) {
                    try {
                        StaticVariables.routineList.add(new Routine(
                                jsonArray.getJSONObject(i).getString("id"),
                                jsonArray.getJSONObject(i).getString("name"),
                                jsonArray.getJSONObject(i).getString("description"),
                                jsonArray.getJSONObject(i).getString("image")
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
        RoutineAdapter adapter = new RoutineAdapter(StaticVariables.routineList, getActivity());
        recyclerView.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}
