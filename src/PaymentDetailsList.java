package com.bluebirdsols.moneymonster;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
//import android.widget.TextView;
import android.widget.TextView;

public class PaymentDetailsList extends Activity{
	DBSharing db;
	ListView lv1;

	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paymentdetailslist);            
       lv1=(ListView)findViewById(R.id.ListView05);
       lv1.setAdapter(new PaymentAdapter(this));
    }
	
	class PaymentAdapter extends BaseAdapter {
	    private LayoutInflater mInflater;
	    

	    public PaymentAdapter(Context context) {
	        mInflater = LayoutInflater.from(context);
	    }

	    public int getCount() {
	       return PaymentDetails.itemDescription.size();
	    }

	    public Object getItem(int position) {
	        return position;
	    }

	    public long getItemId(int position) {
	        return position;
	    }
	    public View getView(int position, View convertView, ViewGroup parent) {
	        ViewHolder holder;
	      
	        if (convertView == null) {
	            convertView = mInflater.inflate(R.layout.paymentadapter, null);
	            holder = new ViewHolder();
	            holder.t1=(TextView)convertView.findViewById(R.id.desclabel);
	            holder.t2=(TextView)convertView.findViewById(R.id.desctext);
	            holder.t3=(TextView)convertView.findViewById(R.id.datelabel);
	            holder.t4=(TextView)convertView.findViewById(R.id.datetext);
	            holder.t5=(TextView)convertView.findViewById(R.id.amountlabel);
	            holder.t6=(TextView)convertView.findViewById(R.id.amounttext);
	            
	           // label.setText(month[position]);
	       

	            convertView.setTag(holder);
	        } else {
	            holder = (ViewHolder) convertView.getTag();
	        }
	        holder.t1.setText("Description  ");
	        holder.t2.setText(PaymentDetails.itemDescription.get(position));
	        holder.t3.setText("Date	            ");
	        holder.t4.setText(PaymentDetails.date.get(position));        
	        holder.t5.setText("Amount	      ");
	        holder.t6.setText(PaymentDetails.amount.get(position));
	        return convertView;
	    }

	    class ViewHolder {
	    	TextView t1;
	    	TextView t2;
	    	TextView t3;
	    	TextView t4;
	    	TextView t5;
	    	TextView t6;
	    }
	}
}