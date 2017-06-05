package com.domoos.fidelist;

import android.content.Context; 
import android.database.sqlite.SQLiteDatabase; 
import android.database.sqlite.SQLiteDatabase.CursorFactory; 
import android.database.sqlite.SQLiteOpenHelper; 
public class PlayersSQLiteHelper extends SQLiteOpenHelper {
	String sqlCreate = "CREATE TABLE Players (id TEXT PRIMARY KEY, name TEXT, t TEXT, wt TEXT, ot TEXT, fed TEXT, rtg TEXT, rpd TEXT, blz TEXT, byear TEXT, s TEXT, iflag TEXT);";	
	
	public PlayersSQLiteHelper(Context contexto, String nombre, CursorFactory factory, int version) {  
		super(contexto, nombre, factory, version); 
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL(sqlCreate);
		
		} 
	@Override  
	public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) { 
		
		db.execSQL("DROP TABLE IF EXISTS Players");  

	db.execSQL(sqlCreate);   

		} 
	}
	
	

