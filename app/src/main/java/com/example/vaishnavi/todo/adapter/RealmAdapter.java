package com.example.vaishnavi.todo.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
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

    private ToDo2Activity activity;
    public View itemView;
    public Context context;
    private ArrayList<TaskModel> arrayList;
    public RecyclerView recyclerView;

    public RealmAdapter(Context context,ArrayList<TaskModel> arrayList, RecyclerView recyclerView,ToDo2Activity activity) {
        this.context = context;
        this.arrayList = arrayList;
        this.recyclerView = recyclerView;
        this.activity = activity;
    }

    @Override
    public TaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_layout, parent, false);
        itemView = view;
        return new TaskViewHolder(view);
    }
    @Override
    public void onBindViewHolder(TaskViewHolder holder, int position) {


        TaskModel mTaskModel = arrayList.get(position);
        final TaskObject taskModel = new TaskObject(mTaskModel.getId(), mTaskModel.getName(), mTaskModel.getDescription(),
                mTaskModel.getDateTime(), mTaskModel.getReminder());

        Log.d("Name in bind",taskModel.getName());

        holder.dot.setText(Html.fromHtml("&#8226;"));
        holder.taskName.setText(mTaskModel.getName());
        if(!TextUtils.isEmpty(mTaskModel.getDateTime())){
            holder.taskDueDate.setText(mTaskModel.getDateTime());
        }else{
            holder.taskDueDate.setText(R.string.no_time);
        }
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activity.finish();
                Intent viewTaskIntent = new Intent(context, ViewTaskActivity.class);
                //use Gson to serialize TaskObject to string
                String serializedTaskObject = ((CustomApplication)((Activity)context).getApplication()).getGsonObject().toJson(taskModel);
                viewTaskIntent.putExtra("TASK_OBJECT", serializedTaskObject);
                context.startActivity(viewTaskIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return  arrayList != null ? arrayList.size() : 0;
    }

    class TaskViewHolder extends RecyclerView.ViewHolder{
        public TextView taskName;
        public TextView taskDueDate;
        public TextView dot;
        public TaskViewHolder(View itemView) {
            super(itemView);
            taskName = (TextView)itemView.findViewById(R.id.task_name);
            taskDueDate = (TextView)itemView.findViewById(R.id.task_date_time);
            dot = itemView.findViewById(R.id.dot);
        }
    }
}