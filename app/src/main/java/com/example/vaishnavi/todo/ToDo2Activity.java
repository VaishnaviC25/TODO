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

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class ToDo2Activity extends AppCompatActivity  {
    private static final String TAG = ToDo2Activity.class.getSimpleName();
    private RecyclerView recyclerView;
    private Realm realmInstance;
    private TaskModel taskModel;
    private String selectedCategory;
    private EditText addTaskName;
    private EditText addTaskDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do2);

       // super.onCreate();



        realmInstance = Realm.getDefaultInstance();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add new quick task
                addTaskDialog();
            }
        });

        recyclerView = (RecyclerView)findViewById(R.id.task_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ToDo2Activity.this);
       // recyclerView.setLayoutManager(linearLayoutManager);

        RealmResults<TaskModel> toDoItems = realmInstance.where(TaskModel.class).findAllAsync();
        RealmAdapter rAdapter = new RealmAdapter(ToDo2Activity.this, toDoItems);
       // RealmAdapter rAdapter = new RealmAdapter(ToDo2Activity.this, getArr1(), recyclerView,this);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(rAdapter);
    }

 /*   public ArrayList<TaskModel> getArr1() {
        ArrayList<TaskModel> arr1 = new ArrayList<>();
        realmInstance = Realm.getDefaultInstance();
        RealmResults<TaskModel> toDoItems = realmInstance.where(TaskModel.class).findAllAsync();
        for (int i=0;i<toDoItems.size();i++){
            TaskModel taskModel = toDoItems.get(i);
            arr1.add(taskModel);
        }

        return arr1;
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.task_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_add_task){
            Intent addTaskIntent = new Intent(ToDo2Activity.this, AddTaskActivity.class);
            startActivity(addTaskIntent);
        }
        return super.onOptionsItemSelected(item);
    }
    private void addTaskDialog(){
        LayoutInflater inflater = LayoutInflater.from(this);
        View subView = inflater.inflate(R.layout.activity_add_task, null);
          addTaskName = (EditText)subView.findViewById(R.id.add_task_name);
         addTaskDescription = (EditText)subView.findViewById(R.id.add_task_description);


        //Spinner addTaskCategory = (Spinner)subView.findViewById(R.id.select_category);
        /*addTaskCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCategory = (String)adapterView.getItemAtPosition(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });*/
       /* CustomArrayAdapter mArrayAdapter = new CustomArrayAdapter(ToDo2Activity.this, R.layout.spinner_list, getResources().getStringArray(R.array.select_task_category));
        mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addTaskCategory.setAdapter(mArrayAdapter);*/

       String taskName = addTaskName.getText().toString();
       String taskDescription = addTaskDescription.getText().toString();




        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add a new task");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("ADD TASK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(TextUtils.isEmpty(taskName) || TextUtils.isEmpty(taskDescription)){
                    Toast.makeText(ToDo2Activity.this, "All fields must be filled", Toast.LENGTH_LONG).show();
                }
                else{
                   // final long rowId = getLastInsertedRowId();
                    // add the new task to Realm Database

                    //realmInstance.beginTransaction();
                Log.d("task name", taskName);
                Log.d("desc",taskDescription);

                    TaskModel tModel = new TaskModel();
                    tModel.setId((int) realmInstance.where(TaskModel.class).count());
                    tModel.setName(taskName);
                    tModel.setDescription(taskDescription);

                    realmInstance.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                           realm.copyToRealmOrUpdate(tModel);
                        }
                    });
                    Toast.makeText(ToDo2Activity.this,"data copied sucessfully",Toast.LENGTH_LONG).show();
                }
            }
        });



        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(ToDo2Activity.this, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }
    private long getLastInsertedRowId(){
        long id = realmInstance.where(TaskModel.class).max("id").longValue();
        return id + 1;
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realmInstance.close();
    }
}
