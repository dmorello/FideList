package com.domoos.fidelist;

import java.util.ArrayList;
import java.util.Arrays;


import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.SimpleXYSeries.ArrayFormat;
import com.domoos.fidelist.customFormat.MyIndexFormat;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class EloActivity extends Activity {
	 private XYPlot mySimpleXYPlot;
	 private XYPlot mySimpleXYPlot2;
	 protected Bundle bundle;
	 protected HistorySQLiteHelper history; 
	 
	    @Override
	    public void onCreate(Bundle savedInstanceState)
	    {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_elo);
	        bundle=this.getIntent().getExtras();
	        // Inicializamos el objeto XYPlot búscandolo desde el layout:
	        mySimpleXYPlot = (XYPlot) findViewById(R.id.mySimpleXYPlot);
	        // Creamos dos arrays de prueba. En el caso real debemos reemplazar

	        // estos datos por los que realmente queremos mostrar

			history= new HistorySQLiteHelper(this, "DBHistory", null, 1);
			//*******
			//Creamos las tablas necesarias...
			//players.onCreate(players.getWritableDatabase());
			//*******
			
			SQLiteDatabase db = history.getReadableDatabase();
			String[] args = new String[] {bundle.getString("Id").replace(" ", "")};
			String rating = bundle.getString("rating");
			String sqlCompleto = "Select period,"+rating+" FROM History Where id=? and "+rating+"<>'  ';";
			Cursor c_completo = db.rawQuery(sqlCompleto, args);
			/**
			 *TAREA: 1-Separar la generación de los arrays
			 * 		 2-Declarar Cursores
			 * 		 3-Declarar lPeriodo para cada tipo de rating
			 * 		 3-Se ejecuta un while para cada cursor
			 */
				
			Number [] seriesRtg = new Number[c_completo.getCount()];
						
			String[] lPeriodoRtg = new String[c_completo.getCount()];
			
			int rtgAux=0;
			
			c_completo.moveToFirst();
			int i=0;
			while(c_completo.moveToNext()){
				try{
					seriesRtg[i]=(Integer.parseInt(c_completo.getString(1).replace(" ", "")));
					rtgAux=Integer.parseInt(c_completo.getString(1).replace(" ", ""));
				}catch(Exception ex){
					seriesRtg[i]=(rtgAux);	
				}
				lPeriodoRtg[i]=(c_completo.getString(0).replace(" ", ""));
				i++;
			}
			
			// Añadimos Línea Número UNO:
	        
			MyIndexFormat mif1 = new MyIndexFormat();
	        mif1.Labels=lPeriodoRtg;

			

	        mySimpleXYPlot.setDomainValueFormat(mif1);
	        
	        SimpleXYSeries series1 = new SimpleXYSeries(Arrays.asList(seriesRtg),  // Array de datos
	                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Sólo valores verticales
	                "Rtg"); // Nombre de la primera serie
	        
	        
	        // Modificamos los colores de la primera serie
	        LineAndPointFormatter series1Format = new LineAndPointFormatter(
	                Color.rgb(0, 200, 0),                   // Color de la línea
	                Color.rgb(0, 100, 0),                   // Color del punto
	                null, null);              // Relleno

	        mySimpleXYPlot.addSeries(series1, series1Format);
	        
	        
	        
	        
	    }
	}