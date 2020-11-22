package com.spottr.spottr.apis;

import android.content.Context;

import com.spottr.spottr.constants.Timeout;
import com.spottr.spottr.services.AuthorizationInterceptor;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIFactory {

    private final Retrofit retrofit;

    public APIFactory(Context ctx) {

        AuthorizationInterceptor authInterceptor = new AuthorizationInterceptor(ctx);

        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(authInterceptor)
                .authenticator(authInterceptor)
                .readTimeout(Timeout.READ, TimeUnit.SECONDS)
                .connectTimeout(Timeout.CONNECT, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl("https://spottr-be.herokuapp.com/")
                .build();
    }

    public CommunityAPI getCommunityAPI() {
        return retrofit.create(CommunityAPI.class);
    }

    public WorkoutAPI getWorkoutAPI() {
        return retrofit.create(WorkoutAPI.class);
    }
    public AdminAPI getAdminAPI() {
        return retrofit.create(AdminAPI.class);
    }

}
