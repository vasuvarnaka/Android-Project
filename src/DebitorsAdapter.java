package com.bluebirdsols.moneymonster;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

class DebitorsAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    

    public DebitorsAdapter(Context context) {
        mInflater = LayoutInflater.from(context);
    }

    public int getCount() {
       return Debitors.frndName.size();
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
            convertView = mInflater.inflate(R.layout.row1, null);
            holder = new ViewHolder();
            holder.t1=(TextView)convertView.findViewById(R.id.frndname);
            holder.t2=(TextView)convertView.findViewById(R.id.frndnametxt);
            holder.t3=(TextView)convertView.findViewById(R.id.amt);
            holder.t4=(TextView)convertView.findViewById(R.id.amttxt);            
            
           // label.setText(month[position]);
       

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.t1.setText("Friend Name ");
        holder.t2.setText(Debitors.frndName.get(position));
        holder.t3.setText("Amount	        ");
        holder.t4.setText(Debitors.Amt.get(position));        
        return convertView;
    }

    class ViewHolder {
    	TextView t1;
    	TextView t2;
    	TextView t3;
    	TextView t4;    	
    }
}