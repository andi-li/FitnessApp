package andi.fitnessapp;

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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
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

        final CustomAdapter listAdapter = new CustomAdapter();

        loadWorkoutButton = findViewById(R.id.loadDay);
        loadWorkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editListView.setAdapter(listAdapter);
               listAdapter.notifyDataSetChanged();
            }
        });

    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            ArrayList<Exercise> exercisesList = weeklyWorkout.get(getSpinnerStringValue(daySpinner));
            return exercisesList.size();
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.listview_edit_workout_field, null);
            ArrayList<Exercise> exercisesList = weeklyWorkout.get(getSpinnerStringValue(daySpinner));//getSpinnerStringValue(daySpinner)
            EditText exerciseView = view.findViewById(R.id.exerciseName);
            EditText setsView = view.findViewById(R.id.sets);
            EditText repsView = view.findViewById(R.id.reps);
            EditText weightsView = view.findViewById(R.id.weight);

            ArrayList<String> exercise = new ArrayList<String>();
            ArrayList<Integer> reps = new ArrayList<Integer>();
            ArrayList<Integer> sets = new ArrayList<Integer>();
            ArrayList<Integer> weight = new ArrayList<Integer>();
            for(int j = 0;j<exercisesList.size();j++){
                    exercise.add(exercisesList.get(j).getName());
                    reps.add(exercisesList.get(j).getReps());
                    sets.add(exercisesList.get(j).getSets());
                    weight.add(exercisesList.get(j).getWeight());
            }
            exerciseView.setText(exercise.get(i));
            setsView.setText(String.valueOf(sets.get(i)));
            repsView.setText(String.valueOf(reps.get(i)));
            weightsView.setText(String.valueOf(weight.get(i)));
            return view;
        }

    }
    public static String getSpinnerStringValue(Spinner s){
        TextView textView = (TextView) s.getSelectedView();
        String result = textView.getText().toString();
        return result;
    }
    public void save(View v){

    }

}
