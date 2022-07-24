package com.example.fyp.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class BreedActivity extends AppCompatActivity {
    private TextView tv_freezon,tv_sahiwal,tv_jersey,tv_malangni,tv_red;
    private Dialog myDialog;
    private FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breed);

        tv_freezon=findViewById(R.id.tv_freezon);
        tv_sahiwal=findViewById(R.id.tv_sahiwal);
        tv_jersey=findViewById(R.id.tv_jersey);
        tv_malangni=findViewById(R.id.tv_malangni);
        tv_red=findViewById(R.id.tv_red);
        myDialog = new Dialog(this);



        tv_freezon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String v1="580 kg (Adult)",v2="145–165 cm",
                        v3=" Black and white patched coat (occasionally red and white)",
                        v4="Horned, mainly dehorned as calves",v5="Dairy and meat",
                        v6="Holstein or Friesian cattle",
                        v7="Germany, Netherlands, Switzerland, France, Austria, Belgium,Traits",
                        v8="Holstein Friesians are a breed of dairy cattle that originated in the Dutch provinces of North Holland and Friesland, and Schleswig-Holstein in Northern Germany. They are known as the world's highest-producing dairy animals. This breed produces approximately 8000-1200 Liter milk per lactation.  ";
                func(v1,v2,v3,v4,v5,v6,v7,v8);
                ImageView images=myDialog.findViewById(R.id.images);
                ProgressBar progressBar=myDialog.findViewById(R.id.progressBar);
                loadImage(tv_freezon.getText().toString(),images,progressBar);

            }
        });
        tv_sahiwal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String v1="23",v2="136-120cm for both",
                        v3="Brownish Red to Greyish Red",v4="Horned",
                        v5="Dual-purpose Dairy/Draft",v6="Montgomery",
                        v7="India, Pakistan",
                        v8="Sahiwal cattle are a breed of zebu cow, named after an area in the Punjab, Pakistan. The cattle is mainly found in Punjab province of Pakistan, and Indian states of Punjab, Haryana, & Rajasthan. Sahiwal is considered a heat-tolerant cattle breed. This breed produces approximately 2200 Liter milk per lactation";
                func(v1,v2,v3,v4,v5,v6,v7,v8);
                ImageView images=myDialog.findViewById(R.id.images);
                ProgressBar progressBar=myDialog.findViewById(R.id.progressBar);
                loadImage(tv_sahiwal.getText().toString(),images,progressBar);

            }
        });
        tv_jersey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String v1="Male: 600–700kg;Female: 350–400kg",
                        v2="115–120 cm",v3="variable",v4="2342",v5="dairy and draught",
                        v6="Jersey",v7="Jersey, Bri",v8="The Jersey is a British breed of small dairy cattle from Jersey, in the British Channel Islands. It is one of three Channel Island cattle breeds, the others being the Alderney – now extinct – and the Guernsey. This breed produces approximately 6000-8000 Liter milk per lectation ";
                func(v1,v2,v3,v4,v5,v6,v7,v8);
                ImageView images=myDialog.findViewById(R.id.images);
                ProgressBar progressBar=myDialog.findViewById(R.id.progressBar);
                loadImage(tv_jersey.getText().toString(),images,progressBar);
            }
        });
        tv_malangni.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String v1="Male: 500kg; Female: 350 kg",v2="130 cm",v3="White body with brown dots",v4="Horned",
                        v5="Draught or Meat",v6="Cholistani",v7="Rahim yaar khan,Bahawalpur, Bahawalnager",
                        v8="The Cholistani is a multi-purpose breed, being used for both meat and milk and as a draft animal. Cholistani are usually speckled red, brown or black. This breed produces approximately 1800-2000 Liter milk per lectation";
                func(v1,v2,v3,v4,v5,v6,v7,v8);
                ImageView images=myDialog.findViewById(R.id.images);
                ProgressBar progressBar=myDialog.findViewById(R.id.progressBar);
                loadImage(tv_malangni.getText().toString(),images,progressBar);
            }
        });
        tv_red.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String v1="Male: 530kg; Female: 350 kg",v2="116 cm",v3="Brick red",v4="Horned",
                        v5="Milk or meat or draught",v6="Red Karachi, Malir, Sindhi",v7="Sindh",
                        v8="The Red Sindhi breed has a very high genetic potential for milk production and comparable with Sahiwal. The breed was used in many countries including USA, Australia, Philippines, Brazil and Sri Lanka for breed development. This breed produces approximately 1100-2600 Liter milk per lectation";
                func(v1,v2,v3,v4,v5,v6,v7,v8);
                ImageView images=myDialog.findViewById(R.id.images);
                ProgressBar progressBar=myDialog.findViewById(R.id.progressBar);
                loadImage(tv_red.getText().toString(),images,progressBar);
            }
        });
    }

    private void func(String v1, String v2, String v3, String v4, String v5, String v6, String v7, String v8) {
        myDialog.setContentView(R.layout.breed);
        myDialog.setCancelable(true);
        myDialog.show();

        final TextView mass=myDialog.findViewById(R.id.mass);
        final TextView height=myDialog.findViewById(R.id.height);
        final TextView color=myDialog.findViewById(R.id.color);
        final TextView horn=myDialog.findViewById(R.id.horn);
        final TextView use=myDialog.findViewById(R.id.use);
        final TextView name=myDialog.findViewById(R.id.name);
        final TextView origin=myDialog.findViewById(R.id.origin);
        final TextView history=myDialog.findViewById(R.id.history);


        mass.setText(String.valueOf(v1));
        height.setText(String.valueOf(v2));
        color.setText(String.valueOf(v3));
        horn.setText(String.valueOf(v4));
        use.setText(String.valueOf(v5));
        name.setText(String.valueOf(v6));
        origin.setText(String.valueOf(v7));
        history.setText(String.valueOf(v8));


    }

    private void loadImage(String name,ImageView images,ProgressBar progressBar){

//        Handler handler  = new Handler();
//        Runnable run = new Runnable() {
//            @Override
//            public void run() {
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

                // we will get a DatabaseReference for the database root node
                DatabaseReference databaseReference = firebaseDatabase.getReference();

                // Here "image" is the child node value we are getting
                // child node data in the getImage variable
                DatabaseReference getImage = databaseReference.child(name);
                getImage.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                            String link = snapshot.getValue(String.class);

//                            Log.d("TAG", "onDataChange: " + link);
////                            Toast.makeText(BreedActivity.this, link, Toast.LENGTH_SHORT).show();
//
//                            Log.d("TAG", "onDataChange: " + name);
                            // loading that data into rImage
                            // variable which is ImageView
                            if (link != null) {
                                Picasso.get().load(link).into(images);
                            } else {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(BreedActivity.this, "Something went wrong!", Toast.LENGTH_SHORT).show();
                            }
                        }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(BreedActivity.this, "Errorjjkcnzx ", Toast.LENGTH_SHORT).show();
                    }
                });
//                handler.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        progressBar.setVisibility(View.GONE);
//                    }
//                });
//            }
//        };
    }
}