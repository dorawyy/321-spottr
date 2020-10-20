package com.spottr.apis;

import com.spottr.constants.MuscleGroup;
import com.spottr.models.Exercise;
import com.spottr.models.Plan;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface WorkoutAPI {

    @GET("/exercises")
    Call<List<Exercise>> getExercises(
            @Query("mgroup") MuscleGroup mgroup
    );

    @GET("/plans")
    Call<List<Plan>> getPlans();

    @POST("/plans")
    Call<Plan> createPlan(@Body Plan plan);

    @GET("/plans/{planID}")
    Call<Plan> getPlanByID(@Path("planID") String planID);

    @GET("/users/{userID}/plans")
    Call<List<Plan>> getSavedPlansByUser(@Path("userID") String userID);

    @GET("/users/{userID}/plans/recommended")
    Call<Plan> getRecommendedPlan(@Path("userID") String userID);
}
