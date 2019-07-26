package com.example.lab3;

import com.example.lab3.model.Lap3Response;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface RetrofitInterface {

    @GET("lab3.json")
    Call<Lap3Response> getlap3();

}
