package com.domoos.fidelist;

import java.util.ArrayList;

import org.jsoup.Jsoup;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.os.Bundle;
import android.app.Activity;
import android.view.Gravity;
import android.view.Menu;
import android.webkit.WebView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class StadisticsActivity extends Activity{
	protected WebView wv;
	protected Bundle bundle;
	protected TextView ww;
	protected TextView wb;
	protected TextView wt;
	protected TextView lw;
	protected TextView lb;
	protected TextView lt;
	protected TextView dw;
	protected TextView db;
	protected TextView dt;
	protected TextView tvNameValue;
	protected ListView listStyle;
	protected ArrayList<String> listResultWhite;
	protected ArrayList<String> listResult;
	protected ArrayList<String> listResultBlack;
	protected ArrayAdapter<String> adaptador;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stadistics);
		wv = (WebView)findViewById(R.id.webView1);
		bundle = getIntent().getExtras();
		
		tvNameValue = (TextView)this.findViewById(R.id.name);
		tvNameValue.setText(bundle.getString("Name"));
		
		ww = (TextView)this.findViewById(R.id.tvValueWW);
		wb = (TextView)this.findViewById(R.id.tvRtg);
		wt = (TextView)this.findViewById(R.id.tvValueWT);
		lw = (TextView)this.findViewById(R.id.tvValueLW);
		lb = (TextView)this.findViewById(R.id.tvValueLB);
		lt = (TextView)this.findViewById(R.id.tvValueLT);
		dw = (TextView)this.findViewById(R.id.tvValueDW);
		db = (TextView)this.findViewById(R.id.tvValueDB);
		dt = (TextView)this.findViewById(R.id.tvValueDT);
		
		listResult = new ArrayList<String>();
		listResultWhite = new ArrayList<String>();
		listResultBlack = new ArrayList<String>();
		
		listStyle = (ListView)this.findViewById(R.id.l_style);
		adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, listResult);
		listStyle.setAdapter(adaptador);
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://ratings.fide.com/chess_statistics.phtml?event=" + bundle.getString("Id").replace(" ", ""), new AsyncHttpResponseHandler(){
			
			@Override
		    public void onSuccess(String response) {
		    	
		    	org.jsoup.nodes.Document doc = Jsoup.parse(response);
		    	org.jsoup.nodes.Element content = doc.getElementById("main-col");
		    	
		    	Elements contentpaneopen = content.getElementsByClass("contentpaneopen");
		    	Elements stadistictable = contentpaneopen.get(1).getElementsByTag("table");
		    	
		    	ww.setText(stadistictable.get(2).getElementsByTag("td").get(2).text().split(":")[1]);
		    	dw.setText(stadistictable.get(2).getElementsByTag("td").get(3).text().split(":")[1]);
		    	lw.setText(stadistictable.get(2).getElementsByTag("td").get(4).text().split(":")[1]);
		    	wb.setText(stadistictable.get(3).getElementsByTag("td").get(2).text().split(":")[1]);
		    	db.setText(stadistictable.get(3).getElementsByTag("td").get(3).text().split(":")[1]);
		    	lb.setText(stadistictable.get(3).getElementsByTag("td").get(4).text().split(":")[1]);
		    	wt.setText(stadistictable.get(4).getElementsByTag("td").get(2).text().split(":")[1]);
		    	dt.setText(stadistictable.get(4).getElementsByTag("td").get(3).text().split(":")[1]);
		    	lt.setText(stadistictable.get(4).getElementsByTag("td").get(4).text().split(":")[1]);
		    	
		    		
		    	
		    }
		});
		AsyncHttpClient clientGames = new AsyncHttpClient();
		clientGames.get("http://chess-db.com/public/pinfo.jsp?id=" + bundle.getString("Id").replace(" ", ""), new AsyncHttpResponseHandler(){
			
			@Override
		    public void onSuccess(String response) {
		    	
		    	org.jsoup.nodes.Document doc = Jsoup.parse(response);
		    	
		    	Elements tables = doc.getElementsByClass("curvedEdges");
		    	Elements filas = tables.get(1).getElementsByTag("tr");
		    	int idfila=0;
		    	for(org.jsoup.nodes.Element fila: filas){
		    		idfila++;
		    		if(idfila==1){
		    			listResultWhite.add("Opening (White)");
		    			listResultBlack.add("Opening (Black)");
		    			continue;
		    		}	
		    		Element celdas = fila.tagName("td");
		    		if(celdas.getElementsByIndexEquals(0).text().contains("tendencies")){
		    			break;
		    		}else{
		    		
		    		listResultWhite.add(celdas.getElementsByIndexEquals(0).text());
		    		listResultBlack.add(celdas.getElementsByIndexEquals(1).text());
		    		
		    		
		    		}
		    		
		    		
		    	}
		    	
		    	listResult.addAll(listResultWhite);
		    	listResult.addAll(listResultBlack);
		    	adaptador.notifyDataSetChanged();
		    }
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.stadistics, menu);
		return true;
	}
	
	public void muestramensaje(String arg){
		Toast toast = Toast.makeText(this, "Position: "+ arg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
	}

}
