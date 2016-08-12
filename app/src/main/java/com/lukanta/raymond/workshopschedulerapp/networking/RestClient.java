package com.lukanta.raymond.workshopschedulerapp.networking;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by raymondlukanta on 28/05/16.
 */
public class RestClient {
    private static final String BASE_URL = "https://dl.dropboxusercontent.com/";
    private ApiService apiService;

    public RestClient() {
        Gson gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();

        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(new OkHttpInterceptor()).readTimeout(30, TimeUnit.SECONDS).build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public ApiService getApiService() {
        return apiService;
    }
}
