package com.example.iet_events.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.iet_events.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMeetingFragment extends Fragment{

    @BindView(R.id.date_select_btn) TextView date_select_btn;
    @BindView(R.id.time_select_btn) TextView time_select_btn;
    @BindView(R.id.meeting_desc_text) EditText meeting_desc_text;
    @BindView(R.id.add_meeting_btn) Button add_meeting_btn;

    int hourUpdate, minuteUpdate;

    public AddMeetingFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_meeting, container, false);

        ButterKnife.bind(this, root);

        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);
        int HOUR = calendar.get(Calendar.HOUR_OF_DAY);
        int MINUTE = calendar.get(Calendar.MINUTE);

        boolean[] isDateTimeSet = {false, false};

        date_select_btn.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                    Calendar dateCalendar = Calendar.getInstance();
                    dateCalendar.set(Calendar.YEAR, year);
                    dateCalendar.set(Calendar.MONTH, month);
                    dateCalendar.set(Calendar.DATE, date);
                    date_select_btn.setText(DateFormat.format("EEEE, MMM d yyyy", dateCalendar).toString());
                    isDateTimeSet[0] = true;
                }
            }, YEAR, MONTH, DATE);

            datePickerDialog.show();
        });

        time_select_btn.setOnClickListener(view -> {
            TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                    hourUpdate = hour;
                    minuteUpdate = minute;
                    Calendar timeCalendar = Calendar.getInstance();
                    timeCalendar.set(Calendar.HOUR_OF_DAY, hour);
                    timeCalendar.set(Calendar.MINUTE, minute);
                    time_select_btn.setText(DateFormat.format("h:mm aa", timeCalendar).toString());
                    isDateTimeSet[1] = true;
                }
            }, HOUR, MINUTE, false);
            timePickerDialog.updateTime(hourUpdate, minuteUpdate);
            timePickerDialog.show();
        });

        add_meeting_btn.setOnClickListener(v -> {
            String meeting_desc = meeting_desc_text.getText().toString();
            if(meeting_desc.length() != 0 && isDateTimeSet[0] && isDateTimeSet[1]){
                Map<String, String> meetingMap = new HashMap<>();
                meetingMap.put("Description",meeting_desc);
                meetingMap.put("Date",date_select_btn.getText().toString());
                meetingMap.put("Time",time_select_btn.getText().toString());
                FirebaseDatabase.getInstance().getReference("Meetings").child(String.valueOf(System.currentTimeMillis()))
                        .setValue(meetingMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(),"Meeting added and Notified!",Toast.LENGTH_LONG).show();
                        getActivity().finish();
                    }
                });
            }else
                Toast.makeText(getContext(),"Please choose all the details",Toast.LENGTH_LONG).show();
        });

        return root;
    }
}