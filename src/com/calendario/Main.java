package com.calendario;

import java.io.IOException;
import java.util.ArrayList;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class Main  extends Activity {
	
  	 ArrayList<String[]>  listaDatosGrupos=new ArrayList<String[]>();
  	ArrayList<String>  datosListview=new ArrayList<String>();
  	String[] arregloDatosGrupos=new String[2];
  	public ListView lista;
  	public Intent intent;
  	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       
        
        BaseDAO oBaseDAO=new BaseDAO(this);
        try {
			oBaseDAO.createDataBase();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        oBaseDAO.open();
          
        listaDatosGrupos= oBaseDAO.consultarGrupos();
        
        for(int i=0; i<listaDatosGrupos.size();i++){
        	arregloDatosGrupos=listaDatosGrupos.get(i);
        	datosListview.add("Partidos "+arregloDatosGrupos[1]);
        }
        
        datosListview.add("Octavos de final");
        datosListview.add("Cuartos de final");
        datosListview.add("Semifinales");
        datosListview.add("Tercer puesto");
        datosListview.add("Final");
        datosListview.add("Partidos de todo el mundial");
        
        intent = new Intent(this, Partidos.class);
        
        lista = (ListView) findViewById(R.id.listView);
        lista.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_checked, datosListview));
        lista.setOnItemClickListener(new OnItemClickListener() { 

        	
        	@Override
			public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {
				
				
				if(posicion<=7){
					arregloDatosGrupos=listaDatosGrupos.get(posicion);
					intent.putExtra("grupo", arregloDatosGrupos[1]);			
					startActivity(intent);
				}
				
				if(posicion==8){
					intent.putExtra("grupo", "Octavos de final");			
					startActivity(intent);
				}
				
				if(posicion==9){
					intent.putExtra("grupo", "Cuartos de final");			
					startActivity(intent);
				}
				
				if(posicion==10){
					intent.putExtra("grupo", "Semifinales");			
					startActivity(intent);
				}
				
				if(posicion==11){
					intent.putExtra("grupo", "Tercer puesto");			
					startActivity(intent);
				}
				
				if(posicion==12){
					intent.putExtra("grupo", "Final");			
					startActivity(intent);
				}
				
				if(posicion==13){
					intent.putExtra("grupo", "Partidos de todo el mundial");			
					startActivity(intent);
				}
				
		}
        });        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        menu.add(Menu.NONE, 1, Menu.NONE, "Info");

        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	int opcion=	item.getItemId();
    	Intent intent;
    	
    	switch (opcion) {
		case 1:
			//OPCION PAUSAR
			//Toast.makeText(getApplicationContext(), "Pausar", Toast.LENGTH_SHORT).show();
			finish();
		    intent = new Intent(this,info.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("finishApplication", true);			
			startActivity(intent);
			break;
 

		default:
			break;
		} 

        return super.onOptionsItemSelected(item); 
    }

}
