package com.example.gym.activities.recycler_adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gym.R;
import com.example.gym.global.Statics;
import com.example.gym.models.Day;

import java.util.ArrayList;
import java.util.List;

public class DayAdapter extends RecyclerView.Adapter<DayAdapter.MyViewHolder> {

    List<Day> dayList = new ArrayList<>();
    Context context;

    public DayAdapter(List<Day> dayList, Context context) {
        this.dayList = dayList;
        this.context = context;
    }

    @NonNull
    @Override

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_days, viewGroup,
                false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        final Day day = dayList.get(i);

        String dayName = Statics.getDay(Statics.stringToDate(day.date_created));
        String date = Statics.getDate(Statics.stringToDate(day.date_created));
        String fullDate = dayName + "\n" + date;

        myViewHolder.day_no.setText("Day " + day.day_no);

        myViewHolder.day.setText(fullDate);
        myViewHolder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, day.day_no + " was clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return dayList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView day_no;
        TextView day;
        ConstraintLayout container;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            day_no = itemView.findViewById(R.id.days_day);
            day = itemView.findViewById(R.id.days_date);
            container = itemView.findViewById(R.id.days_container);
        }
    }
}
