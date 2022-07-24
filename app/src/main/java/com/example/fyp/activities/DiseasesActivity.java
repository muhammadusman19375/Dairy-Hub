package com.example.fyp.activities;

import static com.example.fyp.utils.Methods.readDiseases;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp.R;
import com.example.fyp.adapters.DiseasesAdapter;

public class DiseasesActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private String[] diseases;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diseases);

        recyclerView=findViewById(R.id.recyclerView);
        diseases = new String[11];
        diseases = readDiseases(this);
        Log.d("TAG", "onCreate: "+diseases.length);

        DiseasesAdapter diseasesAdapter =new DiseasesAdapter(DiseasesActivity.this,diseases);
        recyclerView.setLayoutManager(new LinearLayoutManager(DiseasesActivity.this));
        recyclerView.setAdapter(diseasesAdapter);

    }
}