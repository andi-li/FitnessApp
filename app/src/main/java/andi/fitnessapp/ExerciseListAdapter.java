package andi.fitnessapp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;


import workoutplan.Exercise;

public class ExerciseListAdapter extends ArrayAdapter<Exercise> {

    private static final String TAG = "ExerciseListAdapter";
    private ArrayList<Exercise> exercisesList;
    private Context eContext;
    int mResource;
    int totalSets;
    private int lastPosition = -1;

    private ArrayList<String> exercise;
    private ArrayList<Integer> reps;
    private ArrayList<Integer> weight;



    public ExerciseListAdapter(@NonNull Context context, int resource, ArrayList<Exercise> objects) {
        super(context, resource, objects);
        eContext = context;
        mResource = resource;
        for(int i = 0;i<objects.size();i++){
            totalSets+=objects.get(i).getSets();
        }
        exercisesList = objects;

        exercise = new ArrayList<String>();
        reps = new ArrayList<Integer>();
        weight = new ArrayList<Integer>();

        for(int j = 0;j<exercisesList.size();j++){
            int k=0;
            while(k<exercisesList.get(j).getSets()){
                exercise.add(exercisesList.get(j).getName());
                reps.add(exercisesList.get(j).getReps());
                weight.add(exercisesList.get(j).getWeight());
                k++;

            }

        }
    }
    @Override
    public int getCount() {
        return totalSets;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder = null;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(eContext);
            convertView = inflater.inflate(mResource,parent,false);

            holder = new ViewHolder();
            holder.info =convertView.findViewById(R.id.workoutInfo);
            holder.position = position;

            convertView.setTag(holder);
        }
        else{

            holder = (ViewHolder) convertView.getTag();
        }

        holder.info.setText(exercise.get(position) + ": " +  reps.get(position).toString() +" X " + weight.get(position).toString());

        return convertView;

    }
    static class ViewHolder{
        TextView info;
        int position;
    }
}
