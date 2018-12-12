package andi.fitnessapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Environment;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
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
    private int setCounter = 0;
    private HashMap<String, ArrayList<Exercise>> map;
    private ArrayList<Exercise> exercisesList;
    private ArrayList<String> workoutHistory;
    private Chronometer chronometer;
    private Chronometer totalChronometer;
    private ListView listView;
    private long offset;
    private int counter = 0;
    private boolean running;

    private Button done;
    private Button undo;

    private ExerciseListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_workout);

        chronometer = findViewById(R.id.Timer);
        totalChronometer = findViewById(R.id.totalTimer);
        chronometer.setBase(SystemClock.elapsedRealtime());
        totalChronometer.setBase(SystemClock.elapsedRealtime());

        String path = getFilesDir().getAbsolutePath() + "/" + "History.json";

        File file = new File(path);

        if(file.exists()) {
            try {
                Gson gson = new Gson();
                Type type = new TypeToken<ArrayList<String>>() {
                }.getType();

                InputStream is = openFileInput("History.json");
                BufferedReader r = new BufferedReader(new InputStreamReader(is));
                //convert the json string back to object
                workoutHistory = gson.fromJson(r, type);


            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            workoutHistory = new ArrayList<String>();


        }

        Type type = new TypeToken<HashMap<String, ArrayList<Exercise>>>(){}.getType();
        Gson gson = new Gson();
        String settingsPreference = getDefaults("DayOfTheWeek",this);
        String exerciseFile = getDefaults("workout",this);

        try {
            InputStream is1 = openFileInput(exerciseFile + ".json");
            BufferedReader r = new BufferedReader(new InputStreamReader(is1));
            map = gson.fromJson(r, type);
            System.out.println(map.toString());

        } catch (IOException e) {

        }


        if(map!=null){
            exercisesList = map.get(settingsPreference);

            for(int i = 0;i<exercisesList.size();i++){
                setCounter+=exercisesList.get(i).getSets();
            }

            listView = findViewById(R.id.workoutListview);
            adapter = new ExerciseListAdapter(this,R.layout.custom_view_workout,exercisesList);

            listView.setAdapter(adapter);
            totalChronometer.start();

        }
        else{
            Toast.makeText
                    (getApplicationContext(), "No Workout Selected", Toast.LENGTH_SHORT)
                    .show();
            Intent i = new Intent(StartWorkout.this, MainActivity.class);
            startActivity(i);
        }

        done = findViewById(R.id.done);

        done.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(counter == setCounter){
                    String exerciseFile = getDefaults("workout",StartWorkout.this);
                    String settingsPreference = getDefaults("DayOfTheWeek",StartWorkout.this);
                    workoutHistory.add(LocalDateTime.now()
                            .format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) + ": " + exerciseFile + "/" + settingsPreference + " Workout Time:" + totalChronometer.getText().toString());

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
                    if(running){
                        chronometer.stop();
                        offset = SystemClock.elapsedRealtime()-chronometer.getBase();
                        running = false;
                    }
                    else{
                        chronometer.setBase(SystemClock.elapsedRealtime()-offset);
                        chronometer.start();
                        running = true;
                    }
                    Toast.makeText
                            (getApplicationContext(), "Workout Done", Toast.LENGTH_SHORT)
                            .show();
                }
                else{
                    updateColor(counter,true);
                    counter++;
                }

            }
        });

        undo = findViewById(R.id.undo);

        undo.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                if(running){
                    chronometer.setBase(SystemClock.elapsedRealtime());
                    offset = 0;
                }
                if(counter>=0){
                    updateColor(counter,false);
                    counter--;
                }
            }
        });



    }

    public View getViewByPosition(int pos, ListView listView) {
        final int firstListItemPosition = listView.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + listView.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition ) {
            listView.setSelection(pos);
            return listView.getAdapter().getView(pos, null, listView);

        } else {
            final int childIndex = pos - firstListItemPosition;
            return listView.getChildAt(childIndex);
        }
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
    public void updateColor(int position, boolean bool) {
        if(listView !=null && bool) {
            getViewByPosition(position,listView).setBackgroundColor(Color.rgb((float)75.8,(float)139.9,(float)50.8));
        }
        else if(listView!=null && !bool){
            getViewByPosition(position,listView).setBackgroundColor(Color.rgb(47,51,63));
        }
    }




}
