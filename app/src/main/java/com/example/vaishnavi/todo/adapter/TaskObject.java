package com.example.vaishnavi.todo.adapter;

public class TaskObject{
    private int id;
    private String name;
    private String description;
    private String dateTime;
    private String category;
    private boolean reminder;
    public TaskObject(int id, String name, String description, String dateTime, boolean reminder) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.dateTime = dateTime;
        this.reminder = reminder;
    }
    public TaskObject(String name, String description, String dateTime, String category, boolean reminder) {
        this.name = name;
        this.description = description;
        this.dateTime = dateTime;
        this.category = category;
        this.reminder = reminder;
    }
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }
    public String getDateTime() {
        return dateTime;
    }
    public String getCategory() {
        return category;
    }
    public boolean getReminder() {
        return reminder;
    }
}
