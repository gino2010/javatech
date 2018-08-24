package com.gino.moment;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        MainActivityFragment mainActivityFragment = new MainActivityFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, mainActivityFragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_user) {
            UserFragment userFragment = new UserFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, userFragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        }

        if (id == R.id.action_settings) {
            SettingsFragment settingsFragment = new SettingsFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, settingsFragment)
                    .addToBackStack(null)
                    .commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
