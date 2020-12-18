package com.example.iet_events.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.iet_events.R;

import butterknife.ButterKnife;

public class CheckTaskFragment extends Fragment {

    public CheckTaskFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_check_task, container, false);

        ButterKnife.bind(this,root);

        return root;
    }
}