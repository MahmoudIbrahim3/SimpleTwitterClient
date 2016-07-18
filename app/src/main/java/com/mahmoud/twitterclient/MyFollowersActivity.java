package com.mahmoud.twitterclient;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mahmoud.Adapter.MyFollowersAdapter;
import com.mahmoud.Control.DB;
import com.mahmoud.Control.Keys;
import com.mahmoud.Control.MyFollowersControl;
import com.mahmoud.Control.Utilities;
import com.mahmoud.Model.MyFollowersModel;
import com.mahmoud.Model.User;

import java.util.ArrayList;
import java.util.List;

public class MyFollowersActivity extends AppCompatActivity {

    private MyFollowersAdapter adapter;
    private ViewGroup myView;
    private ListView lvMyFollowers;
    private DB db;
    private String TAG = "MyFollowersActivity";
    private TextView tvResult;
    private ProgressBar pbLoading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_followers);

        init(this);
        getIntentData();
    }

    private void init(Activity activity) {
        myView = (ViewGroup) ((ViewGroup) activity
                .findViewById(android.R.id.content)).getChildAt(0);

        lvMyFollowers = (ListView) myView.findViewById(R.id.lv_my_followers);
        pbLoading = (ProgressBar) myView.findViewById(R.id.pb_loading);
        tvResult = (TextView) myView.findViewById(R.id.tv_result);

    }

    private void getIntentData() {
        Intent intent  = getIntent();
        Bundle bundle = intent.getExtras();
        String authToken = bundle.getString(Keys.INTENT_AUTH_TOKEN);
        Log.e(Keys.TAG, "authToken: " + authToken);

        if(Utilities.isOnline(this))
            getMyFollowers(authToken);
        else
            getCachedFollowers();
    }

    private void getMyFollowers(String authToken){
        pbLoading.setVisibility(View.VISIBLE);
        MyFollowersControl getMyFollowersControl = new MyFollowersControl();
        getMyFollowersControl.getMyFollowers(this, authToken);
    }

    public void showMyFollowers(Activity activity, MyFollowersModel myFollowersModel) {
        Log.e(Keys.TAG, "Size: " + myFollowersModel.getUsers().size());

        init(activity);

        pbLoading.setVisibility(View.GONE);

        if(myFollowersModel.getUsers().size() == 0)
            tvResult.setText(getResources().getString(R.string.no_followers));
        else{
            adapter = new MyFollowersAdapter(activity, myFollowersModel);
            lvMyFollowers.setAdapter(adapter);
        }

        cacheFollowersToLocalDB(activity, myFollowersModel);
    }

    private void cacheFollowersToLocalDB(Activity activity, MyFollowersModel myFollowersModel) {
        db = new DB(activity);

        List<User> listFollowers = myFollowersModel.getUsers();

        String query = "select * from " + db.getFOLLOWERS_TABLE();
        Cursor cursor = db.getDb().rawQuery(query, null);
        Log.e(TAG, "size_of_local_db_before_insert_this_page_of_followers: " + cursor.getCount());
        Log.e(TAG, "size_of_this_page_that_want_to_inserted: " + listFollowers.size());

        for (int i = 0; i < listFollowers.size(); i++) {
            ContentValues cv = new ContentValues();
            User model = listFollowers.get(i);
            cv.put(db.getFt_key(), cursor.getCount() + i);
            cv.put(db.getFt_id(), model.getIdStr());
            cv.put(db.getFt_description(), model.getDescription());
            cv.put(db.getFt_name(), model.getName());
            cv.put(db.getFt_profile_image(), model.getProfileImageUrl());
            cv.put(db.getFt_screen_name(), model.getScreenName());

            db.getDb().insert(db.getFOLLOWERS_TABLE(), null, cv);
        }
        db.close();
    }

    private void getCachedFollowers(){
        db = new DB(this);
        String query = "select * from " + db.getFOLLOWERS_TABLE();
        Cursor cursor = db.getDb().rawQuery(query, null);
        Log.e(TAG, "followers local db size: " + cursor.getCount());

        if(cursor.getCount() == 0) {
            tvResult.setVisibility(View.VISIBLE);
            tvResult.setText(getResources().getString(R.string.internet_connection_error));
        }
        else {
            List<User> listFollowers = new ArrayList<>();

            cursor.moveToFirst();
            do {
                User model = new User();
                model.setIdStr(cursor.getString(cursor.getColumnIndex(db.getFt_id())));
                model.setProfileImageUrl(cursor.getString(cursor.getColumnIndex(db.getFt_profile_image())));
                model.setName(cursor.getString(cursor.getColumnIndex(db.getFt_name())));
                model.setScreenName(cursor.getString(cursor.getColumnIndex(db.getFt_screen_name())));
                model.setDescription(cursor.getString(cursor.getColumnIndex(db.getFt_description())));

                listFollowers.add(model);
            } while (cursor.moveToNext());

            MyFollowersModel myFollowersModel = new MyFollowersModel();
            myFollowersModel.setUsers(listFollowers);

            adapter = new MyFollowersAdapter(this, myFollowersModel);
            lvMyFollowers.setAdapter(adapter);
        }
    }
}
