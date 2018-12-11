package andi.fitnessapp;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TableRow;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import workoutplan.Exercise;

public class WorkoutCreator extends AppCompatActivity {

    private ListView listView;

    private EditText exerciseInput;
    private EditText setsInput;
    private EditText repsInput;
    private EditText weightInput;
    private EditText nameOfWorkoutInput;

    private LinearLayout parentLinearLayout;
    private String exercise, dayOfTheWeek,nameOfWorkout;
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
        weeklyWorkout = new HashMap<String, ArrayList<Exercise>>();
        Intent intent = getIntent();
        nameOfWorkout = intent.getStringExtra(SetupDayOfTheWeek.EXERCISE_NAME);
        dayOfTheWeek = intent.getStringExtra(SetupDayOfTheWeek.DAY_OF_WEEK);

        try {
            Gson gson = new Gson();
            Type type = new TypeToken<HashMap<String, ArrayList<Exercise>>>(){}.getType();
            InputStream is = openFileInput(nameOfWorkout + ".json");
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            //convert the json string back to object
            weeklyWorkout = gson.fromJson(r, type);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void addPreviousExercise(View v){
        
    }
    public void onAddExerciseField(View v){
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View rowView = inflater.inflate(R.layout.exercise_field, null);

        dailyWorkout = new ArrayList<Exercise>(); //MAYBE FIX LATER

        for(int i = 0;i<parentLinearLayout.getChildCount();i++){
            exerciseInput = parentLinearLayout.getChildAt(i).findViewById(R.id.exerciseInput);
            setsInput = parentLinearLayout.getChildAt(i).findViewById(R.id.setsInput);
            repsInput =  parentLinearLayout.getChildAt(i).findViewById(R.id.repsInput);
            weightInput = parentLinearLayout.getChildAt(i).findViewById(R.id.weightInput);


            if(exerciseInput !=null && setsInput != null && repsInput !=null){
                exercise = exerciseInput.getText().toString();
                sets = Integer.valueOf(setsInput.getText().toString());
                reps = Integer.valueOf(repsInput.getText().toString());
                weight = Integer.valueOf(weightInput.getText().toString());
                exerciseObj = new Exercise(exercise,sets,reps,weight);
                dailyWorkout.add(exerciseObj);
            }
        }        // Add the new row before the add field button.
        parentLinearLayout.addView(rowView, parentLinearLayout.getChildCount() - 1);
    }
    public void onDelete(View v) {
        parentLinearLayout.removeView((View) v.getParent());
    }

    public void  finishCreating(View v){


        weeklyWorkout.put(dayOfTheWeek,dailyWorkout);
        Gson gson = new Gson();
        String json = gson.toJson(weeklyWorkout);
        String filename = nameOfWorkout + ".json";
        String fileContents = json;
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(fileContents.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText
                (getApplicationContext(), "Workout Saved", Toast.LENGTH_SHORT)
                .show();
    }

    }
