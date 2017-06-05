package com.domoos.fidelist;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.os.AsyncTask;
import android.view.Gravity;
import android.widget.Toast;

class UpdateEloTask extends AsyncTask<String, Integer, String> {
	String resultado ="";
	@Override
	protected String doInBackground(String... ids) {
		
		for(int i=0;i<ids.length;i++){
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("http://ratings.fide.com/search.phtml?search=" + ids[0].replace(" ", "+"), new AsyncHttpResponseHandler() {
		    @Override
		    public void onSuccess(String response) {
		    	org.jsoup.nodes.Document doc = Jsoup.parse(response);
		    	org.jsoup.nodes.Element content = doc.getElementById("displayDiv");
		    	Elements filas = content.getElementsByTag("tr");
		    	for(org.jsoup.nodes.Element fila: filas){
		    		Element celdas = fila.tagName("td");
		    		if (celdas.getElementsByIndexEquals(10).text().equals("") || celdas.getElementsByIndexEquals(10).text().equals("B-Year")){
						continue;
						}
		    			resultado=  "campo 5: "+ fila.getElementsByTag("td").get(5).text() + "campo 6: "+ fila.getElementsByTag("td").get(6).text() + "campo 7: "+ fila.getElementsByTag("td").get(7).text(); 
		    			 
		    		}
		    	
		    }
		    
		});	
		}

		return resultado;
	}
	
	
}
