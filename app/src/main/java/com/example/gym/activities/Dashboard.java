package com.example.gym.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.gym.R;
import com.example.gym.activities.fragments.HistoryFragment;
import com.example.gym.activities.fragments.ProfileFragment;
import com.example.gym.activities.fragments.RoutineFragment;

public class Dashboard extends AppCompatActivity {

    private static final String TAG = "Dashboard";
    private TextView mTextMessage;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            Log.d(TAG, "Menu Selected");
            switch (item.getItemId()) {
                case R.id.navigation_exercises:
                    selectedFragment = new RoutineFragment();
                    Log.d(TAG, "transaction fragment selected");
                    break;
                case R.id.navigation_history:
                    selectedFragment = new HistoryFragment();
                    Log.d(TAG, "CustomerFragment fragment selected");
                    break;
                case R.id.navigation_profile:
                    selectedFragment = new ProfileFragment();
                    Log.d(TAG, "ProfileFragment fragment selected");
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();
            return true;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new RoutineFragment()).commit();
    }

}
