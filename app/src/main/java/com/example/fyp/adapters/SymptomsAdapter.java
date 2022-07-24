package com.example.fyp.adapters;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp.R;
import com.example.fyp.models.SymptomsModel;

import java.util.ArrayList;

public class SymptomsAdapter extends RecyclerView.Adapter<SymptomsAdapter.UsersViewHolder> {

    Context context;
    ArrayList<SymptomsModel> symptoms;
    int checked = 0;
    String diseases = "Symptoms in my animal are: \n";
    String message = "";


    public SymptomsAdapter(Context context, ArrayList<SymptomsModel> symptoms) {
        this.context = context;
        this.symptoms = symptoms;
    }

    @NonNull
    @Override
    public SymptomsAdapter.UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.symptoms_item_view,parent,false);
        return new SymptomsAdapter.UsersViewHolder(view);    }

    @Override
    public void onBindViewHolder(@NonNull SymptomsAdapter.UsersViewHolder holder, int position) {
        holder.textView.setText(symptoms.get(position).getSymptomName());
        SharedPreferences prefs = context.getSharedPreferences("isChecked", MODE_PRIVATE);

        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {


                symptoms.get(position).setChecked(b);
                String msg = symptoms.get(position).getSymptomName();
                Log.d("TAG", "hello: "+symptoms.get(position).getSymptomName());
                message = message+"' "+msg;
                for (int i = 0;i<symptoms.size();i++){
                    if (symptoms.get(i).getChecked()){
                        checked++;
                    }else {
                        checked--;
                    }
                }
                prefs.edit().putString("symptom",diseases+message).commit();

                prefs.edit().putInt("isChecked",checked).apply();
                Log.d("TAG", "onBindViewHolder: "+prefs.getString("symptom",""));


            }
        });

    }

    @Override
    public int getItemCount() {
        return symptoms.size();
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        CheckBox checkBox;
        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.symptomName);
            checkBox = itemView.findViewById(R.id.check);
        }
    }
}
