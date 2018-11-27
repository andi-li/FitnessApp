package andi.fitnessapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import WorkoutPlan.Exercise;

public class WorkoutCreator extends AppCompatActivity {


    private EditText exerciseInput;
    private EditText setsInput;
    private EditText repsInput;
    private EditText weightInput;
    private EditText nameOfWorkout;

    private LinearLayout parentLinearLayout;
    private String exercise, dayOfTheWeek;
    private int sets;
    private int reps;
    private int weight;

    private HashMap<String, ArrayList<Exercise>> weeklyWorkout;
    private ArrayList<Exercise> dailyWorkout = new ArrayList<Exercise>();

    private Exercise exerciseObj;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_creator);
        parentLinearLayout = findViewById(R.id.parent_linear_layout);

        Intent intent = getIntent();
        dayOfTheWeek = intent.getStringExtra(SetupDayOfTheWeek.DAY_OF_WEEK);

    }
    public void onAddExerciseField(View v){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.exercise_field, null);

        dailyWorkout = new ArrayList<Exercise>(); //MAYBE FIX LATER

        for(int i = 1;i<parentLinearLayout.getChildCount();i++){
            exerciseInput = parentLinearLayout.getChildAt(i).findViewById(R.id.exerciseInput);
            setsInput = parentLinearLayout.getChildAt(i).findViewById(R.id.setsInput);
            repsInput =  parentLinearLayout.getChildAt(i).findViewById(R.id.repsInput);
            weightInput = parentLinearLayout.getChildAt(i).findViewById(R.id.weightInput);


            if(exerciseInput !=null){
                exercise = exerciseInput.getText().toString();
                sets = Integer.valueOf(setsInput.getText().toString());
                reps = Integer.valueOf(repsInput.getText().toString());
                weight = Integer.valueOf(weightInput.getText().toString());
                exerciseObj = new Exercise(exercise,sets,reps,weight);
                dailyWorkout.add(exerciseObj);
            }


        }
        // Add the new row before the add field button.
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
    }
    public void onDelete(View v) {
        parentLinearLayout.removeView((View) v.getParent());
    }
    public void  finishCreating(View v){
        weeklyWorkout = new HashMap<String, ArrayList<Exercise>>();
        weeklyWorkout.put(dayOfTheWeek,dailyWorkout);

        Intent intent = new Intent(WorkoutCreator.this,SetupDayOfTheWeek.class);
        intent.putExtra("weeklyWorkout",weeklyWorkout);
        startActivity(intent);

    }
}
