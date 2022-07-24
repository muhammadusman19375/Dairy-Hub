package com.example.fyp.adapters;

import static android.content.Context.MODE_PRIVATE;
import static com.example.fyp.utils.Methods.readSymptoms;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp.R;
import com.example.fyp.activities.ChatListActivity;
import com.example.fyp.models.SymptomsModel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

public class DiseasesAdapter extends RecyclerView.Adapter<DiseasesAdapter.UsersViewHolder> {

    Context context;
    String[] diseases;
    ArrayList<SymptomsModel> symptoms;


    public DiseasesAdapter(Context context, String[] diseases) {
        this.context = context;
        this.diseases = diseases;
    }

    @NonNull
    @Override
    public DiseasesAdapter.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.diseases_item_view,parent,false);
        return new DiseasesAdapter.UsersViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull DiseasesAdapter.UsersViewHolder holder, int position) {
        holder.textView.setText(diseases[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                View view1 = LayoutInflater.from(context).inflate(R.layout.diseases_popup, (RelativeLayout)
                        view.findViewById(R.id.layoutDialogContainerInformation));
                builder.setView(view1);
                final AlertDialog alertDialog = builder.create();
                alertDialog.setCancelable(true);
                SharedPreferences prefs = context.getSharedPreferences("isChecked", MODE_PRIVATE);

                RecyclerView recyclerView = view1.findViewById(R.id.recyclerView);
                CardView cancel = view1.findViewById(R.id.cancel);
                CardView checkDiseases = view1.findViewById(R.id.checkDisease);
                TextView diseaseName = view1.findViewById(R.id.diseased);
                Button share = view1.findViewById(R.id.share);

                symptoms = new ArrayList<>();
                String fName;
                fName = holder.textView.getText().toString().toLowerCase(Locale.ROOT).replace(" ","_");
                InputStream inputStream = context.getResources().openRawResource(
                        context.getResources().getIdentifier(fName,
                                "raw", context.getPackageName()));
                symptoms = readSymptoms(context,inputStream);
                SymptomsAdapter symptomsAdapter =new SymptomsAdapter(context,symptoms);
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
                recyclerView.setAdapter(symptomsAdapter);

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        alertDialog.cancel();
                    }
                });

                checkDiseases.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        int i = prefs.getInt("isChecked",0);
                        prefs.edit().putInt("isChecked",0).apply();

                        if (i==symptoms.size()){
                            diseaseName.setVisibility(View.VISIBLE);
                            diseaseName.setText("Your animal may be infected by "+diseases[position]);
                        }else{
                            diseaseName.setVisibility(View.GONE);
                            share.setVisibility(View.VISIBLE);
                            share.setText("Share with doctor");
                            share.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    Intent intent = new Intent(context, ChatListActivity.class);
                                    intent.putExtra("diseases",prefs.getString("symptom",""));
                                    prefs.edit().putString("symptom","").commit();
                                    intent.putExtra("role","dairy farmer");
                                    context.startActivity(intent);
                                    Log.d("TAG", "onClick: "+prefs.getString("symptom",""));
                                }
                            });

                        }
                    }
                });
                alertDialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return diseases.length;
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.diseaseName);
        }
    }
}
