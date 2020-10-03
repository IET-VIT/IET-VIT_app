package com.example.iet_events;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.iet_events.fragments.DashboardFragment;
import com.example.iet_events.fragments.HomeFragment;
import com.example.iet_events.fragments.ProfileFragment;
import com.example.iet_events.ui.LoginActivity;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.drawer_layout) DrawerLayout drawer_layout;
    @BindView(R.id.nav_view) NavigationView nav_view;
    @BindView(R.id.main_toolbar) Toolbar main_toolbar;
    @BindView(R.id.qr_code_button) FloatingActionButton qr_code_button;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(main_toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer_layout, main_toolbar, R.string.nav_drawer_open, R.string.nav_drawer_close);
        drawer_layout.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
        nav_view.setNavigationItemSelectedListener(this);

        mAuth = FirebaseAuth.getInstance();

        qr_code_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                qrDialogBox();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null) {
            Toast.makeText(MainActivity.this,"Signed-In", Toast.LENGTH_LONG).show();
        }else{
            sendToLogin();
        }
    }

    @Override
    public void onBackPressed() {
        if(drawer_layout.isDrawerOpen(GravityCompat.START))
            drawer_layout.closeDrawer(GravityCompat.START);
        else
            super.onBackPressed();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new HomeFragment()).commit();
                break;
            case R.id.nav_dashboard:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new DashboardFragment()).commit();
                break;
            case R.id.nav_profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.nav_host_fragment, new ProfileFragment()).commit();
                break;
            case R.id.action_logout:
                mAuth.signOut();
                sendToLogin();
                break;
        }
        drawer_layout.closeDrawer(GravityCompat.START);
        return true;
    }


    private void sendToLogin() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void qrDialogBox() {
        Dialog updateDialog = new Dialog(this);
        updateDialog.setContentView(R.layout.qr_code_dialog);
        updateDialog.setCancelable(true);
        updateDialog.show();

        ImageView qr_code = updateDialog.findViewById(R.id.qr_code_image);

        String qr_text = "Get Ready for the best of IET";
        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        try {
            BitMatrix bitMatrix = multiFormatWriter.encode(qr_text, BarcodeFormat.QR_CODE,450,450);
            BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
            Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
            qr_code.setImageBitmap(bitmap);
        } catch (WriterException e) {
            e.printStackTrace();
        }
    }
}