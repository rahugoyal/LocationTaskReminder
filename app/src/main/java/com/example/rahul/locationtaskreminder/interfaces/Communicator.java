package com.example.rahul.locationtaskreminder.interfaces;

import com.example.rahul.locationtaskreminder.pojos.ItemPojo;

/**
 * Created by Rahul on 12/26/2016.
 */

public interface Communicator {
    public void communicate(double lat, double lon);

    public void alertCall(ItemPojo itemPojo);
    public void refreshData(int pos);
}
