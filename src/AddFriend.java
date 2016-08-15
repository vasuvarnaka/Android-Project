package com.bluebirdsols.moneymonster;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class AddFriend extends Activity implements OnClickListener{
	EditText frndName ,phNo;
	Button ok ,cancel;
	DBSharing db;
	long check,check1;
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addfriend);
        frndName = (EditText)findViewById(R.id.frndname);
        phNo = (EditText)findViewById(R.id.phno);
        ok = (Button)findViewById(R.id.add);
        cancel = (Button)findViewById(R.id.cancel);
        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);
        db=new DBSharing(this);
    }
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.add:
			 db.open();
			
			 if(frndName.getText().toString().equals("")||phNo.getText().toString().equals(""))
				{
					AlertDialog.Builder alert = new AlertDialog.Builder(this);
					alert.setMessage("Required All Fields");
					alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
											
						}
					});
					alert.show();
				}
			 else
			 {
				 try
				 {
				 check = db.insertAddFrnd(Login.UID,frndName.getText().toString(),phNo.getText().toString());
				 check1 = db.insertDesc(Login.UID,frndName.getText().toString());
				 }catch (Exception e) {
					// TODO: handle exception				 
				}
			 }
			 	if(check>0&&check1>0){
				AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setMessage("Added Successfully.\nDo You Want To Add Another One ?");
				alert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					frndName.setText("");
					phNo.setText("");
					}
				});
				alert.setNegativeButton("NO", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
					Intent in = new Intent(AddFriend.this,Home.class);
					startActivity(in);
					}
				});
				alert.show();
			 }
			 else
			 {
				 	AlertDialog.Builder alert = new AlertDialog.Builder(this);
					alert.setMessage("Data Not Inserted");
					alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog, int which) {
						frndName.setText("");
						phNo.setText("");
						}
					});					
			 }
			 db.close();
			break;
		case R.id.cancel:
			Intent i = new Intent(this,Home.class);
			startActivity(i);
			break;
		}
	}
}