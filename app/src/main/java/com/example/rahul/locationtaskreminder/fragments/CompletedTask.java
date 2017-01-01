package com.example.rahul.locationtaskreminder.fragments;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.rahul.locationtaskreminder.Constants.Constant;
import com.example.rahul.locationtaskreminder.R;
import com.example.rahul.locationtaskreminder.adapters.CustomListAdapter;
import com.example.rahul.locationtaskreminder.interfaces.Communicator;
import com.example.rahul.locationtaskreminder.pojos.ItemPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul on 12/24/2016.
 */

public class CompletedTask extends Fragment {
    private ListView mListView;
    private CustomListAdapter mAdapter;
    private List<ItemPojo> pojoList, dbList;
    private Communicator mCommunicator;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.completed_task_fragment, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        mListView = (ListView) view.findViewById(R.id.lv_completed);
        mCommunicator = (Communicator) getActivity();

        pojoList = new ArrayList<>();
        dbList = Constant.dbHelper.getAllTasks();

        if (dbList != null) {
            for (int i = 0; i < dbList.size(); i++) {
                if (dbList.get(i).getTaskStatus().equals("completed")) {
                    pojoList.add(dbList.get(i));
                }
            }
        }

        mAdapter = new CustomListAdapter(getActivity().getApplicationContext(), pojoList);
        mListView.setAdapter(mAdapter);
        mListView.setEmptyView(view.findViewById(android.R.id.empty));


        mListView.setOnItemLongClickListener(new ListView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long row_id) {
                deleteAlert(pojoList.get(position));
                return true;
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                TabLayout tableLayout = (TabLayout) getActivity().findViewById(R.id.tabLayout);
                tableLayout.setVisibility(View.GONE);
                ViewPager viewPager = (ViewPager) getActivity().findViewById(R.id.viewpager);
                viewPager.setVisibility(View.GONE);
                EditFragment editFragment = new EditFragment();
                Bundle args = new Bundle();
                args.putSerializable("pojo", pojoList.get(i));
                args.putInt("status", 2);
                editFragment.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, editFragment).addToBackStack("Edit Task").commit();

            }
        });
    }

    public void deleteAlert(final ItemPojo itemPojo) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Warning!");
        alertDialog.setMessage("Do you want to delete this task?");
        alertDialog.setIcon(R.mipmap.ic_warning);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                Constant.dbHelper.deleteTask(itemPojo.getId());
                mCommunicator.refreshData(2);
                dialog.cancel();

            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                mCommunicator.refreshData(2);
                dialog.cancel();
            }
        });
        alertDialog.show();
    }

}
