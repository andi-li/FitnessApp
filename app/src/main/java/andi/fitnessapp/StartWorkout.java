package andi.fitnessapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import workoutplan.Exercise;

public class StartWorkout extends AppCompatActivity {
    private HashMap<String, ArrayList<Exercise>> map;
    private ArrayList<Exercise> exercisesList;
    SharedPreferences preferences;

    private Button done;
    private Button undo;
    private ListView listView;
    int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_workout);
        Type type = new TypeToken<HashMap<String, ArrayList<Exercise>>>(){}.getType();
        Type type1 = new TypeToken<ArrayList<String>>(){}.getType();
        Gson gson = new Gson();

        try {
            InputStream is1 = openFileInput("workout1.json");//this.getAssets().open("workout1.json");
            BufferedReader r = new BufferedReader(new InputStreamReader(is1));

            //convert the json string back to object
            map = gson.fromJson(r, type);
            System.out.println(map.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
        String storedPreference = preferences.getString("DayOfTheWeek", "NULL");
        exercisesList = map.get("Monday");
        listView = findViewById(R.id.workoutListview);
        CustomAdapter adapter = new CustomAdapter();
        listView.setAdapter(adapter);
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
    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("yourfilename.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
    public void exerciseDone(View v){
        int setCounter = 0;
        for(int i = 0;i<exercisesList.size();i++){
            setCounter+=exercisesList.get(i).getSets();
        }
        listView.getChildAt(counter).setBackgroundColor(Color.GREEN);
        if(counter<setCounter-1){
            counter++;
        }

    }
    public void undoExercise(View v){
        if(counter!=0){
            counter--;
        }
        listView.getChildAt(counter).setBackgroundColor(Color.WHITE);
    }


}
