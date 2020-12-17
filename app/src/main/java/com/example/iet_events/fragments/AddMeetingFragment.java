package com.example.iet_events.fragments;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.AsyncTask;
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
import com.example.iet_events.database.UserDatabase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AddMeetingFragment extends Fragment{

    @BindView(R.id.date_select_btn) TextView date_select_btn;
    @BindView(R.id.time_select_btn) TextView time_select_btn;
    @BindView(R.id.meeting_desc_text) EditText meeting_desc_text;
    @BindView(R.id.loc_link_text) EditText loc_link_text;
    @BindView(R.id.add_meeting_btn) Button add_meeting_btn;

    int hourUpdate, minuteUpdate;
    String hourMeeting, minuteMeeting, dateMeeting;

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
                    dateMeeting = String.valueOf(DateFormat.format("d MMM", dateCalendar));
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
                    hourMeeting = String.valueOf(timeCalendar.getTime().getHours());
                    minuteMeeting = String.valueOf(timeCalendar.getTime().getMinutes());
                    isDateTimeSet[1] = true;
                }
            }, HOUR, MINUTE, false);
            timePickerDialog.updateTime(hourUpdate, minuteUpdate);
            timePickerDialog.show();
        });

        add_meeting_btn.setOnClickListener(v -> {
            String meeting_desc = meeting_desc_text.getText().toString();
            String meeting_link_text = loc_link_text.getText().toString();
            if(meeting_desc.length() != 0 && isDateTimeSet[0] && isDateTimeSet[1] && meeting_link_text.length() != 0){
                Map<String, String> meetingMap = new HashMap<>();
                meetingMap.put("Description",meeting_desc);
                meetingMap.put("Date", date_select_btn.getText().toString());
                meetingMap.put("Time", time_select_btn.getText().toString());
                meetingMap.put("Location_Link", meeting_link_text);
                FirebaseDatabase.getInstance().getReference("Meetings").child(String.valueOf(System.currentTimeMillis()))
                        .setValue(meetingMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(),"Meeting added and Notified!",Toast.LENGTH_LONG).show();
                        new sendMessage().execute(hourMeeting, minuteMeeting, dateMeeting);
                        getActivity().finish();
                    }
                });
            }else
                Toast.makeText(getContext(),"Please choose all the details",Toast.LENGTH_LONG).show();
        });

        return root;
    }

    private class sendMessage extends AsyncTask<String, Void, Void> {
        @Override
        protected Void doInBackground(String... voids) {
            try {
                final String apiKey = getString(R.string.FCM_API_KEY);
                URL url = new URL("https://fcm.googleapis.com/fcm/send");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setDoOutput(true);
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setRequestProperty("Authorization", "key=" + apiKey);
                conn.setDoOutput(true);
                JSONObject message = new JSONObject();
                message.put("registration_ids", new JSONArray(UserDatabase.getInstance(getContext()).UserDao().loadFCMTokens()));
                message.put("priority", "high");

                JSONObject notification = new JSONObject();
                notification.put("title", "Meeting Time!");
                notification.put("body", "Meeting scheduled at " + voids[0] + ":" + voids[1] + " on " + voids[2]);
                notification.put("hourMeeting", voids[0]);
                notification.put("minuteMeeting", voids[1]);
                message.put("data", notification);
                OutputStream os = conn.getOutputStream();
                os.write(message.toString().getBytes());
                os.flush();
                os.close();

                int responseCode = conn.getResponseCode();
                Log.e("Sending POST request to", String.valueOf(url));
                Log.e("Post parameters", message.toString());
                Log.e("Response Code : ", String.valueOf(responseCode));
                Log.e("Response Code : ", conn.getResponseMessage());

                BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String inputLine;
                StringBuffer response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                Log.e("Response",response.toString());

            } catch (Exception e) {
                e.printStackTrace();
                Log.e("error",e.getMessage());
            }
            return null;
        }
    }
}