package com.doubleslash.playground.infoGroup;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doubleslash.playground.ClientApp;
import com.doubleslash.playground.R;
import com.doubleslash.playground.socket.model.Message;
import com.doubleslash.playground.socket.model.Type;

import java.util.ArrayList;

public class JoinAdapter extends RecyclerView.Adapter<JoinAdapter.ViewHolder>{
    ArrayList<Join> items = new ArrayList<Join>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.join_item, parent, false);

        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Join item = items.get(position);
        holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(Join item){
        items.add(item);
    }

    public void setItems(ArrayList<Join> items){
        this.items = items;
    }

    public Join getItem(int position){
        return items.get(position);
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView profileImage;
        TextView name;
        TextView location;
        TextView univ;
        Button acceptBtn;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profileImage = itemView.findViewById(R.id.profile_image);
            name = itemView.findViewById(R.id.user_name);
            location = itemView.findViewById(R.id.location);
            univ = itemView.findViewById(R.id.univ);
            acceptBtn = itemView.findViewById(R.id.accept_button);
        }

        public void setItem(Join item){
            // profileImage.setImageResource(item.getImage());
            name.setText(item.getUserName());
            location.setText(item.getLocation());
            univ.setText(item.getUniv());

            acceptBtn.setOnClickListener(v -> {
                //ClientApp.socketMananger.sendMessage(new Message(Type.REQUEST, ClientApp.userEmail, ));
            });
        }
    }
}