package com.example.cezar.projekt4;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Ruben on 12/12/2015.
 */
public interface BecServce {

    @POST("/auth")
    public AuthResponseDto authentication(@Body UserDto userDto);

    Call<UserDto> createUser(@Body UserDto userDto);
}