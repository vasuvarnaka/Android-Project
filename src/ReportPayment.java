package com.bluebirdsols.moneymonster;

import java.util.ArrayList;

import com.bluebirdsols.moneymonster.R;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;

	public class ReportPayment extends Activity implements OnClickListener, OnItemSelectedListener
	{
		private RadioButton rb1, rb2;
		TextView reclbl,paylbl,amtrectxt,amtpaidtxt;
		static int temp;
		long amtrec,amtpaid;
		Spinner spin1;
		DBSharing db;
		static String SpinnerChoice;
		ArrayList<String> arrlist=new ArrayList<String>();
		@Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.rpayment);
	        db=new DBSharing(this);
	        reclbl = (TextView)findViewById(R.id.reclbl);
	        paylbl = (TextView)findViewById(R.id.paylbl);
	        amtrectxt = (TextView)findViewById(R.id.amtrec);
	        amtpaidtxt = (TextView)findViewById(R.id.amtpay);
	        //spinner
	        db.open();
			 Cursor c = DBSharing.db.rawQuery("SELECT friendname FROM  friendslist WHERE username = ?;",
					 new String [] {Login.UID});		
			 if(c!=null)
			 {					
				 c.moveToFirst();							 
				      do {                        
            try
            {
			 int frndname = c.getColumnIndexOrThrow("friendname");
			 arrlist.add(c.getString(frndname));
            }
			 catch (Exception e) {
				// TODO: handle exception
				 AlertDialog.Builder alert = new AlertDialog.Builder(this);
     				alert.setMessage("You Have To Add Friends First");
     				alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
     					public void onClick(DialogInterface dialog, int which) {
     						Intent i = new Intent(ReportPayment.this,Home.class);
     						startActivity(i);
     					}
    				});
    				alert.show(); 
				 
			}
				 }while (c.moveToNext());				 
			 }
            else
            {
            	AlertDialog.Builder alert = new AlertDialog.Builder(this);
            	alert.setMessage("You Have To Add Friends First");
 				alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
 					public void onClick(DialogInterface dialog, int which) {
 						Intent i = new Intent(ReportPayment.this,Home.class);
 						startActivity(i);
 					}
				});
				alert.show(); 
            }				 				 
            c.close();
			db.close();
			String[] frndname = new String[arrlist.size()];
			arrlist.toArray(frndname);
	        spin1=(Spinner)findViewById(R.id.spin_frnd_name);
	        ArrayAdapter<String> bb=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,frndname);
			bb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spin1.setAdapter(bb);
			spin1.setOnItemSelectedListener(
	                new OnItemSelectedListener() {
	                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
	                    amtrec = 0;
	                    amtpaid=0;
	                    SpinnerChoice = spin1.getItemAtPosition(position).toString();
	                    db.open();
	       			 Cursor c = DBSharing.db.rawQuery("SELECT amountreceived,amountpaid FROM  paymentdetails WHERE username = ? AND friendname = ?;",
	       					 new String [] {Login.UID,SpinnerChoice});		
	       			 if(c!=null)
	       			 {					
	       				 c.moveToFirst();							 
	       				      do {                        
	                   try
	                   {
	       			 int amtr = c.getColumnIndexOrThrow("amountreceived");
	       			 //arrlist.add(c.getString(amtr));
	       			 amtrec = amtrec + Long.parseLong(c.getString(amtr));
	       			int amtp = c.getColumnIndexOrThrow("amountpaid");
	       			 //arrlist.add(c.getString(amtr));
	       			 amtpaid = amtpaid + Long.parseLong(c.getString(amtp));
	       			reclbl.setVisibility(0);
           	        paylbl.setVisibility(0);   
                	amtrectxt.setVisibility(0);
	       			amtpaidtxt.setVisibility(0); 
	       			 if(amtrec>amtpaid){
	       				 amtrectxt.setText("0");
	       				 amtpaidtxt.setText((amtrec-amtpaid)+"");
	       			 }
	       			 else if(amtpaid>amtrec){
	       				 amtrectxt.setText((amtpaid-amtrec)+"");
	       				 amtpaidtxt.setText("0");
	       			 }
	                   }
	       			 catch (Exception e) {
	       				// TODO: handle exception
	       				reclbl.setVisibility(4);	       				
	           	        paylbl.setVisibility(4);   
	                	amtrectxt.setVisibility(4);
		       			amtpaidtxt.setVisibility(4); 
	       				 
	       			}
	       				 }while (c.moveToNext());				 
	       			 }
	                   else
	                   {
	                	reclbl.setVisibility(4);
	           	        paylbl.setVisibility(4);   
	                	amtrectxt.setVisibility(4);
		       			amtpaidtxt.setVisibility(4); 
	                   }				 				 
	                   c.close();
	       			db.close();
	                    }

	                    public void onNothingSelected(AdapterView<?> parent) {
	                       
	                    }
	                }); 

	    //    spin.setOnItemSelectedListener(this);
	         
	      	//radio button    
	        rb1 = (RadioButton) findViewById(R.id.rb1);
	        rb2 = (RadioButton) findViewById(R.id.rb2);
	        rb1.setOnClickListener(new RadioGroup.OnClickListener() {
	            public void onClick(View v){ 
	            	temp=1;
	          	Intent i = new Intent(ReportPayment.this,PaymentDetailsActivity.class);
	        		startActivity(i);
	            }
	           });
	        rb2.setOnClickListener(new RadioGroup.OnClickListener() {
	            public void onClick(View v){ 
	            	temp=2;
	          	Intent i = new Intent(ReportPayment.this,PaymentDetailsActivity.class);
	        		startActivity(i);
	            }
	           });
	   }
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
		}
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			// TODO Auto-generated method stub
			
		}
		public void onNothingSelected(AdapterView<?> arg0) {
			// TODO Auto-generated method stub
			
		}	
	}