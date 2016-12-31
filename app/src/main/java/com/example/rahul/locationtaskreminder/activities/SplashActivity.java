package com.example.rahul.locationtaskreminder.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.rahul.locationtaskreminder.Constants.Constant;
import com.example.rahul.locationtaskreminder.R;
import com.example.rahul.locationtaskreminder.database.DBHelper;


public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Constant.dbHelper = new DBHelper(this);
        Constant.editor = getSharedPreferences("data", MODE_PRIVATE).edit();
        Constant.preferences = getSharedPreferences("data", MODE_PRIVATE);
        int task_id =  Constant.preferences.getInt("task_id",0);
        if (task_id == 0) {
            Constant.editor.putInt("task_id",1);
            Constant.editor.commit();
        }

        Thread timerThread = new Thread() {
            public void run() {
                try {
                    sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }
        };
        timerThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

}
