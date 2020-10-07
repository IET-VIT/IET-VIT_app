package com.example.iet_events.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.iet_events.R;
import com.example.iet_events.utils.TaskAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.iet_events.MainActivity.roleList;
import static com.example.iet_events.MainActivity.tasksList;

public class DashboardFragment extends Fragment {

    @BindView(R.id.tasks_recycler_view) RecyclerView tasks_recycler_view;

    public DashboardFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);
        ButterKnife.bind(this, root);

        TaskAdapter taskAdapter = new TaskAdapter(tasksList, roleList);
        LinearLayoutManager mLayout = new LinearLayoutManager(getContext());
        tasks_recycler_view.setHasFixedSize(true);
        tasks_recycler_view.setLayoutManager(mLayout);
        tasks_recycler_view.setAdapter(taskAdapter);
        return root;
    }
}