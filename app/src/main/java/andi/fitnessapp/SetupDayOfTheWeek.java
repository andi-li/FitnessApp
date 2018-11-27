package andi.fitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.time.DayOfWeek;

public class SetupDayOfTheWeek extends AppCompatActivity {

    public static final String DAY_OF_WEEK = "andi.fitnessapp.SetupDayOfTheWeek.DAYOFWEEK";

    Button Monday;
    Button Tuesday;
    Button Wednesday;
    Button Thursday;
    Button Friday;
    Button Saturday;
    Button Sunday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_day_of_the_week);

        Monday = findViewById(R.id.Monday);

        Monday.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(SetupDayOfTheWeek.this,WorkoutCreator.class);
                String day = "Monday";
                intent.putExtra(DAY_OF_WEEK, day);
                startActivity(intent);
            }
        });

        Tuesday = findViewById(R.id.Tuesday);

        Tuesday.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(SetupDayOfTheWeek.this,WorkoutCreator.class);
                String day = "Tuesday";
                intent.putExtra(DAY_OF_WEEK, day);
                startActivity(intent);
            }
        });
    }

}
