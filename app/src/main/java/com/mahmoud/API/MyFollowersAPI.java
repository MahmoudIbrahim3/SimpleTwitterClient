package com.mahmoud.API;

import com.mahmoud.Model.MyFollowersModel;
import com.mahmoud.Control.APIs;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by Mahmoud Ibrahim on 7/16/2016.
 */
public interface MyFollowersAPI {
    @GET(APIs.API_GET_FOLLOWERS)
    Call<MyFollowersModel> getMyFollowers(@Header("Authorization") String authorization);
}
