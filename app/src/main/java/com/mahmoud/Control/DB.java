package com.mahmoud.Control;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DB extends SQLiteOpenHelper {
	
	private static final String dbName="DB_TWITTER_CLIENT_APP";
	
	private final String FOLLOWERS_TABLE="FOLLOWERS_TABLE";

	private final String ft_key = "ft_key";  // for indexing only.
	private final String ft_id = "ft_id";
	private final String ft_profile_image = "ft_profile_image";
	private final String ft_name = "ft_name";
	private final String ft_screen_name = "ft_screen_name";
	private final String ft_description = "ft_description";
	private SQLiteDatabase db;

	public static String getDbName() {
		return dbName;
	}

	public String getFOLLOWERS_TABLE() {
		return FOLLOWERS_TABLE;
	}

	public String getFt_key() {
		return ft_key;
	}

	public String getFt_id() {
		return ft_id;
	}

	public String getFt_profile_image() {
		return ft_profile_image;
	}

	public String getFt_name() {
		return ft_name;
	}

	public String getFt_screen_name() {
		return ft_screen_name;
	}

	public String getFt_description() {
		return ft_description;
	}

	public SQLiteDatabase getDb() {
		return db;
	}

	public void setDb(SQLiteDatabase db) {
		this.db = db;
	}

	public DB(Context context) {
		super(context, dbName, null, 33);
		
		db=context.openOrCreateDatabase(dbName, SQLiteDatabase.CREATE_IF_NECESSARY, null);

		//FOLLOWERS_TABLE
		db.execSQL("CREATE TABLE IF NOT EXISTS "+FOLLOWERS_TABLE+"("+
				ft_key+" INTEGER PRIMARY KEY, "+
				ft_id+" TEXT , "+
				ft_profile_image+" TEXT , "+
				ft_name+" TEXT , "+
				ft_screen_name+" TEXT , "+
				ft_description+" TEXT )");
	}
	
	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		
	}

}
