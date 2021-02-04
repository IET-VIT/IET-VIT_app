package com.example.iet_events.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.iet_events.R;
import com.example.iet_events.database.UserDatabase;
import com.example.iet_events.models.Users;
import com.example.iet_events.utils.PeersAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;
import static com.example.iet_events.MainActivity.USERS_DATA;

public class PeersFragment extends Fragment {

    @BindView(R.id.peers_recycler_view) RecyclerView peers_recycler_view;
    @BindView(R.id.peers_progress_bar) ProgressBar peers_progress_bar;

    public PeersFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_peers, container, false);

        ButterKnife.bind(this, root);

        if(!USERS_DATA)
            getAllUsers();
        else
            setupRecyclerView();

        return root;
    }

    private void getAllUsers() {
        SharedPreferences loginPrefs = getContext().getSharedPreferences("LoginInfo", MODE_PRIVATE);
        SharedPreferences.Editor Ed = loginPrefs.edit();

        peers_progress_bar.setVisibility(View.VISIBLE);
        UserDatabase userDatabase = UserDatabase.getInstance(getContext());
        userDatabase.UserDao().clearDb();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Users");
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    String userID = data.getKey();
                    Users user = data.getValue(Users.class).withID(userID);
                    userDatabase.UserDao().insertUser(user);
                }
                USERS_DATA = true;
                Ed.putInt("UserCount",userDatabase.UserDao().getUserCount());
                Ed.commit();
                setupRecyclerView();
                peers_progress_bar.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Problem in retrieving data",Toast.LENGTH_LONG).show();
                peers_progress_bar.setVisibility(View.GONE);
            }
        });
    }

    private void setupRecyclerView() {
        List<Users> users = UserDatabase.getInstance(getContext()).UserDao().loadAllUsers();

        LinearLayoutManager mLayout = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
        PeersAdapter peersAdapter = new PeersAdapter(users);

        peers_recycler_view.setHasFixedSize(true);
        peers_recycler_view.setLayoutManager(mLayout);
        peers_recycler_view.setAdapter(peersAdapter);
    }
}