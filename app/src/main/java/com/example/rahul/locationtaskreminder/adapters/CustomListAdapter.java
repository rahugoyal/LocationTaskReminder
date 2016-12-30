package com.example.rahul.locationtaskreminder.adapters;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.rahul.locationtaskreminder.R;
import com.example.rahul.locationtaskreminder.pojos.ItemPojo;

import java.util.List;

/**
 * Created by Rahul on 12/27/2016.
 */

public class CustomListAdapter extends BaseAdapter {
    private Context mContext;
    private List<ItemPojo> pojoList;
    View v;


    public CustomListAdapter(Context context, List<ItemPojo> pojoList) {
        mContext = context;
        this.pojoList = pojoList;
    }

    public View getV() {
        return v;
    }

    @Override
    public int getCount() {
        return pojoList.size();
    }

    @Override
    public Object getItem(int i) {
        return pojoList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {

        v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(R.layout.custom_list_item, null);
        }

        ItemPojo item = pojoList.get(position);

        if (item != null) {
            final TextView name = (TextView) v.findViewById(R.id.tv_list_item_name);
            TextView description = (TextView) v.findViewById(R.id.tv_list_item_description);
            final TextView tooltip = (TextView) v.findViewById(R.id.tooltip);
            if (name != null) {
                name.setText(item.getName());
            }

            if (description != null) {
                description.setText(item.getDescription());
            }

            v.findViewById(R.id.ib_list_item_location).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (tooltip.getText().equals(""))
                        tooltip.setText("Kondapur");

                    else
                        tooltip.setText("");

                }
            });


        }

        return v;
    }

}
