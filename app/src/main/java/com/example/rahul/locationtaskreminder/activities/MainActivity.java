package com.example.rahul.locationtaskreminder.activities;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.example.rahul.locationtaskreminder.R;
import com.example.rahul.locationtaskreminder.adapters.PagerAdapter;

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


        //
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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        switch (item.getItemId()) {


        }
        return false;
    }
}
