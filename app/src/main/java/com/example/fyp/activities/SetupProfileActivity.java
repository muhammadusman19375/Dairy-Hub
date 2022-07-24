package com.example.fyp.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp.databinding.ActivitySetupProfileBinding;
import com.example.fyp.models.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SetupProfileActivity extends AppCompatActivity {
    ActivitySetupProfileBinding binding;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Uri selectedImage;
    ProgressDialog dialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySetupProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialog = new ProgressDialog(this);
        dialog.setMessage("Updating profile...");
        dialog.setCancelable(false);

        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();
        auth=FirebaseAuth.getInstance();

        binding.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,45);
            }
        });
        binding.continueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=binding.nameBox.getText().toString();

                if(name.isEmpty()){
                    binding.nameBox.setError("Please type a name");
                    return;
                }
                dialog.show();
                if(selectedImage!=null){
                    StorageReference reference=storage.getReference().child("Profiles").child(auth.getUid());
                    reference.putFile(selectedImage).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                            if(task.isSuccessful()){
                                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {

                                        String imageUrl=uri.toString();
                                        String uid=auth.getUid();
                                        String phone=auth.getCurrentUser().getPhoneNumber();
                                        String name=binding.nameBox.getText().toString();

                                        int selectedId=binding.role.getCheckedRadioButtonId();
                                        RadioButton roleSelected=(RadioButton)findViewById(selectedId);
                                        String role = roleSelected.getText().toString();

                                        if (name!=null&&role!=null){
                                            user user=new user(uid,name,phone,imageUrl,role);

                                            String finalRole = role;
                                            database.getReference()
                                                    .child("users")
                                                    .child(uid)
                                                    .setValue(user)
                                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                        @Override
                                                        public void onSuccess(Void unused) {
                                                            dialog.dismiss();
                                                            Intent intent;
                                                            if (finalRole.equalsIgnoreCase("Doctor")){
                                                                intent = new Intent(SetupProfileActivity.this, DoctorauthActivity.class);
                                                            }else{
                                                                intent = new Intent(SetupProfileActivity.this, HomeActivity.class);
                                                            }
                                                            startActivity(intent);
                                                            finish();

                                                        }
                                                    });
                                        }
                                    }
                                });
                            }
                        }
                    });
                }
                else{
                    String uid=auth.getUid();
                    String phone=auth.getCurrentUser().getPhoneNumber();
                    int selectedId=binding.role.getCheckedRadioButtonId();
                    RadioButton roleSelected=(RadioButton)findViewById(selectedId);
                    String role = roleSelected.getText().toString();

                    user user=new user(uid,name,phone,"No image",role);

                    String finalRole = role;
                    database.getReference()
                            .child("users")
                            .child(uid)
                            .setValue(user)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    dialog.dismiss();
                                    Intent intent;
                                    if (role.equalsIgnoreCase("Doctor")){
                                        intent = new Intent(SetupProfileActivity.this, ChatListActivity.class);
                                        intent.putExtra("role","doctor");
                                    }else{
                                        intent = new Intent(SetupProfileActivity.this, HomeActivity.class);
                                    }
                                    startActivity(intent);
                                    finish();
                                }
                            });
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            if(data.getData()!=null){
                binding.imageView.setImageURI(data.getData());
                selectedImage = data.getData();
            }
        }
    }
}