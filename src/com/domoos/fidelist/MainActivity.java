package com.domoos.fidelist;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TabHost;


@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {
	protected TabHost tabs;
	protected Intent intent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tabs = (TabHost)findViewById(android.R.id.tabhost);
		tabs.setup();

		tabs.addTab(tabs.newTabSpec("Buscador").setIndicator("Search").setContent(new Intent(this, SearchPlayer.class)));
		tabs.addTab(tabs.newTabSpec("Lista").setIndicator("List").setContent(new Intent(this, FollowPlayers.class)));
		
		tabs.setCurrentTab(0);
		
	
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
