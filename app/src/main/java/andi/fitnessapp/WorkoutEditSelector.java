package andi.fitnessapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

public class WorkoutEditSelector extends AppCompatActivity {
    public static final String WORKOUTNAME = "andi.fitnessapp.WorkoutEditSelector.WNAME";

    private ArrayList<String> listOfWorkouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_workout_selector);
        listOfWorkouts = new ArrayList<String>();
        File file = this.getFilesDir();
        File[] arrFile = file.listFiles();

        for (int i = 0; i < arrFile.length; i++) {
            if (arrFile[i].getName().endsWith(".json")) {
                listOfWorkouts.add(arrFile[i].getName().substring(0, arrFile[i].getName().length() - 5));
            }
        }

        ListView listView = findViewById(R.id.workoutListview);
        WorkoutEditSelector.CustomAdapter adapter = new WorkoutEditSelector.CustomAdapter();
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent goToEditScreen = new Intent(WorkoutEditSelector.this,EditWorkout.class);
                goToEditScreen.putExtra(WORKOUTNAME,listOfWorkouts.get(i));
                startActivity(goToEditScreen);
            }
        });
    }


    class CustomAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return listOfWorkouts.size();
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
            view = getLayoutInflater().inflate(R.layout.listview_edit_workout_selector, null);
            TextView info = view.findViewById(R.id.workoutJson);
            info.setText(listOfWorkouts.get(i));
            return view;
        }

    }

}

