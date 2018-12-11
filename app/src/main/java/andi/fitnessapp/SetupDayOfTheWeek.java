package andi.fitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.time.DayOfWeek;

public class SetupDayOfTheWeek extends AppCompatActivity {

    public static final String DAY_OF_WEEK = "andi.fitnessapp.SetupDayOfTheWeek.DAYOFWEEK";
    public static final String EXERCISE_NAME = "andi.fitnessapp.SetupDatOfTheWeek.ENAME";

    private EditText workout;
    private Button Monday;
    private Button Tuesday;
    private Button Wednesday;
    private Button Thursday;
    private Button Friday;
    private Button Saturday;
    private Button Sunday;
    private String workoutName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_day_of_the_week);

        if(savedInstanceState != null){
            String savedWorkout = savedInstanceState.getString(EXERCISE_NAME);
            workout.setText(savedWorkout);

        }
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
         }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putString(EXERCISE_NAME,workout.getText().toString());
        super.onSaveInstanceState(savedInstanceState);
    }
    public void saveView(View view){
        workout.setText(workout.getText().toString());
    }

    public void monday(View view){
        workout = findViewById(R.id.WorkoutName);
        String workoutName = workout.getText().toString();
        Intent intent = new Intent(SetupDayOfTheWeek.this,WorkoutCreator.class);
        String day = "Monday";
        intent.putExtra(EXERCISE_NAME, workoutName);
        intent.putExtra(DAY_OF_WEEK, day);
        startActivity(intent);
    }
    public void tuesday(View view){
        workout = findViewById(R.id.WorkoutName);
        String workoutName = workout.getText().toString();
        Intent intent = new Intent(SetupDayOfTheWeek.this,WorkoutCreator.class);
        String day = "Tuesday";
        intent.putExtra(EXERCISE_NAME,workoutName);
        intent.putExtra(DAY_OF_WEEK, day);
        startActivity(intent);
    }

}
