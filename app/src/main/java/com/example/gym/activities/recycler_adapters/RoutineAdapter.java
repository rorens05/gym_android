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
import com.example.gym.models.Routine;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RoutineAdapter extends RecyclerView.Adapter<RoutineAdapter.MyViewHolder> {

    private static final String TAG = "RoutineAdapter";
    List<Routine> routines;
    Context context;

    public RoutineAdapter(List<Routine> routines, Context context) {
        this.routines = routines;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_routines, viewGroup,
                false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {
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
                Toast.makeText(context, routine.name + "was clicked", Toast.LENGTH_SHORT).show();
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

        ConstraintLayout container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.routine_image);
            name = itemView.findViewById(R.id.routine_name);
            container = itemView.findViewById(R.id.routine_container);
        }
    }
}
