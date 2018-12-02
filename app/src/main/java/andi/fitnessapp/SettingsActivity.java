package andi.fitnessapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class SettingsActivity extends AppCompatActivity {

    private Spinner dSpinner;
    private Spinner eSpinner;
    private ArrayList<String> listOfWorkouts;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        String settingsDayPreference = getDefaults("DayOfTheWeek",this);
        String exerciseFile = getDefaults("workout",this);
        listOfWorkouts = new ArrayList<String>();
        dSpinner = findViewById(R.id.daySpinner);
        eSpinner = findViewById(R.id.workoutSpinner);
        File file = this.getFilesDir();
        File[] arrFile = file.listFiles();

        for(int i = 0;i<arrFile.length;i++){
            if(arrFile[i].getName().endsWith(".json")){
                listOfWorkouts.add(arrFile[i].getName().substring(0,arrFile[i].getName().length()-5));
            }
        }
        ArrayAdapter<String> workoutAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,listOfWorkouts);
        workoutAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eSpinner.setAdapter(workoutAdapter);

// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.days_of_the_week, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner

        dSpinner.setAdapter(adapter);
        dSpinner.setSelection(retrieveAllStringsAndGetIndex(dSpinner,settingsDayPreference));
        eSpinner.setSelection(retrieveAllStringsAndGetIndex(eSpinner,exerciseFile));
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

    }
    public void save(View v) {
        setDefaults("DayOfTheWeek",getSpinnerStringValue(dSpinner),this);
        setDefaults("workout",getSpinnerStringValue(eSpinner),this);
    }
    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.commit();
    }
    public static String getSpinnerStringValue(Spinner s){
        TextView textView = (TextView) s.getSelectedView();
        String result = textView.getText().toString();
        return result;
    }
    public int retrieveAllStringsAndGetIndex(Spinner s,String info){
        int index=0;
        Adapter adapter = s.getAdapter();
        int n = adapter.getCount();
        List<String> stringList = new ArrayList<String>(n);
        for (int i = 0; i < n; i++) {
            String str = (String) adapter.getItem(i);
            stringList.add(str);
        }
        for(int i = 0;i<stringList.size();i++){
            if(info.equals(stringList.get(i))){
                index = i;
                break;
            }
        }
        return index;


    }
    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }



}

