package andi.fitnessapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import WorkoutPlan.Exercise;

public class WorkoutCreator extends AppCompatActivity {

    private LinearLayout parentLinearLayout;
    String exercise, dayOfTheWeek;
    int sets;
    int reps;
    int weight;

    EditText exerciseInput;
    EditText setsInput;
    EditText repsInput;
    EditText weightInput;

    Button addExercise;

    Exercise exerciseObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_creator);
        parentLinearLayout = findViewById(R.id.parent_linear_layout);

        exerciseInput = findViewById(R.id.exerciseInput);
        setsInput =  findViewById(R.id.setsInput);
        repsInput =  findViewById(R.id.repsInput);
        weightInput = findViewById(R.id.weightInput);

        addExercise = findViewById(R.id.addExercise);

    }
    public void onAddExerciseField(View v){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.exercise_field, null);

        exercise = exerciseInput.getText().toString();
        sets = Integer.valueOf(setsInput.getText().toString());
        reps = Integer.valueOf(repsInput.getText().toString());
        weight = Integer.valueOf(weightInput.getText().toString());

       //exerciseObj = new Exercise(exercise,sets,reps,weight);

                // Add the new row before the add field button.
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);

    }
    public void onDelete(View v) {
        parentLinearLayout.removeView((View) v.getParent());
    }
}
