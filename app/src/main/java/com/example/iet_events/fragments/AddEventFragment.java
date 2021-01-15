package com.example.iet_events.fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.iet_events.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;
import static com.example.iet_events.MainActivity.NAME;

public class AddEventFragment extends Fragment {

    @BindView(R.id.add_event_photo) ImageView add_event_photo;
    @BindView(R.id.add_event_name) TextInputEditText add_event_name;
    @BindView(R.id.add_event_date) TextView add_event_date;
    @BindView(R.id.add_event_button) Button add_event_button;
    @BindView(R.id.add_event_progress) ProgressBar add_event_progress;

    final public static int IMAGE_CODE = 1;
    boolean[] isDatePhotoSet = {false, false};
    private Uri imageUri;
    private String eventMillis;

    public AddEventFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_add_event, container, false);

        ButterKnife.bind(this, root);

        Calendar calendar = Calendar.getInstance();
        int YEAR = calendar.get(Calendar.YEAR);
        int MONTH = calendar.get(Calendar.MONTH);
        int DATE = calendar.get(Calendar.DATE);

        add_event_date.setOnClickListener(view -> {
            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker datePicker, int year, int month, int date) {

                    Calendar dateCalendar = Calendar.getInstance();
                    dateCalendar.set(Calendar.YEAR, year);
                    dateCalendar.set(Calendar.MONTH, month);
                    dateCalendar.set(Calendar.DATE, date);
                    add_event_date.setText(DateFormat.format("EEEE, MMM d yyyy", dateCalendar).toString());
                    eventMillis = String.valueOf(dateCalendar.getTimeInMillis());
                    isDatePhotoSet[0] = true;
                }
            }, YEAR, MONTH, DATE);

            datePickerDialog.show();
        });

        add_event_photo.setOnClickListener(view -> {
            selectImage();
        });

        add_event_button.setOnClickListener(view -> {
            String eventName = add_event_name.getText().toString().trim();
            if (eventName.length() == 0)
                Snackbar.make(view, "Enter the Event name", Snackbar.LENGTH_LONG).show();
            else if (!isDatePhotoSet[0])
                Snackbar.make(view, "Set the Event date", Snackbar.LENGTH_LONG).show();
            else if (!isDatePhotoSet[1])
                Snackbar.make(view, "Set the Event logo", Snackbar.LENGTH_LONG).show();
            else {
                add_event_button.setEnabled(false);
                add_event_button.setAlpha(0.75f);
                add_event_progress.setVisibility(View.VISIBLE);
                uploadImageToStorage(view, eventName);
            }
        });

        return root;
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_CODE);
    }

    private void uploadImageToStorage(View view, String eventName) {
        String currentUNIX = String.valueOf(System.currentTimeMillis());
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference storagePath = mStorageRef.child("Event Images").child(eventName + "_" + currentUNIX);
        storagePath.putFile(imageUri)
                .continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }
                        return storagePath.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    String downloadUrl = task.getResult().toString();

                    Map<String, String> eventMap = new HashMap<>();
                    eventMap.put("Name", eventName);
                    eventMap.put("Link", downloadUrl);
                    eventMap.put("Uploaded_By", NAME);
                    eventMap.put("Date", add_event_date.getText().toString());

                    FirebaseDatabase.getInstance().getReference("Events").child(eventMillis).setValue(eventMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                Toast.makeText(getContext(), "Event Uploaded", Toast.LENGTH_SHORT).show();
                                getActivity().finish();

                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(getContext(), "(FIREBASE Error): " + error, Toast.LENGTH_SHORT).show();
                                add_event_progress.setVisibility(View.GONE);
                            }
                        }
                    });
                }else{
                    add_event_button.setEnabled(true);
                    add_event_button.setAlpha(1);
                }
            }
        });

    }

    private void cropImage(){
        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(getContext(),this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_CODE && resultCode == RESULT_OK && data != null) {
            imageUri = data.getData();
            cropImage();
        } else if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK) {
                Glide.with(getContext()).load(result.getUri()).into(add_event_photo);
                imageUri = result.getUri();
                isDatePhotoSet[1] = true;
            }
        } else {
            Toast.makeText(getContext(), "Please select a file", Toast.LENGTH_SHORT).show();
        }
    }
}