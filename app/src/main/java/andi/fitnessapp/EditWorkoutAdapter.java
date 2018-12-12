package andi.fitnessapp;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.TextKeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import workoutplan.Exercise;

public class EditWorkoutAdapter extends ArrayAdapter<Exercise> {
    private static final String TAG = "ExerciseListAdapter";
    private ArrayList<Exercise> exercisesList;
    private Context eContext;
    int mResource;

    private LayoutInflater inflater;


    public EditWorkoutAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Exercise> objects) {
        super(context, resource, objects);
        eContext = context;
        mResource = resource;
        exercisesList = objects;

        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null){
            holder = new ViewHolder();
            convertView = inflater.inflate(mResource, null);

            holder.position = position;
            holder.exercise = convertView.findViewById(R.id.exerciseInput);
            holder.sets = convertView.findViewById(R.id.setsInput);
            holder.reps = convertView.findViewById(R.id.repsInput);
            holder.weight = convertView.findViewById(R.id.weightInput);
            holder.delete = convertView.findViewById(R.id.delete_button);

            holder.exercise.setId(position);
            holder.sets.setId(position);
            holder.reps.setId(position);
            holder.weight.setId(position);

            convertView.setTag(holder);
        }
        else{

            holder =  (ViewHolder)convertView.getTag();
        }
        Exercise e = exercisesList.get(position);

        holder.exercise.setText(e.getName());
        holder.sets.setText(String.valueOf(e.getSets()));
        holder.reps.setText(String.valueOf(e.getReps()));
        holder.weight.setText(String.valueOf(e.getWeight()));

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(exercisesList.size() > 0){
                    exercisesList.remove(position);
                }
            }
        });
        holder.exercise.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    final int position = view.getId();
                    final EditText exercise = (EditText)view;
                    exercisesList.get(position).setName(exercise.getText().toString());

                }

            }
        });
        holder.sets.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    final int position = view.getId();
                    final EditText exercise = (EditText)view;
                    exercisesList.get(position).setSets(Integer.parseInt(exercise.getText().toString()));

                }

            }
        });
        holder.reps.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    final int position = view.getId();
                    final EditText exercise = (EditText)view;
                    exercisesList.get(position).setReps(Integer.parseInt(exercise.getText().toString()));

                }

            }
        });
        holder.weight.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View view, boolean b) {
                if(!b){
                    final int position = view.getId();
                    final EditText exercise = (EditText)view;
                    exercisesList.get(position).setWeight(Integer.parseInt(exercise.getText().toString()));

                }

            }
        });


        return convertView;

    }

    @Override
    public int getCount() {
        return exercisesList.size();
    }
    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(
                activity.getCurrentFocus().getWindowToken(), 0);
    }


    static class ViewHolder{
        EditText sets;
        EditText exercise;
        EditText reps;
        EditText weight;
        Button delete;
        int position;
    }

}
