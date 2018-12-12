package andi.fitnessapp;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


public class SettingsActivity extends AppCompatActivity {
    private Spinner rSpinner;
    private Spinner dSpinner;
    private Spinner eSpinner;
    private Spinner porKSpinner;

    private ArrayList<String> listOfWorkouts;
    private EditText weightText;
    private TextView predictedMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        String settingsDayPreference = getDefaults("DayOfTheWeek",this);
        String exerciseFile = getDefaults("workout",this);


        listOfWorkouts = new ArrayList<String>();
        dSpinner = findViewById(R.id.daySpinner);
        eSpinner = findViewById(R.id.workoutSpinner);
        rSpinner = findViewById(R.id.repsSpinner);
        porKSpinner = findViewById(R.id.lbsOrKilos);

        File file = this.getFilesDir();
        File[] arrFile = file.listFiles();

        for(int i = 0;i<arrFile.length;i++){
            if(arrFile[i].getName().endsWith(".json")){
                listOfWorkouts.add(arrFile[i].getName().substring(0,arrFile[i].getName().length()-5));
            }
        }
        ArrayList<Integer> repList = new ArrayList<Integer>();
        for(int i = 1;i<=12;i++){
            repList.add(i);
        }

        ArrayAdapter<String> workoutAdapter = new ArrayAdapter<String>(this,R.layout.custom_spinner,listOfWorkouts);
        ArrayAdapter<CharSequence> dayAdapter = ArrayAdapter.createFromResource(this, R.array.days_of_the_week, R.layout.custom_spinner);
        ArrayAdapter<Integer> repAdapter = new ArrayAdapter<Integer>(this,R.layout.custom_spinner_drop_down,repList);
        ArrayAdapter<CharSequence> lbsOrKilosAdapter = ArrayAdapter.createFromResource(this, R.array.lbsOrKilos, R.layout.custom_spinner);

        repAdapter.setDropDownViewResource(R.layout.custom_spinner_drop_down);
        dayAdapter.setDropDownViewResource(R.layout.custom_spinner_drop_down);
        workoutAdapter.setDropDownViewResource(R.layout.custom_spinner_drop_down);
        lbsOrKilosAdapter.setDropDownViewResource(R.layout.custom_spinner_drop_down);

        predictedMax = findViewById(R.id.predictedMax);
        weightText = findViewById(R.id.enterWeight);

        eSpinner.setAdapter(workoutAdapter);
        dSpinner.setAdapter(dayAdapter);
        rSpinner.setAdapter(repAdapter);
        porKSpinner.setAdapter(lbsOrKilosAdapter);

        if(settingsDayPreference != null && exerciseFile !=null){
            dSpinner.setSelection(retrieveAllStringsAndGetIndex(dSpinner,settingsDayPreference));
            eSpinner.setSelection(retrieveAllStringsAndGetIndex(eSpinner,exerciseFile));

        }

        porKSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String check = weightText.getText().toString();
                if(!check.matches("")){
                    updateText();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                rSpinner.setSelection(0);
            }
        });

        rSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               String check = weightText.getText().toString();
                if(!check.matches("")){
                    updateText();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                rSpinner.setSelection(0);
            }
        });
        TextWatcher textWatcher = new TextWatcher() {

            public void afterTextChanged(Editable s) {
                if(weightText.getText().toString().equals("")){
                    predictedMax.setText("0 lbs");
                }
                updateText();
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {

            }
        };
        weightText.addTextChangedListener(textWatcher);
    }

    private void updateText(){
        int spinnerState = Integer.parseInt(rSpinner.getSelectedItem().toString());
        if(!weightText.getText().toString().equals("") && isNumeric(weightText.getText().toString())){
            int weight = Integer.parseInt(weightText.getText().toString());

            predictedMax.setText(calculateOneRepMax(weight,spinnerState) + " lbs");
        }

    }
    public boolean isNumeric(String s) {
        return s != null && s.matches("[-+]?\\d*\\.?\\d+");
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
        Toast.makeText
                (getApplicationContext(), "Settings Saved", Toast.LENGTH_SHORT)
                .show();
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
    public int calculateOneRepMax(int weight, int reps){
        double max = 0;
        double x = 1.0;
        if(reps > 1){
            x = (1.0 + ((double)reps/30.0));
        }
        max =  weight*x;
        if(porKSpinner.getSelectedItem().toString().equals("lbs")){
            return (int)Math.round(max);
        }
        else{
            return (int)Math.round(max*2.2);
        }

    }


}

