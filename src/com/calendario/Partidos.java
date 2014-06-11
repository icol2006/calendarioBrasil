package com.calendario;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Partidos extends Activity{

	public ListView lista;
	BaseDAO oBaseDAO;
  	 ArrayList<String[]>  ListadoDatosPartidos=new ArrayList<String[]>();
  	 String[] arregloDatosEquipos=new String[3];
  	 String grupo="";
   	ArrayList<String>  datosListview=new ArrayList<String>();

  	 
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        oBaseDAO=new BaseDAO(this);
        
        Bundle extras = getIntent().getExtras();
		grupo = extras.getString("grupo");
		
		oBaseDAO.open();
		ListadoDatosPartidos=oBaseDAO.consultarPartidoPorGrupo(grupo);
		
		datosListview.add(grupo);
		
        for(int i=0; i<ListadoDatosPartidos.size();i++){
        	arregloDatosEquipos=ListadoDatosPartidos.get(i);
        	datosListview.add(arregloDatosEquipos[1]+" - "+arregloDatosEquipos[2]+" - "+arregloDatosEquipos[4]+" vs "+arregloDatosEquipos[5]);
        }
        
        lista = (ListView) findViewById(R.id.listView);
        lista.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_checked, datosListview));
        
        
	}
}
