package com.bluebirdsols.moneymonster;

import java.util.ArrayList;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class BillingDetails extends Activity implements OnClickListener{
	EditText fromDate,toDate;
	private static final  int FromDATE_PICKER_DIALOG=1;
	private static final  int ToDATE_PICKER_DIALOG=2;
	DBSharing db;
	Button submit,cancel;
	Spinner spin1;
	static ArrayList<String> itemDescription=new ArrayList<String>();
	static ArrayList<String> date=new ArrayList<String>();
	static ArrayList<String> amount=new ArrayList<String>();
	String[] SpinArray = {"","ALL","During Period"};
	static String SpinnerChoice;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billingdetails);
        submit = (Button)findViewById(R.id.submit);
        submit.setOnClickListener(this);
        cancel = (Button)findViewById(R.id.cancelbtn);
        cancel.setOnClickListener(this);
        fromDate = (EditText)findViewById(R.id.fromdate);
        toDate = (EditText)findViewById(R.id.todate);
        fromDate.setOnClickListener(this);
        toDate.setOnClickListener(this);
        fromDate.setInputType(0);
        toDate.setInputType(0);
        db=new DBSharing(this);
        spin1=(Spinner)findViewById(R.id.spin_billingdetails);        
        ArrayAdapter<String> bb=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,SpinArray);
		bb.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spin1.setAdapter(bb);
		spin1.setOnItemSelectedListener(
                new OnItemSelectedListener() {
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {                    
                    SpinnerChoice = spin1.getItemAtPosition(position).toString();
                    if(SpinnerChoice.equals("ALL"))
                    {
                    //	Intent i = new Intent(PaymentDetails.this,BillingDetailsList.class);
                    //	startActivity(i);
                    	submit.setVisibility(0);
                    	cancel.setVisibility(0);
                                   	  
                    	 
                    	fromDate.setVisibility(4);
                   	toDate.setVisibility(4);
                    }
                    else if(SpinnerChoice.equals("During Period"))
                    {
                    	submit.setVisibility(0);
                    	cancel.setVisibility(0);
                  
                    	fromDate.setVisibility(0);
                    	toDate.setVisibility(0);
                    //	fromDate.setText("");
                    //	toDate.setText("");
                    }
                    else
                    {
                    	submit.setVisibility(4);
                    	cancel.setVisibility(4);
                  
                    	fromDate.setVisibility(4);
                    	
                    	toDate.setVisibility(4);
                    }
                   
                    }

                    public void onNothingSelected(AdapterView<?> parent) {
                       
                    }
                }); 
    }
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.fromdate:
			showDialog(FromDATE_PICKER_DIALOG);
			break;
		case R.id.todate:
			showDialog(ToDATE_PICKER_DIALOG);
			break;
		case R.id.submit:
			if(SpinnerChoice.equals("During Period"))
			{		if(fromDate.getText().toString().equals("")||toDate.getText().toString().equals(""))
			{
				AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setMessage("All Fields are Required");
				alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {					
					}
			});
			alert.show(); 
			}
			else
			{
				 itemDescription.clear();
				 date.clear();
				 amount.clear();
			        db.open();
			   	 Cursor c = DBSharing.db.rawQuery("SELECT itemdesription,date,amount FROM billingdetails WHERE username = ? AND date>=? AND date<=?;",
						 new String [] {Login.UID,fromDate.getText().toString(),toDate.getText().toString()});		
				 if(c!=null)
				 {					
					 c.moveToFirst();							 
					      do {                        
			    try
			    {
				 int itemdesc = c.getColumnIndexOrThrow("itemdesription");
				 itemDescription.add(c.getString(itemdesc));
				 int dateIndex = c.getColumnIndexOrThrow("date");
				 date.add(c.getString(dateIndex));
				 int amtIndex = c.getColumnIndexOrThrow("amount");
				 amount.add(c.getString(amtIndex));
			    }
				 catch (Exception e) {
					// TODO: handle exception
					 AlertDialog.Builder alert = new AlertDialog.Builder(this);
							alert.setMessage("You have no Records on those Dates");
							alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									
								}
						});
						alert.show(); 						
				}
					 }while (c.moveToNext());					     
				 }
			    else
			    {
			    	AlertDialog.Builder alert = new AlertDialog.Builder(this);
						alert.setMessage("You have no Records on those Dates");
						alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {								
							}
					});
					alert.show(); 
			    }			
			    c.close();
			    db.close();
			}
			}
			else if(SpinnerChoice.equals("ALL"))
			{			
				 itemDescription.clear();
				 date.clear();
				 amount.clear();
			        db.open();
			   	 Cursor c = DBSharing.db.rawQuery("SELECT itemdesription,date,amount FROM billingdetails WHERE username = ?;",
						 new String [] {Login.UID});		
				 if(c!=null)
				 {					
					 c.moveToFirst();							 
					      do {                        
			    try
			    {
				 int itemdesc = c.getColumnIndexOrThrow("itemdesription");
				 itemDescription.add(c.getString(itemdesc));
				 int dateIndex = c.getColumnIndexOrThrow("date");
				 date.add(c.getString(dateIndex));
				 int amtIndex = c.getColumnIndexOrThrow("amount");
				 amount.add(c.getString(amtIndex));
			    }
				 catch (Exception e) {
					// TODO: handle exception
					 AlertDialog.Builder alert = new AlertDialog.Builder(this);
						alert.setMessage("You have no Records");
						alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {								
							}
					});
					alert.show();
				}
					 }while (c.moveToNext());					     
				 }
			    else
			    {
			    	AlertDialog.Builder alert = new AlertDialog.Builder(this);
						alert.setMessage("You have no Records");
						alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {								
							}
					});
					alert.show(); 
			    }			
			    c.close();
			    db.close();
			}
			
			    if(itemDescription.size()>0){
			    	 Intent i = new Intent(this,BillingDetailsList.class);
					    startActivity(i);
			    }			
			break;
		case R.id.cancelbtn:
			Intent i = new Intent(this,Home.class);
			startActivity(i);
			break;
		}
	}
	public Dialog onCreateDialog(int id) {
		switch(id){
		case FromDATE_PICKER_DIALOG:
			Calendar c = Calendar.getInstance();
			DatePickerDialog dpd = new DatePickerDialog(this,mDateListener,(int)c.get(Calendar.YEAR),(int)c.get(Calendar.MONTH),(int)c.get(Calendar.DAY_OF_MONTH));
			return dpd;
		case ToDATE_PICKER_DIALOG:
			Calendar c2 = Calendar.getInstance();
			DatePickerDialog dpd2 = new DatePickerDialog(this,mDateListener2,(int)c2.get(Calendar.YEAR),(int)c2.get(Calendar.MONTH),(int)c2.get(Calendar.DAY_OF_MONTH));
			return dpd2;
		}
		return null;
	}	
DatePickerDialog.OnDateSetListener mDateListener = new DatePickerDialog.OnDateSetListener() {
	String dayOfMonthTemp,monthOfYearTemp;
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			//Toast.makeText(PaymentDetailsActivity.this, "Selected Date="+Integer.toString(year)+" Mon= "+Integer.toString(monthOfYear)+" Day= "+Integer.toString(dayOfMonth),Toast.LENGTH_LONG).show();
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
			fromDate.setText(year+"-"+monthOfYearTemp+"-"+dayOfMonthTemp);
			
		}
	};
DatePickerDialog.OnDateSetListener mDateListener2 = new DatePickerDialog.OnDateSetListener() {
	String dayOfMonthTemp,monthOfYearTemp;
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			//Toast.makeText(PaymentDetailsActivity.this, "Selected Date="+Integer.toString(year)+" Mon= "+Integer.toString(monthOfYear)+" Day= "+Integer.toString(dayOfMonth),Toast.LENGTH_LONG).show();
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
			toDate.setText(year+"-"+monthOfYearTemp+"-"+dayOfMonthTemp);
			//toDate.setText(dayOfMonth+"/"+(monthOfYear+1)+"/"+year);
		}
	};
}
