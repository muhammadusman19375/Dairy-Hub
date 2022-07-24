package com.example.fyp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp.R;
import com.example.fyp.Utils;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class LoginActivity extends AppCompatActivity {
    private EditText et_email,et_password;
    private TextView tv_forgot,tv_new;
    private CheckBox checkbox;
    private Button button;
    private FirebaseFirestore db;
    private Utils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_email=findViewById(R.id.et_email);
        et_password=findViewById(R.id.et_password);
        tv_forgot=findViewById(R.id.tv_forgot);
        tv_new=findViewById(R.id.tv_new);
        checkbox=findViewById(R.id.checkbox);
        button=findViewById(R.id.button);
        db=FirebaseFirestore.getInstance();
        utils=new Utils(LoginActivity.this);

        //New user intent
        tv_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

        //Action Bar of this page

        //Checkbox code
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    et_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
                else{
                    et_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        //Login button functionality
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(et_email.getText().toString().isEmpty()){
                    et_email.setError("Required");
                    if(et_password.getText().toString().isEmpty()){
                        et_password.setError("Required");
                    }
                }
                else{
                    getData();
                }
            }
        });
    }

    private void getData() {
        db.collection("Farmer").whereEqualTo("Email",et_email.getText().toString().trim())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                if(document.getString("Password").equals(et_password.getText().toString())){
                                    Intent intent=new Intent(LoginActivity.this,HomeActivity.class);
                                    startActivity(intent);
                                    utils.putToken(document.getId().toString().trim());
                                }
                                else{
                                    Toast.makeText(LoginActivity.this, "Incorrect Email or Password", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                    }
                });
    }
}