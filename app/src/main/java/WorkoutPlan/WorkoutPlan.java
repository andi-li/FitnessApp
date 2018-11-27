package WorkoutPlan;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class WorkoutPlan {


    private List<HashMap<String, ArrayList<Exercise>>> workoutPlan;

    public WorkoutPlan(List<HashMap<String, ArrayList<Exercise>>> plan){
                this.workoutPlan = plan;
    }

    public List<HashMap<String, ArrayList<Exercise>>> getPlan() {

        return workoutPlan;
    }

    public void setPlan(List<HashMap<String, ArrayList<Exercise>>> workoutPlan) {
        this.workoutPlan = workoutPlan;
    }



}
