package com.example.vaishnavi.todo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.example.vaishnavi.todo.adapter.TaskObject;
import com.example.vaishnavi.todo.helper.CustomApplication;
import com.example.vaishnavi.todo.realm.TaskModel;
import io.realm.Realm;
public class ViewTaskActivity extends AppCompatActivity {
    private static final String TAG = ViewTaskActivity.class.getSimpleName();
    private Realm viewRealm;
    private TaskObject viewTaskObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_task);
        setTitle("View Task");
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        viewRealm = Realm.getDefaultInstance();
        String taskObject = getIntent().getExtras().getString("TASK_OBJECT");
        viewTaskObject = ((CustomApplication)getApplication()).getGsonObject().fromJson(taskObject, TaskObject.class);
        TextView viewTaskName = (TextView)findViewById(R.id.view_task_name);
        TextView viewTaskDescription = (TextView)findViewById(R.id.view_task_description);
      //  TextView viewTaskCategory = (TextView)findViewById(R.id.view_task_category);
        TextView viewTaskDueDate = (TextView)findViewById(R.id.view_task_due_date);
        TextView reminderState = (TextView)findViewById(R.id.alarm_state);
        if(viewTaskObject != null){
            viewTaskName.setText(viewTaskObject.getName());
            viewTaskDescription.setText(viewTaskObject.getDescription());
      //      viewTaskCategory.setText(viewTaskObject.getCategory());
            if(TextUtils.isEmpty(viewTaskObject.getDateTime())){
                viewTaskDueDate.setText("Due date not set");
            }else{
                viewTaskDueDate.setText(viewTaskObject.getDateTime());
            }
            if(viewTaskObject.getReminder()){
                reminderState.setText("Reminder alarm is on");
            }else{
                reminderState.setText("Reminder alarm is off");
            }
        }else{
            Toast.makeText(ViewTaskActivity.this, "Something went wrong with this task data", Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_task_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_delete_task){
            deleteSingleRowInDatabase(viewTaskObject.getId());
            finish();
            Intent listTaskIntent = new Intent(ViewTaskActivity.this, ToDo2Activity.class);
            startActivity(listTaskIntent);
            return true;
        }
        if(id == R.id.action_edit_task){
            finish();
            Intent editTaskIntent = new Intent(ViewTaskActivity.this, EditTaskActivity.class);
            String editObject = ((CustomApplication)getApplication()).getGsonObject().toJson(viewTaskObject);
            editTaskIntent.putExtra("TASK_OBJECT", editObject);
            startActivity(editTaskIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void deleteSingleRowInDatabase(final int id){
        viewRealm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                viewRealm.where(TaskModel.class).equalTo("id", id).findFirst().deleteFromRealm();
            }
        });
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewRealm.close();
    }
}