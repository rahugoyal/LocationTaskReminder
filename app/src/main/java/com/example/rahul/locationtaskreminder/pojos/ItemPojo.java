package com.example.rahul.locationtaskreminder.pojos;

import java.io.Serializable;

/**
 * Created by Rahul on 12/27/2016.
 */

public class ItemPojo implements Serializable {
    String name;
    String description;
    String location;
    String taskStatus;
    int id;

    public ItemPojo() {
    }

    public ItemPojo( String name, String description, String location, String taskStatus) {

        this.name = name;
        this.description = description;
        this.location = location;
        this.taskStatus = taskStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
