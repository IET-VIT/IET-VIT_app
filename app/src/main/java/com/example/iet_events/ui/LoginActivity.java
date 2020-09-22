package com.example.iet_events.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iet_events.MainActivity;
import com.example.iet_events.R;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.all_main_login_layout) ConstraintLayout all_main_login_layout;
    @BindView(R.id.email_login_layout) ConstraintLayout email_login_layout;
    @BindView(R.id.emailLogin) TextView emailLogin;
    @BindView(R.id.googleLogin) TextView googleLogin;
    @BindView(R.id.githubLogin) TextView githubLogin;
    @BindView(R.id.email_login_input) TextInputEditText email_login_input;
    @BindView(R.id.password_login_input) TextInputEditText password_login_input;
    @BindView(R.id.mail_login_button) Button mail_login_button;
    @BindView(R.id.go_to_register) Button go_to_register;

    private boolean otherLayoutOpened;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        otherLayoutOpened = false;

        emailLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                otherLayoutOpened = true;
                all_main_login_layout.setVisibility(View.GONE);
                email_login_layout.setVisibility(View.VISIBLE);
            }
        });

        googleLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Getting Ready....Coming Soon :)", Snackbar.LENGTH_LONG).show();
            }
        });

        githubLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Getting Ready....Coming Soon :)", Snackbar.LENGTH_LONG).show();
            }
        });

        go_to_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(v,"Getting Ready....Page Under Construction :)", Snackbar.LENGTH_LONG).show();
                //TODO : Setup Register Activity
//                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(otherLayoutOpened){
            all_main_login_layout.setVisibility(View.VISIBLE);
            email_login_layout.setVisibility(View.GONE);
            otherLayoutOpened = false;
        }else {
            finish();
        }
        return true;
    }
}