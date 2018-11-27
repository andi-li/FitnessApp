package WorkoutPlan;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class WorkoutPlan {


    private HashMap<String, ArrayList<Exercise>> dayToExercise;

    public WorkoutPlan(HashMap<String, ArrayList<Exercise>> plan){
        this.dayToExercise = plan;
    }

    public HashMap<String, ArrayList<Exercise>> getDayToExercise() {
        return dayToExercise;
    }

    public void setDayToExercise(HashMap<String, ArrayList<Exercise>> dayToExercise) {
        this.dayToExercise = dayToExercise;
    }



}
