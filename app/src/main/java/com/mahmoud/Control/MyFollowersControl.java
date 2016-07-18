package com.mahmoud.Control;

import android.app.Activity;
import android.util.Log;

import com.mahmoud.API.MyFollowersAPI;
import com.mahmoud.Model.MyFollowersModel;
import com.mahmoud.twitterclient.MyFollowersActivity;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Mahmoud Ibrahim on 3/16/2016.
 */
public class MyFollowersControl {

    private static String TAG = "MyFollowersControl";

    private static long generateTimeStamp() {
        long timeStamp = Calendar.getInstance().getTimeInMillis();
        Log.e(TAG, "timeStamp: " + timeStamp);
        return timeStamp;
    }

    public void getMyFollowers(final Activity activity, final String accessToken) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Keys.DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MyFollowersAPI service = retrofit.create(MyFollowersAPI.class);

        /*Log.e(Keys.TAG, "includeUserEntities: " + includeUserEntities);
        Log.e(Keys.TAG, "screenName: " + screenName);
        Log.e(Keys.TAG, "skipStatus: " + skipStatus);
        Log.e(Keys.TAG, "cursor: " + cursor);*/

        String auth = "OAuth oauth_consumer_key=" + Keys.TWITTER_KEY +
                ", oauth_nonce=\"0ddddff20481ae3567739f4eefa49b22\"," +
                " oauth_signature=\"7DQKPmbqJAzRh%2FM0UTB2w82lGvY%3D\"," +
                " oauth_signature_method=\"HMAC-SHA1\", oauth_timestamp=\"" + "1468795885" +
                "\"," + " oauth_token=" + accessToken + ", oauth_version=\"1.0\"";

        Log.e(Keys.TAG, "auth: " + auth);

        Call<MyFollowersModel> myFollowersModelCall = service.getMyFollowers(auth);

        myFollowersModelCall.enqueue(new Callback<MyFollowersModel>() {
            @Override
            public void onResponse(Call<MyFollowersModel> call,
                                   Response<MyFollowersModel> response) {
                Log.e(Keys.TAG, "CallRequest: " + call.request().toString());
                Log.e(Keys.TAG, "ResponseCode: " + response.code());
                Log.e(Keys.TAG, "ResponseMessage: " + response.message());

                if(response.code() == Keys.SUCCESS) {
                    Log.e(Keys.TAG, "Body: " + response.body().getUsers().size());

                    MyFollowersActivity myFollowersActivity = new MyFollowersActivity();
                    myFollowersActivity.showMyFollowers(activity, response.body());
                }
            }

            @Override
            public void onFailure(Call<MyFollowersModel> call, Throwable t) {
                Log.e("onFailure", "onFailure: " + t.getMessage());
            }
        });
    }
}
