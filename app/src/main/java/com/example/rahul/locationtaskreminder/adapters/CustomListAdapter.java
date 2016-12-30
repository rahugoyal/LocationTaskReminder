package com.example.rahul.locationtaskreminder.adapters;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.rahul.locationtaskreminder.R;
import com.example.rahul.locationtaskreminder.pojos.ItemPojo;
import com.venmo.view.TooltipView;

import java.util.List;

/**
 * Created by Rahul on 12/27/2016.
 */

public class CustomListAdapter extends BaseAdapter {
    private Context mContext;
    private List<ItemPojo> pojoList;
    TooltipView mTooltip;

    public CustomListAdapter(Context context, List<ItemPojo> pojoList) {
        mContext = context;
        this.pojoList = pojoList;
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
    public View getView(int position, View convertView, ViewGroup parent) {

        View v = convertView;

        if (v == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(mContext);
            v = vi.inflate(R.layout.custom_list_item, null);
        }

        ItemPojo item = pojoList.get(position);

        if (item != null) {
            TextView name = (TextView) v.findViewById(R.id.tv_list_item_name);
            TextView description = (TextView) v.findViewById(R.id.tv_list_item_description);
            ImageButton locationBtn = (ImageButton) v.findViewById(R.id.ib_list_item_location);
            mTooltip = (TooltipView) v.findViewById(R.id.tooltip);
            mTooltip.setText("Kothaguda, Hyderabad");

            if (name != null) {
                name.setText(item.getName());
            }

            if (description != null) {
                description.setText(item.getDescription());
            }

            locationBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (mTooltip.isShown()) {
                        mTooltip.setVisibility(View.GONE);

                    } else {
                        mTooltip.setVisibility(View.VISIBLE);

                    }
                }
            });

        }

        return v;
    }

}
