package com.example.vaishnavi.todo;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.vaishnavi.todo.adapter.CustomArrayAdapter;
import com.example.vaishnavi.todo.adapter.TaskObject;
import com.example.vaishnavi.todo.helper.CustomApplication;
import com.example.vaishnavi.todo.realm.TaskModel;
import java.util.Calendar;
import io.realm.Realm;
public class EditTaskActivity extends AppCompatActivity {
    private static final String TAG = EditTaskActivity.class.getSimpleName();
    private EditText taskName;
    private EditText taskDescription;
    private Spinner taskCategory;
    private static EditText taskDueDate;
    private static EditText taskDueTime;
    private CheckBox reminder;
    private boolean isAlarmSet = false;
    private String selectedCategory;
    private static String selectedTime;
    private static String selectedDate;
    private Realm realm;
    private TaskObject taskObject;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_task);
        setTitle("Edit task");
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        realm = Realm.getDefaultInstance();
        String taskStringObject = getIntent().getExtras().getString("TASK_OBJECT");
        taskObject = ((CustomApplication)getApplication()).getGsonObject().fromJson(taskStringObject, TaskObject.class);

        taskName = (EditText)findViewById(R.id.add_task_name);
        taskDescription = (EditText)findViewById(R.id.add_task_description);
        taskDueDate = (EditText)findViewById(R.id.add_task_ending);
        taskDueTime = (EditText)findViewById(R.id.add_task_ending_time);
        reminder = (CheckBox)findViewById(R.id.set_task_alarm);
        if(!TextUtils.isEmpty(taskObject.getName())){
            taskName.setText(taskObject.getName());
        }
        if(!TextUtils.isEmpty(taskObject.getDescription())){
            taskDescription.setText(taskObject.getDescription());
        }
        if(taskObject.getReminder()){
            reminder.setChecked(true);
        }else{
            reminder.setChecked(false);
        }
        if(!TextUtils.isEmpty(taskObject.getDateTime())){
            String[] dateTimes = taskObject.getDateTime().split(" ");
            taskDueDate.setText(dateTimes[0]);
            taskDueTime.setText(dateTimes[1]);
        }
        reminder.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    isAlarmSet = true;
                }else{
                    isAlarmSet = false;
                }
            }
        });

        // populate task category to the spinner object
   /*     taskCategory = (Spinner)findViewById(R.id.select_category);
        CustomArrayAdapter mArrayAdapter = new CustomArrayAdapter(EditTaskActivity.this, R.layout.spinner_list, getResources().getStringArray(R.array.select_task_category));
        mArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        taskCategory.setAdapter(mArrayAdapter);
        taskCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selectedCategory = (String)adapterView.getItemAtPosition(i);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });*/
        //add task date


        ImageView addTaskDate = (ImageView)findViewById(R.id.add_task_date);
        addTaskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerFragment().show(getSupportFragmentManager(), "Edit Task Date");
            }
        });
        // delete task date
        ImageView deleteTaskDate = (ImageView)findViewById(R.id.delete_task_date);
        deleteTaskDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskDueDate.setText("");
            }
        });
        ImageView addTaskTime = (ImageView)findViewById(R.id.add_task_time);
        addTaskTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new TimePicker().show(getSupportFragmentManager(), "Edit Task Time");
            }
        });
        ImageView deleteTaskTime = (ImageView)findViewById(R.id.delete_task_time);
        deleteTaskTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                taskDueTime.setText("");
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_task_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_add_task){
            //Edit and save task to database.
            validateInputAndSave();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void validateInputAndSave() {
        String name = taskName.getText().toString();
        String description = taskDescription.getText().toString();
        String dueDate = taskDueDate.getText().toString();
        String dueTime = taskDueTime.getText().toString();
        if (TextUtils.isEmpty(name) || TextUtils.isEmpty(description)) {
            Toast.makeText(EditTaskActivity.this, R.string.invalid_input_values, Toast.LENGTH_LONG).show();
        } else if (TextUtils.isEmpty(dueDate) || TextUtils.isEmpty(dueTime)) {
            Toast.makeText(EditTaskActivity.this, R.string.task_date_and_time, Toast.LENGTH_LONG).show();
        } else {
            //add task to realm database
            long currentRowId = getLastInsertedRowId();
            String[] databaseValues = new String[]{name, description, dueDate, dueTime};
            addNewTaskToRealmDatabase(databaseValues, isAlarmSet);
        }
    }
    private void addNewTaskToRealmDatabase(final String[] columnValues, final boolean isAlarm){
        //Insert to realm database
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                TaskModel mModel = realm.where(TaskModel.class).equalTo("id", taskObject.getId()).findFirst();
                mModel.setName(columnValues[0]);
                mModel.setDescription(columnValues[1]);
                mModel.setDateTime(columnValues[2] + " " + columnValues[3]);
                mModel.setReminder(isAlarm);
            }
        });
        resetViewInput();
        finish();
        Intent addTaskIntent = new Intent(EditTaskActivity.this, ToDo2Activity.class);
        startActivity(addTaskIntent);
    }

    public static class TimePicker extends DialogFragment implements TimePickerDialog.OnTimeSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);
            return new TimePickerDialog(getActivity(), this, hour, minute, DateFormat.is24HourFormat(getActivity()));
        }
        @Override
        public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
            selectedTime =  String.valueOf(hourOfDay) + ":" + String.valueOf(minute);
            taskDueTime.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
        }
    }
    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }
        public void onDateSet(DatePicker view, int year, int month, int day) {
            selectedDate = String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day);
            taskDueDate.setText(String.valueOf(year) + "-" + String.valueOf(month) + "-" + String.valueOf(day));
        }
    }
    private long getLastInsertedRowId(){
        //long id = realm.where(TaskModel.class).max("id").longValue();
        long id=realm.where(TaskModel.class).count();
        return id + 1;
    }
    private void resetViewInput(){
        taskName.setText("");
        taskDescription.setText("");
        taskDueDate.setText("");
        taskDueTime.setText("");
        reminder.setChecked(false);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        realm.close();
    }
}
