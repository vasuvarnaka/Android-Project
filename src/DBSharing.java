package com.bluebirdsols.moneymonster;

import android.content.ContentValues;
import android.content.Context;
//import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//import android.widget.Toast;

public class DBSharing {
	public static final String KEY_AmtReceived="amountreceived";
	public static final String KEY_AmtPaid="amountpaid";
	public static final String KEY_InsertTime="InsertTime";
	public static final String KEY_Desc="descroption";
	public static final String KEY_Uid="username";
	public static final String KEY_Pwd="password";
	public static final String KEY_FrndName="friendname";
	public static final String KEY_CntNum="contactnumber";
	public static final String KEY_ItemDesc="itemdesription";
	public static final String KEY_Date="date";
	public static final String KEY_TotAmt="amount";
	public static final String KEY_Collect="collect";
	public static final String KEY_Return="return";
	static String DATABASE_Name="moneymonster";
	static String DATABASE_Tablelogin="login";
	static String DATABASE_Tablefriend="friendslist";
	static String DATABASE_Tablepayment="paymentdetails";
	static String DATABASE_Tablebilling="billingdetails";
	static String DATABASE_Desc="desc";
	private static final int DATABASE_VERSION=4;
	final  Context context;	
	static Databasehelper DBhelper;
	static SQLiteDatabase db;
 static String DATABASE_CREATE1="create table login(username  varchar2(25) PRIMARY KEY ,"+"password varchar2(15)); ";
 static String DATABASE_CREATE2="create table friendslist(username varchar2(15),"+"friendname varchar2(15)  , "+"contactnumber varchar2(15) );";
 static String DATABASE_CREATE3="create table paymentdetails(username varchar2(15),"+"friendname varchar2(15)  , "+"amountreceived varchar2(15),"+"amountpaid long,"+"date varchar2(15),"+"descroption varchar2(15));";
 static String DATABASE_CREATE4="create table billingdetails(username varchar2(15),"+"itemdesription varchar2(15)  , "+"date varchar2(15),"+"amount varchar2(15));";
 static String DATABASE_CREATE5="create table desc(username varchar2(15),"+"friendname varchar2(15),"+"collect varchar2(15),"+"return varchar2(15));";
 static String[] statements = new String[]{DATABASE_CREATE1, DATABASE_CREATE2,DATABASE_CREATE3,DATABASE_CREATE4,DATABASE_CREATE5};
	public DBSharing(Context cont){
		this.context=cont;
		DBhelper=new Databasehelper(context);
	}
	static class Databasehelper extends SQLiteOpenHelper {

		Databasehelper(Context context) {
			super(context,DATABASE_Name,null,DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}
		public void onCreate(SQLiteDatabase db){
			for(String sql : statements){
			    db.execSQL(sql);
			}
			//db.execSQL(DATABASE_CREATE);
			//db.execSQL(DATABASE_CREATE2);
		}
		public void onUpgrade(SQLiteDatabase db,int oldVersion,int newVersion){
			db.execSQL("DROP TABLE IF EXISTS moneymonster");
		//	db.execSQL("DROP TABLE IF EXISTS sharingmomentVoice");
			onCreate(db);
		}
	
		
	}
		public DBSharing open() throws SQLException{
			db=DBhelper.getWritableDatabase();
			return this;
		}
		public void close(){
			DBhelper.close();
		}
		public long insertSignup(String Uid,String Pwd){
			ContentValues args=new ContentValues();
			args.put(KEY_Uid, Uid);
			args.put(KEY_Pwd, Pwd);
			return db.insert(DATABASE_Tablelogin,null,args);			
		}
		public long insertAddFrnd(String Uid,String FrndName,String CntNum){
			ContentValues args=new ContentValues();
			args.put(KEY_Uid, Uid);
			args.put(KEY_FrndName, FrndName);
			args.put(KEY_CntNum, CntNum);
			return db.insert(DATABASE_Tablefriend,null,args);			
		}
		public long UpdateDesc(String Uid,String FrndName,String collect,String amtreturn){
			ContentValues args=new ContentValues();
			args.put(KEY_Collect, collect);
			args.put(KEY_Return, amtreturn);
			return db.update(DATABASE_Desc, args, "username=? AND friendname=?", new String[] {Uid,FrndName});			
		}
		public long insertDesc(String Uid,String FrndName){
			ContentValues args=new ContentValues();
			args.put(KEY_Uid, Uid);
			args.put(KEY_FrndName, FrndName);
			args.put(KEY_Collect, "0");
			args.put(KEY_Return, "0");
			return db.insert(DATABASE_Desc,null,args);			
		}
		public long insertReportBill(String Uid,String desc,String totamt,String date){
			ContentValues args=new ContentValues();
			args.put(KEY_Uid, Uid);
			args.put(KEY_ItemDesc, desc);
			args.put(KEY_Date, date);
			args.put(KEY_TotAmt, totamt);
			return db.insert(DATABASE_Tablebilling,null,args);			
		}
		public long insertPaymentDetails(String Uid,String frnd,String amtrec,String amtpaid,String date,String desc){
			ContentValues args=new ContentValues();
			args.put(KEY_Uid, Uid);
			args.put(KEY_FrndName, frnd);
			args.put(KEY_AmtReceived, amtrec);
			args.put(KEY_AmtPaid, amtpaid);
			args.put(KEY_Date, date);
			args.put(KEY_Desc, desc);
			return db.insert(DATABASE_Tablepayment,null,args);			
		}	
}