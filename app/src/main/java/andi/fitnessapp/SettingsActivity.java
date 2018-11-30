package andi.fitnessapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.gson.Gson;

import java.io.FileOutputStream;
import java.util.ArrayList;


public class SettingsActivity extends AppCompatActivity {
    public SharedPreferences preferences;
    public SharedPreferences.Editor editor;
    public static final String PREF_FILE_NAME = "PrefFile";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        preferences = getSharedPreferences(PREF_FILE_NAME, MODE_PRIVATE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Spinner spinner = (Spinner) findViewById(R.id.daySpinner);
        String dayOfTheWeek= spinner.getSelectedItem().toString();
        editor = preferences.edit();
        editor.putString("DayOfTheWeek",dayOfTheWeek);
        editor.apply();

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.days_of_the_week, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

    }
    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outstate){
        super.onSaveInstanceState(outstate);
    }
    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Restore UI state from the savedInstanceState.
        // This bundle has also been passed to onCreate.
        boolean myBoolean = savedInstanceState.getBoolean("MyBoolean");
        double myDouble = savedInstanceState.getDouble("myDouble");
        int myInt = savedInstanceState.getInt("MyInt");
        String myString = savedInstanceState.getString("MyString");
    }

    public void  objToJson(Object o,String s){
        Gson gson = new Gson();
        String json = gson.toJson(o);
        String filename = s + ".json";
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

