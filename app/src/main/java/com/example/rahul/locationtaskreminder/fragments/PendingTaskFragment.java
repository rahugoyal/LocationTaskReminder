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
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TableLayout;
import android.widget.Toast;

import com.example.rahul.locationtaskreminder.R;
import com.example.rahul.locationtaskreminder.adapters.CustomListAdapter;
import com.example.rahul.locationtaskreminder.pojos.ItemPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Rahul on 12/24/2016.
 */

public class PendingTaskFragment extends Fragment {
    private ListView mListView;
    private CustomListAdapter mAdapter;
    private List<ItemPojo> pojoList;
    private ImageButton mIbLocation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pending_task_fragment, container, false);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        mListView = (ListView) view.findViewById(R.id.lv_pending);
        pojoList = new ArrayList<>();
        ItemPojo pojo = new ItemPojo("purchasing oil", "what are u doing?", "","");
        ItemPojo pojo1 = new ItemPojo("doing work", "what did u do?", "","");
        ItemPojo pojo2 = new ItemPojo("doing work", "what did u do?", "","");
        pojoList.add(pojo);
        pojoList.add(pojo1);
        pojoList.add(pojo2);


        mAdapter = new CustomListAdapter(getActivity().getApplicationContext(), pojoList);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemLongClickListener(new ListView.OnItemLongClickListener() {

            @Override
            public boolean onItemLongClick(AdapterView<?> arg0, View view, int position, long row_id) {
                deleteAlert();
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
                args.putInt("status", 1);
                editFragment.setArguments(args);
                getActivity().getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, editFragment).addToBackStack("Edit Task").commit();


            }
        });



    }

    public void deleteAlert() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Warning!");
        alertDialog.setMessage("Do you want to delete this task?");
        alertDialog.setIcon(R.mipmap.ic_warning);

        alertDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

            }
        });

        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        alertDialog.show();
    }
}
