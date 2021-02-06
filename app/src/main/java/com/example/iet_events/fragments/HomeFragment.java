package com.example.iet_events.fragments;

import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.example.iet_events.R;
import com.example.iet_events.database.TaskDatabase;
import com.example.iet_events.models.Meeting;
import com.example.iet_events.models.Task;
import com.example.iet_events.utils.MeetingAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.content.Context.MODE_PRIVATE;
import static com.example.iet_events.MainActivity.ROLE;
import static com.example.iet_events.MainActivity.USER_ID;

public class HomeFragment extends Fragment {

    @BindView(R.id.tasks_status_text) TextView tasks_status_text;
    @BindView(R.id.no_meeting_text) TextView no_meeting_text;
    @BindView(R.id.task_number_text) TextView task_number_text;
    @BindView(R.id.animationView) LottieAnimationView animationView;
    @BindView(R.id.loadingAnimationTask) LottieAnimationView loadingAnimationTask;
    @BindView(R.id.loadingAnimationMeeting) LottieAnimationView loadingAnimationMeeting;
    @BindView(R.id.meeting_recycler_view) RecyclerView meeting_recycler_view;

    @BindView(R.id.eventName) TextView eventName;
    @BindView(R.id.eventDate) TextView eventDate;
    @BindView(R.id.eventLogo) ImageView eventLogo;

    private SharedPreferences loginPrefs;
    private TaskDatabase taskDatabase;

    public HomeFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this, root);
        loginPrefs = getContext().getSharedPreferences("LoginInfo", MODE_PRIVATE);
        USER_ID = loginPrefs.getString("UserId", null);

        taskDatabase = TaskDatabase.getInstance(getContext());
        if(USER_ID != null) {
            fetchTasks(USER_ID);
            fetchMeetings();
            updateDisplayEvent();
        }

        return root;
    }

    public void fetchTasks(String userId){
        taskDatabase.TaskDao().clearTaskDb();
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Users");
        mRef.child(userId).child("Tasks").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    for (DataSnapshot taskSnap : snapshot.getChildren()) {
                        Task task = taskSnap.getValue(Task.class).withId(taskSnap.getKey());
                        taskDatabase.TaskDao().insertTask(task);
                    }
                }
                setUndoneTasksCount();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Database Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        String token = FirebaseInstanceId.getInstance().getToken();
        SharedPreferences.Editor Ed = loginPrefs.edit();
        if(!token.equals(loginPrefs.getString("FCM_Token", "null"))){
            FirebaseDatabase.getInstance().getReference("Users").child(userId).child("FCM_Token").setValue(token)
                    .addOnCompleteListener(task -> {
                        Ed.putString("FCM_Token", token);
                        Ed.commit();
                    });
        }
    }

    private void fetchMeetings() {
        List<String> meetingsToBeDeleted = new ArrayList<>();
        List<Meeting> meetingList = new ArrayList<>();
        int[] meetingCount = {0};
        FirebaseDatabase.getInstance().getReference("Meetings").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot meetingSnap : snapshot.getChildren()) {
                        String dateAndTime = meetingSnap.child("Date").getValue() + " " + meetingSnap.child("Time").getValue();
                        try {
                            Date date1 = new SimpleDateFormat("EEEE, MMM d yyyy h:mm aa").parse(dateAndTime);
                            if ((date1.getTime() + 490000) < System.currentTimeMillis())
                                meetingsToBeDeleted.add(meetingSnap.getKey());
                            else if(meetingSnap.child("For").getValue().equals("All") || meetingSnap.child("For").getValue().equals(ROLE)){
                                meetingList.add(meetingSnap.getValue(Meeting.class));
                                meetingCount[0] += 1;
                            }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    deleteMeetings(meetingsToBeDeleted);
                    if (meetingList.size() == 0){
                        loadingAnimationMeeting.setVisibility(View.GONE);
                        no_meeting_text.setVisibility(View.VISIBLE);
                    } else {
                        meeting_recycler_view.setVisibility(View.VISIBLE);
                        loadingAnimationMeeting.setVisibility(View.GONE);
                        MeetingAdapter meetingAdapter = new MeetingAdapter(meetingList);
                        LinearLayoutManager mLayout = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false);
                        meeting_recycler_view.setHasFixedSize(true);
                        meeting_recycler_view.setLayoutManager(mLayout);
                        meeting_recycler_view.setAdapter(meetingAdapter);
                    }
                } else {
                    loadingAnimationMeeting.setVisibility(View.GONE);
                    no_meeting_text.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Database Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void deleteMeetings(List<String> meetingsToBeDeleted) {
        for(String key : meetingsToBeDeleted){
            FirebaseDatabase.getInstance().getReference("Meetings").child(key).removeValue();
        }
    }

    private void setUndoneTasksCount() {
        int undone = taskDatabase.TaskDao().countUndoneTasks();
        task_number_text.setText(String.valueOf(undone));
        task_number_text.setVisibility(View.VISIBLE);
        if(undone == 0) {
            task_number_text.setVisibility(View.GONE);
            animationView.setVisibility(View.VISIBLE);
            tasks_status_text.setText("All Tasks Completed");
        }
        loadingAnimationTask.setVisibility(View.GONE);
    }

    private void updateDisplayEvent() {
        DatabaseReference eventRef = FirebaseDatabase.getInstance().getReference("Events");
        eventRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    for (DataSnapshot event : snapshot.getChildren()) {
                        eventName.setText(String.valueOf(event.child("Name").getValue()));
                        eventDate.setText(String.valueOf(event.child("Date").getValue()));
                        Uri eventLogoURL = Uri.parse(String.valueOf(event.child("Link").getValue()));
                        Glide.with(getContext()).load(eventLogoURL).into(eventLogo);
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Database Error : " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}