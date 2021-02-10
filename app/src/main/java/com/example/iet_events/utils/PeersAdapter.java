package com.example.iet_events.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.iet_events.R;
import com.example.iet_events.models.Users;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PeersAdapter extends RecyclerView.Adapter<PeersAdapter.ViewHolder>{

    private Context context;
    private List<Users> usersList;

    public PeersAdapter(List<Users> usersList) {
        this.usersList = usersList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.peers_card_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Users user = usersList.get(position);
        Glide.with(context).load(user.getProfile()).into(holder.peer_profile);
        holder.peer_name.setText(user.getName());

        holder.peer_github.setOnClickListener(view -> {
            String github = user.getGithub();
            if(github != null) {
                try {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(user.getGithub())));
                }catch (Exception e) {
                    Toast.makeText(context, "Error in the link", Toast.LENGTH_SHORT).show();
                }
            }else
                Snackbar.make(view,"Not Available", Snackbar.LENGTH_SHORT).show();
        });
        holder.peer_insta.setOnClickListener(view -> {
            String insta = user.getInstagram();
            if(insta != null) {
                try {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(user.getInstagram())));
                }catch (Exception e){
                    Toast.makeText(context, "Error in the link", Toast.LENGTH_SHORT).show();
                }
            }else
                Snackbar.make(view,"Not Available", Snackbar.LENGTH_SHORT).show();
        });
        holder.peer_linkedin.setOnClickListener(view -> {
            String linkedIn = user.getLinkedIn();
            if(linkedIn != null) {
                try {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(user.getLinkedIn())));
                } catch (Exception e) {
                    Toast.makeText(context, "Error in the link", Toast.LENGTH_SHORT).show();
                }
            }else
                Snackbar.make(view,"Not Available", Snackbar.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return usersList != null? usersList.size(): 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView peer_name;
        private CircleImageView peer_profile;
        private ImageView peer_github, peer_insta, peer_linkedin;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            peer_profile = itemView.findViewById(R.id.peer_profile);
            peer_name = itemView.findViewById(R.id.peer_name);
            peer_github = itemView.findViewById(R.id.peer_github);
            peer_insta = itemView.findViewById(R.id.peer_insta);
            peer_linkedin = itemView.findViewById(R.id.peer_linkedin);
        }
    }
}
