package com.example.fyp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.fyp.R;
import com.example.fyp.adapters.ChatListAdapter;
import com.example.fyp.databinding.ActivityChatListBinding;
import com.example.fyp.models.user;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatListActivity extends AppCompatActivity {

    ActivityChatListBinding binding;
    FirebaseDatabase database;
    ArrayList<user> users;
    ChatListAdapter usersAdapter;
    FirebaseAuth auth;
    String role;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityChatListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        auth = FirebaseAuth.getInstance();
        Intent intent = getIntent();
        role = intent.getStringExtra("role");
        message = intent.getStringExtra("diseases");

        database = FirebaseDatabase.getInstance();
        users = new ArrayList<>();

        database.getReference().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                for (DataSnapshot snapshot1 : snapshot.getChildren()){
                    user user=snapshot1.getValue(com.example.fyp.models.user.class);
                    if (user.getUid().equals(auth.getUid())){
//                        Toast.makeText(ChatListActivity.this, (CharSequence) auth.getCurrentUser(),Toast.LENGTH_SHORT).show();
                    }else{
                        if (role.equalsIgnoreCase("Doctor")){
                            if (user.getRole().equalsIgnoreCase("Dairy Farmer")){
                                users.add(user);
                            }
                        }else{
                            if (user.getRole().equalsIgnoreCase("Doctor")){
                                users.add(user);
                            }
                        }


//                        Toast.makeText(ChatListActivity.this, user.getName(), Toast.LENGTH_SHORT).show();

                    }
                    usersAdapter = new ChatListAdapter(ChatListActivity.this,users,message);

                   LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(ChatListActivity.this);
                    binding.recyclerView.setLayoutManager(mLinearLayoutManager);
                    binding.recyclerView.setAdapter(usersAdapter);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



    }

    @Override
    protected void onResume() {
        super.onResume();
        String currentId = FirebaseAuth.getInstance().getUid();
        database.getReference().child("presence").child(currentId).setValue("Online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        String currentId = FirebaseAuth.getInstance().getUid();
        database.getReference().child("presence").child(currentId).setValue("offline");
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                Toast.makeText(ChatListActivity.this, "Search clicked.", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.topmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }
}