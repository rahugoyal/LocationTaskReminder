package com.example.rahul.locationtaskreminder.fragments;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.rahul.locationtaskreminder.R;
import com.example.rahul.locationtaskreminder.interfaces.Communicator;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocomplete;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

/**
 * Created by Rahul on 12/25/2016.
 */

public class AddTask extends Fragment {
    private LinearLayout mLlsearch;

    private TextView mTvLocation, mTvSeekbar;
    private Communicator mCommunicator;
    private SeekBar mSeekbar;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_task_fragment, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        mLlsearch = (LinearLayout) view.findViewById(R.id.ll_add_task);
        mTvLocation = (TextView) view.findViewById(R.id.tv_location_task);
        mTvSeekbar = (TextView) view.findViewById(R.id.tv_seekbar_task);
        mSeekbar = (SeekBar) view.findViewById(R.id.seekbar_add_task);
        mTvSeekbar.setText("0");

        mSeekbar.setMax(5);
        mSeekbar.setProgress(0);

        mSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                mTvSeekbar.setText("" + i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        mCommunicator = (Communicator) getActivity();
        mLlsearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findPlace();

            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
                Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                LatLng latlng = place.getLatLng();
                List<Address> addresses = null;
                try {
                    addresses = geocoder.getFromLocation(latlng.latitude, latlng.longitude, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String address = addresses.get(0).getLocality();
                mTvLocation.setText(place.getName() + ", " + address);

                mCommunicator.communicate(latlng.latitude, latlng.longitude);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                Log.i("error", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
            }
        }
    }
}
