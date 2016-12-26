package com.example.rahul.locationtaskreminder.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rahul.locationtaskreminder.R;
import com.example.rahul.locationtaskreminder.interfaces.Communicator;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by Rahul on 12/25/2016.
 */

public class AddTask extends Fragment {
    private LinearLayout mLlsearch;
    private EditText mEtName, mEtDescription;
    private TextView mTvLocation;
    private Communicator mCommunicator;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_task_fragment, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        mLlsearch = (LinearLayout) view.findViewById(R.id.ll_add_task);
        mEtName = (EditText) view.findViewById(R.id.et_add_task_name);
        mEtDescription = (EditText) view.findViewById(R.id.et_add_task_description);
        mTvLocation = (TextView) view.findViewById(R.id.tv_location_task);
        mCommunicator = (Communicator) getActivity();
        mLlsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findPlace();

            }
        });

    }

    public void findPlace() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(getActivity());
            startActivityForResult(intent, 9);
        } catch (GooglePlayServicesRepairableException e) {
        } catch (GooglePlayServicesNotAvailableException e) {
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 9) {
            if (resultCode == RESULT_OK) {
                Place place = PlaceAutocomplete.getPlace(getActivity(), data);
                mTvLocation.setText(place.getAddress() + "");
                LatLng latlng = place.getLatLng();
                mCommunicator.communicate(latlng.latitude,latlng.longitude);
                Log.e("latlong", place.getLatLng() + "");
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                Log.i("error", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
            }
        }
    }
}
