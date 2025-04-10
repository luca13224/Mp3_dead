package com.example.android.mp3musicapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mp3musicapp.Activity.MusicListActivity;
import com.example.android.mp3musicapp.Model.PlayList;
import com.example.android.mp3musicapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllPlayListAdapter extends RecyclerView.Adapter<AllPlayListAdapter.ViewHolder> {
    Context context;
    ArrayList<PlayList> playLists;

    public AllPlayListAdapter(Context context, ArrayList<PlayList> playLists) {
        this.context = context;
        this.playLists = playLists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.all_play_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        PlayList playList = playLists.get(position);
        Picasso.get().load(playList.getHinhNen()).into(holder.img);
        holder.tvTitle.setText(playList.getTen());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MusicListActivity.class);
            intent.putExtra("playlist", playList);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return playLists.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageViewAllPlayList);
            tvTitle = itemView.findViewById(R.id.tvAllPlayList);
        }
    }
}