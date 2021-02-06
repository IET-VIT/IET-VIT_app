package com.example.iet_events.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminTaskFragment extends Fragment {

    public static Spinner domain_spinner;
    @BindView(R.id.users_recyclerview) RecyclerView users_recyclerview;
    @BindView(R.id.post_task_button) Button post_task_button;
    @BindView(R.id.admin_task_description) EditText admin_task_description;
    @BindView(R.id.admin_task_code) EditText admin_task_code;
    @BindView(R.id.name_filter) EditText name_filter;
    @BindView(R.id.task_select_all) CheckBox task_select_all;

    public static List<Users> uploadTaskUserList;
    private List<Users> usersList;

    private AdminTaskAdapter adminTaskAdapter;

    public AdminTaskFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_admin_task, container, false);

        ButterKnife.bind(this, root);

        domain_spinner = root.findViewById(R.id.domain_spinner);
        String[] items = new String[]{"All", "Board", "Administration", "Technical", "Design", "Media", "Publicity", "External", "Editorial"};
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

        name_filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String text = s.toString();
                List<Users> filteredList = new ArrayList<>();

                for(Users item : usersList){
                    if(item.getName().toLowerCase().contains(text.toLowerCase().trim())){
                        filteredList.add(item);
                    }
                }
                adminTaskAdapter.notify(filteredList);
            }
        });

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

        task_select_all.setOnCheckedChangeListener((checkBox, isChecked) -> {
            if(isChecked) {
                adminTaskAdapter.selectAll();
                uploadTaskUserList = new ArrayList<>(usersList);
                domain_spinner.setEnabled(false);
                name_filter.setEnabled(false);
            }
            else {
                adminTaskAdapter.unselectAll();
                uploadTaskUserList.clear();
                domain_spinner.setEnabled(true);
                name_filter.setEnabled(true);
            }
        });

        post_task_button.setOnClickListener(v -> {
            String task_description = admin_task_description.getText().toString();
            String task_code = admin_task_code.getText().toString();
            if (!task_description.isEmpty() && !task_code.isEmpty()) {
                Map<String, String> taskMap = new HashMap<>();
                taskMap.put("Description", task_description);
                taskMap.put("Status", "Not Done");
                DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Users");
                for (Users user : uploadTaskUserList) {
                    mRef.child(user.UserID).child("Tasks").child(task_code).setValue(taskMap);
                }
                getActivity().finish();
            } else {
                Snackbar.make(v, "Please enter the code & description", Snackbar.LENGTH_LONG).show();
            }
        });

        return root;
    }
}