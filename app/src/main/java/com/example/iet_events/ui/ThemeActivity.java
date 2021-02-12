package com.example.iet_events.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.widget.SwitchCompat;

import com.example.iet_events.MainActivity;
import com.example.iet_events.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ThemeActivity extends AppCompatActivity {

    @BindView(R.id.blue_theme_card) CardView blue_theme_card;
    @BindView(R.id.purple_theme_card) CardView purple_theme_card;
    @BindView(R.id.green_theme_card) CardView green_theme_card;
    @BindView(R.id.orange_theme_card) CardView orange_theme_card;
    @BindView(R.id.night_switch) SwitchCompat night_switch;
    @BindView(R.id.update_theme_button) Button update_theme_button;

    final String BLUE_THEME = "BlueTheme";
    final String PURPLE_THEME = "PurpleTheme";
    final String GREEN_THEME = "GreenTheme";
    final String ORANGE_THEME = "OrangeTheme";
    final boolean DARK_MODE = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences app_theme_prefs = getSharedPreferences("AppTheme",MODE_PRIVATE);
        boolean darkMode = app_theme_prefs.getBoolean("DarkMode", !DARK_MODE);
        String appTheme = app_theme_prefs.getString("AppThemeColor", BLUE_THEME);

        switch (appTheme) {
            case PURPLE_THEME:
                setTheme(R.style.AppTheme_Purple);
                break;
            case GREEN_THEME:
                setTheme(R.style.AppTheme_Green);
                break;
            case ORANGE_THEME:
                setTheme(R.style.AppTheme_Orange);
                break;
        }
        setContentView(R.layout.activity_theme);

        ButterKnife.bind(this);

        SharedPreferences.Editor Ed = app_theme_prefs.edit();
        boolean[] isChanged = {false, false};

        switch (appTheme) {
            case BLUE_THEME:
                blue_theme_card.setCardBackgroundColor(getResources().getColor(R.color.lightBlue));
                break;
            case PURPLE_THEME:
                purple_theme_card.setCardBackgroundColor(getResources().getColor(R.color.lightPurple));
                break;
            case GREEN_THEME:
                green_theme_card.setCardBackgroundColor(getResources().getColor(R.color.lightGreen));
                break;
            case ORANGE_THEME:
                orange_theme_card.setCardBackgroundColor(getResources().getColor(R.color.lightOrange));
                break;
        }

        night_switch.setChecked(darkMode);

        blue_theme_card.setOnClickListener(v -> {
            purple_theme_card.setCardBackgroundColor(getColor(R.color.White));
            green_theme_card.setCardBackgroundColor(getColor(R.color.White));
            orange_theme_card.setCardBackgroundColor(getColor(R.color.White));
            blue_theme_card.setCardBackgroundColor(getResources().getColor(R.color.lightBlue));
            isChanged[0] = !appTheme.equals(BLUE_THEME);
            update_theme_button.setEnabled(isChanged[0] || isChanged[1]);
            Ed.putString("AppThemeColor", BLUE_THEME);
        });

        purple_theme_card.setOnClickListener(v -> {
            blue_theme_card.setCardBackgroundColor(getColor(R.color.White));
            green_theme_card.setCardBackgroundColor(getColor(R.color.White));
            orange_theme_card.setCardBackgroundColor(getColor(R.color.White));
            purple_theme_card.setCardBackgroundColor(getResources().getColor(R.color.lightPurple));
            isChanged[0] = !appTheme.equals(PURPLE_THEME);
            update_theme_button.setEnabled(isChanged[0] || isChanged[1]);
            Ed.putString("AppThemeColor", PURPLE_THEME);
        });

        green_theme_card.setOnClickListener(v -> {
            purple_theme_card.setCardBackgroundColor(getColor(R.color.White));
            blue_theme_card.setCardBackgroundColor(getColor(R.color.White));
            orange_theme_card.setCardBackgroundColor(getColor(R.color.White));
            green_theme_card.setCardBackgroundColor(getResources().getColor(R.color.lightGreen));
            isChanged[0] = !appTheme.equals(GREEN_THEME);
            update_theme_button.setEnabled(isChanged[0] || isChanged[1]);
            Ed.putString("AppThemeColor", GREEN_THEME);
        });

        orange_theme_card.setOnClickListener(v -> {
            blue_theme_card.setCardBackgroundColor(getColor(R.color.White));
            green_theme_card.setCardBackgroundColor(getColor(R.color.White));
            purple_theme_card.setCardBackgroundColor(getColor(R.color.White));
            orange_theme_card.setCardBackgroundColor(getResources().getColor(R.color.lightOrange));
            isChanged[0] = !appTheme.equals(ORANGE_THEME);
            update_theme_button.setEnabled(isChanged[0] || isChanged[1]);
            Ed.putString("AppThemeColor", ORANGE_THEME);
        });

        night_switch.setOnCheckedChangeListener((compoundButton, isChecked) -> {
            Ed.putBoolean("DarkMode", isChecked);
            isChanged[1] = isChecked != darkMode;
            update_theme_button.setEnabled(isChanged[0] || isChanged[1]);
        });

        update_theme_button.setOnClickListener(view -> {
            if(isChanged[1]){
                //TODO: change the dark mode
            }
            Ed.apply();
            Intent intent = new Intent(ThemeActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });
    }
}