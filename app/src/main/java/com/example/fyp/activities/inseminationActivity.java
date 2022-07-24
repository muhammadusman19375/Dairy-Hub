package com.example.fyp.activities;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp.R;
import com.example.fyp.adapters.UsersAdapter;
import com.example.fyp.models.UsersModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class inseminationActivity extends AppCompatActivity {
    private TextView tv_add;
    private RecyclerView recyclerView;
    private FirebaseDatabase database;
    private ProgressBar progressBar;
    private ArrayList<UsersModel> users;
    private UsersAdapter usersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insemination);
        tv_add=findViewById(R.id.tv_add);
        progressBar=findViewById(R.id.progressBar);
        database=FirebaseDatabase.getInstance();
        users=new ArrayList<>();

        try {
            database.getReference().child("cows").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    users.clear();
                    for(DataSnapshot snapshot1 : snapshot.getChildren()){
                        UsersModel user = snapshot1.getValue(UsersModel.class);
                        users.add(user);
                    }

                    Collections.reverse(users);
                    usersAdapter=new UsersAdapter(inseminationActivity.this,users);
                    recyclerView=findViewById(R.id.recyclerView);
                    recyclerView.setLayoutManager(new LinearLayoutManager(inseminationActivity.this));

                    Log.d(TAG, "onDataChange: "+users.size());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            recyclerView.setAdapter(usersAdapter);
                            usersAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        }
                    });

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Log.d(TAG, "onCancelled: ");
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }


        tv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(inseminationActivity.this,ExampleActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}