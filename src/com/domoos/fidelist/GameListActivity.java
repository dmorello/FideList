package com.domoos.fidelist;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.google.ads.Ad;
import com.google.ads.AdListener;
import com.google.ads.AdRequest;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.InterstitialAd;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class GameListActivity extends Activity implements AdListener {
protected Bundle bundle;
protected ListView lv;
protected ArrayAdapter<String> adaptador;
protected ArrayList<String> listResult;
protected ArrayList<String> listUrlGames;
//private InterstitialAd interstitial;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game_list);
		
		//COMENTARIOS EN EL ANUNCIO INTERSTITIAL
	//	interstitial = new InterstitialAd(this, "ca-app-pub-4256844643535775/2137031042");
		 
	    AdRequest adRequest = new AdRequest();
	   
	  //  interstitial.loadAd(adRequest);
	  //  interstitial.setAdListener(this);

		lv = (ListView)findViewById(R.id.gamesList);
		listResult = new ArrayList<String>();
		listUrlGames = new ArrayList<String>();
		adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listResult);
		lv.setAdapter(adaptador);
		bundle = getIntent().getExtras();
		//http://chess-db.com/public/pinfo.jsp?id=2294290
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			
				final String url = listUrlGames.get(arg2); 
				if(url.equals("No games found")){
					
				}else{
				try{
					AsyncHttpClient client = new AsyncHttpClient();
					
					client.get(url, new AsyncHttpResponseHandler(){
						@Override
					    public void onSuccess(String response) {
							
							Intent intent = new Intent(Intent.ACTION_VIEW);
							intent.setData(Uri.parse(url));
							startActivity(intent);
							
						}
					
					});
				}catch(Exception e){
					
					muestramensaje(e.getMessage());
				
				}
				}
			}
		});
		
		//*****CHESS-DB.COM*****
		AsyncHttpClient client = new AsyncHttpClient();
		
		client.get("http://chess-db.com/public/pinfo.jsp?id=" + bundle.getString("Id").replace(" ", ""), new AsyncHttpResponseHandler() {
		    @Override
		    public void onSuccess(String response) {
		    	boolean havegames =false;
		    	org.jsoup.nodes.Document doc = Jsoup.parse(response);
		    	
		    	Elements aElements = doc.getElementsByTag("a");
		    	
		    	for(org.jsoup.nodes.Element aElement: aElements){
		    		
		    		if(aElement.attr("href").contains(bundle.getString("Id").replace(" ", "")) && aElement.attr("href").contains("download")){
		    			listResult.add("Games in Chess-db.com");
		    			havegames=true;
		    			listUrlGames.add("http://chess-db.com/public/download.jsp?id=" + bundle.getString("Id").replace(" ", ""));
		    			break;
		    		}
		    	}
		    	
		    	if(havegames==false){
		    		listResult.add("No games found");
	    			
		    	}
		    	adaptador.notifyDataSetChanged();
		    }
		});
		//*****FIN****CHESS-DB.COM*****
		
		//*****CHESS-RESULTS.COM*****
		// httphandler chessresults = new httphandler();
		// chessresults.post("http://chess-results.com/PartieSuche.aspx?lan=2&_ctl0_P1_txt_nachname=Morello");
	//*******FIN****CHESS-RESULTS.COM*****

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game_list, menu);
		return true;
	}

	@Override
	public void onDismissScreen(Ad arg0) {
		
		
	}

	@Override
	public void onFailedToReceiveAd(Ad arg0, ErrorCode arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onLeaveApplication(Ad arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPresentScreen(Ad arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onReceiveAd(Ad arg0) {
		 //interstitial.show();
		
	}
	
	public void muestramensaje(String arg){
		Toast toast = Toast.makeText(this, arg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
	}	
}
