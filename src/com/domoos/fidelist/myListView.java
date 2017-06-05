package com.domoos.fidelist;
import android.content.Context;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;

public class myListView extends ListView {
	protected TextView tv;
	protected CheckBox cb;
	public myListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		tv = new TextView(context);
		cb = new CheckBox(context);
		
		
	}

}
