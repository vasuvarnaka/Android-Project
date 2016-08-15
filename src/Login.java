package com.bluebirdsols.moneymonster;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class Login  extends Activity implements OnClickListener{
	Button signin , signup;
	EditText uid,pwd;
	DBSharing db;
	static String UID;
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.login);
	        signin = (Button)findViewById(R.id.signin);
	        signup = (Button)findViewById(R.id.signupp);
	        signin.setOnClickListener(this);
	        signup.setOnClickListener(this);
	        uid = (EditText)findViewById(R.id.uid);
	        pwd = (EditText)findViewById(R.id.password);
	        uid.setOnClickListener(this);
	        pwd.setOnClickListener(this);
	        db=new DBSharing(this);
	 }
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case R.id.signin:
			if(uid.getText().toString().equals("")||pwd.getText().toString().equals(""))
			{
				AlertDialog.Builder alert = new AlertDialog.Builder(this);
				alert.setMessage("Required All Fields");
				alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
										
					}
				});
				alert.show();
				//uid.setText("");
				//pwd.setText("");
				if(uid.getText().toString().equals(""))
				uid.requestFocus();
				else if(pwd.getText().toString().equals(""))
				pwd.requestFocus();
					
			}
			else{
				 db.open();
				 Cursor c = DBSharing.db.rawQuery("SELECT username,password" + " FROM  login WHERE username = ?;",
						 new String [] {uid.getText().toString()});		
				 if(c!=null)
				 {					
					 c.moveToFirst();							 
					      do {                        
                 try
                 {
				 int username = c.getColumnIndexOrThrow("username");
				 String userid = c.getString(username);
                 int password = c.getColumnIndexOrThrow("password");
                 String userpwd = c.getString(password);
                 if(uid.getText().toString().equals(userid) && pwd.getText().toString().equals(userpwd))
                 {
                	 UID = uid.getText().toString();
                	 Intent in = new Intent(this,Home.class);
                	 startActivity(in);
                 }
                 else
                 {
                	 AlertDialog.Builder alert = new AlertDialog.Builder(this);
      				alert.setMessage("Invalid Username/Password");
      				alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
      					public void onClick(DialogInterface dialog, int which) {
      						uid.setText("");
      						pwd.setText("");      						
      					}
     				});
     				alert.show(); 
                 }
                 }
				 catch (Exception e) {
					// TODO: handle exception
					 AlertDialog.Builder alert = new AlertDialog.Builder(this);
	      				alert.setMessage("Invalid Username/Password");
	      				alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
	      					public void onClick(DialogInterface dialog, int which) {
	      						uid.setText("");
	      						pwd.setText("");	      						
	      					}
	     				});
	     				alert.show(); 
					 
				}
					 }while (c.moveToNext());				 
				 }
                 else
                 {
                	 AlertDialog.Builder alert = new AlertDialog.Builder(this);
     				alert.setMessage("Invalid Username/Password");
     				alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
     					public void onClick(DialogInterface dialog, int which) {
     						uid.setText("");
     						pwd.setText("");     						
     					}
     				});
     				alert.show(); 
                 }				 				 
                 c.close();
				db.close();
				
			}
			break;
		case R.id.signupp:
			Intent i = new Intent(this,SignUp.class);
			startActivity(i);
			break;
		}
	}
}