package com.spottr.spottr.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.spottr.spottr.R;
import com.spottr.spottr.models.Exercise;
import com.spottr.spottr.models.Plan;
import com.spottr.spottr.models.Rest;

import java.text.SimpleDateFormat;
import java.util.Date;

public class WorkoutActivity extends AppCompatActivity {

    private boolean isExercise = true;

    private int exerciseCounter = 0;
    private int restCounter = 0;

    private Exercise currentExercise;
    private Rest currentRest;
    private Plan workoutPlan;
    private CountDownTimer restTimer;
    private Date now;
    private long startTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout);

        Gson gson = new Gson();
        String gsonString = getIntent().getStringExtra("PLAN");
        workoutPlan = gson.fromJson(gsonString, Plan.class);
        workoutPlan.num_exercises = workoutPlan.exercises.size();
        Log.d("TEST", String.valueOf(workoutPlan.exercises.get(exerciseCounter)));
        Log.d("TEST", String.valueOf(workoutPlan.exercises.get(exerciseCounter).name));


        currentExercise = workoutPlan.exercises.get(exerciseCounter);
        currentRest = workoutPlan.breaks.get(restCounter);

        TextView exerciseTitle = (TextView) findViewById(R.id.workout_title);
        exerciseTitle.setText(currentExercise.name);
        findViewById(R.id.workout_done).setVisibility(View.INVISIBLE);
        TextView clock = (TextView) findViewById(R.id.workout_clock);
        clock.setText("Sets: " + currentExercise.sets + "  Reps: " + currentExercise.reps);

        now = new Date();
        startTime = now.getTime();


        //Buttons
        Button exitButton = (Button) findViewById(R.id.workout_exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        Button nextButton = (Button) findViewById(R.id.workout_next);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextExercise();
            }
        });

        Button toSubmit = (Button) findViewById(R.id.workout_done);
        toSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long timeDiff = now.getTime() - startTime;
                Intent newIntent = new Intent(WorkoutActivity.this, ReviewActivity.class);
                newIntent.putExtra("time", timeDiff);
                startActivity(newIntent);
            }
        });


    }

    public void nextExercise() {
        //check if changing to the last exercise
        if (isExercise) {
            currentRest = workoutPlan.breaks.get(restCounter);
            TextView exerciseTitle = (TextView) findViewById(R.id.workout_title);
            exerciseTitle.setText(currentRest.name);
            ImageView image = (ImageView) findViewById(R.id.mainImage);
            image.setImageResource(R.drawable.ic_baseline_accessibility_24);


            findViewById(R.id.workout_next).setVisibility(View.INVISIBLE);

            final TextView clock = (TextView) findViewById(R.id.workout_clock);
            createCountdownTimer(currentRest.duration_sec.intValue(), clock);

            isExercise = !isExercise;
        } else {
            //increment both the rest and exercise counter to the next only if pressing next on a break
            this.exerciseCounter++;
            this.restCounter++;
            currentExercise = workoutPlan.exercises.get(exerciseCounter);
            TextView exerciseTitle = (TextView) findViewById(R.id.workout_title);
            exerciseTitle.setText(String.valueOf(currentExercise.name));
            TextView clock = (TextView) findViewById(R.id.workout_clock);
            clock.setText("Sets: " + currentExercise.sets + "  Reps: " + currentExercise.reps);


            ImageView image = (ImageView) findViewById(R.id.mainImage);
            image.setImageResource(R.drawable.ic_baseline_directions_run_24);
            findViewById(R.id.workout_next).setVisibility(View.VISIBLE);
            isExercise = !isExercise;
        }

        if (exerciseCounter == workoutPlan.exercises.size() - 1) {
            findViewById(R.id.workout_next).setVisibility(View.INVISIBLE);
            findViewById(R.id.spottrLogo).setVisibility(View.INVISIBLE);
            findViewById(R.id.workout_done).setVisibility(View.VISIBLE);
        }

    }

    private void createCountdownTimer(int seconds, TextView clock) {
        final TextView thisClock = clock;
        restTimer = new CountDownTimer(seconds * 1000, 10) {

            public void onTick(long millisUntilFinished) {
                thisClock.setText("" + new SimpleDateFormat("mm:ss:SS").format(new Date(millisUntilFinished)));
            }

            public void onFinish() {
                findViewById(R.id.workout_next).setVisibility(View.VISIBLE);
                thisClock.setText("Next exercise!");
            }
        }.start();

    }
}