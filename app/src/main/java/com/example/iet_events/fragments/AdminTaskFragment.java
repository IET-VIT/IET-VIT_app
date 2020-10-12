package com.example.iet_events.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.iet_events.R;
import com.example.iet_events.database.UserDatabase;
import com.example.iet_events.models.Users;
import com.example.iet_events.utils.AdminTaskAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminTaskFragment extends Fragment {

    @BindView(R.id.domain_spinner)
    Spinner domain_spinner;
    @BindView(R.id.users_recyclerview)
    RecyclerView users_recyclerview;
    @BindView(R.id.post_task_button)
    Button post_task_button;
    @BindView(R.id.admin_task_description)
    EditText admin_task_description;
    @BindView(R.id.admin_task_code)
    EditText admin_task_code;

    public static List<String> uploadTaskUserList;
    private List<Users> usersList;

    private AdminTaskAdapter adminTaskAdapter;

    public AdminTaskFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_admin_task, container, false);

        ButterKnife.bind(this, root);

        String[] items = new String[]{"All", "Admin", "Tech", "Design", "Media", "Publicity", "External", "Editorial"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, items);
        domain_spinner.setAdapter(adapter);

        UserDatabase userDb = UserDatabase.getInstance(getContext());
        usersList = userDb.UserDao().loadAllUsers();

        adminTaskAdapter = new AdminTaskAdapter(usersList);

        uploadTaskUserList = new ArrayList<>();

        LinearLayoutManager mLayout = new LinearLayoutManager(getContext());
        users_recyclerview.setHasFixedSize(true);
        users_recyclerview.setLayoutManager(mLayout);
        users_recyclerview.setAdapter(adminTaskAdapter);

        domain_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String role = items[position];
                if (role.equals("All")) {
                    usersList = userDb.UserDao().loadAllUsers();
                } else {
                    usersList = userDb.UserDao().loadAllUsersByRole(role);
                }
                adminTaskAdapter.notify(usersList);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        post_task_button.setOnClickListener(v -> {
            String tasks_description = admin_task_description.getText().toString();
            String tasks_code = admin_task_code.getText().toString();
            if (!tasks_description.isEmpty() && !tasks_code.isEmpty()) {
                DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Users");
                for (String user : uploadTaskUserList) {
                    mRef.child(user).child("Tasks").child(tasks_code).setValue(tasks_description);
                }
            } else {
                Snackbar.make(v, "Please enter the code & description", Snackbar.LENGTH_LONG).show();
            }
        });

        return root;
    }
}