package com.calendario;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

public class info extends Activity  {

	
	 protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.info);
	       
     }
	 
	    @Override
	    public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if ((keyCode == KeyEvent.KEYCODE_BACK)) {
	    	startActivity(new Intent(this, Main.class));
	    	finish();
	    }
	    return super.onKeyDown(keyCode, event);

	    }
	    
	 
	 
}
