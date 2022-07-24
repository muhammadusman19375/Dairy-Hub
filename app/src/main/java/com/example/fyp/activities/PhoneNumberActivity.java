package com.example.fyp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.fyp.R;
import com.example.fyp.models.user;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class PhoneNumberActivity extends AppCompatActivity {
    private EditText phoneNumber;
    private Button btn_getOtp;
    private ProgressBar progressBar;
    FirebaseAuth auth;
    FirebaseDatabase database;
    ArrayList<user> users;
    String role;
    ConstraintLayout mainLayout;
    ProgressBar progress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_number);

        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        mainLayout = findViewById(R.id.mainLayout);
        progress = findViewById(R.id.progressbar);
        mainLayout.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);

        Log.d("TAG", "onCreate: "+auth.getCurrentUser());

        users = new ArrayList<>();

        if (auth.getCurrentUser()!=null) {
            database.getReference().child("users").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    users.clear();
                    for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                        user user = snapshot1.getValue(com.example.fyp.models.user.class);
                        users.add(user);
                        role = "";

                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                        String phone = firebaseUser.getPhoneNumber();
                        Log.d("TAG", "onDataChange: " + firebaseUser.getPhoneNumber());
                        for (int i = 0; i < users.size(); i++) {
                            if (phone.equals(users.get(i).getPhoneNumber())) {
                                role = users.get(i).getRole();
                                if (role.equalsIgnoreCase("Doctor")) {
                                    Intent intent = new Intent(PhoneNumberActivity.this, DoctorauthActivity.class);
                                    intent.putExtra("role", "doctor");
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Intent intent = new Intent(PhoneNumberActivity.this, HomeActivity.class);
                                    intent.putExtra("role", "dairy farmer");
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }else{
            mainLayout.setVisibility(View.VISIBLE);
            progress.setVisibility(View.GONE);
        }
        phoneNumber=findViewById(R.id.nameBox);
        btn_getOtp=findViewById(R.id.continueBtn);
        progressBar=findViewById(R.id.progressBar);



        btn_getOtp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(phoneNumber.getText().toString().trim().isEmpty()){
                    Toast.makeText(PhoneNumberActivity.this, "Enter number", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                btn_getOtp.setVisibility(View.INVISIBLE);

                String phone="+92"+phoneNumber.getText().toString().trim();

                PhoneAuthProvider.getInstance().verifyPhoneNumber(phone, 60, TimeUnit.SECONDS, PhoneNumberActivity.this,
                        new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                            @Override
                            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                                progressBar.setVisibility(View.GONE);
                                btn_getOtp.setVisibility(View.VISIBLE);
                            }

                            @Override
                            public void onVerificationFailed(@NonNull FirebaseException e) {
                                progressBar.setVisibility(View.GONE);
                                btn_getOtp.setVisibility(View.VISIBLE);
                                Toast.makeText(PhoneNumberActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                                progressBar.setVisibility(View.GONE);
                                btn_getOtp.setVisibility(View.VISIBLE);
                                Intent intent=new Intent(getApplicationContext(),OTPActivity.class);
                                intent.putExtra("mobile", phoneNumber.getText().toString());
                                intent.putExtra("VerificationId",verificationId);
                                startActivity(intent);
                            }
                        }
                );


            }
        });

    }

}
