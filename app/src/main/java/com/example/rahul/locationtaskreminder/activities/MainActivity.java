package com.example.rahul.locationtaskreminder.activities;

import android.Manifest;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.rahul.locationtaskreminder.Constants.Constant;
import com.example.rahul.locationtaskreminder.R;
import com.example.rahul.locationtaskreminder.adapters.PagerAdapter;
import com.example.rahul.locationtaskreminder.fragments.AddTask;
import com.example.rahul.locationtaskreminder.fragments.EditFragment;
import com.example.rahul.locationtaskreminder.interfaces.Communicator;
import com.example.rahul.locationtaskreminder.pojos.ItemPojo;
import com.example.rahul.locationtaskreminder.receivers.ProximityIntentReceiver;


public class MainActivity extends AppCompatActivity implements Communicator {
    private Toolbar toolbar;
    private PagerAdapter adapterViewPager;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private MenuItem saveTask, addTask;
    private static final long POINT_RADIUS = 1000; // in Meters
    private static final long PROX_ALERT_EXPIRATION = -1; // It will never expire
    private static final String PROX_ALERT_INTENT = "com.example.rahul.locationtaskreminder.activities.ProximityAlert";
    private LocationManager locationManager;
    private double lattitude = 0, longitude = 0;
    public static final String TIME_SERVER = "time-a.nist.gov";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getLocation Manager context
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //Toolbar setup
        toolbar = (Toolbar) findViewById(R.id.toolbar_main);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.mipmap.ic_toolbar);
        toolbar.setTitle("Location Task Reminder");


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

        getSupportFragmentManager().addOnBackStackChangedListener(new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                shouldDisplayHomeUp();

            }
        });

        shouldDisplayHomeUp();

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
        addTask = menu.findItem(R.id.add_reminder);
        saveTask = menu.findItem(R.id.save_reminder);
        return true;
    }

    public void shouldDisplayHomeUp() {
        boolean canback = getSupportFragmentManager().getBackStackEntryCount() > 0;
        getSupportActionBar().setDisplayHomeAsUpEnabled(canback);
        if (!canback) {
            toolbar.setNavigationIcon(R.mipmap.ic_toolbar);

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.add_reminder:
                tabLayout.setVisibility(View.GONE);
                viewPager.setVisibility(View.GONE);
                saveTask.setVisible(true);
                addTask.setVisible(false);
                getSupportFragmentManager().beginTransaction().add(R.id.fragment_container, new AddTask()).addToBackStack("Add Task").commit();
                break;

            case R.id.save_reminder:
                String name = ((EditText) findViewById(R.id.et_add_task_name)).getText().toString();
                String description = ((EditText) findViewById(R.id.et_add_task_description)).getText().toString();
                String location = ((TextView) findViewById(R.id.tv_location_task)).getText().toString();
                if (name.equals("")) {
                    ((EditText) findViewById(R.id.et_add_task_name)).setError("please give name of task ");
                } else if (description.equals("")) {
                    ((EditText) findViewById(R.id.et_add_task_description)).setError("please give description of task");
                } else if (location.equals("")) {
                    ((TextView) findViewById(R.id.tv_location_task)).setError("location can not be empty");
                } else {
                    int task_id = Constant.preferences.getInt("task_id", 0);
                    ItemPojo itemPojo = new ItemPojo(task_id, name, description, location, "pending");
                    task_id = task_id + 1;
                    Constant.editor.clear();
                    Constant.editor.putInt("task_id", task_id);
                    Constant.editor.commit();
                    addProximityAlert(itemPojo);
                    saveTask.setVisible(false);
                    addTask.setVisible(true);
                    getSupportFragmentManager().popBackStack();
                    tabLayout.setVisibility(View.VISIBLE);
                    viewPager.setVisibility(View.VISIBLE);
                    Constant.dbHelper.insertTask(itemPojo);
                    Toast.makeText(this, "Data saved successfully", Toast.LENGTH_SHORT).show();
                }
                break;


            default:
                Toast.makeText(this, "No valid operation", Toast.LENGTH_SHORT).show();


        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        // if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
        if (saveTask.isVisible())
            saveTask.setVisible(false);
        if (!addTask.isVisible())
            addTask.setVisible(true);
        if (tabLayout.getVisibility() == View.GONE)
            tabLayout.setVisibility(View.VISIBLE);
        if (viewPager.getVisibility() == View.GONE)
            viewPager.setVisibility(View.VISIBLE);
        getSupportFragmentManager().popBackStack();
        //}
    }

    private void addProximityAlert(ItemPojo itemPojo) {
        Intent intent = new Intent(PROX_ALERT_INTENT);
        Bundle bundle = new Bundle();
        bundle.putSerializable("object", itemPojo);
        intent.putExtras(bundle);

        PendingIntent proximityIntent = PendingIntent.getBroadcast(this, itemPojo.getId(), intent, 0);

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        if (lattitude != 0 && longitude != 0) {
            locationManager.addProximityAlert(lattitude, longitude, POINT_RADIUS, PROX_ALERT_EXPIRATION, proximityIntent);
            IntentFilter filter = new IntentFilter(PROX_ALERT_INTENT);

            registerReceiver(new ProximityIntentReceiver(), filter);
            Toast.makeText(getApplicationContext(), "Task reminder added", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(this, "latitude and longitude are not valid", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 0: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show();

                }
                return;
            }

        }
    }

    @Override
    public void communicate(double lat, double lon) {
        lattitude = lat;
        longitude = lon;
    }
}
