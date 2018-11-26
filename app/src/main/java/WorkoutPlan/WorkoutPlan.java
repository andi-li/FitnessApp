package WorkoutPlan;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class WorkoutPlan {


    private HashMap<DateFormat, ArrayList<Exercise>> dayToExercise;

    public WorkoutPlan(HashMap<DateFormat, ArrayList<Exercise>> plan){
        this.dayToExercise = plan;
    }

    public HashMap<DateFormat, ArrayList<Exercise>> getDayToExercise() {
        return dayToExercise;
    }

    public void setDayToExercise(HashMap<DateFormat, ArrayList<Exercise>> dayToExercise) {
        this.dayToExercise = dayToExercise;
    }



}
