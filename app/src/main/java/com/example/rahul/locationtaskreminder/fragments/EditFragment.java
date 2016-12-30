package com.example.rahul.locationtaskreminder.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.rahul.locationtaskreminder.Constants.Constant;
import com.example.rahul.locationtaskreminder.R;
import com.example.rahul.locationtaskreminder.pojos.ItemPojo;
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
 * Created by Rahul on 12/29/2016.
 */

public class EditFragment extends Fragment {
    ItemPojo itemPojo;
    EditText mEtname, mEtdescription;
    TextView mTvLocation;
    ImageButton mIbUpdate;
    LinearLayout mLlLocation;
    int fragmentStatus = 0;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_fragment, container, false);

        if (getArguments() != null) {
            itemPojo = (ItemPojo) getArguments().getSerializable("pojo");
            fragmentStatus = getArguments().getInt("status");
        }
        initializeViews(view);

        return view;
    }

    private void initializeViews(View view) {
        mEtname = (EditText) view.findViewById(R.id.et_edit_name);
        mEtdescription = (EditText) view.findViewById(R.id.et_edit_description);
        mTvLocation = (TextView) view.findViewById(R.id.tv_location_edit);
        mIbUpdate = (ImageButton) view.findViewById(R.id.ib_update_edit);
        mLlLocation = (LinearLayout) view.findViewById(R.id.ll_edit);
        mLlLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                findPlace();
            }
        });

        if (itemPojo != null) {
            mEtname.setText(itemPojo.getName());
            mEtdescription.setText(itemPojo.getDescription());
            mTvLocation.setText(itemPojo.getLocation());
        }
        mIbUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (fragmentStatus == 1) {
                    if (validateData()) {
                        TabLayout tableLayout = (TabLayout) getActivity().findViewById(R.id.tabLayout);
                        tableLayout.setVisibility(View.VISIBLE);
                        ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);
                        viewPager.setVisibility(View.VISIBLE);
                        ItemPojo pojo = new ItemPojo();
                        pojo.setId(itemPojo.getId());
                        pojo.setName(mEtname.getText().toString());
                        pojo.setDescription(mEtdescription.getText().toString());
                        pojo.setLocation(mTvLocation.getText().toString());
                        pojo.setTaskStatus("pending");
                        Constant.dbHelper.updateTask(pojo);
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                } else if (fragmentStatus == 2) {
                    if (validateData()) {
                        alert();
                    }
                }
            }
        });
    }

    public void findPlace() {
        try {
            Intent intent =
                    new PlaceAutocomplete.IntentBuilder(PlaceAutocomplete.MODE_FULLSCREEN)
                            .build(getActivity());
            startActivityForResult(intent, 10);
        } catch (GooglePlayServicesRepairableException e) {
        } catch (GooglePlayServicesNotAvailableException e) {
        }
    }

    private void alert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Warning!");
        alertDialog.setMessage("This task will move to Pending Task! \nDo you want to do this?");
        alertDialog.setIcon(R.mipmap.ic_warning);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                TabLayout tableLayout = (TabLayout) getActivity().findViewById(R.id.tabLayout);
                tableLayout.setVisibility(View.VISIBLE);
                ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);
                viewPager.setVisibility(View.VISIBLE);
                ItemPojo pojo = new ItemPojo();
                pojo.setId(itemPojo.getId());
                pojo.setName(mEtname.getText().toString());
                pojo.setDescription(mEtdescription.getText().toString());
                pojo.setLocation(mTvLocation.getText().toString());
                pojo.setTaskStatus("pending");
                Constant.dbHelper.updateTask(pojo);
                getActivity().getSupportFragmentManager().popBackStack();
                dialog.cancel();

            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                TabLayout tableLayout = (TabLayout) getActivity().findViewById(R.id.tabLayout);
                tableLayout.setVisibility(View.VISIBLE);
                ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);
                viewPager.setVisibility(View.VISIBLE);
                getActivity().getSupportFragmentManager().popBackStack();
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

    public boolean validateData() {
        boolean validateStatus = false;
        if (mEtname.getText().toString().isEmpty()) {
            mEtname.setError("please give task name");
        } else if (mEtdescription.getText().toString().isEmpty()) {
            mEtdescription.setError("please give task description");
        } else if (mTvLocation.getText().toString().isEmpty()) {
            mTvLocation.setError("location can not be empty");
        } else validateStatus = true;
        return validateStatus;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 10) {
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

                // mCommunicator.communicate(latlng.latitude, latlng.longitude);
            } else if (resultCode == PlaceAutocomplete.RESULT_ERROR) {
                Status status = PlaceAutocomplete.getStatus(getActivity(), data);
                Log.i("error", status.getStatusMessage());

            } else if (resultCode == RESULT_CANCELED) {
            }
        }
    }
}
