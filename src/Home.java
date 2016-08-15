package com.bluebirdsols.moneymonster;



import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
public class Home extends Activity implements OnClickListener{
	ImageView reportpayment,reportbill,debitors,creditors,addfriend,billingdetails,paymentdetails;
	long collect=0,amtreturn=0;
	TextView debttxt,credittxt;
	DBSharing db;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.main);
	        debttxt = (TextView)findViewById(R.id.debittxt);
	        credittxt = (TextView)findViewById(R.id.credittxt);
	        reportpayment = (ImageView)findViewById(R.id.reportpayment);
	        reportbill = (ImageView)findViewById(R.id.reportbill);
	        debitors = (ImageView)findViewById(R.id.debitors);
	        creditors = (ImageView)findViewById(R.id.creditors);
	        addfriend = (ImageView)findViewById(R.id.addfriend);
	        billingdetails = (ImageView)findViewById(R.id.billingdetails);
	        paymentdetails = (ImageView)findViewById(R.id.paymentdetails);
	        reportpayment.setOnClickListener(this);
	        reportbill.setOnClickListener(this);
	        debitors.setOnClickListener(this);
	        creditors.setOnClickListener(this);
	        addfriend.setOnClickListener(this);
	        billingdetails.setOnClickListener(this);
	        paymentdetails.setOnClickListener(this);
	        db=new DBSharing(this);
	        db.open();
	        Cursor c = DBSharing.db.rawQuery("SELECT collect,return FROM desc WHERE username = ?;",
	   			 new String [] {Login.UID});
	   	 if(c!=null)
	   	 {					
	   		 c.moveToFirst();							 
	   		      do {                        
	       try
	       {
	   	 int col = c.getColumnIndexOrThrow("collect");
	   	 collect = collect + Long.parseLong(c.getString(col));
	   	 int retn = c.getColumnIndexOrThrow("return");
	   	 amtreturn = amtreturn + Long.parseLong(c.getString(retn));
	       }
	   	 catch (Exception e) {
	   		// TODO: handle exception
	   		   		 
	   	}
	   		 }while (c.moveToNext());				 
	   	 }	      		 				 
	       c.close();
	       db.close();
	       debttxt.setText(""+collect);
	       credittxt.setText(""+amtreturn);
	    }
	public void onClick(View v) {
		switch(v.getId())
		{
		case R.id.reportpayment:
		Intent rp = new Intent(this,ReportPayment.class);
		startActivity(rp);
		break;
		case R.id.reportbill:
		Intent rb = new Intent(this,ReportBill.class);
		startActivity(rb);
		break;
		case R.id.debitors:
		Intent dbt = new Intent(this,Debitors.class);
		startActivity(dbt);
		break;
		case R.id.creditors:
		Intent cdt = new Intent(this,Creditors.class);
		startActivity(cdt);
		break;
		case R.id.addfriend:
		Intent af = new Intent(this,AddFriend.class);
		startActivity(af);
		break;
		case R.id.billingdetails:
		Intent bd = new Intent(this,BillingDetails.class);
		startActivity(bd);
		break;
		case R.id.paymentdetails:
		Intent pd = new Intent(this,PaymentDetails.class);
		startActivity(pd);
		break;
		}
		// TODO Auto-generated method stub		
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if (keyCode == KeyEvent.KEYCODE_BACK) {
//	        moveTaskToBack(true);
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
}