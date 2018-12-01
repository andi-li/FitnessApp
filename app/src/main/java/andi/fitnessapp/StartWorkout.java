package andi.fitnessapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


import workoutplan.Exercise;

public class StartWorkout extends AppCompatActivity {
    private HashMap<String, ArrayList<Exercise>> map;
    private ArrayList<Exercise> exercisesList;
    private ArrayList<String> workoutHistory;
    private Chronometer chronometer;
    private Chronometer totalChronometer;
    private ListView listView;
    private long offset;
    int counter = 0;
    private boolean running;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_workout);
        chronometer = findViewById(R.id.Timer);
        totalChronometer = findViewById(R.id.totalTimer);

        chronometer.setBase(SystemClock.elapsedRealtime());
        totalChronometer.setBase(SystemClock.elapsedRealtime());

        Type type = new TypeToken<HashMap<String, ArrayList<Exercise>>>(){}.getType();
        Gson gson = new Gson();
        String settingsPreference = getDefaults("DayOfTheWeek",this);
        String exerciseFile = getDefaults("workout",this);
        try {
            InputStream is1 = openFileInput(exerciseFile + ".json");//this.getAssets().open("workout1.json");
            BufferedReader r = new BufferedReader(new InputStreamReader(is1));

            //convert the json string back to object
            map = gson.fromJson(r, type);
            System.out.println(map.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }

        exercisesList = map.get(settingsPreference);
        listView = findViewById(R.id.workoutListview);
        CustomAdapter adapter = new CustomAdapter();
        listView.setAdapter(adapter);
        totalChronometer.start();
    }
    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            int setCounter = 0;
            for(int i = 0;i<exercisesList.size();i++){
                setCounter+=exercisesList.get(i).getSets();
            }
            return setCounter;
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
            ArrayList<Integer> reps = new ArrayList<Integer>();
            ArrayList<Integer> weight = new ArrayList<Integer>();
            for(int j = 0;j<exercisesList.size();j++){
                int k=0;
                while(k<exercisesList.get(j).getSets()){
                    exercise.add(exercisesList.get(j).getName());
                    reps.add(exercisesList.get(j).getReps());
                    weight.add(exercisesList.get(j).getWeight());
                    k++;
                }
                k=0;

            }
            info.setText(exercise.get(i) + ": " +  reps.get(i).toString() +" X " + weight.get(i).toString());

            return view;
        }
    }

    public void exerciseDone(View v){
        int setCounter = 0;
        for(int i = 0;i<exercisesList.size();i++){
            setCounter+=exercisesList.get(i).getSets();
        }
        listView.getChildAt(counter).setBackgroundColor(Color.GREEN);
        if(counter<setCounter){
            counter++;
        }
        if(running){
            chronometer.setBase(SystemClock.elapsedRealtime());
            offset = 0;
        }
        if(counter == setCounter){
            String exerciseFile = getDefaults("workout",this);
            String settingsPreference = getDefaults("DayOfTheWeek",this);
            workoutHistory = new ArrayList<String>();
            workoutHistory.add(LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + ": " + exerciseFile + "/" + settingsPreference);
            Gson gson = new Gson();
            String json = gson.toJson(workoutHistory);
            String filename = "History.json";
            String fileContents = json;
            FileOutputStream outputStream;

            try {
                outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
                outputStream.write(fileContents.getBytes());
                outputStream.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
    public void undoExercise(View v){
        if(running){
            chronometer.setBase(SystemClock.elapsedRealtime());
            offset = 0;
        }
        if(counter!=0){
            counter--;
        }
        listView.getChildAt(counter).setBackgroundColor(Color.WHITE);
    }
    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    public void startChronometer(View v){
        if(!running){
            chronometer.setBase(SystemClock.elapsedRealtime()-offset);
            chronometer.start();
            running = true;
        }
    }
    public void pauseChronometer(View v){
        if(running){
            chronometer.stop();
            offset = SystemClock.elapsedRealtime()-chronometer.getBase();
            running = false;
        }

    }



}
