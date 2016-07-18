package com.mahmoud.Control;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.view.Display;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Locale;

public class Utilities extends Application {

	private static String TAG = "Utilities";

	public static int SCREEN_WIDTH = 0;
	public static int SCREEN_HEIGHT = 0;

	@Override
	public void onCreate() {
		super.onCreate();
	}

	public static void changeLang(Activity context, String lang) {
		savePreferences(context, Keys.PREF_LANG, lang);
    	if (lang.equalsIgnoreCase(""))
    		return;
    	Locale myLocale = new Locale(lang);
    	saveLocale(context, lang);
        Locale.setDefault(myLocale);
        android.content.res.Configuration config = new android.content.res.Configuration();
        config.locale = myLocale;
        context.getResources().updateConfiguration(config, context.getResources().getDisplayMetrics());
    }

    public static void saveLocale(Context context, String lang) {
    	String langPref = "Language";
    	SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    	Editor editor = prefs.edit();
		editor.putString(langPref, lang);
		editor.commit();
    }	

	public static void savePreferences(Context context, String key, String value) {
		SharedPreferences mSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
	}	
		
	public static void savePreferences(Context context, String key, Boolean value) {
		SharedPreferences mSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
	}	
	
	public static void getWindowDimentions(Activity activity){
		Display display = activity.getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		SCREEN_WIDTH = size.x;
		SCREEN_HEIGHT = size.y;
	}
	
	public static void savePreferences(Activity context, String key, Integer value) {
		SharedPreferences mSharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(context);
        Editor editor = mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
	}	
		
	public static void showToast(Activity activity, String msg){
		Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
	}

	public static void hideSoftKeyboard(Activity act, EditText edt) {
		InputMethodManager imm=(InputMethodManager)act.getSystemService(
				Context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(edt.getWindowToken(), 0);
	}

	public static boolean isOnline(Context context) {
		ConnectivityManager cm =
				(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting())
			return true;
		return false;
	}
}
