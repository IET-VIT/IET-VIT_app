package com.example.iet_events.ui;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.iet_events.R;
import com.example.iet_events.database.UserDatabase;
import com.example.iet_events.fragments.AdminTaskFragment;
import com.example.iet_events.models.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminActivity extends AppCompatActivity {

    @BindView(R.id.add_tasks_card) CardView add_tasks_card;
    @BindView(R.id.admin_frame) FrameLayout admin_frame;
    @BindView(R.id.admin_main_layout) ConstraintLayout admin_main_layout;
    @BindView(R.id.admin_toolbar) Toolbar admin_toolbar;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ButterKnife.bind(this);

        admin_toolbar.setTitle("Admin Page");
        setSupportActionBar(admin_toolbar);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);
        progressDialog.show();
        getAllUsers();

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        add_tasks_card.setOnClickListener(v -> {
            admin_frame.setVisibility(View.VISIBLE);
            admin_main_layout.setVisibility(View.GONE);
            getSupportFragmentManager().beginTransaction().replace(R.id.admin_frame, new AdminTaskFragment()).commit();
        });
    }

    private void getAllUsers() {
        UserDatabase userDatabase = UserDatabase.getInstance(AdminActivity.this);
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
                progressDialog.dismiss();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(AdminActivity.this,"Problem in retrieving data",Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
                finish();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}