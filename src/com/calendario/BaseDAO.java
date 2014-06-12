package com.calendario;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.telephony.TelephonyManager;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.telephony.TelephonyManager;
import android.text.format.Time;
import android.util.Log;

    public class BaseDAO extends SQLiteOpenHelper{ 
     
    //The Android's default system path of your application database.

    private static String DB_PATH = "/data/data/com.calendario/databases/";
      
    private static String DB_NAME = "base.sqlite"; 
     
    private SQLiteDatabase db;
     
    private final Context myContext; 
      
    /**
      * Constructor 
      * Takes and keeps a reference of the passed context in order to access to the application assets and resources.
      * @param context 
      */
    public BaseDAO(Context context) {

    super(context, DB_NAME, null, 1);
    this.myContext = context;
    }	
     
    /**
      * Creates a empty database on the system and rewrites it with your own database.
      * */
    public void createDataBase() throws IOException{
     
    boolean dbExist = checkDataBase();
     
    	if(dbExist){
    		//do nothing - database already exist
    	}else{ 
     
    //By calling this method and empty database will be created into the default system path
    //of your application so we are gonna be able to overwrite that database with our database.
    		this.getReadableDatabase();
     
    	try {
     
    		copyDataBase();
      
    	} catch (IOException e) {
     
    		throw new Error("Error copying database");
     
    	}
    }
     
    }
     
    /**
      * Check if the database already exist to avoid re-copying the file each time you open the application.
      * @return true if it exists, false if it doesn't
      */
    private boolean checkDataBase(){
     
    SQLiteDatabase checkDB = null;
     
    try{
    String myPath = DB_PATH + DB_NAME;
    checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
     
    }catch(SQLiteException e){
     
    //database does't exist yet.
     
    }
     
    if(checkDB != null){
     
    checkDB.close();
     
    }
     
    return checkDB != null ? true : false;
    }
     
    /**
      * Copies your database from your local assets-folder to the just created empty database in the
      * system folder, from where it can be accessed and handled.
      * This is done by transfering bytestream.
      * */
    private void copyDataBase() throws IOException{
     
    //Open your local db as the input stream
    InputStream myInput = myContext.getAssets().open(DB_NAME);
     
    // Path to the just created empty db
    String outFileName = DB_PATH + DB_NAME;
     
    //Open the empty db as the output stream
    OutputStream myOutput = new FileOutputStream(outFileName);
     
    //transfer bytes from the inputfile to the outputfile
    byte[] buffer = new byte[1024];
    int length;
    while ((length = myInput.read(buffer))>0){
    myOutput.write(buffer, 0, length);
    }
     
    //Close the streams
    myOutput.flush();
    myOutput.close();
    myInput.close();
     
    }
     
    public void open() throws SQLException{
     
    //Open the database
    String myPath = DB_PATH + DB_NAME;
    db = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READWRITE);
     
    }
     
    @Override
    public synchronized void close() {
     
    if(db != null)
    	db.close();
     
    super.close();
     
    }
     
    @Override
    public void onCreate(SQLiteDatabase db) {
     
    }
     
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
     
    }
     
    // Add your public helper methods to access and get content from the database.
    // You could return cursors by doing "return myDataBase.query(....)" so it'd be easy
    // to you to create adapters for your views.
  
    public ArrayList<String[]> consultarGrupos() 
    {    	 
   	 ArrayList<String[]>  listaDatosGrupos=new ArrayList<String[]>();
   	 String[] arregloDatosGrupos=new String[2];
   	 String consulta="";
   	 
   	 Cursor fila=null;
   	 
   	 //seleccione los valores de respuestas de no aplica
   	 consulta="SELECT cod_grupo, nombre_grupo FROM grupos";
   	 fila=db.rawQuery(consulta,null);
   	 
   	 if (fila.moveToFirst())
        {
   		 do{
   		 arregloDatosGrupos=new String[2];
   		 arregloDatosGrupos[0]=fila.getString(0);
   		 arregloDatosGrupos[1]=fila.getString(1);
   		 listaDatosGrupos.add(arregloDatosGrupos);
   		 
   		 }while(fila.moveToNext()); 
        }  
   	
        return listaDatosGrupos;
        	
    }
    
    public ArrayList<String[]> consultarEquiposPorGrupo(String codigoGrupo) 
    {    	 
   	 ArrayList<String[]>  ListadoDatosEquipos=new ArrayList<String[]>();
   	 String[] arregloDatosEquipos=new String[3];
   	 String consulta="";
   	 
   	 Cursor fila=null;
   	 
   	 //seleccione los valores de respuestas de no aplica
   	 consulta="SELECT cod_equipo, nombre_equipo, cod_grupo FROM equipos";
   	 fila=db.rawQuery(consulta,null);
   	 
   	 if (fila.moveToFirst())
        {
   		 do{
   		 arregloDatosEquipos=new String[3];
   		 arregloDatosEquipos[0]=fila.getString(0);
   		 arregloDatosEquipos[1]=fila.getString(1);
   		 arregloDatosEquipos[2]=fila.getString(2);
   		 ListadoDatosEquipos.add(arregloDatosEquipos);
   		 
   		 }while(fila.moveToNext()); 
        }  
   	
        return ListadoDatosEquipos;
        	
    }
    
    
    public ArrayList<String[]>  consultarPartidoPorGrupo(String nombreGrupo) 
    {    	 
   	 ArrayList<String[]>  ListadoDatosPartidos=new ArrayList<String[]>();
   	 String[] arregloPartidos=new String[6];
   	 String consulta="";
   	 
   	 Cursor fila=null;
   	 
   int s=nombreGrupo.indexOf("Grupo");
   	 if(nombreGrupo.indexOf("Grupo")>=0){
   	 consulta="select p.cod_partido, p.fecha, p.hora, p.lugar,p.nombre_equipo1,"+
   	 "p.nombre_equipo2 from partidos p, equipos e, grupos g "+
   	 "where g.nombre_grupo='"+nombreGrupo+"' and g.cod_grupo=e.cod_grupo"+
   	  " and e.nombre_equipo=p.nombre_equipo1";
   	 }
   	 
   	 if(nombreGrupo.equals("Octavos de final")){
   		consulta="select p.cod_partido, p.fecha, p.hora, p.lugar,p.nombre_equipo1,"+
   		   	 "p.nombre_equipo2 from partidos p where cod_partido>=49 and cod_partido<=56";
   	 }
   	 
   	 if(nombreGrupo.equals("Cuartos de final")){
    		consulta="select p.cod_partido, p.fecha, p.hora, p.lugar,p.nombre_equipo1,"+
    	   		   	 "p.nombre_equipo2 from partidos p where cod_partido>=57 and cod_partido<=60";	 
   	 }
   	 
   	 if(nombreGrupo.equals("Semifinales")){
 		consulta="select p.cod_partido, p.fecha, p.hora, p.lugar,p.nombre_equipo1,"+
	   		   	 "p.nombre_equipo2 from partidos p where cod_partido>=61 and cod_partido<=62";	
   	 }
   	 
   	 if(nombreGrupo.equals("Tercer puesto")){
  		consulta="select p.cod_partido, p.fecha, p.hora, p.lugar,p.nombre_equipo1,"+
	   		   	 "p.nombre_equipo2 from partidos p where cod_partido=63";
   	 }
   	 
   	 if(nombreGrupo.equals("Final")){
   		consulta="select p.cod_partido, p.fecha, p.hora, p.lugar,p.nombre_equipo1,"+
	   		   	 "p.nombre_equipo2 from partidos p where cod_partido=64";
   	 }
   	 
   	 if(nombreGrupo.equals("Partidos de todo el mundial")){
   		consulta="select p.cod_partido, p.fecha, p.hora, p.lugar,p.nombre_equipo1,"+
	   		   	 "p.nombre_equipo2 from partidos p order by p.cod_partido asc";
   	 }
   	 
   	 fila=db.rawQuery(consulta,null);
   	 
   	 if (fila.moveToFirst())
        {
   		 do{
   			arregloPartidos=new String[6];
   			arregloPartidos[0]=fila.getString(0);
   			arregloPartidos[1]=fila.getString(1);
   			arregloPartidos[2]=fila.getString(2);
   			arregloPartidos[3]=fila.getString(3);
   			arregloPartidos[4]=fila.getString(4);
   			arregloPartidos[5]=fila.getString(5);
   			ListadoDatosPartidos.add(arregloPartidos);
   		 
   		 }while(fila.moveToNext()); 
        }  
   	
        return ListadoDatosPartidos;
        	
    }
     

     
    }