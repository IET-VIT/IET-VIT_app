package com.example.iet_events.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.example.iet_events.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.email_register_input) EditText email_register_input;
    @BindView(R.id.password_register_input) EditText password_register_input;
    @BindView(R.id.confirm_pass_input) EditText confirm_pass_input;
    @BindView(R.id.mail_register_button) Button mail_register_button;
    @BindView(R.id.register_progress_bar) ProgressBar register_progress_bar;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ButterKnife.bind(this);
        //TODO : Add email verification
        mAuth = FirebaseAuth.getInstance();

        mail_register_button.setOnClickListener(view -> {
            String email = String.valueOf(email_register_input.getText()).trim();
            String password = String.valueOf(password_register_input.getText());
            String confirm_pass = String.valueOf(confirm_pass_input.getText());

            if (email.isEmpty() || password.isEmpty() || confirm_pass.isEmpty()) {
                Snackbar.make(view, "Please enter all the credentials", Snackbar.LENGTH_LONG).show();
            }else if (!confirm_pass.equals(password)){
                Snackbar.make(view, "Passwords do not match!", Snackbar.LENGTH_LONG).show();
            }else{
                register_progress_bar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            SharedPreferences setupCheck = getSharedPreferences("SetupCheck", MODE_PRIVATE);
                            SharedPreferences loginPrefs = getSharedPreferences("LoginInfo", MODE_PRIVATE);
                            SharedPreferences.Editor loginEd = loginPrefs.edit();
                            SharedPreferences.Editor registerEd = setupCheck.edit();
                            registerEd.putString("SignUp","Yes");
                            registerEd.putString("Setup","No");
                            registerEd.commit();
                            loginEd.putString("UserId", mAuth.getUid());
                            loginEd.putString("Email", email);
                            loginEd.commit();
                            startActivity(new Intent(RegisterActivity.this, SetupActivity.class));
                            finish();
                        } else {
                            Log.e("RegisterActivity","createUserWithEmail:failure" +"\n"+ task.getException());
                            Snackbar.make(view, "Authentication failed.", Snackbar.LENGTH_LONG).show();
                        }
                        register_progress_bar.setVisibility(View.GONE);
                    }
                });
            }
        });

    }
}