package com.example.fyp.activities;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fyp.R;
import com.google.firebase.firestore.FirebaseFirestore;

public class FodderActivity extends AppCompatActivity {
    private TextView tv_barseem,tv_alfalfa,tv_sarson,tv_oat,tv_corn,tv_milet,tv_soregam,tv_janter,tv_rhode,tv_mott;
    private Dialog myDialog;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fodder);


        tv_barseem=findViewById(R.id.tv_barseem);
        tv_alfalfa=findViewById(R.id.tv_alfalfa);
        tv_sarson=findViewById(R.id.tv_sarson);
        tv_oat=findViewById(R.id.tv_oat);
        tv_corn=findViewById(R.id.tv_corn);
        tv_milet=findViewById(R.id.tv_milet);
        tv_soregam=findViewById(R.id.tv_soregam);
        tv_janter=findViewById(R.id.tv_janter);
        tv_rhode=findViewById(R.id.tv_rhode);
        tv_mott=findViewById(R.id.tv_mott);
        myDialog = new Dialog(this);
        db=FirebaseFirestore.getInstance();

        tv_barseem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float v1= (float) 19.9,v2= (float) 9.6,v3= (float) 19.3,v4= (float) 2.7,v5= (float) 22.3,v6= 12.5F;
                func(v1,v2,v3,v4,v5,v6);
            }
        });
        tv_alfalfa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float v1= (float) 18.3,v2= (float) 8.5,v3= (float) 22.1,v4= (float) 2.7,v5= (float) 28.6,v6= (float) 90.6;
                func(v1,v2,v3,v4,v5,v6);
            }
        });
        tv_sarson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float v1= (float) 34.9,v2= (float) 11.1,v3= 0.5F,v4= (float) 11.1,v5= (float) 7.1,v6= (float) 89.4;
                func(v1,v2,v3,v4,v5,v6);
            }
        });
        tv_oat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float v1= 11.0F,v2= (float) 12.2,v3= (float) 1.1,v4= (float) 3.6,v5= (float) 13.9,v6= (float) 87.9;
                func(v1,v2,v3,v4,v5,v6);
            }
        });
        tv_corn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float v1= 9.4F,v2= (float) 13.6,v3= 0.5F,v4= 3.0F,v5= 2.5F,v6= (float) 86.3;
                func(v1,v2,v3,v4,v5,v6);
            }
        });
        tv_milet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float v1= (float) 12.4,v2= (float) 9.2,v3= 5.5F,v4= (float) 2.8,v5= (float) 29.2,v6= (float) 19.4;
                func(v1,v2,v3,v4,v5,v6);
            }
        });
        tv_soregam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float v1= 8.2F,v2= (float) 8.8,v3= (float) 4.1,v4= 2.0F,v5= (float) 33.6,v6= (float) 28.1;
                func(v1,v2,v3,v4,v5,v6);
            }
        });
        tv_janter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float v1= (float) 24.4,v2= 11.5F,v3= (float) 15.9,v4= (float) 3.3,v5= (float) 12.9,v6= 26.0F;
                func(v1,v2,v3,v4,v5,v6);
            }
        });
        tv_rhode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float v1= (float) 10.1,v2= (float) 8.1,v3= (float) 3.1,v4= (float) 2.6,v5= (float) 35.3,v6= (float) 86.4;
                func(v1,v2,v3,v4,v5,v6);
            }
        });
        tv_mott.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float v1= 9.0F,v2= (float) 8.5F,v3= (float) 3.8,v4= (float) 2.9,v5= (float) 36.9,v6= (float) 24.9;
                func(v1,v2,v3,v4,v5,v6);
            }
        });
    }

    private void func(float v1, float v2, float v3, float v4, float v5, float v6) {
        myDialog.setContentView(R.layout.bareseem);
        myDialog.setCancelable(true);
        myDialog.show();

        final TextView cp=myDialog.findViewById(R.id.cp);
        final TextView me=myDialog.findViewById(R.id.me);
        final TextView ca=myDialog.findViewById(R.id.ca);
        final TextView p=myDialog.findViewById(R.id.p);
        final TextView ndf=myDialog.findViewById(R.id.ndf);
        final TextView acid=myDialog.findViewById(R.id.acid);

        cp.setText(String.valueOf(v1));
        me.setText(String.valueOf(v2));
        ca.setText(String.valueOf(v3));
        p.setText(String.valueOf(v4));
        ndf.setText(String.valueOf(v5));
        acid.setText(String.valueOf(v6));
    }
}