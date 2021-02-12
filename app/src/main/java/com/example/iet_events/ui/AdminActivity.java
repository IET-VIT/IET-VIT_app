package com.example.iet_events.ui;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.example.iet_events.R;
import com.example.iet_events.database.UserDatabase;
import com.example.iet_events.fragments.AddEventFragment;
import com.example.iet_events.fragments.AddMeetingFragment;
import com.example.iet_events.fragments.AdminTaskFragment;
import com.example.iet_events.fragments.CheckTaskFragment;
import com.example.iet_events.models.Users;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.example.iet_events.MainActivity.USERS_DATA;

public class AdminActivity extends AppCompatActivity {

    @BindView(R.id.add_tasks_card) CardView add_tasks_card;
    @BindView(R.id.check_tasks_card) CardView check_tasks_card;
    @BindView(R.id.meeting_card) CardView meeting_card;
    @BindView(R.id.add_event_card) CardView add_event_card;
    @BindView(R.id.admin_frame) FrameLayout admin_frame;
    @BindView(R.id.admin_main_layout) ConstraintLayout admin_main_layout;
    @BindView(R.id.admin_toolbar) Toolbar admin_toolbar;

    private ProgressDialog progressDialog;
    private boolean isFrameOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences app_theme_prefs = getSharedPreferences("AppTheme",MODE_PRIVATE);
        String appTheme = app_theme_prefs.getString("AppThemeColor", "BlueTheme");

        switch (appTheme) {
            case "PurpleTheme":
                setTheme(R.style.AppTheme_Purple);
                break;
            case "GreenTheme":
                setTheme(R.style.AppTheme_Green);
                break;
            case "OrangeTheme":
                setTheme(R.style.AppTheme_Orange);
                break;
        }
        setContentView(R.layout.activity_admin);

        ButterKnife.bind(this);

        admin_toolbar.setTitle("Admin Page");
        setSupportActionBar(admin_toolbar);
        isFrameOpen = false;

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.setCancelable(false);

        if(!USERS_DATA) {
            getAllUsers();
            progressDialog.show();
        }

        if (getSupportActionBar() != null){
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        add_tasks_card.setOnClickListener(v -> changeFragment(new AdminTaskFragment()));

        check_tasks_card.setOnClickListener(v -> changeFragment(new CheckTaskFragment()));

        meeting_card.setOnClickListener(v -> changeFragment(new AddMeetingFragment()));

        add_event_card.setOnClickListener(v -> changeFragment(new AddEventFragment()));
    }

    private void changeFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction().replace(R.id.admin_frame, fragment).commit();
        admin_frame.setVisibility(View.VISIBLE);
        admin_main_layout.setVisibility(View.GONE);
        isFrameOpen = true;
    }

    private void getAllUsers() {
        SharedPreferences loginPrefs = getSharedPreferences("LoginInfo", MODE_PRIVATE);
        SharedPreferences.Editor Ed = loginPrefs.edit();

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
                USERS_DATA = true;
                Ed.putInt("UserCount",userDatabase.UserDao().getUserCount());
                Ed.commit();
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

    @Override
    public void onBackPressed() {
        if(isFrameOpen) {
            isFrameOpen = false;
            admin_frame.setVisibility(View.GONE);
            admin_main_layout.setVisibility(View.VISIBLE);
        }
        else
            super.onBackPressed();
    }
}