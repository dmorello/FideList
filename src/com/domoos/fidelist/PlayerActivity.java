package com.domoos.fidelist;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class PlayerActivity extends TabActivity {
	protected TabHost tabs;
	protected Intent intent;
	protected Bundle bundle;
	protected PlayersSQLiteHelper players; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Resources res = getResources();
		tabs = (TabHost)findViewById(android.R.id.tabhost);
		tabs.setup();
		
		intent = new Intent(this,CardPlayerActivity.class);
		intent.putExtras(getIntent());
		
		tabs.addTab(tabs.newTabSpec("Info").setIndicator("", getResources().getDrawable(R.drawable.info_alfa)).setContent(intent));
		intent = new Intent(this,StadisticsActivity.class);
		intent.putExtras(getIntent());
		tabs.addTab(tabs.newTabSpec("Stadistics").setIndicator("", getResources().getDrawable(R.drawable.estadisticas_alfa)).setContent(intent));
		intent = new Intent(this,GameListActivity.class);
		intent.putExtras(getIntent());
		tabs.addTab(tabs.newTabSpec("Games").setIndicator("", getResources().getDrawable(R.drawable.guardar_alfa)).setContent(intent));
		tabs.setCurrentTab(0);
		
		
		
	}
	@Override
	public boolean onOptionsItemSelected (MenuItem item){
		    // Handle presses on the action bar items
		players= new PlayersSQLiteHelper(this, "DBPlayers", null, 1);
		
		bundle = getIntent().getExtras();
		
		switch (item.getItemId()) {
		    case R.id.bvAdd:
		    	SQLiteDatabase db = players.getWritableDatabase();    
			            	
					if(db !=null)
					{
						try {
							db.execSQL("INSERT INTO Players (id, name, fed, t, rtg, rpd, blz, byear, s) " +      
							"VALUES ('" + bundle.getString("Id") + "','" + bundle.getString("Name") +"','" + bundle.getString("Federation") +"','" + bundle.getString("Title") + "','" + bundle.getString("rtg") + "','" + bundle.getString("rpd") + "','"+ bundle.getString("blz") + "','"+ bundle.getString("fnac") + "','" + bundle.getString("sex") + "')"); 
						}
						catch(Exception e){
							muestramensaje(e.getMessage());	
							}
						}	// TODO Auto-generated method stub
					//Inicio Petición Bajar Histórico
	        		 AsyncHttpClient clientH = new AsyncHttpClient();
	        		 clientH.get("http://ratings.fide.com/id.phtml?event=" + bundle.getString("Id").replace(" ", ""), new AsyncHttpResponseHandler(){
	        			 @Override
	        			public void onSuccess(String response){
	        				 SQLiteDatabase dbHistory = openOrCreateDatabase("DBHistory", SQLiteDatabase.OPEN_READWRITE, null);
		        		    	
	        				 org.jsoup.nodes.Document doc = Jsoup.parse(response);
		        		    	Elements content = doc.getElementsByClass("list");
		        		    	Elements filas = content.get(0).getElementsByTag("tr");
		        		    	for(org.jsoup.nodes.Element fila: filas){
		        		    		
		        		    		//Insertamos datos en la tabla History (id TEXT, period TEXT PRIMARY KEY, rtg TEXT, gms_rtg TEXT, rpd TEXT, gms_rpd TEXT, blz TEXT, gms_blz TEXT)
		        		    		try{
		        		    		
		        		    		dbHistory.execSQL("INSERT INTO History(id, period, rtg, gms_rtg, rpd, gms_rpd, blz, gms_blz) VALUES ('"+ bundle.getString("Id").replace(" ", "") + "','"+ fila.getElementsByTag("td").get(0).text() +"','"+ fila.getElementsByTag("td").get(1).text() +"','"+ fila.getElementsByTag("td").get(2).text() +"','"+ fila.getElementsByTag("td").get(3).text() +"','"+ fila.getElementsByTag("td").get(4).text() +"','"+ fila.getElementsByTag("td").get(5).text() +"','"+ fila.getElementsByTag("td").get(6).text() +"');"); 
		        		    		}catch(Exception ex){
		        		    			ex.printStackTrace();
		        		    		} 
		        		    		}
		        		    	dbHistory.close();
		        		    	
	        			}
	        		 });
	        		 //Fin de Petición
					muestramensaje(bundle.getString("Name") + ">> Añadido");
					db.close();
					return false;
		        case R.id.bvDel:
		        	SQLiteDatabase db2 = players.getWritableDatabase();    
		    	    	
		        	db2 = players.getWritableDatabase();
					if(db2 !=null)
					{
						try {
							db2.execSQL("DELETE FROM Players WHERE id ='" + bundle.getString("Id")+"'"); 
						}
						catch(Exception e){
							muestramensaje(e.getMessage());	
							}
						}	// TODO Auto-generated method stub
					muestramensaje(bundle.getString("Name") + ">> Eliminado");
					db2.close();
					return false;
		        default:
		           return false;
		  
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.player, menu);
		bundle = getIntent().getExtras();
		 menu.setGroupVisible(R.id.addGroup, !bundle.getBoolean("deleteFlag"));
		 menu.setGroupVisible(R.id.delGroup, bundle.getBoolean("deleteFlag"));
		return true;
	}


public void muestramensaje(String arg){
	Toast toast = Toast.makeText(this, arg, Toast.LENGTH_SHORT);
    toast.setGravity(Gravity.CENTER, 0, 0);
    toast.show();
}
@Override
protected void onPause() {
   

    getAdView().stopLoading();

    super.onPause();
}

@Override
protected void onDestroy() {
  
    getAdView().destroy();

    super.onDestroy();
}

private AdView getAdView()
{
    return (AdView) findViewById(R.id.adViewCardPlayer);
}
}