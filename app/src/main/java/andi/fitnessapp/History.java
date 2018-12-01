package andi.fitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import workoutplan.Exercise;

public class History extends AppCompatActivity {

    private ListView listView;
    private ArrayList<String> historyOfWorkout;
    public static final String DAY_OF_WEEK = "andi.fitnessapp.SetupDayOfTheWeek.DAYOFWEEK";
    public static final String WORKOUT_NAME = "andi.fitnessapp.SetupDatOfTheWeek.WNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        try {
            Gson gson = new Gson();
            Type type = new TypeToken<ArrayList<String>>(){}.getType();
            InputStream is = openFileInput("History.json");
            BufferedReader r = new BufferedReader(new InputStreamReader(is));
            //convert the json string back to object
            historyOfWorkout = gson.fromJson(r, type);

        } catch (IOException e) {
            e.printStackTrace();
        }

        listView = findViewById(R.id.workoutHistoryListview);
        ArrayAdapter<String> wHistory = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, historyOfWorkout);
        listView.setAdapter(wHistory);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                Intent appInfo = new Intent(History.this, HistoryWorkoutViewer.class);
                String info = historyOfWorkout.get(position).substring(historyOfWorkout.get(position).lastIndexOf(":") + 1);
                String[] parts = info.split("/");
                String workoutName = parts[0];
                String day = parts[1];
                appInfo.putExtra(DAY_OF_WEEK,day);
                appInfo.putExtra(WORKOUT_NAME,workoutName);
                startActivity(appInfo);
            }
        });
   }
}
