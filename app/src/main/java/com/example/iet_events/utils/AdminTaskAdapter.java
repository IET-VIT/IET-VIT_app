package com.example.iet_events.utils;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iet_events.R;
import com.example.iet_events.models.Users;

import java.util.List;

import static com.example.iet_events.fragments.AdminTaskFragment.uploadTaskUserList;

public class AdminTaskAdapter extends RecyclerView.Adapter<AdminTaskAdapter.ViewHolder> {

    private Context context;
    private List<Users> fullList;

    public AdminTaskAdapter(List<Users> fullList){
        this.fullList = fullList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.user_card_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users user = fullList.get(position);

        if(uploadTaskUserList.contains(user.getName())){
            holder.checkbox_card_item.setChecked(true);
        }else{
            holder.checkbox_card_item.setChecked(false);
        }

        holder.name_card_item.setText(user.getName());
        holder.role_card_item.setText(user.getRole());
        holder.checkbox_card_item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    uploadTaskUserList.add(user.UserID);
                }else {
                    uploadTaskUserList.remove(user.UserID);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return fullList != null ? fullList.size(): 0;
    }

    public void notify(List<Users> list){
        this.fullList = list;
        notifyDataSetChanged();
        Log.e("info",uploadTaskUserList.size() + " Hi");
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name_card_item, role_card_item;
        private CheckBox checkbox_card_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_card_item = itemView.findViewById(R.id.name_card_item);
            role_card_item = itemView.findViewById(R.id.role_card_item);
            checkbox_card_item = itemView.findViewById(R.id.checkbox_card_item);
        }
    }
}
