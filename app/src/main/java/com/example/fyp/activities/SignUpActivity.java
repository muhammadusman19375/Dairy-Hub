package com.example.fyp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    private Spinner district;
    private EditText et_fname, et_lname, et_animal, et_address, et_contact, et_1password, et_confirm;
    private Button btn_signup;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        et_fname = findViewById(R.id.et_fname);
        et_lname = findViewById(R.id.et_lname);
        et_animal = findViewById(R.id.et_animal);
        et_address = findViewById(R.id.et_address);
        et_contact = findViewById(R.id.et_contact);
        et_1password = findViewById(R.id.et_1password);
        et_confirm = findViewById(R.id.et_confirm);
        btn_signup = findViewById(R.id.btn_signup);
        db = FirebaseFirestore.getInstance();

        //Action Bar og this page

        //spinner code
        district = findViewById(R.id.district);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.district, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        district.setAdapter(adapter);

        //Sign up button functionality
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_fname.getText().toString().isEmpty()) {
                    et_fname.setError("Required");
                }
                if (et_lname.getText().toString().isEmpty()) {
                    et_lname.setError("Required");
                }
                if (et_address.getText().toString().isEmpty()) {
                    et_address.setError("Required");
                }
                if (et_contact.getText().toString().isEmpty()) {
                    et_contact.setError("Required");
                }
                if (et_1password.getText().toString().isEmpty()) {
                    et_1password.setError("Required");
                }
                if (et_confirm.getText().toString().isEmpty()) {
                    et_confirm.setError("Required");
                }
                if (et_animal.getText().toString().isEmpty()) {
                    et_animal.setError("Required");
                } else if (!et_1password.getText().toString().equals(et_confirm.getText().toString())) {
                    et_confirm.setError("Password don't match");

                } else if (emailValidation()) {
                    if (passwordValidation()) {
                        saveRecord();
                    } else {
                        Toast.makeText(SignUpActivity.this, "Password validation failed", Toast.LENGTH_SHORT).show();
                    }

                }

            }

        });

    }

    private void saveRecord() {
        db.collection("Farmer Portal").whereEqualTo("Email or Phone number", et_contact.getText().toString())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            if (!task.getResult().isEmpty()) {
                                et_contact.setError("Email or Phone no already exist");
                            } else {
                                Map<String, Object> user = new HashMap<>();
                                user.put("First Name", et_fname.getText().toString());
                                user.put("Last Name", et_lname.getText().toString());
                                user.put("Address", et_address.getText().toString());
                                user.put("Number of Animals", et_animal.getText().toString());
                                user.put("Password1", et_1password.getText().toString());
                                user.put("Confirm Password", et_confirm.getText().toString());
                                user.put("Email or Phone number", et_contact.getText().toString());

// Add a new document with a generated ID
                                db.collection("Farmer Portal")
                                        .add(user)
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Toast.makeText(SignUpActivity.this, "Record Save", Toast.LENGTH_SHORT).show();

                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(SignUpActivity.this, "Connection Error", Toast.LENGTH_SHORT).show();

                                            }
                                        });
                            }
                        }
                    }
                });
    }


    private boolean passwordValidation() {
        String passwordPattern = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        String password = et_1password.getText().toString().trim();

        if (password.matches(passwordPattern) && password.length() > 7) {
            return true;

        } else {
            Toast.makeText(SignUpActivity.this, "Password must contain Alphabet,Numeric,Symbol", Toast.LENGTH_SHORT).show();
            return false;
        }
    }


    private boolean emailValidation() {
        String email = et_contact.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if (email.matches(emailPattern) && email.length() > 0) {
            return true;

        } else {
            et_contact.setError("Invalid E-mail Pattern");
            return false;
        }

    }
}