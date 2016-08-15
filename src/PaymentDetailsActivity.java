package com.bluebirdsols.moneymonster;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
public class PaymentDetailsActivity extends Activity implements OnClickListener{
	Button s,c;
	EditText desc,totamt,dt;
	TextView t1,t2;
	DBSharing db;
	boolean flag=false;
	long collect,amtreturn;
	long check;
	private static final  int DATE_PICKER_DIALOG=1;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reportpayment);
        s = (Button)findViewById(R.id.save);
        s.setOnClickListener(this);
        c = (Button)findViewById(R.id.can);
        c.setOnClickListener(this);
        t1 = (TextView)findViewById(R.id.pmp);
        t2 = (TextView)findViewById(R.id.prp);
        desc = (EditText)findViewById(R.id.desc);
        totamt = (EditText)findViewById(R.id.totamt);
        dt = (EditText)findViewById(R.id.dt);
       dt.setInputType(0);
        desc.setOnClickListener(this);
        totamt.setOnClickListener(this);
        dt.setOnClickListener(this);
      //  dt.setOnTouchListener(otl);        
        db=new DBSharing(this);
        db.open();
   	 Cursor c = DBSharing.db.rawQuery("SELECT collect,return FROM  desc WHERE username = ? AND friendname=?;",
			 new String [] {Login.UID,ReportPayment.SpinnerChoice});		
	 if(c!=null)
	 {					
		 c.moveToFirst();							 
		      do {                        
    try
    {
	 int col = c.getColumnIndexOrThrow("collect");
	 collect = Long.parseLong(c.getString(col));
	 int retn = c.getColumnIndexOrThrow("return");
	 amtreturn = Long.parseLong(c.getString(retn));
    }
	 catch (Exception e) {
		// TODO: handle exception
		 AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setMessage("You Have To Add Friends First");
				alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					//	Intent i = new Intent(ReportPayment.this,Home.class);
					//	startActivity(i);
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
					//Intent i = new Intent(Pa.this,Home.class);
					//startActivity(i);
				}
		});
		alert.show(); 
    }				 				 
    c.close();
    db.close();
        if(ReportPayment.temp==1)
        { 
        	t1.setText(ReportPayment.SpinnerChoice);
        	t2.setText(Login.UID);
        	
        }
        else if(ReportPayment.temp==2)
        {
        	flag=true;
        	t1.setText(Login.UID);
        	t2.setText(ReportPayment.SpinnerChoice);
        }
    }
	public void onClick(View v) {
		// TODO Auto-generated method stub
	/*	Intent i = new Intent(this,ReportPayment.class);
		startActivity(i);*/
		switch (v.getId()) {
		case R.id.dt:
//			InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
//			imm.hideSoftInputFromWindow(dt.getWindowToken(), 0);
		showDialog(DATE_PICKER_DIALOG);
		break;
		case R.id.save:
			if(desc.getText().toString().equals("")||totamt.getText().toString().equals("")||dt.getText().toString().equals(""))
			{
				AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setMessage("Required All Fields");
				alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
										
					}
				});
				alert.show();
				if(desc.getText().toString().equals(""))
				desc.requestFocus();
				else if(totamt.getText().toString().equals(""))
				totamt.requestFocus();
				else if(dt.getText().toString().equals(""))
				dt.requestFocus();
					
			}
			else{

			 	db.open();			 	
			 	if(flag){
			    if(collect==0&&amtreturn==0)
			    {
			    	db.UpdateDesc(Login.UID, ReportPayment.SpinnerChoice, totamt.getText().toString(), "0");	
			    }
			    else
			    if(collect==0&&amtreturn!=0){
			    	if(amtreturn>Integer.parseInt(totamt.getText().toString())){
			    		db.UpdateDesc(Login.UID, ReportPayment.SpinnerChoice, "0",""+(amtreturn-Long.parseLong(totamt.getText().toString())));
			    	}
			    	else if(amtreturn<Integer.parseInt(totamt.getText().toString())){
			    		db.UpdateDesc(Login.UID, ReportPayment.SpinnerChoice, ""+(Long.parseLong(totamt.getText().toString())-amtreturn),"0");
			    	}
			    	else
			    	{
			    		db.UpdateDesc(Login.UID, ReportPayment.SpinnerChoice, "0","0");
			    	}
			    }
			    else
				    if(collect!=0&&amtreturn==0){
				    	//if(collect>Integer.parseInt(totamt.getText().toString())){
				    		db.UpdateDesc(Login.UID, ReportPayment.SpinnerChoice, ""+(collect+Long.parseLong(totamt.getText().toString())), "0");
				    	//}
				    }
				check = db.insertPaymentDetails(Login.UID,ReportPayment.SpinnerChoice,"0",totamt.getText().toString(),dt.getText().toString(),desc.getText().toString());
			    }
			    else
			    {
			    	if(collect==0&&amtreturn==0)
				    {
				    	db.UpdateDesc(Login.UID, ReportPayment.SpinnerChoice, "0",totamt.getText().toString());	
				    }
			    	else
					    if(collect==0&&amtreturn!=0){
					    	//if(amtreturn>Integer.parseInt(totamt.getText().toString())){
					    		db.UpdateDesc(Login.UID, ReportPayment.SpinnerChoice, "0",""+(amtreturn+Long.parseLong(totamt.getText().toString())));
					    	//}
					    }
					    else
						    if(collect!=0&&amtreturn==0){
						    	if(collect>Integer.parseInt(totamt.getText().toString())){
						    		db.UpdateDesc(Login.UID, ReportPayment.SpinnerChoice, ""+(collect-Long.parseLong(totamt.getText().toString())), "0");
						    	}
						    	else if(collect<Integer.parseInt(totamt.getText().toString())){
						    		db.UpdateDesc(Login.UID, ReportPayment.SpinnerChoice, "0",""+(Long.parseLong(totamt.getText().toString())- collect));
						    	}
						    	else
						    	{
						    		db.UpdateDesc(Login.UID, ReportPayment.SpinnerChoice, "0","0");
						    	}
						    }
			    	check = db.insertPaymentDetails(Login.UID,ReportPayment.SpinnerChoice,totamt.getText().toString(),"0",dt.getText().toString(),desc.getText().toString());
			    }
			 	if(check>0)
			 	{	
				Toast.makeText(this, "Successfully Inserted",Toast.LENGTH_LONG).show();
				db.close();
				Intent i = new Intent(this,Home.class);
				startActivity(i);
			 	}
			 	else
			 	{
			 		Toast.makeText(this, "Data Not Inserted",Toast.LENGTH_LONG).show();
			 	}
			}
		break;
		case R.id.can:
		finish();
		break;	
		default:
		break;
		}
	}
	public Dialog onCreateDialog(int id) {
		switch(id){
		case DATE_PICKER_DIALOG:
			Calendar c = Calendar.getInstance();
			DatePickerDialog dpd = new DatePickerDialog(PaymentDetailsActivity.this,mDateListener,(int)c.get(Calendar.YEAR),(int)c.get(Calendar.MONTH),(int)c.get(Calendar.DAY_OF_MONTH));
			return dpd;
		}
		return null;
	}	
DatePickerDialog.OnDateSetListener mDateListener = new DatePickerDialog.OnDateSetListener() {
		String dayOfMonthTemp,monthOfYearTemp;
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			if(dayOfMonth<10)
				dayOfMonthTemp = "0"+dayOfMonth;
			else
				dayOfMonthTemp = ""+dayOfMonth;
				//dt.setText(year+"-"+(monthOfYear+1)+"-"+"0"+dayOfMonth);
			if(monthOfYear<9)
				monthOfYearTemp = "0"+(monthOfYear+1);
			else
				monthOfYearTemp = ""+(monthOfYear+1);
			//Toast.makeText(PaymentDetailsActivity.this, "Selected Date="+Integer.toString(year)+" Mon= "+Integer.toString(monthOfYear)+" Day= "+Integer.toString(dayOfMonth),Toast.LENGTH_LONG).show();
			dt.setText(year+"-"+monthOfYearTemp+"-"+dayOfMonthTemp);
		}
	};
}