package com.bluebirdsols.moneymonster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

class BillingAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    

    public BillingAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
       return BillingDetails.itemDescription.size();
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
            convertView = mInflater.inflate(R.layout.row4, null);
            holder = new ViewHolder();
            holder.t1=(TextView)convertView.findViewById(R.id.desclbl);
            holder.t2=(TextView)convertView.findViewById(R.id.desctxt);
            holder.t3=(TextView)convertView.findViewById(R.id.datelbl);
            holder.t4=(TextView)convertView.findViewById(R.id.datetxt);
            holder.t5=(TextView)convertView.findViewById(R.id.amountlbl);
            holder.t6=(TextView)convertView.findViewById(R.id.amounttxt);
            
           // label.setText(month[position]);
       

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.t1.setText("Description  ");
        holder.t2.setText(BillingDetails.itemDescription.get(position));
        holder.t3.setText("Date	        ");
        holder.t4.setText(BillingDetails.date.get(position));        
        holder.t5.setText("Amount	    ");
        holder.t6.setText(BillingDetails.amount.get(position));
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