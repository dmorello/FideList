package com.domoos.fidelist;

import android.content.Context; 
import android.database.sqlite.SQLiteDatabase; 
import android.database.sqlite.SQLiteDatabase.CursorFactory; 
import android.database.sqlite.SQLiteOpenHelper; 
public class HistorySQLiteHelper extends SQLiteOpenHelper {
	String sqlCreateHistory = "CREATE TABLE History (id TEXT, period TEXT, rtg TEXT, gms_rtg TEXT, rpd TEXT, gms_rpd TEXT, blz TEXT, gms_blz TEXT, PRIMARY KEY (id, period));";
	
	public HistorySQLiteHelper(Context contexto, String nombre, CursorFactory factory, int version) {  
		super(contexto, nombre, factory, version); 
	}
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL(sqlCreateHistory);
		
		} 
	@Override  
	public void onUpgrade(SQLiteDatabase db, int versionAnterior, int versionNueva) { 
		
		db.execSQL("DROP TABLE IF EXISTS History");  
		db.execSQL(sqlCreateHistory);
		} 
	}
	
	

