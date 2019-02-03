package com.example.vaishnavi.todo.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class TaskModel extends RealmObject {
    @PrimaryKey
    private int id;
    @Required
    private String name;
    @Required
    private String description;
    private String dateTime;
    private String category;
    private boolean reminder;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
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
    public String getDateTime() {
        return dateTime;
    }
    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public boolean getReminder() {
        return reminder;
    }
    public void setReminder(boolean reminder) {
        this.reminder = reminder;
    }
}