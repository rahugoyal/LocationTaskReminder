package com.example.rahul.locationtaskreminder.activities;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.rahul.locationtaskreminder.R;
import com.example.rahul.locationtaskreminder.adapters.PagerAdapter;
import com.example.rahul.locationtaskreminder.fragments.AddTask;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private PagerAdapter adapterViewPager;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar setup
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        toolbar.setNavigationIcon(R.mipmap.ic_toolbar);
        toolbar.setTitle("Location Task Reminder");
        setSupportActionBar(toolbar);

        //viewpager setup with fragment

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        adapterViewPager = new PagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapterViewPager);

        //setup tabs view pager
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);


        //custom tabs
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabOne.setText("Map");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_location, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabTwo.setText("Pending");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_pending, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.custom_tab, null);
        tabThree.setText("Completed");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.mipmap.ic_completed, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);

        tabLayout.setSelectedTabIndicatorColor(Color.parseColor("#FF0B7ED6"));

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    0);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.add_reminder:
                tabLayout.setVisibility(View.GONE);
                viewPager.setVisibility(View.GONE);
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new AddTask()).addToBackStack("Add Task").commit();
                break;
            default:
                Toast.makeText(this, "No valid operation", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();
                    // adapterViewPager.notifyDataSetChanged();

                }
                return;
            }

        }
    }
}
