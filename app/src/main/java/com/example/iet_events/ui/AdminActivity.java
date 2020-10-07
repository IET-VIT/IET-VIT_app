package com.example.iet_events.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.iet_events.R;
import com.example.iet_events.fragments.AdminTaskFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AdminActivity extends AppCompatActivity {

    @BindView(R.id.tasks_card) CardView tasks_card;
    @BindView(R.id.admin_frame) FrameLayout admin_frame;
    @BindView(R.id.admin_main_layout) ConstraintLayout admin_main_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ButterKnife.bind(this);

        tasks_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                admin_frame.setVisibility(View.VISIBLE);
                admin_main_layout.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().replace(R.id.admin_frame, new AdminTaskFragment()).commit();
            }
        });

    }
}