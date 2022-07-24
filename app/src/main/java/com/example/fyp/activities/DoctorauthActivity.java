package com.example.fyp.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp.R;
import com.example.fyp.models.ImagesUploadModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class DoctorauthActivity extends AppCompatActivity {
    private ImageView recent_pic,idcard,liscence;
    private EditText et_pvmcnumber;
    private Button upload;
    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    String image1,image2,image3;
    Uri selectedImage,selectedImage1,selectedImage2;
    StorageReference storageReference;
    private Dialog myDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctorauth);
        recent_pic=findViewById(R.id.recent_pic);
        idcard=findViewById(R.id.idcard);
        liscence=findViewById(R.id.liscence);
        et_pvmcnumber=findViewById(R.id.et_pvmcnumber);
        upload=findViewById(R.id.upload);
        myDialog = new Dialog(this);

        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();
        auth=FirebaseAuth.getInstance();

        myDialog.setContentView(R.layout.verification_dialog);
        myDialog.setCancelable(false);
        getDoctorVerification();
        recent_pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 0);
            }
        });
        idcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
            }
        });
        liscence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);
            }
        });
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadImage();
            }
        });
    }

    private void uploadImage() {
        if (selectedImage!= null&&selectedImage1!=null&&selectedImage2!=null) {
            ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();
            ImagesUploadModel imagesUploadModel = new ImagesUploadModel();

                Handler handler = new Handler();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
//                        imagesUploadModel.setSelectedImage(base64String(selectedImage));
//                        imagesUploadModel.setSelectedImage1(base64String(selectedImage1));
//                        imagesUploadModel.setSelectedImage2(base64String(selectedImage2));
                        imagesUploadModel.setPymcNumber(et_pvmcnumber.getText().toString());
                        imagesUploadModel.setVerified(false);
                        imagesUploadModel.setUid(auth.getCurrentUser().getUid());
                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference yourRef = rootRef.child("DoctorAuth").push();
                        yourRef.setValue(imagesUploadModel);

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                myDialog.show();
                                progressDialog.dismiss();
                            }
                        });
                    }
                }).start();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getData()!=null){
            switch (requestCode){
                case 0:
                    recent_pic.setImageURI(data.getData());
                    selectedImage = data.getData();
                    Log.d("TAG", "onActivityResult: "+selectedImage.toString());
                    break;
                case 1:
                    idcard.setImageURI(data.getData());
                    selectedImage1 = data.getData();
                    break;
                case 2:
                    liscence.setImageURI(data.getData());
                    selectedImage2 = data.getData();
                    break;
            }
        }
    }
    public void getDoctorVerification(){
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        rootRef.child("DoctorAuth").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                    ImagesUploadModel imagesUploadModel = snapshot1.getValue(ImagesUploadModel.class);
                    if (snapshot.exists()) {
                        if (auth.getCurrentUser().getUid().equalsIgnoreCase(imagesUploadModel.getUid())) {
                            if (snapshot1.hasChild("verified")) {
                                myDialog.show();
                            } else {
                                Intent intent = new Intent(DoctorauthActivity.this, ChatListActivity.class);
                                intent.putExtra("role", "Doctor");
                                startActivity(intent);
                                finish();
                            }
                        }
                    }else {
                        myDialog.dismiss();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private String base64String(Uri uri){
        Bitmap bitmap = null;
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        byte[] byteArray = outputStream.toByteArray();

        //Use your Base64 String as you wish
        String encodedString = Base64.encodeToString(byteArray, Base64.DEFAULT);
        Log.d("TAG", "uploadImage: "+encodedString);
        return encodedString;
    }
}