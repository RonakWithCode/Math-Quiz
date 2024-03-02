package com.Ronosoftstudio.mathquizgame;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

import com.Ronosoftstudio.mathquizgame.databinding.ActivityMainBinding;
import com.google.android.gms.ads.MobileAds;
public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        MobileAds.initialize(this, initializationStatus -> {
//
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


//        this.finish();
//        startActivity(new Intent(this, MainActivity.class));

    }

//    @Override
//    public void recreate() {
//        super.recreate();
//
//        // Apply the saved night mode preference when the activity resumes
//        SharedPreferences sharedPreferences = getSharedPreferences("Profile", Context.MODE_PRIVATE);
//        int screenMode = sharedPreferences.getInt("ScreenMode", AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
//
//        AppCompatDelegate.setDefaultNightMode(screenMode);
//        // Recreate the activity to apply the changes
////        recreate();
//
//    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sharedPreferences = getSharedPreferences("Settings", Context.MODE_PRIVATE);
        int screenMode = sharedPreferences.getInt("ScreenMode", AppCompatDelegate.MODE_NIGHT_YES);
        Log.d("NightMode", "Retrieved screen mode: " + screenMode);
        AppCompatDelegate.setDefaultNightMode(screenMode);
    }
}