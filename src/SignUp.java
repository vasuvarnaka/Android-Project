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

public class SignUp  extends Activity implements OnClickListener{
	Button signup , cancel;
	EditText username , pwd , confirmpwd;
	DBSharing db;
	boolean flag;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.signup);
	        signup = (Button)findViewById(R.id.signup);
	        cancel = (Button)findViewById(R.id.cancell);
	        signup.setOnClickListener(this);
	        cancel.setOnClickListener(this);
	        username = (EditText)findViewById(R.id.username);
	        pwd = (EditText)findViewById(R.id.pwd);
	        confirmpwd = (EditText)findViewById(R.id.confirmpwd);
	        db=new DBSharing(this);
	       
	 }
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.signup:
			if(username.getText().toString().equals("")||pwd.getText().toString().equals("")||(confirmpwd.getText().toString().equals("")))
			{
				AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setMessage("Required All Fields");
				alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
										
					}
				});
				alert.show();
				if(username.getText().toString().equals(""))
				username.requestFocus();
				else if(pwd.getText().toString().equals(""))
				pwd.requestFocus();
				else if(confirmpwd.getText().toString().equals(""))
				confirmpwd.requestFocus();	
			}
			else if(!pwd.getText().toString().equals(confirmpwd.getText().toString()))
			{
				AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setMessage("Password Not Matched");
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
				 flag=false;
				 db.open();
				
					 long check = db.insertSignup(username.getText().toString(),pwd.getText().toString());
	         			if(check>0)
	         			{         				
	         				AlertDialog.Builder alert = new AlertDialog.Builder(this);
	              			alert.setMessage("Successfully Registered");
	              			alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	              				public void onClick(DialogInterface dialog, int which) {
	              					Intent i = new Intent(SignUp.this,Login.class);
	              					startActivity(i);
	              				}
	              			});
	              			alert.show();
	         			}else {
	         				AlertDialog.Builder alert = new AlertDialog.Builder(this);
	              			alert.setMessage("Username Already Exist");
	              			alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	              				public void onClick(DialogInterface dialog, int which) {
	              					username.setText("");
	              					pwd.setText("");
	              					confirmpwd.setText("");
	              				}
	              			});
	              			alert.show();
	         				 }												 
			}
			catch(Exception e){
				AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setMessage("Username Already Exist");
				alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
										
					}
				});
				alert.show();
			}}
			db.close();
			break;
		case R.id.cancell:
			Intent i = new Intent(this,Login.class);
			startActivity(i);
			break;
		}
	}
}