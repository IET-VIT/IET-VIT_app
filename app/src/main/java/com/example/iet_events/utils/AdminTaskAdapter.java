package com.example.iet_events.utils;

import android.content.Context;
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

import static com.example.iet_events.fragments.AdminTaskFragment.domain_spinner;
import static com.example.iet_events.fragments.AdminTaskFragment.uploadTaskUserList;

public class AdminTaskAdapter extends RecyclerView.Adapter<AdminTaskAdapter.ViewHolder> {

    private Context context;
    private List<Users> fullList;
    private boolean isSelectedAll;

    public AdminTaskAdapter(List<Users> fullList){
        this.fullList = fullList;
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

        holder.checkbox_card_item.setOnCheckedChangeListener(null);
        holder.name_card_item.setText(user.getName());
        holder.role_card_item.setText(user.getRole());

        if (!isSelectedAll){
            holder.checkbox_card_item.setChecked(false);
            holder.checkbox_card_item.setEnabled(true);
        }else {
            holder.checkbox_card_item.setChecked(true);
            holder.checkbox_card_item.setEnabled(false);
        }

        holder.checkbox_card_item.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    uploadTaskUserList.add(user);
                    domain_spinner.setEnabled(false);
                }
                else {
                    uploadTaskUserList.remove(user);
                    if(uploadTaskUserList.isEmpty())
                        domain_spinner.setEnabled(true);
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
    }

    public void selectAll(){
        isSelectedAll = true;
        notifyDataSetChanged();
    }
    public void unselectAll(){
        isSelectedAll = false;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView name_card_item;
        private final TextView role_card_item;
        private final CheckBox checkbox_card_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name_card_item = itemView.findViewById(R.id.name_card_item);
            role_card_item = itemView.findViewById(R.id.role_card_item);
            checkbox_card_item = itemView.findViewById(R.id.checkbox_card_item);
        }
    }
}
