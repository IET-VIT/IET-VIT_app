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
    @BindView(R.id.domain_setup_grp_1) RadioGroup domain_setup_grp_1;
    @BindView(R.id.domain_setup_grp_2) RadioGroup domain_setup_grp_2;
    @BindView(R.id.setup_complete_btn) Button setup_complete_btn;
    @BindView(R.id.setup_progress_bar) ProgressBar setup_progress_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup);

        ButterKnife.bind(this);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        setup_complete_btn.setOnClickListener(view -> {
            RadioButton roleButton1 = findViewById(domain_setup_grp_1.getCheckedRadioButtonId());
            RadioButton roleButton2 = findViewById(domain_setup_grp_2.getCheckedRadioButtonId());
            String domain_text_1 = roleButton1.getText().toString();
            String domain_text_2 = roleButton2.getText().toString();
            String name_text = String.valueOf(name_setup_input.getText()).trim();
            String phone_text = String.valueOf(phone_setup_input.getText()).trim();

            if(name_text.isEmpty() || phone_text.isEmpty())
                Snackbar.make(view, "Oops! Looks like you have forgotten your name and number.", Snackbar.LENGTH_LONG).show();
            else if(domain_text_1.equals(domain_text_2)){
                Snackbar.make(view, "Oops! Selected same 2 domains.", Snackbar.LENGTH_LONG).show();
            } else {
                setup_progress_bar.setVisibility(View.VISIBLE);
                Map<String,String> userMap = new HashMap<>();
                userMap.put("Name", name_text);
                userMap.put("Number", phone_text);
                userMap.put("Role", domain_text_1);
                userMap.put("Role2", domain_text_2);
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