package com.bluebirdsols.moneymonster;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;

public class Flaseimg extends Activity{
	 public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		      //Remove notification bar
		      this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
	        setContentView(R.layout.flse);
	       

	        Thread th=new Thread()
	       
	        {
	        	public void run()
	        	{
	        try {
	        	Thread.sleep(3000);
				 Intent i=new Intent(getApplicationContext(),Login.class);
			        startActivity(i);
			        finish();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	       
		}
	};
	           th.start();
	 }

}
