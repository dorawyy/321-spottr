package com.spottr.spottr.apis;


import retrofit2.Call;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReviewAPI {

    @POST("/users/{userId}/workout/complete")
    Call<Integer> completedWorkout(
            @Path("userId") Integer userId,
            @Query("workout-plan-id") String workoutId,
            @Query("length-seconds") Integer length
    );

    @PUT("/users/{userId}/workout/change-difficulty")
    Call<Integer> changeDifficulty(
            @Path("userId") Integer userId,
            @Query("target-muscle-group") String muscleId,
            @Query("factor") Integer factor
    );
}
