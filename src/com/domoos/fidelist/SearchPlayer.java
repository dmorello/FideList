package com.domoos.fidelist;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.domoos.fidelist.PlayersSQLiteHelper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.R.string;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.Toast;
import com.google.ads.*;

public class SearchPlayer extends Activity {
	
	protected SearchView sv;
	protected ListView lv;
	protected ImageButton ib;
	protected Button bv;
	protected ArrayList<String [] > listResultDB;
	protected ArrayList<String> listResult;
	protected ArrayList<itemPlayer> list_items_players;
	protected ArrayAdapter<String> adaptador;
	protected ItemPlayerAdapter adapter;
	protected PlayersSQLiteHelper players; 
	protected HistorySQLiteHelper history;
	protected String sql = "SELECT * FROM Players ORDER BY rtg DESC";
	protected Intent intent;
	protected Bundle bundle;
	protected boolean addFlag;
	protected boolean deleteFlag;
	protected String resultado = "";
	protected int registrosUpdate;
	protected String id;
	
	//protected AdView adView;
	@Override
	
protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_player);
		
		sv = (SearchView)findViewById(R.id.svBuscar);
		lv = (ListView)findViewById(R.id.gamesList);
			//adView = (AdView)this.findViewById(R.id.adView);
		
		registerForContextMenu(lv);
		try{
		SQLiteDatabase db = openOrCreateDatabase("DBPlayers", SQLiteDatabase.OPEN_READONLY, null);
		SQLiteDatabase dbh = openOrCreateDatabase("DBHistory", SQLiteDatabase.OPEN_READONLY, null);
		
		listResult = new ArrayList<String>();
		listResultDB = new ArrayList<String []>();
		list_items_players = new ArrayList<itemPlayer>();
		
		adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listResult);
		//adapter = new ItemPlayerAdapter(this, list_items_players);
        //lv.setAdapter(adapter); 
		
        lv.setAdapter(adaptador);
		Cursor c = null;
    	c = db.rawQuery(sql, null);
    	int numrow = c.getCount();
    	while(c.moveToNext()){
    		listResult.add(c.getString(1) + ":\n" + c.getString(6));
    		String[] linea = {c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7),c.getString(8),c.getString(9),c.getString(10),c.getString(11)};
    		list_items_players.add(new itemPlayer(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7),c.getString(8),c.getString(9),c.getString(10),c.getString(11)));
    		listResultDB.add(linea);
    		adaptador.notifyDataSetChanged();
    		deleteFlag = true;
    		addFlag = false;
    		//adapter.notifyDataSetChanged();
    	}
    	
    	
    	dbh.close();
    	db.close();
		}catch(Exception ex){
			listResult.add("--> Lista Vacía <--"); 
			adaptador.notifyDataSetChanged();
			deleteFlag = true;
			addFlag = false;
			}
		
		
		 intent = new Intent(this,PlayerActivity.class);
		try{
			sv.setOnQueryTextListener(new OnQueryTextListener() {
				
				@Override
				public boolean onQueryTextSubmit(String query) {
					AsyncHttpClient client = new AsyncHttpClient();
					client.get("http://ratings.fide.com/search.phtml?search=" + query.replace(" ", "+"), new AsyncHttpResponseHandler() {
					    @Override
					    public void onSuccess(String response) {
					    	listResult.clear();
					    	listResultDB.clear();
					    	list_items_players.clear();
					    	
					    	org.jsoup.nodes.Document doc = Jsoup.parse(response);
					    	org.jsoup.nodes.Element content = doc.getElementById("displayDiv");
					    	Elements filas = content.getElementsByTag("tr");
					    	for(org.jsoup.nodes.Element fila: filas){
					    		Element celdas = fila.tagName("td");
					    		if (celdas.getElementsByIndexEquals(10).text().equals("") || celdas.getElementsByIndexEquals(10).text().equals("B-Year")){
									continue;
									}
					    		String [] linea = new String[13];
					    		//IDcode[y] Name[y] T[y] WT OT Fed Rtg[y] Rpd[y] Blz[y] N B-Year S F 

					    		String href = celdas.select("a[href]").attr("href");
					    	
					    		
					    		linea[0]= fila.getElementsByTag("td").get(1).text();
					    		linea[1] = fila.getElementsByTag("td").get(2).text(); 
					    		linea[2] = fila.getElementsByTag("td").get(3).text(); 
					    		linea[3] = fila.getElementsByTag("td").get(4).text(); 
					    		linea[4] = fila.getElementsByTag("td").get(5).text(); 
					    		linea[5] = fila.getElementsByTag("td").get(6).text();
					    		linea[6] = fila.getElementsByTag("td").get(7).text(); 
					    		linea[7]= fila.getElementsByTag("td").get(8).text(); 
					    		linea[8] = fila.getElementsByTag("td").get(9).text(); 
					    		linea[9] = fila.getElementsByTag("td").get(10).text(); 
					    		linea[10] = fila.getElementsByTag("td").get(11).text(); 
					    		linea[11] = fila.getElementsByTag("td").get(12).text();
					    		linea[12] = href;
					    			    		
			
					    		
					    		listResultDB.add(linea);
					    		listResult.add(linea[1] + ":\n"+ linea[6]);
					    		adaptador.notifyDataSetChanged();
					    		//adapter.notifyDataSetChanged();
					    		}
					    	
					    }
					});	
					return false;
			}
				
				@Override
				public boolean onQueryTextChange(String newText) {
					// TODO Auto-generated method stub
					return false;
				}
			});
		
			//CLICK EN LA LISTA CARGADA DE LA BD.
			lv.setOnItemClickListener(new OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
						long arg3) {
					try{
					String[] info = (String [])listResultDB.get(arg2); 
					intent.putExtra("Id", info[0]);
					intent.putExtra("Name", info[1]);
					intent.putExtra("Federation", info[5]);
					intent.putExtra("Title", info[2]);
					intent.putExtra("rtg", info[6]);
					intent.putExtra("rpd", info[7]);
					intent.putExtra("blz", info[8]);
					intent.putExtra("fnac", info[9]);
					intent.putExtra("sex", info[10]);
					startActivity(intent);
					}catch(NullPointerException ex){
					} 
					//muestraPosicion(arg2);
				}
			});
			
			adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listResult);
	    	lv.setAdapter(adaptador);
			//adapter = new ItemPlayerAdapter(this, list_items_players);
	        //lv.setAdapter(adapter); 
				
		}catch(Exception e){
				
			}
			}
	@Override
	public boolean onOptionsItemSelected (MenuItem item){
		    // Handle presses on the action bar items
		    switch (item.getItemId()) {
		        case R.id.bShowPlayers:
		        	SQLiteDatabase db = openOrCreateDatabase("DBPlayers", SQLiteDatabase.OPEN_READONLY, null);
		        	try{
		        	listResult.clear();
		        	listResultDB.clear();
		        	list_items_players.clear();
		    		Cursor c = null;
		        	c = db.rawQuery(sql, null);
		        	
		        	while(c.moveToNext()){
		        		listResult.add(c.getString(1) + ":\n" + c.getString(6));
		        		String [] linea = {c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7),c.getString(8),c.getString(9),c.getString(10),c.getString(11)};
		        		listResultDB.add(linea);
		        		list_items_players.add(new itemPlayer(c.getString(0),c.getString(1),c.getString(2),c.getString(3),c.getString(4),c.getString(5),c.getString(6),c.getString(7),c.getString(8),c.getString(9),c.getString(10),c.getString(11)));
		        		
		        		adaptador.notifyDataSetChanged();
		        	//adapter.notifyDataSetChanged();
		        	}
		        	
		    		}catch(Exception ex){
		    			muestramensaje("La lista está vacía"); 
		    			
		    			}
		        	finally{
		        		db.close();
		        	}
		        	return false;
		       
		        case R.id.bActualiza:
		        	
		        	//Al hacer clic en el botón actualizar
		        	//1)Generamos una lista con los jugadores de la base de datos...
		        	SQLiteDatabase dbRead = openOrCreateDatabase("DBPlayers", SQLiteDatabase.OPEN_READONLY, null);
		        	Cursor c = null;
		        	//Ejecutamos la consulta sobre la base de datos.
		        	c = dbRead.rawQuery(sql, null);
		        	registrosUpdate = c.getCount();
		        	
		        	//Nos desplazaremos por todos los jugadores.
		        	while(c.moveToNext()){
		        		id = c.getString(0);
		        		//AsyncTask<String, Integer, String> resultado = new UpdateEloTask().execute(id);
		        		muestramensaje(this.getString(R.string.sync));
		        		try{
		        		//Inicio de Petición
		        		AsyncHttpClient client = new AsyncHttpClient();
		        		client.get("http://ratings.fide.com/search.phtml?search=" + id.replace(" ", ""), new AsyncHttpResponseHandler() {
		        			
		        			@Override
		        		    public void onSuccess(String response) {
		        				SQLiteDatabase dbPlayers = openOrCreateDatabase("DBPlayers", SQLiteDatabase.OPEN_READWRITE, null);
		        		    	org.jsoup.nodes.Document doc = Jsoup.parse(response);
		        		    	org.jsoup.nodes.Element content = doc.getElementById("displayDiv");
		        		    	Elements filas = content.getElementsByTag("tr");
		        		    	for(org.jsoup.nodes.Element fila: filas){
		        		    		Element celdas = fila.tagName("td");
		        		    		if (celdas.getElementsByIndexEquals(10).text().equals("") || celdas.getElementsByIndexEquals(10).text().equals("B-Year")){
		        						continue;
		        						}
		        		    		dbPlayers.execSQL("UPDATE Players SET rtg = '"+ fila.getElementsByTag("td").get(7).text() + "', rpd ='"+ fila.getElementsByTag("td").get(8).text() + "' , blz = '"+ fila.getElementsByTag("td").get(9).text() + "' Where id = '"+ fila.getElementsByTag("td").get(1).text() +"';"); 
		        		    			 
		        		    		}
		        		    	
		        		    	dbPlayers.close();
		        		    	
		        			}
		        			});
		        		//Fin de Petición
		        		
		        		 dbRead.close();
		        		
		        		
		        		}catch(Exception ex){muestramensaje(ex.getMessage());}
		        		
		        	}
		        	
		        	return false;
		       
		        default:
		           return false;
		  
		}
	}
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    
	   AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	   Intent i;
	    
	   switch(item.getItemId())
	   {
	      case R.id.view:
	        VerJugador(info.position);
	         return true;
	       
	      case R.id.delete:
	         EliminaJugador(info.position);
	         return true;
	      
	    }
	    return super.onContextItemSelected(item);
	}
	private void VerJugador(int id){
		String[] info = (String [])listResultDB.get(id); 
		intent = new Intent(this,PlayerActivity.class);
		
		intent.putExtra("Id", info[0]);
		intent.putExtra("Name", info[1]);
		intent.putExtra("Federation", info[5]);
		intent.putExtra("Title", info[4]);
		intent.putExtra("rtg", info[6]);
		intent.putExtra("rpd", info[7]);
		intent.putExtra("blz", info[8]);
		intent.putExtra("fnac", info[9]);
		intent.putExtra("sex", info[10]);
		startActivity(intent);
	}
	
	private void EliminaJugador(int id){
		String[] info = (String [])listResultDB.get((int) id); 
		
		players= new PlayersSQLiteHelper(this, "DBPlayers", null, 1);
		
		        	SQLiteDatabase db = players.getWritableDatabase();
					if(db !=null)
					{
						try {
							db.execSQL("DELETE FROM Players WHERE id ='" + info[0]+"'"); 
						}
						catch(Exception e){
							muestramensaje(e.getMessage());	
							}
						}	// TODO Auto-generated method stub
					db.close();
					listResult.remove(id);
					adaptador.notifyDataSetChanged();
		    		
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	ContextMenuInfo menuInfo) {

	  super.onCreateContextMenu(menu, v, menuInfo);
	  getMenuInflater().inflate(R.menu.context_menu,menu);
	  
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		 getMenuInflater().inflate(R.menu.search_player, menu);
		 SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		  SearchView sv2 = (SearchView) menu.findItem(R.id.svBuscar).getActionView();
		 sv2.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		 sv2.setOnQueryTextListener(new OnQueryTextListener() {
			
			@Override
			public boolean onQueryTextSubmit(String query) {
				AsyncHttpClient client = new AsyncHttpClient();
				client.get("http://ratings.fide.com/search.phtml?search=" + query.replace(" ", "+"), new AsyncHttpResponseHandler() {
				    @Override
				    public void onSuccess(String response) {
				    	listResult.clear();
				    	listResultDB.clear();
				    	list_items_players.clear();
				    	
				    	org.jsoup.nodes.Document doc = Jsoup.parse(response);
				    	org.jsoup.nodes.Element content = doc.getElementById("displayDiv");
				    	Elements filas = content.getElementsByTag("tr");
				    	for(org.jsoup.nodes.Element fila: filas){
				    		Element celdas = fila.tagName("td");
				    		
				    		//Si no es una fila de Jugador, saltamos a la siguiente
				    		if (celdas.getElementsByIndexEquals(10).text().equals("") || celdas.getElementsByIndexEquals(10).text().equals("B-Year")){
								continue;
								}
				    		String [] linea = new String[13];
				    		//IDcode[y] Name[y] T[y] WT OT Fed Rtg[y] Rpd[y] Blz[y] N B-Year S F 
				    		//Guardamos el enlace a la ficha del Jugador.	
				    		String href = celdas.select("a[href]").attr("href");
				    		
				    		//
				    		linea[0]= fila.getElementsByTag("td").get(1).text();
				    		linea[1] = fila.getElementsByTag("td").get(2).text(); //id
				    		linea[2] = fila.getElementsByTag("td").get(3).text(); //name
				    		linea[3] = fila.getElementsByTag("td").get(4).text(); //Title
				    		linea[4] = fila.getElementsByTag("td").get(5).text(); //
				    		linea[5] = fila.getElementsByTag("td").get(6).text(); //
				    		linea[6] = fila.getElementsByTag("td").get(7).text(); //Fed
				    		linea[7]= fila.getElementsByTag("td").get(8).text(); //rtg
				    		linea[8] = fila.getElementsByTag("td").get(9).text(); //rpd
				    		linea[9] = fila.getElementsByTag("td").get(11).text(); //blz
				    		linea[10] = fila.getElementsByTag("td").get(12).text(); 
				    		
				    		linea[12] = href;
				    		
				    		
				    		listResultDB.add(linea);
				    		list_items_players.add(new itemPlayer(linea[0], linea[1], linea[2], linea[3], linea[4], linea[5], linea[6], linea[7], linea[8], linea[9], linea[10], linea[11]));
				    		listResult.add(linea[1] + ": "+ linea[6]);
				    		adaptador.notifyDataSetChanged();
				    		addFlag = true;
				    		deleteFlag = false;
				    		//adapter.notifyDataSetChanged();
				    	}
				    	
				    }
				});
				
				return false;
		}
			@Override
			public boolean onQueryTextChange(String arg0) {
				// TODO Auto-generated method stub
				return false;
			}
		});
			intent = new Intent(this,PlayerActivity.class);
			//CLICK DESPUES DE BUSQUEDA Y ANTES DE AÑADIR AL JUGADOR.
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				try{
				String[] info = (String [])listResultDB.get(arg2); 
				
				
				intent.putExtra("Id", info[0]);
				intent.putExtra("Name", info[1]);
				intent.putExtra("Federation", info[5]);
				intent.putExtra("Title", info[2]);
				intent.putExtra("rtg", info[6]);
				intent.putExtra("rpd", info[7]);
				intent.putExtra("blz", info[8]);
				intent.putExtra("fnac", info[9]);
				intent.putExtra("sex", info[10]);
				intent.putExtra("deleteFlag", deleteFlag);
				intent.putExtra("addFlag",addFlag);
				startActivity(intent);
				}catch(Exception ex){
					muestramensaje("La lista está vacía");
				} 
				
			}
		});
		adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listResult);
    	lv.setAdapter(adaptador);
		//adapter = new ItemPlayerAdapter(this, list_items_players);
        //lv.setAdapter(adapter); 
		
		return true;
	}	
	
	
	public void muestraPosicion(int arg){
		Toast toast = Toast.makeText(this, "Position: "+ ((String [])listResultDB.get(arg))[1] , Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
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
	        return (AdView) findViewById(R.id.adView);
	    }
	

}
