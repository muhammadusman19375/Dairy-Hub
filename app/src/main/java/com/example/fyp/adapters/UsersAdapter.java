package com.example.fyp.adapters;

import static com.example.fyp.utils.Methods.getDateDiff;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.fyp.R;
import com.example.fyp.models.UsersModel;
import com.example.fyp.utils.Methods;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

    public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersViewHolder> {
    Context context;
    ArrayList<UsersModel> users;


    public UsersAdapter(Context context, ArrayList<UsersModel> users){
        this.context = context;
        this.users = users;

    }


    @NonNull
    @Override
    public UsersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cow_record,parent,false);
        return new UsersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersViewHolder holder, int position) {
        UsersModel user = users.get(position);

        holder.tv_name.setText(user.getCowname());
        holder.tv_breeds.setText(user.getCowbreed());
        holder.parturition1.setText(user.getParturition());
        holder.tv_date.setText(user.getDate());
        holder.insemination1.setText(user.getInsemType());
        if (user.getImage_cow()!=null){
            holder.image_cow.setImageBitmap(Methods.base64ToBitmap(user.getImage_cow()));

        }

        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat(" dd/MM/yyyy");
        String todayDate = df.format(date);
        String insemDate = Methods.insemDate(user.getDate());

        int dateDifference = (int) getDateDiff(new SimpleDateFormat("dd/MM/yyyy"), todayDate, insemDate);
        holder.left.setText(dateDifference+ "days");

    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class UsersViewHolder extends RecyclerView.ViewHolder{

        TextView tv_name,tv_breeds,parturition1,tv_date,insemination1,left;
        ImageView image_cow;

        public UsersViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_breeds= itemView.findViewById(R.id.tv_breeds);
            tv_name = itemView.findViewById(R.id.tv_name);
            insemination1= itemView.findViewById(R.id.insemination1);
            tv_date = itemView.findViewById(R.id.tv_date);
            left = itemView.findViewById(R.id.left);
            parturition1 = itemView.findViewById(R.id.parturition1);
            image_cow = itemView.findViewById(R.id.cow_image);
        }
    }
}
