package com.domoos.fidelist;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;

public class ItemPlayerAdapter extends BaseAdapter{
	protected Activity activity;
	protected ArrayList<itemPlayer> items;
	protected View vi;
	
	public ItemPlayerAdapter(Activity activity, ArrayList<itemPlayer> items){
		this.activity=activity;
		this.items = items;
	}
	
	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return items.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View contentView, ViewGroup parent) {
		vi=contentView;
		
		if(contentView==null){
			  LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		      vi = inflater.inflate(R.layout.list_item_layout, null);
		}
		
		itemPlayer item = items.get(position);
		
		CheckBox cb = (CheckBox) vi.findViewById(R.id.options);
				
		TextView nombre = (TextView) vi.findViewById(R.id.nombre);
		nombre.setText(item.getName());
		
		TextView fide = (TextView) vi.findViewById(R.id.fide);
		fide.setText(item.getRtg());
		
				
		return vi;
	}
	
	

}
