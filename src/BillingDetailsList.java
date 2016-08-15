package com.bluebirdsols.moneymonster;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
//import android.widget.TextView;

public class BillingDetailsList extends Activity{
	DBSharing db;
	ListView lv1;
	//static ArrayList<String> frndName=new ArrayList<String>();
	//static ArrayList<String> Amt=new ArrayList<String>();
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billdetailslist);            
       lv1=(ListView)findViewById(R.id.ListView04);
       lv1.setAdapter(new BillingAdapter(this));
    }	
}