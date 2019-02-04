package com.example.vaishnavi.todo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.vaishnavi.todo.adapter.CustomArrayAdapter;
import com.example.vaishnavi.todo.adapter.RealmAdapter;
import com.example.vaishnavi.todo.realm.TaskModel;
//import com.facebook.stetho.Stetho;
//import com.uphyca.stetho_realm.RealmInspectorModulesProvider;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class ToDo2Activity extends AppCompatActivity  {
    private static final String TAG = ToDo2Activity.class.getSimpleName();
    private RecyclerView recyclerView;
    private Realm realmInstance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do2);

        realmInstance = Realm.getDefaultInstance();

        recyclerView = (RecyclerView)findViewById(R.id.task_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ToDo2Activity.this);
        RealmResults<TaskModel> toDoItems = realmInstance.where(TaskModel.class).findAll();
        ArrayList<TaskModel> results = new ArrayList<>();
        results.addAll(toDoItems);
        Log.d("Realm count", String.valueOf(realmInstance.where(TaskModel.class).count()));
        Log.d("Arraylist Count" , String.valueOf(results.size()));
        RealmAdapter rAdapter = new RealmAdapter(ToDo2Activity.this, results , recyclerView,ToDo2Activity.this);
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setAdapter(rAdapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_add_task){
            finish();
            Intent addTaskIntent = new Intent(ToDo2Activity.this, AddTaskActivity.class);
            startActivity(addTaskIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmInstance.close();
    }
}
