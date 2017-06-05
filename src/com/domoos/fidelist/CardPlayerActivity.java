package com.domoos.fidelist;



import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.ads.AdView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class CardPlayerActivity extends Activity {
	protected TextView tvIdValue;
	protected TextView tvNameValue;
	protected TextView tvFedValue;
	protected TextView tvRtgValue;
	protected TextView tvRtgHighValue;
	protected TextView tvRtgLowValue;
	protected TextView tvRpdValue;
	protected TextView tvRpdHighValue;
	protected TextView tvRpdLowValue;
	protected TextView tvBlzValue;
	protected TextView tvBlzHighValue;
	protected TextView tvBlzLowValue;
	protected TextView tvTitleValue;
	protected TextView tvGeneroValue;
	protected TextView tvBYearValue;
	protected ImageView ivGrafica;
	protected ImageView ivGraficaRpd;
	protected ImageView ivGraficaBlz;
	protected Intent intentGrafica;
	protected HistorySQLiteHelper history; 
	protected Bundle bundle;
	
	//protected AdView adview;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_card_player);
		setContentView(R.layout.activity_card_player_better);
		intentGrafica = new Intent(this, EloActivity.class);
		
		history= new HistorySQLiteHelper(this, "DBHistory", null, 1);
		//*******
		//Creamos las tablas necesarias...
		//players.onCreate(players.getWritableDatabase());
		//*******
		ivGrafica = (ImageView)findViewById(R.id.ivGrafica);
		ivGrafica.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
					intentGrafica.putExtra("rating", "rtg");
					intentGrafica.putExtras(bundle);
				startActivity(intentGrafica);
				}catch(Exception ex){
					
					}
				}
		});
		ivGraficaRpd = (ImageView)findViewById(R.id.ivGraficaRpd);
		ivGraficaRpd.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
					intentGrafica.putExtra("rating", "rpd");
					intentGrafica.putExtras(bundle);
				startActivity(intentGrafica);
				}catch(Exception ex){
					
					}
				}
		});
		ivGraficaBlz = (ImageView)findViewById(R.id.ivGraficaBlz);
		ivGraficaBlz.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{
					intentGrafica.putExtra("rating", "blz");
					intentGrafica.putExtras(bundle);
				startActivity(intentGrafica);
				}catch(Exception ex){
					
					}
				}
		});
		bundle = getIntent().getExtras();
		
		String[] args = new String[] {bundle.getString("Id").replace(" ", "")};
		String sql_delete = "DELETE FROM History;";
		String sql_rtg = "SELECT max(rtg) FROM History Where id=? AND rtg<>'  ' AND rtg is not null;";
		String sql_min_rtg = "SELECT min(rtg) FROM History Where id=? AND rtg<>'  ' AND rtg is not null;";
		String sql_rpd = "SELECT max(rpd) FROM History Where id=? AND rpd<>'  ' AND rpd is not null;";
		String sql_min_rpd = "SELECT min(rpd) FROM History Where id=? AND rpd<>'  ' AND rpd is not null;";
		String sql_blz = "SELECT max(blz) FROM History Where id=? AND blz<>'  ' AND blz is not null;";
		String sql_min_blz = "SELECT min(blz) FROM History Where id=? AND blz<>'  ' AND blz is not null;";
		
		
		
		SQLiteDatabase db = history.getReadableDatabase();
		
		
		//db.execSQL(sql_delete);
		
		Cursor c_rtg = db.rawQuery(sql_rtg, args);
		Cursor c_min_rtg = db.rawQuery(sql_min_rtg, args);
		Cursor c_rpd = db.rawQuery(sql_rpd, args);
		Cursor c_min_rpd = db.rawQuery(sql_min_rpd, args);
		Cursor c_blz = db.rawQuery(sql_blz, args);
		Cursor c_min_blz = db.rawQuery(sql_min_blz, args);
		
				
		
		//db.close();
		//adview = (AdView)this.findViewById(R.id.adViewCardPlayer);
		
		tvNameValue = (TextView)this.findViewById(R.id.name);
		tvNameValue.setText(bundle.getString("Name"));
			
		tvFedValue = (TextView)this.findViewById(R.id.tvFederationValue);
		tvFedValue.setText(bundle.getString("Federation"));
		
		tvIdValue = (TextView)this.findViewById(R.id.tvFideIdValue);
		tvIdValue.setText(bundle.getString("Id"));
	
		tvBYearValue=(TextView)this.findViewById(R.id.tvBYearValue);
		tvBYearValue.setText(bundle.getString("fnac"));
		
		tvGeneroValue=(TextView)this.findViewById(R.id.tvGeneroValue);
		tvGeneroValue.setText(bundle.getString("sex"));
		//LINEA RATING ESTANDAR
		
		tvRtgValue = (TextView)this.findViewById(R.id.tvRtgValue);
		tvRtgValue.setText("Rtg:"+bundle.getString("rtg"));
		try{
			tvRtgHighValue = (TextView)this.findViewById(R.id.tvRtgHighValue);
			c_rtg.moveToFirst();
			tvRtgHighValue.setText("Max:"+c_rtg.getString(0).replace(" ","")+"");
		}catch(Exception ex){
			
		}
		try{
			tvRtgLowValue = (TextView)this.findViewById(R.id.tvMinRtg);
			c_min_rtg.moveToFirst();
			tvRtgLowValue.setText("Min:"+c_min_rtg.getString(0).replace(" ","")+ "");
		}catch(Exception ex){
			
		}
		//LINEA DE RATING RAPIDO
		tvRpdValue = (TextView)this.findViewById(R.id.tvRpdValue);
		tvRpdValue.setText("Rpd:"+bundle.getString("rpd"));
		
		try{
			tvRpdHighValue = (TextView)this.findViewById(R.id.tvRpdHighValue);
			c_rpd.moveToFirst();
			tvRpdHighValue.setText("Max:"+c_rpd.getString(0).replace(" ", "")+"");
		}catch(Exception ex){
			
		}
		try{
			tvRpdLowValue = (TextView)this.findViewById(R.id.tvRpdLowValue);
			c_min_rpd.moveToFirst();
			if(c_min_rpd.getString(0).equals("null")){
				tvRpdLowValue.setText("Min:");
			}else{
			tvRpdLowValue.setText("Min:"+ c_min_rpd.getString(0).replace(" ", "") +"");
			}
		}catch(Exception ex){
			
		}
		//LINEA DE RATING BLITZ
		tvBlzValue = (TextView)this.findViewById(R.id.tvBlzValue);
		tvBlzValue.setText("Blz:"+bundle.getString("blz"));
		
		try{
			tvBlzHighValue = (TextView)this.findViewById(R.id.tvMaxBlz);
			c_blz.moveToFirst();
			
			//muestramensaje(c_blz.getString(0));
			if(c_blz.getString(0).equals("null")){
				tvBlzHighValue.setText("Max:");
			}else{
				tvBlzHighValue.setText("Max:"+ c_blz.getString(0)+"");
			}
		}catch(Exception ex){
			
		}
		try{
			tvBlzLowValue = (TextView)this.findViewById(R.id.tvBlzLowValue);
			c_min_blz.moveToFirst();
			tvBlzLowValue.setText("Min:"+ c_min_blz.getString(0).replace(" ", "")+"");
		}catch(Exception ex){
			
		}
		tvTitleValue = (TextView)this.findViewById(R.id.tvTitleValue);
		tvTitleValue.setText(bundle.getString("Title"));
		db.close();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.card_player, menu);
		return true;
	}
	public void muestramensaje(String arg){
		Toast toast = Toast.makeText(this, arg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
	}
	
	@Override
	public boolean onOptionsItemSelected (MenuItem item){
		    // Handle presses on the action bar items
		bundle = getIntent().getExtras();
		
		    switch (item.getItemId()) {
		        case R.id.bActualiza:
		        	muestramensaje("Actualizando Datos...");
		        	//Inicio Petición
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
	        	
		        	return false;
		        case R.id.bVerGraficaRtg:
		        	try{
						intentGrafica.putExtra("rating", "rtg");
						intentGrafica.putExtras(bundle);
					startActivity(intentGrafica);
					}catch(Exception ex){
						
						}
					
		        	return false;
		        case R.id.bVerGraficaRpd:
		        	try{
						intentGrafica.putExtra("rating", "rpd");
						intentGrafica.putExtras(bundle);
					startActivity(intentGrafica);
					}catch(Exception ex){
						
						}
					
		        	return false;
		        case R.id.bVerGraficaBlz:
		        	try{
						intentGrafica.putExtra("rating", "blz");
						intentGrafica.putExtras(bundle);
					startActivity(intentGrafica);
					}catch(Exception ex){
						
						}
					
		        	return false;
		        default:
		        	return false;
		    }
		    
	}
}