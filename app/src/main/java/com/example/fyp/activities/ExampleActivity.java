package com.example.fyp.activities;

import static com.example.fyp.utils.Methods.getDateDiff;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp.databinding.ActivityExampleBinding;
import com.example.fyp.models.UsersModel;
import com.example.fyp.utils.Methods;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ExampleActivity extends AppCompatActivity {
   ActivityExampleBinding binding;
    final Calendar myCalendar= Calendar.getInstance();

    FirebaseAuth auth;
    FirebaseDatabase database;
    FirebaseStorage storage;
    Uri selectedImage;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityExampleBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        dialog = new ProgressDialog(this);
        dialog.setMessage("Updating profile...");
        dialog.setCancelable(false);

        database=FirebaseDatabase.getInstance();
        storage=FirebaseStorage.getInstance();
        auth=FirebaseAuth.getInstance();

        binding.imageCow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(ExampleActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        binding.save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//
                UsersModel usersModel = new UsersModel();
                usersModel.setCowname(binding.cowname.getText().toString());
                usersModel.setCowbreed(binding.cowbreed.getText().toString());
                usersModel.setParturition(binding.parturition.getText().toString());
                usersModel.setDate(binding.date.getText().toString());
                int selectedId = binding.radioGroup2.getCheckedRadioButtonId();

                RadioButton radioButton=findViewById(selectedId);
                BitmapDrawable drawable = (BitmapDrawable) binding.imageCow.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                String imageUrl= Methods.encodeBitmapTobase64(bitmap);
                usersModel.setImage_cow(imageUrl);
                usersModel.setInsemType(radioButton.getText().toString());
                Date date = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat(" dd/MM/yyyy");
                String todayDate = df.format(date);
                String insemDate = Methods.insemDate(binding.date.getText().toString());
                int dateDifference = (int) getDateDiff(new SimpleDateFormat("dd/MM/yyyy"), todayDate, insemDate);
                usersModel.setDayleft(String.valueOf(dateDifference));

                Handler handler = new Handler();
//                Runnable runnable = new Runnable() {
//                    @Override
//                    public void run() {
                        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference yourRef = rootRef.child("cows").push();
                        yourRef.setValue(usersModel);
                        dialog.show();
                        Log.d("TAG", "run: adding");

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                dialog.dismiss();
                                Intent intent = new Intent(ExampleActivity.this,inseminationActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
//                    }
//                };

            }
        });


        binding.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.datePicker.setVisibility(View.VISIBLE);
                binding.datePicker.init(myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int i, int i1, int i2) {
                        int month = datePicker.getMonth()+1;
                        binding.date.setText(datePicker.getDayOfMonth()+"/"+month+"/"+datePicker.getYear());
                        Date date = Calendar.getInstance().getTime();
                        SimpleDateFormat df = new SimpleDateFormat(" dd/MM/yyyy");
                        String todayDate = df.format(date);
                        String insemDate = Methods.insemDate(binding.date.getText().toString());

                        int dateDifference = (int) getDateDiff(new SimpleDateFormat("dd/MM/yyyy"), todayDate, insemDate);
                        binding.dayleft.setText(String.valueOf(dateDifference));
                        datePicker.setVisibility(View.GONE);
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data.getData()!=null){
            binding.imageCow.setImageURI(data.getData());
            selectedImage = data.getData();
        }
    }
}