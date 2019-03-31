package com.example.gym.activities.recycler_adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gym.R;
import com.example.gym.models.Exercise;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.MyViewHolder> {

    List<Exercise> exerciseList = new ArrayList<>();
    Context context;

    public ExerciseAdapter(List<Exercise> exerciseList, Context context) {
        this.exerciseList = exerciseList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_exercise, viewGroup,
                false);
        return new ExerciseAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
        final Exercise exercise = exerciseList.get(i);
        myViewHolder.name.setText(exercise.name);
        myViewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, exercise.name, Toast.LENGTH_SHORT).show();
            }
        });

        if (exercise.image.equalsIgnoreCase("")) {
            myViewHolder.image.setBackgroundResource(R.drawable.logo);
        }else{
            Picasso.get()
                    .load(exercise.image)
                    .resize(300, 150)
                    .centerCrop()
                    .into(myViewHolder.image);
        }
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name;
        ConstraintLayout container;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.exercise_image);
            name = itemView.findViewById(R.id.exercise_name);
            container = itemView.findViewById(R.id.exercise_container);
        }
    }
}
