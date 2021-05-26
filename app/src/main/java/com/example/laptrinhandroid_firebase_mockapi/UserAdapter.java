package com.example.laptrinhandroid_firebase_mockapi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.laptrinhandroid_firebase_mockapi.entity.User;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.NameViewHolder> {
    private List<User> users;
    private LayoutInflater inflater;

    public UserAdapter(List<User> users, Context context) {
        this.users = users;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public UserAdapter.NameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View view = inflater.inflate(R.layout.line_item,parent,false);
       return new NameViewHolder(view,this);
    }

    @Override
    public void onBindViewHolder(@NonNull UserAdapter.NameViewHolder holder, int position) {
        User user = users.get(position);
        holder.txt_id.setText("Id : "+user.getId());
        holder.txt_age.setText("Age : " +user.getAge()+"");
        holder.txt_name.setText("Name : "+user.getName());
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class NameViewHolder extends RecyclerView.ViewHolder {
        private UserAdapter adapter;
        private TextView txt_id,txt_name,txt_age;
        public NameViewHolder(@NonNull View itemView , UserAdapter adapter) {
            super(itemView);
            this.adapter = adapter;
            txt_id = itemView.findViewById(R.id.txt_id);
            txt_name = itemView.findViewById(R.id.txt_name);
            txt_age = itemView.findViewById(R.id.txt_age);
        }
    }
}
