package andi.fitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import workoutplan.Exercise;

public class History extends AppCompatActivity {
    private Button deleteButton;
    private ListView listView;
    private ArrayList<String> historyOfWorkout;
    public static final String DAY_OF_WEEK = "andi.fitnessapp.SetupDayOfTheWeek.DAYOFWEEK";
    public static final String WORKOUT_NAME = "andi.fitnessapp.SetupDatOfTheWeek.WNAME";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
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
                historyOfWorkout = gson.fromJson(r, type);


            } catch (IOException e) {
                e.printStackTrace();
            }

            listView = findViewById(R.id.workoutHistoryListview);
            final ArrayAdapter<String> wHistory = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, historyOfWorkout);
            deleteButton = findViewById(R.id.deleteHistory);

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    File dir = getFilesDir();
                    File file = new File(dir, "History.json");
                    file.delete();
                    historyOfWorkout.clear();
                    wHistory.notifyDataSetChanged();
                    listView.setAdapter(wHistory);
                    Toast.makeText
                            (getApplicationContext(), "History Deleted", Toast.LENGTH_SHORT)
                            .show();
                }
            });
            listView.setAdapter(wHistory);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapter, View view, int position, long arg) {
                    Intent appInfo = new Intent(History.this, WorkoutHistoryViewer.class);
                    String info = historyOfWorkout.get(position).substring(historyOfWorkout.get(position).lastIndexOf("|") + 1);
                    String[] parts = info.split("/");
                    String workoutName = parts[0];
                    String day = parts[1];
                    appInfo.putExtra(DAY_OF_WEEK, day);
                    appInfo.putExtra(WORKOUT_NAME, workoutName);
                    startActivity(appInfo);
                }
            });
        }


   }
    void deleteRecursive(File fileOrDirectory) {

        if (fileOrDirectory.isDirectory())
            for (File child : fileOrDirectory.listFiles())
                deleteRecursive(child);

        fileOrDirectory.delete();

    }

}
