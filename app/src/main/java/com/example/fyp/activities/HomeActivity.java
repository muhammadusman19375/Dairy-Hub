package com.example.fyp.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.fyp.R;
import com.example.fyp.Utils;
import com.example.fyp.adapters.SliderAdapter;
import com.example.fyp.models.Sliderdata;
import com.google.android.material.navigation.NavigationView;
import com.smarteist.autoimageslider.SliderView;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private TextView tv_fodder,tv_breed,tv_insemination,tv_medicine,tv_disease,tv_doctor;
    private Button logout;
    private Utils utils;
    private DrawerLayout drawar_layout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        DrawerLayout drawer =  findViewById(R.id.drawar_layout);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationView navigationView =  drawer.findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        tv_fodder=findViewById(R.id.tv_fodder);
        tv_breed=findViewById(R.id.tv_breed);
        tv_insemination=findViewById(R.id.tv_insemination);
        tv_doctor=findViewById(R.id.tv_doctor);
        tv_medicine=findViewById(R.id.tv_medicine);
        tv_disease=findViewById(R.id.tv_disease);
        Toolbar toolbar=findViewById(R.id.toolbar);


        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer,toolbar, R.string.nav_open, R.string.nav_close);
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();



            String url1 = "https://images.pexels.com/photos/422218/pexels-photo-422218.jpeg?cs=srgb&dl=pexels-matthias-zomer-422218.jpg&fm=jpg";
            String url2 = "https://media.istockphoto.com/photos/its-only-the-best-for-these-cows-picture-id875237010?b=1&k=20&m=875237010&s=170667a&w=0&h=6JkPk5q8x33DdHHDMFhT7-5luwjNDFxOc_JdjGN17lY=";
            String url3 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS9psda2lXMV-MjOhbi_vI-A3ghpOjUBzs81g&usqp=CAU";
            String url4 = "https://images.pexels.com/photos/422202/pexels-photo-422202.jpeg?auto=compress&cs=tinysrgb&w=1260&h=750&dpr=1";

                ArrayList<Sliderdata.SliderData> sliderDataArrayList = new ArrayList<>();

                SliderView sliderView = findViewById(R.id.slider);

                sliderDataArrayList.add(new Sliderdata.SliderData(url1));
                sliderDataArrayList.add(new Sliderdata.SliderData(url2));
                sliderDataArrayList.add(new Sliderdata.SliderData(url3));
                sliderDataArrayList.add(new Sliderdata.SliderData(url4));

                SliderAdapter adapter = new SliderAdapter(this, sliderDataArrayList);
                sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
                sliderView.setSliderAdapter(adapter);
                sliderView.setScrollTimeInSec(3);
                sliderView.setAutoCycle(true);
                sliderView.startAutoCycle();

        tv_insemination.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this,inseminationActivity.class);
                startActivity(intent);
            }
        });
        tv_fodder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this,FodderActivity.class);
                startActivity(intent);
            }
        });
        tv_breed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this,BreedActivity.class);
                startActivity(intent);
            }
        });
        tv_disease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this, DiseasesActivity.class);
                startActivity(intent);
            }
        });
        tv_doctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this, ChatListActivity.class);
                intent.putExtra("role","dairy farmer");
                startActivity(intent);
            }
        });
        tv_medicine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(HomeActivity.this,MedicineActivity.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.idItem1) {
            Intent intent=new Intent(HomeActivity.this,ChatListActivity.class);
            intent.putExtra("role","dairy farmer");
            startActivity(intent);
        } else if (id == R.id.idItem2) {
            Intent intent = new Intent(HomeActivity.this, MedicineActivity.class);
            startActivity(intent);
        } else if (id == R.id.idItem3) {
            Intent intent = new Intent(HomeActivity.this, DiseasesActivity.class);
            startActivity(intent);
        } else if (id == R.id.idItem4) {
            Intent intent = new Intent(HomeActivity.this, FodderActivity.class);
            startActivity(intent);
        } else if (id == R.id.idItem5) {
            Intent intent = new Intent(HomeActivity.this, BreedActivity.class);
            startActivity(intent);
        } else if (id == R.id.idItem6) {
            Intent intent = new Intent(HomeActivity.this, inseminationActivity.class);
            startActivity(intent);
        } else if (id == R.id.idItem8) {
            Intent intent=new Intent(Intent.ACTION_SEND);
            intent.setData(Uri.parse("email"));
            String[] s={"fypproject@gmail.com"};
            intent.putExtra(Intent.EXTRA_EMAIL,s);
            intent.putExtra(Intent.EXTRA_SUBJECT,"Complaint");
            intent.putExtra(Intent.EXTRA_TEXT,"Enter your complaint here");
            intent.setType("message/rfc822");
            Intent chooser=Intent.createChooser(intent,"Launch Email");
            startActivity(chooser); }
        else if (id == R.id.idItem9) {
        }
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawar_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

}