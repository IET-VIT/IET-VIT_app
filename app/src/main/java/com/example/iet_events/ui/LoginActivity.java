package com.example.iet_events.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.iet_events.MainActivity;
import com.example.iet_events.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

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
    @BindView(R.id.loginProgress) ProgressBar loginProgress;

    private String TAG = "LoginActivity";
    private boolean otherLayoutOpened;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ButterKnife.bind(this);

        otherLayoutOpened = false;
        mAuth = FirebaseAuth.getInstance();

        SharedPreferences loginPrefs = getSharedPreferences("LoginInfo", MODE_PRIVATE);
        SharedPreferences.Editor Ed = loginPrefs.edit();

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
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        mail_login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                assert imm != null;

                View focusedView = LoginActivity.this.getCurrentFocus();
                if (focusedView != null) {
                    imm.hideSoftInputFromWindow(focusedView.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }

                String email = String.valueOf(email_login_input.getText()).trim();
                String password = String.valueOf(password_login_input.getText());

                if(email.isEmpty() || password.isEmpty()) {
                    Snackbar.make(v, "Please enter all the credentials", Snackbar.LENGTH_LONG).show();
                }else{
                    loginProgress.setVisibility(View.VISIBLE);
                    mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Ed.putString("UserId", mAuth.getUid());
                                Ed.putString("Email", email);
                                Ed.commit();
                                Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
                                mainIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(mainIntent);
                                finish();
                            } else {
                                Log.e(TAG, "signInWithEmail:failure", task.getException());
                                Snackbar.make(v, "Authentication failed. Please contact administrator", Snackbar.LENGTH_LONG).show();
                            }
                            loginProgress.setVisibility(View.GONE);
                        }
                    });
                }
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