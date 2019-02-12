package com.amazonaws.compute.ap_northeast_2.ec2_54_180_85_255.contactbackup.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amazonaws.compute.ap_northeast_2.ec2_54_180_85_255.contactbackup.R;
import com.amazonaws.compute.ap_northeast_2.ec2_54_180_85_255.contactbackup.data.AddressOne;

import java.util.ArrayList;

public class AddressAdapter extends BaseAdapter {
    ArrayList<AddressOne> addressOnes;

    public AddressAdapter(ArrayList<AddressOne> addressOnes) {
        this.addressOnes = addressOnes;
    }

    @Override
    public int getCount() {
        return addressOnes.size();
    }

    @Override
    public Object getItem(int position) {
        return addressOnes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder = new Holder();
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.addresslv_layout, parent, false);
            holder.lv_tv = convertView.findViewById(R.id.lv_tv);
            holder.lv_tv_num = convertView.findViewById(R.id.lv_tv_num);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        AddressOne item = (AddressOne)getItem(position);
        holder.lv_tv.setText(item.getName());
        holder.lv_tv_num.setText(item.getNumber());
        return convertView;

    }

    private class Holder {
        TextView lv_tv;
        TextView lv_tv_num;
    }
}
