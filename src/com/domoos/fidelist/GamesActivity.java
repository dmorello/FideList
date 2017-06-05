package com.domoos.fidelist;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.Intent;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

public class GamesActivity extends Activity {
	protected ImageButton ibDownload;
	protected Bundle bundle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_games);
		ibDownload = (ImageButton)findViewById(R.id.ibDownload);
		bundle = getIntent().getExtras();
		ibDownload.setOnClickListener(new OnClickListener() {
		
			@Override
			public void onClick(View arg0) {
				try{
				AsyncHttpClient client = new AsyncHttpClient();
				
				client.get("http://www.chess-db.com/public/download.jsp?id=" + bundle.getString("Id").substring(1), new AsyncHttpResponseHandler(){
					@Override
				    public void onSuccess(String response) {
						
						Intent intent = new Intent(Intent.ACTION_VIEW);
						intent.setData(Uri.parse("http://www.chess-db.com/public/download.jsp?id="+bundle.getString("Id").substring(1)));
						startActivity(intent);
						
					}
				
				});
			}catch(Exception e){
				
				muestramensaje(e.getMessage());
			
			}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.games, menu);
		return true;
	}
	public void muestramensaje(String arg){
		Toast toast = Toast.makeText(this, "Position: "+ arg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
	}
}
