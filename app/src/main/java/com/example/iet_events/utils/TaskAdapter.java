package com.example.iet_events.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iet_events.R;

import java.util.List;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private Context context;
    private List<String> taskList;
    private List<String> roleList;

    public TaskAdapter(List<String> taskList, List<String> roleList) {
        this.taskList = taskList;
        this.roleList = roleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.task_item_card,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.task_desc.setText(taskList.get(position));
        holder.task_role.setText(roleList.get(position));
    }

    @Override
    public int getItemCount() {
        return taskList != null? taskList.size(): 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView task_desc, task_role;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            task_desc = itemView.findViewById(R.id.task_desc);
            task_role = itemView.findViewById(R.id.task_role);
        }
    }
}
