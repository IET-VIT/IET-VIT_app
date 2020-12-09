package com.example.iet_events.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.iet_events.R;
import com.example.iet_events.fragments.AdminTaskFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminActivity extends AppCompatActivity {

    @BindView(R.id.add_tasks_card) CardView add_tasks_card;
    @BindView(R.id.admin_frame) FrameLayout admin_frame;
    @BindView(R.id.admin_main_layout) ConstraintLayout admin_main_layout;
    @BindView(R.id.admin_toolbar) Toolbar admin_toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ButterKnife.bind(this);

        admin_toolbar.setTitle("Admin Page");
        setSupportActionBar(admin_toolbar);

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

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}