package com.example.iet_events.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.example.iet_events.MainActivity.EMAIL;
import static com.example.iet_events.MainActivity.NAME;
import static com.example.iet_events.MainActivity.PHONE;
import static com.example.iet_events.MainActivity.ROLE;
import static com.example.iet_events.MainActivity.USER_ID;
import static com.example.iet_events.fragments.AddEventFragment.IMAGE_CODE;

public class ProfileFragment extends Fragment {

    @BindView(R.id.profile_image) CircleImageView profile_image;
    @BindView(R.id.profile_image_edit) ImageView profile_image_edit;
    @BindView(R.id.profile_name) TextView profile_name;
    @BindView(R.id.profile_email_text) TextView profile_email_text;
    @BindView(R.id.profile_phone_text) TextView profile_phone_text;
    @BindView(R.id.profile_role_text) TextView profile_role_text;
    @BindView(R.id.profile_sec_role) TextView profile_sec_role;
    @BindView(R.id.profile_timetable_text) TextView profile_timetable_text;
    @BindView(R.id.update_button) Button update_button;
    @BindView(R.id.profile_progress) ProgressBar profile_progress;

    private Uri profileImageURI;
    private SharedPreferences loginPrefs;

    public ProfileFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        ButterKnife.bind(this, root);
        loginPrefs = getActivity().getSharedPreferences("LoginInfo", MODE_PRIVATE);

        profile_name.setText(NAME);
        profile_email_text.setText(EMAIL);
        profile_role_text.setText(ROLE);
        profile_phone_text.setText(PHONE);

        String profileURL = loginPrefs.getString("Photo", null);
        if(!profileURL.equals("null"))
            Glide.with(getContext()).load(Uri.parse(profileURL)).into(profile_image);
        else
            Glide.with(getContext()).load(R.drawable.profile_image).into(profile_image);

        profile_image_edit.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT>= Build.VERSION_CODES.M){
                if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},101);
                }else{
                    bringImageSelection();
                }
            }else{
                bringImageSelection();
            }
        });

        update_button.setOnClickListener(v -> {
            update_button.setEnabled(false);
            update_button.setAlpha(0.75f);
            profile_progress.setVisibility(View.VISIBLE);
            uploadProfileImageToStorage(v);
        });

        return root;
    }

    private void bringImageSelection() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_CODE);
    }

    private void cropImage(){
        CropImage.activity(profileImageURI)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1,1)
                .start(getContext(),this);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == IMAGE_CODE && resultCode == RESULT_OK && data != null) {
            profileImageURI = data.getData();
            cropImage();
        } else if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK) {
                Glide.with(getContext()).load(result.getUri()).into(profile_image);
                profileImageURI = result.getUri();
                update_button.setVisibility(View.VISIBLE);
            }
        } else {
            Toast.makeText(getContext(), "Please select a image", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 101) {
            if (permissions[0].equals(Manifest.permission.READ_EXTERNAL_STORAGE) && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                bringImageSelection();
            } else {
                Toast.makeText(getContext(), "Please provide the permissions!",Toast.LENGTH_LONG).show();
            }
        }
    }

    private void uploadProfileImageToStorage(View v) {
        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference storagePath = mStorageRef.child("User Profile Photos").child(USER_ID);
        storagePath.putFile(profileImageURI)
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

                    SharedPreferences.Editor Ed = loginPrefs.edit();
                    Ed.putString("Photo", downloadUrl);
                    Ed.commit();

                    FirebaseDatabase.getInstance().getReference("Users").child(USER_ID).child("Profile").setValue(downloadUrl)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {

                                Snackbar.make(v, "Profile Photo Uploaded", Snackbar.LENGTH_SHORT).show();
                                update_button.setVisibility(View.GONE);

                            } else {
                                String error = task.getException().getMessage();
                                Toast.makeText(getContext(), "(FIREBASE Error): " + error, Toast.LENGTH_SHORT).show();
                            }
                            profile_progress.setVisibility(View.GONE);
                            update_button.setEnabled(true);
                            update_button.setAlpha(1);
                        }
                    });
                }else{
                    update_button.setEnabled(true);
                    update_button.setAlpha(1);
                    profile_progress.setVisibility(View.GONE);
                }
            }
        });

    }
}