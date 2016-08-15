package com.bluebirdsols.moneymonster;

import java.util.ArrayList;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;
//import android.widget.TextView;

public class Creditors extends Activity{
	DBSharing db;
	ListView lv1;
	static ArrayList<String> frndName=new ArrayList<String>();
	static ArrayList<String> Amt=new ArrayList<String>();
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.creditors);
        
        db=new DBSharing(this);
        db.open();
        frndName.clear();
        Amt.clear();
        Cursor c = DBSharing.db.rawQuery("SELECT friendname,return FROM desc WHERE username = ? AND return>0;",
   			 new String [] {Login.UID});
   	 if(c!=null)
   	 {					
   		 c.moveToFirst();							 
   		      do {                        
       try
       {
    	   	int frnd = c.getColumnIndexOrThrow("friendname");
    	   	frndName.add(c.getString(frnd));
    	   	int col = c.getColumnIndexOrThrow("return");
    	   	Amt.add(c.getString(col));   	 
       }
   	 catch (Exception e) {
   		// TODO: handle exception   		   		 
   	}
   		 }while (c.moveToNext());				 
   	 }	      		 				 
       c.close();
       db.close();
       lv1=(ListView)findViewById(R.id.ListView03);
       lv1.setAdapter(new CreditorsAdapter(this));
    }	
}