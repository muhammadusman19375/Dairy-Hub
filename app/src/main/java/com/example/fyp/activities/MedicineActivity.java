package com.example.fyp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp.R;

public class MedicineActivity extends AppCompatActivity {
    private Button vaccination,necessary,dcintnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);

        vaccination=findViewById(R.id.vaccination);
        necessary=findViewById(R.id.necessary);
        dcintnt=findViewById(R.id.dcintnt);

        vaccination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MedicineActivity.this,VaccineActivity.class);
                startActivity(intent);
            }
        });
        necessary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MedicineActivity.this,NecessaryMediciansActivity.class);
                startActivity(intent);
            }
        });
        dcintnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MedicineActivity.this,ChatListActivity.class);
                intent.putExtra("role","dairy farmer");
                startActivity(intent);
            }
        });
    }
}