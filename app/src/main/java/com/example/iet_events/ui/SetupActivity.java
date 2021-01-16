package com.example.iet_events.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.example.iet_events.MainActivity;
import com.example.iet_events.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SetupActivity extends AppCompatActivity {

    @BindView(R.id.name_setup_input) TextInputEditText name_setup_input;
    @BindView(R.id.phone_setup_input) TextInputEditText phone_setup_input;
    @BindView(R.id.domain_setup_group) RadioGroup domain_setup_group;
    @BindView(R.id.setup_complete_btn) Button setup_complete_btn;
    @BindView(R.id.setup_progress_bar) ProgressBar setup_progress_bar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        ButterKnife.bind(this);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        setup_complete_btn.setOnClickListener(view -> {
            RadioButton radioButton = findViewById(domain_setup_group.getCheckedRadioButtonId());
            String domain_text = radioButton.getText().toString();
            String name_text = String.valueOf(name_setup_input.getText()).trim();
            String phone_text = String.valueOf(phone_setup_input.getText()).trim();

            if(name_text.isEmpty() || phone_text.isEmpty())
                Snackbar.make(view, "Oops! Looks like you have forgotten your name and number.", Snackbar.LENGTH_LONG).show();
            else {
                setup_progress_bar.setVisibility(View.VISIBLE);
                Map<String,String> userMap = new HashMap<>();
                userMap.put("Name", name_text);
                userMap.put("Number", phone_text);
                userMap.put("Role", domain_text);
                FirebaseDatabase.getInstance().getReference("Users").child(user.getUid())
                        .setValue(userMap).addOnCompleteListener(task -> {
                            if(!task.isSuccessful())
                                Snackbar.make(view,"",Snackbar.LENGTH_LONG).show();
                            else {
                                SharedPreferences setupCheck = getSharedPreferences("SetupCheck", MODE_PRIVATE);
                                SharedPreferences.Editor Ed = setupCheck.edit();
                                Ed.putString("Setup","Yes");
                                Ed.commit();
                                startActivity(new Intent(SetupActivity.this, MainActivity.class));
                                finish();
                            }
                            setup_progress_bar.setVisibility(View.GONE);
                        });
            }
        });
    }
}