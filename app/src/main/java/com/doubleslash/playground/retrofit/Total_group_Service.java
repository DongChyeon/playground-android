package com.doubleslash.playground.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;

public interface Total_group_Service {
    @GET("/all/teams")
    Call<Total_group_responseDTO> getData();
}