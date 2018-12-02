package andi.fitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

import workoutplan.Exercise;

public class WorkoutHistoryViewer extends AppCompatActivity {
    private HashMap<String, ArrayList<Exercise>> map;
    private String nameOfWorkout;
    private String dayOfTheWeek;
    private ArrayList<Exercise> exercisesList;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_history_viewer);
        Type type = new TypeToken<HashMap<String, ArrayList<Exercise>>>(){}.getType();
        Gson gson = new Gson();
        Intent intent = getIntent();
        nameOfWorkout = intent.getStringExtra(History.WORKOUT_NAME).trim();
        dayOfTheWeek = intent.getStringExtra(History.DAY_OF_WEEK);
        try {
            InputStream is = openFileInput(nameOfWorkout + ".json");
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            map = gson.fromJson(r, type);
            System.out.println(map.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        exercisesList = map.get(dayOfTheWeek);
        listView = findViewById(R.id.workoutListView);
        WorkoutHistoryViewer.CustomAdapter adapter = new WorkoutHistoryViewer.CustomAdapter();
        listView.setAdapter(adapter);
    }

    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
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
            view = getLayoutInflater().inflate(R.layout.custom_view_workout,null);
            TextView info = view.findViewById(R.id.workoutInfo);
            ArrayList<String> exercise = new ArrayList<String>();
            ArrayList<Integer> sets = new ArrayList<Integer>();
            ArrayList<Integer> reps = new ArrayList<Integer>();
            ArrayList<Integer> weight = new ArrayList<Integer>();
            for(int j = 0;j<exercisesList.size();j++){
                    exercise.add(exercisesList.get(j).getName());
                    sets.add(exercisesList.get(i).getSets());
                    reps.add(exercisesList.get(j).getReps());
                    weight.add(exercisesList.get(j).getWeight());

            }
            info.setText(exercise.get(i) + ": " + sets.get(i).toString() +" X " + reps.get(i).toString() +" at " + weight.get(i).toString() + "lbs");

            return view;
        }
    }
}
