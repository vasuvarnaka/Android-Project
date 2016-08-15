package com.bluebirdsols.moneymonster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

class CreditorsAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    

    public CreditorsAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
       return Creditors.frndName.size();
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
            convertView = mInflater.inflate(R.layout.row2, null);
            holder = new ViewHolder();
            holder.t1=(TextView)convertView.findViewById(R.id.frndnamecrd);
            holder.t2=(TextView)convertView.findViewById(R.id.frndnamecrdtxt);
            holder.t3=(TextView)convertView.findViewById(R.id.amtcrd);
            holder.t4=(TextView)convertView.findViewById(R.id.amtcrdtxt);            
            
           // label.setText(month[position]);
       

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.t1.setText("Friend Name ");
        holder.t2.setText(Creditors.frndName.get(position));
        holder.t3.setText("Amount	        ");
        holder.t4.setText(Creditors.Amt.get(position));        
        return convertView;
    }

    class ViewHolder {
    	TextView t1;
    	TextView t2;
    	TextView t3;
    	TextView t4;    	
    }
}