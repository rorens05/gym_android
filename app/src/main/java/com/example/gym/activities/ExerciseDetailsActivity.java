package com.example.gym.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.gym.R;
import com.example.gym.global.GlobalVariables;
import com.squareup.picasso.Picasso;

public class ExerciseDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercise_details);
        TextView name = findViewById(R.id.ed_name);
        TextView desciption = findViewById(R.id.ed_description);
        TextView instruction = findViewById(R.id.ed_instruction);
        ImageView image = findViewById(R.id.ed_image);

        name.setText(GlobalVariables.selectedExercise.name);
        desciption.setText(GlobalVariables.selectedExercise.description);
        instruction.setText(GlobalVariables.selectedExercise.usage);

        if (GlobalVariables.selectedExercise.image.equalsIgnoreCase("")) {
        }else{
            Picasso.get()
                    .load(GlobalVariables.selectedExercise.image)
                    .resize(600, 300)
                    .centerCrop()
                    .into(image);
        }
    }
}
