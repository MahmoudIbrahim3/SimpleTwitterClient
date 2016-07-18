package com.mahmoud.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mahmoud.Model.MyFollowersModel;
import com.mahmoud.twitterclient.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Mahmoud Ibrahim on 3/17/2016.
 */
public class MyFollowersAdapter extends BaseAdapter{

    Activity activity;
    MyFollowersModel myFollowersModel;
    private LayoutInflater inflater;
    private String TAG = "MyFollowersAdapter";

    public MyFollowersAdapter(Activity activity, MyFollowersModel myFollowersModel){
        this.activity = activity;
        this.myFollowersModel = myFollowersModel;
    }

    @Override
    public int getCount() {
        return myFollowersModel.getUsers().size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Log.e(TAG, "position: " + position);

        if(inflater == null)
            inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null)
            convertView = inflater.inflate(R.layout.activity_my_followers_item, parent, false);

        return fillView(position, convertView);
    }

    private View fillView(final int position, View view){
        Log.e(TAG, "name: " + myFollowersModel.getUsers().get(position).getName());

        ImageView ivProfileImage = (ImageView) view.findViewById(R.id.iv_profile_image);
        TextView tvName = (TextView) view.findViewById(R.id.tv_name);
        TextView tvHandle = (TextView) view.findViewById(R.id.tv_handle);
        TextView tvBio = (TextView) view.findViewById(R.id.tv_bio);

        tvName.setText(myFollowersModel.getUsers().get(position).getName());
        tvHandle.setText("@" + myFollowersModel.getUsers().get(position).getScreenName());
        tvBio.setText(myFollowersModel.getUsers().get(position).getDescription());

        Picasso.with(activity)
                .load(myFollowersModel.getUsers().get(position).getProfileImageUrl())
                .into(ivProfileImage);

        return view;
    }
}
