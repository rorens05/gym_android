package com.example.gym.activities.recycler_adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.gym.R;
import com.example.gym.activities.DayDetailsActivity;
import com.example.gym.activities.ExerciseActivity;
import com.example.gym.global.ConstantVariables;
import com.example.gym.global.GlobalVariables;
import com.example.gym.global.Statics;
import com.example.gym.libraries.MySingleton;
import com.example.gym.models.Routine;
import com.example.gym.models.UserRoutine;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRoutineAdapter extends RecyclerView.Adapter<UserRoutineAdapter.MyViewHolder> {

    private static final String TAG = "RoutineAdapter";
    List<UserRoutine> userRoutines;
    List<Routine> routines;
    Context context;

    public UserRoutineAdapter(List<UserRoutine> userRoutines, Context context) {
        this.userRoutines = userRoutines;
        this.context = context;

        routines = new ArrayList<>();
        for (int i = 0; i < userRoutines.size(); i++) {
            routines.add(userRoutines.get(i).routine);
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_user_routines, viewGroup,
                false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, final int i) {
        final Routine routine = routines.get(i);

        if (routine.image.equalsIgnoreCase("")) {
            myViewHolder.image.setBackgroundResource(R.drawable.logo);
        }else{
            Picasso.get()
                    .load(routine.image)
                    .resize(300, 150)
                    .centerCrop()
                    .into(myViewHolder.image);
        }

        myViewHolder.name.setText(routine.name);
        myViewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalVariables.selectedRoutine = routine;
                context.startActivity(new Intent(context, ExerciseActivity.class));
            }
        });
        
        myViewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Statics.startProgressDialog("Deleting", context);

                StringRequest stringRequest = new StringRequest(Request.Method.POST,
                        ConstantVariables.DELETE_ROUTINE_URL, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(context, "deleted", Toast.LENGTH_SHORT).show();
                        Statics.stopProgressDialog();
                        ((DayDetailsActivity) context).getHealthStatus();


                    }
                }, Statics.getErrorListener(context)){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("id", userRoutines.get(i).id);
                        return params;
                    }
                };

                MySingleton.getInstance(context).addToRequestQueue(stringRequest);

            }
        });
    }

    @Override
    public int getItemCount() {
        return routines.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView name;
        Button delete;
        ConstraintLayout container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.user_routine_image);
            name = itemView.findViewById(R.id.user_routine_name);
            container = itemView.findViewById(R.id.user_routine_container);
            delete = itemView.findViewById(R.id.button5);
        }
    }
}
