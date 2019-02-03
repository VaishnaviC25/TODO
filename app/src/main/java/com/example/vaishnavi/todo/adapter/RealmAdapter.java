package com.example.vaishnavi.todo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.vaishnavi.todo.R;
import com.example.vaishnavi.todo.ViewTaskActivity;
import com.example.vaishnavi.todo.helper.CustomApplication;
import com.example.vaishnavi.todo.realm.TaskModel;
import com.example.vaishnavi.todo.ToDo2Activity;

import java.util.ArrayList;

import io.realm.OrderedRealmCollection;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;

import static android.media.CamcorderProfile.get;

public class RealmAdapter extends RecyclerView.Adapter<RealmAdapter.TaskViewHolder>{

  /*  public RealmAdapter(ToDo2Activity activity, ArrayList<TaskModel> arrayList, RecyclerView recyclerView, Context context)
    {
        this.activity = activity;
        this.arrayList = arrayList;
        this.recyclerView = recyclerView;
        this.context = context;
    }*/

    private ToDo2Activity activity;
    public View itemView;
    public Context context;
    private ArrayList<TaskModel> arrayList;
    public RecyclerView recyclerView;

    public RealmAdapter(Context context,ArrayList<TaskModel> arrayList, RecyclerView recyclerView) {
        this.context = context;
        this.arrayList = arrayList;
        this.recyclerView = recyclerView;
    }



    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        itemView = view;
        return new TaskViewHolder(view);
    }
    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {

        //TaskModel mTaskModel = getData().get(position);
        //final TaskObject taskModel = new TaskObject(mTaskModel.getId(), mTaskModel.getName(), mTaskModel.getDescription(),
        //        mTaskModel.getDateTime(), mTaskModel.getCategory(), mTaskModel.getReminder());

        TaskModel taskModel = arrayList.get(position);

        Log.d("Name in bind",taskModel.getName());


        holder.taskName.setText(taskModel.getName());
        /*if(!TextUtils.isEmpty(taskModel.getDateTime())){
            //holder.taskDueDate.setText(taskModel.getDateTime());
        }else{
            //holder.taskDueDate.setText(R.string.no_time);
        }*/

      /*  TaskModel taskModel = arrayList.get(position);
        holder.taskName.setText(taskModel.getName());
      //  holder.taskName.setText("aaa");
        if(!TextUtils.isEmpty(taskModel.getDateTime())){
            holder.taskDueDate.setText(taskModel.getDateTime());
        }else{
            holder.taskDueDate.setText(R.string.no_time);
        }*/
      //  holder.taskCategory.setText(taskModel.getCategory());
      /*  itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewTaskIntent = new Intent(context, ViewTaskActivity.class);
                //use Gson to serialize TaskObject to string
                String serializedTaskObject = ((CustomApplication)((Activity)context).getApplication()).getGsonObject().toJson(taskModel);
                viewTaskIntent.putExtra("TASK_OBJECT", serializedTaskObject);
                context.startActivity(viewTaskIntent);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return  arrayList != null ? arrayList.size() : 0;
    }

    class TaskViewHolder extends RecyclerView.ViewHolder{
        public TextView taskName;
        public TextView taskDueDate;
   //     public TextView taskCategory;
        public TaskViewHolder(View itemView) {
            super(itemView);
            taskName = (TextView)itemView.findViewById(R.id.task_name);
            taskDueDate = (TextView)itemView.findViewById(R.id.task_date_time);
         //   taskCategory = (TextView)itemView.findViewById(R.id.task_category);
        }
    }
}