package com.bluebirdsols.moneymonster;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

public class ReportBill extends Activity implements OnClickListener{
	private static final  int DATE_PICKER_DIALOG=1;
	EditText desc,totamt,dt;
	DBSharing db;
	long check;
	Button s,c;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.billpayment);
        desc = (EditText)findViewById(R.id.description);
        totamt = (EditText)findViewById(R.id.billtotamt);
        dt = (EditText)findViewById(R.id.date);
        dt.setOnClickListener(this);
        db=new DBSharing(this);
        dt.setInputType(0);
        s = (Button)findViewById(R.id.savebill);
        c = (Button)findViewById(R.id.billcancel);
        s.setOnClickListener(this);
        c.setOnClickListener(this);
    }
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.date:
		showDialog(DATE_PICKER_DIALOG);
		break;
		case R.id.savebill:
			 db.open();
			 if(desc.getText().toString().equals("")||totamt.getText().toString().equals("")||(dt.getText().toString().equals("")))
				{
				 	AlertDialog.Builder alert = new AlertDialog.Builder(this);
					alert.setMessage("Required All Fields");
					alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							if(desc.getText().toString().equals(""))
							desc.requestFocus();
							else if(totamt.getText().toString().equals(""))
							totamt.requestFocus();
							else if(dt.getText().toString().equals(""))
							dt.requestFocus();
						}
					});
					alert.show();
				}
				else
				{
				check =	db.insertReportBill(Login.UID,desc.getText().toString(),totamt.getText().toString(),dt.getText().toString());
				if(check>0)
				{
					Toast.makeText(this, "Bill Details Added Successfully",Toast.LENGTH_LONG).show();
					db.close();
					Intent i = new Intent(this,Home.class);
					startActivity(i);
				}
				else
				{
					AlertDialog.Builder alert = new AlertDialog.Builder(this);
					alert.setMessage("Data Not Inserted");
					alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
							//desc.requestFocus();				
						}
					});
					alert.show();
				}
				}
		break;
		case R.id.billcancel:
			finish();
			break;
	}
	}
	public Dialog onCreateDialog(int id) {
		switch(id){
		case DATE_PICKER_DIALOG:
			Calendar c = Calendar.getInstance();
			DatePickerDialog dpd = new DatePickerDialog(ReportBill.this,mDateListener,(int)c.get(Calendar.YEAR),(int)c.get(Calendar.MONTH),(int)c.get(Calendar.DAY_OF_MONTH));
			return dpd;
		}
		return null;
	}	
DatePickerDialog.OnDateSetListener mDateListener = new DatePickerDialog.OnDateSetListener() {
		
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			String dayOfMonthTemp,monthOfYearTemp;
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
			dt.setText(year+"-"+monthOfYearTemp+"-"+dayOfMonthTemp);
		}
	};
}