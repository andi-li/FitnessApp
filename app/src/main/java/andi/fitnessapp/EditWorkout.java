package andi.fitnessapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import workoutplan.Exercise;

public class EditWorkout extends AppCompatActivity {

    private ListView editListView;
    private Button loadWorkoutButton;
    private Spinner daySpinner;
    private String workoutName;
    private HashMap<String, ArrayList<Exercise>> weeklyWorkout;
    private EditWorkoutAdapter editAdapter;
    ArrayList<Exercise> exercisesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout);
        Intent editWorkoutIntent = getIntent();
        workoutName = editWorkoutIntent.getStringExtra(WorkoutEditSelector.WORKOUTNAME);
        try {
            Gson gson = new Gson();
            Type type = new TypeToken<HashMap<String, ArrayList<Exercise>>>(){}.getType();
            InputStream is = openFileInput(workoutName + ".json");
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            weeklyWorkout = gson.fromJson(r, type);

        } catch (IOException e) {
            e.printStackTrace();
        }
        daySpinner = findViewById(R.id.daySpinner);
        editListView = findViewById(R.id.editWorkoutListView);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.days_of_the_week, R.layout.custom_spinner);
        adapter.setDropDownViewResource(R.layout.custom_spinner_drop_down);

        daySpinner.setAdapter(adapter);

        loadWorkoutButton = findViewById(R.id.loadDay);
        loadWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                exercisesList = weeklyWorkout.get(getSpinnerStringValue(daySpinner));
                editAdapter = new EditWorkoutAdapter(EditWorkout.this,R.layout.exercise_field,exercisesList);
                editListView.setAdapter(editAdapter);

            }
        });

    }


    public static String getSpinnerStringValue(Spinner s){
        TextView textView = (TextView) s.getSelectedView();
        String result = textView.getText().toString();
        return result;
    }
    public void addExercise(View v){
        Exercise e = new Exercise("New Exercise",0,0,0);
        exercisesList.add(e);
        editAdapter.notifyDataSetChanged();
    }
    public void save(View v){
        Gson gson = new Gson();
        String json = gson.toJson(weeklyWorkout);
        String filename = workoutName+ ".json";
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
                (getApplicationContext(), "Changes Saved", Toast.LENGTH_SHORT)
                .show();
    }
    }


