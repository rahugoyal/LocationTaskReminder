package com.example.rahul.locationtaskreminder.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.rahul.locationtaskreminder.R;
import com.example.rahul.locationtaskreminder.pojos.ItemPojo;

/**
 * Created by Rahul on 12/29/2016.
 */

public class EditFragment extends Fragment {
    ItemPojo itemPojo;
    EditText mEtname, mEtdescription;
    TextView mTvLocation;
    ImageButton mIbUpdate;
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

        if (itemPojo != null) {
            mEtname.setText(itemPojo.getName());
            mEtdescription.setText(itemPojo.getDescription());
            mTvLocation.setText("loc");
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
}
