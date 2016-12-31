package com.example.rahul.locationtaskreminder.database;

/**
 * Created by Rahul on 12/30/2016.
 */

import java.util.ArrayList;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;

import com.example.rahul.locationtaskreminder.pojos.ItemPojo;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "task.db";
    public static final String TASK_TABLE_NAME = "location_task";
    public static final String TASK_COLUMN_ID = "id";
    public static final String TASK_COLUMN_NAME = "name";
    public static final String TASK_COLUMN_DESCRIPTION = "description";
    public static final String TASK_COLUMN_LOCATION = "location";
    public static final String TASK_COLUMN_TASK_STATUS = "task_status";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table  " + TASK_TABLE_NAME +
                        "(" + TASK_COLUMN_ID + " integer primary key ," + TASK_COLUMN_NAME + " text," + TASK_COLUMN_DESCRIPTION + " text," + TASK_COLUMN_LOCATION + " text, " + TASK_COLUMN_TASK_STATUS + " text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TASK_TABLE_NAME);
        onCreate(db);
    }

    public boolean insertTask(ItemPojo itemPojo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_COLUMN_ID, itemPojo.getId());
        contentValues.put(TASK_COLUMN_NAME, itemPojo.getName());
        contentValues.put(TASK_COLUMN_DESCRIPTION, itemPojo.getDescription());
        contentValues.put(TASK_COLUMN_LOCATION, itemPojo.getLocation());
        contentValues.put(TASK_COLUMN_TASK_STATUS, itemPojo.getTaskStatus());

        db.insert(TASK_TABLE_NAME, null, contentValues);
        return true;
    }


    public boolean updateTask(ItemPojo itemPojo) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK_COLUMN_NAME, itemPojo.getName());
        contentValues.put(TASK_COLUMN_DESCRIPTION, itemPojo.getDescription());
        contentValues.put(TASK_COLUMN_LOCATION, itemPojo.getLocation());
        contentValues.put(TASK_COLUMN_TASK_STATUS, itemPojo.getTaskStatus());
        db.update(TASK_TABLE_NAME, contentValues, "id = ? ", new String[]{Integer.toString(itemPojo.getId())});
        return true;
    }

    public Integer deleteTask(Integer id) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete(TASK_TABLE_NAME,
                "id = ? ",
                new String[]{Integer.toString(id)});
    }

    public ArrayList<ItemPojo> getAllTasks() {
        ArrayList<ItemPojo> array_list = new ArrayList<ItemPojo>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from " + TASK_TABLE_NAME, null);
        if (res.moveToFirst()) {
            do {
                ItemPojo pojo = new ItemPojo();
                pojo.setId(Integer.parseInt(res.getString(0)));
                pojo.setName(res.getString(1));
                pojo.setDescription(res.getString(2));
                pojo.setLocation(res.getString(3));
                pojo.setTaskStatus(res.getString(4));

                array_list.add(pojo);
            } while (res.moveToNext());
        }
        return array_list;
    }
}
