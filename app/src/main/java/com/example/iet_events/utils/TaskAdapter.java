package com.example.iet_events.utils;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iet_events.R;
import com.example.iet_events.database.TaskDatabase;
import com.example.iet_events.models.Task;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

import static com.example.iet_events.MainActivity.USER_ID;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.ViewHolder> {

    private Context context;
    private final List<Task> taskList;

    public TaskAdapter(List<Task> taskList) {
        this.taskList = taskList;
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
        Task task = taskList.get(position);
        holder.task_desc.setText(task.getDescription());
        holder.task_role.setText(task.TaskId);

        String status = task.getStatus();
        if(status.equals("Done"))
            holder.task_item_layout.setBackgroundColor(context.getResources().getColor(R.color.paleGreen));

        holder.task_item_layout.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!status.equals("Done")) {
                    new AlertDialog.Builder(context)
                            .setMessage("Are you sure you completed this task?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    FirebaseDatabase.getInstance().getReference("Users").child(USER_ID).child("Tasks")
                                            .child(task.TaskId + "/Status").setValue("Done")
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull com.google.android.gms.tasks.Task<Void> firebaseTask) {
                                                    holder.task_item_layout.setBackgroundColor(context.getResources().getColor(R.color.paleGreen));
                                                    TaskDatabase.getInstance(context).TaskDao().updateTaskDone(task.getPrimary_key());
                                                }
                                            });
                                }
                            }).setNegativeButton("No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context, "Please be sure from next time :)", Toast.LENGTH_LONG).show();
                        }
                    })
                            .show();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return taskList != null? taskList.size(): 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView task_desc;
        private final TextView task_role;
        private final ConstraintLayout task_item_layout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            task_desc = itemView.findViewById(R.id.task_desc);
            task_role = itemView.findViewById(R.id.task_role);
            task_item_layout = itemView.findViewById(R.id.task_item_layout);
        }
    }
}
