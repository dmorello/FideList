package com.domoos.fidelist;

import java.util.ArrayList;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class FollowPlayers extends Activity {
	protected ListView lv;
	protected ArrayAdapter<String> adaptador;
	protected ArrayList<String [] > listResultDB;
	protected ArrayList<String> listResult;
	protected String sql = "SELECT * FROM Players";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_follow_players);
		SQLiteDatabase db = openOrCreateDatabase("DBPlayers", SQLiteDatabase.OPEN_READONLY, null);
		lv = (ListView)findViewById(R.id.gamesList);
		listResult = new ArrayList<String>();
		listResultDB = new ArrayList<String []>();
		adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listResult);
    	lv.setAdapter(adaptador);
		Cursor c = null;
    	c = db.rawQuery(sql, null);
    	int numrow = c.getCount();
    	while(c.moveToNext()){
    		listResult.add(c.getString(1));
    		adaptador.notifyDataSetChanged();
    	}
    	
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.follow_players, menu);
		return true;
	}

	public void muestramensaje(String arg){
		Toast toast = Toast.makeText(this, "Position: "+ arg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
	}

}
