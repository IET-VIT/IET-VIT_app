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

public class PeersFragment extends Fragment {

    @BindView(R.id.peers_recycler_view) RecyclerView peers_recycler_view;

    public PeersFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_peers, container, false);

        ButterKnife.bind(this, root);

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
//                progressDialog.dismiss();
                setupRecyclerView();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Problem in retrieving data",Toast.LENGTH_LONG).show();
//                progressDialog.dismiss();
//                finish();
            }
        });

        return root;
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