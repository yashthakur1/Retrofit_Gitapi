package com.example.yashthakur.testapp.API;

import com.example.yashthakur.testapp.MODEL.gitmodel;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

public interface gitapi {
    //creating get request to send api url with custom parameters
    @GET("/users/{user}")
    public void getFeed(@Path("user") String user, Callback<gitmodel> response); // custom parameters is "user" entered from edittext
    //getFeed has a overide in Mainactivity.java
}
