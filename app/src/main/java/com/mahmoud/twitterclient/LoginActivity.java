package com.mahmoud.twitterclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.mahmoud.Control.Keys;
import com.mahmoud.Control.Utilities;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;
import io.fabric.sdk.android.Fabric;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private TwitterLoginButton loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final TwitterAuthConfig authConfig = new TwitterAuthConfig(Keys.TWITTER_KEY, Keys.TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));
        setContentView(R.layout.activity_login);

        checkLoginStatus();
        loginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);

        loginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                TwitterSession session = result.data;
                // with your app's user model
                String msg = "@" + session.getUserName() + " logged in! (#" + session.getUserId() + ")";
                Log.d(TAG, "user data: " + msg);

                String authToken = session.getAuthToken().token;
                Log.d(TAG, "getAuthToken: " + authToken);

                Utilities.savePreferences(LoginActivity.this, Keys.PREF_AUTH_TOKEN, authToken);

                startActivity(new Intent(LoginActivity.this, MyFollowersActivity.class)
                        .putExtra(Keys.INTENT_AUTH_TOKEN, authToken));
                finish();
            }

            @Override
            public void failure(TwitterException exception) {
                Log.d(TAG, "Login with Twitter failure", exception);
            }
        });
    }

    private void checkLoginStatus() {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(this);
        String authToken = pref.getString(Keys.PREF_AUTH_TOKEN, "");
        if(!authToken.equals("")) {
            startActivity(new Intent(LoginActivity.this, MyFollowersActivity.class)
                    .putExtra(Keys.INTENT_AUTH_TOKEN, authToken));
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Make sure that the loginButton hears the result from any
        // Activity that it triggered.
        loginButton.onActivityResult(requestCode, resultCode, data);
    }
}
