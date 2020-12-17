package com.example.iet_events.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iet_events.R;
import com.example.iet_events.models.Meeting;

import java.util.List;

public class MeetingAdapter extends RecyclerView.Adapter<MeetingAdapter.ViewHolder>{

    private Context context;
    private List<Meeting> meetingList;

    public MeetingAdapter(List<Meeting> meetingList) {
        this.meetingList = meetingList;
    }

    @NonNull
    @Override
    public MeetingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.meeting_card_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingAdapter.ViewHolder holder, int position) {
        Meeting meet = meetingList.get(position);
        holder.meet_desc_item.setText(meet.getDescription());
        holder.meet_time_item.setText(meet.getDate() + ", " + meet.getTime());
        holder.meet_link_item.setText(meet.getLocation_Link());
    }

    @Override
    public int getItemCount() {
        return meetingList != null? meetingList.size(): 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView meet_desc_item, meet_time_item, meet_link_item;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            meet_desc_item = itemView.findViewById(R.id.meet_desc_item);
            meet_time_item = itemView.findViewById(R.id.meet_time_item);
            meet_link_item = itemView.findViewById(R.id.meet_link_item);
        }
    }
}
