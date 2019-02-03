package com.example.vaishnavi.todo.helper;

import android.app.Application;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.realm.Realm;
import io.realm.RealmConfiguration;
public class CustomApplication extends Application{
    private Gson gson;
    private GsonBuilder builder;
    @Override
    public void onCreate() {
        super.onCreate();
        builder = new GsonBuilder();
        gson = builder.create();
        Realm.init(getApplicationContext());
        RealmConfiguration realmConfiguration = new RealmConfiguration.Builder()
                .deleteRealmIfMigrationNeeded()
                .build();
        Realm.setDefaultConfiguration(realmConfiguration);
    }
    public Gson getGsonObject(){
        return gson;
    }
}