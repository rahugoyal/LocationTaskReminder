package com.example.rahul.locationtaskreminder.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.rahul.locationtaskreminder.R;

/**
 * Created by Rahul on 12/24/2016.
 */

public class PendingTaskFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pending_task_fragment, container, false);
        return view;
    }
}
