package com.example.android.mp3musicapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mp3musicapp.Activity.PlayMusicActivity;
import com.example.android.mp3musicapp.Model.BaiHat;
import com.example.android.mp3musicapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class SearchMusicAdapter extends RecyclerView.Adapter<SearchMusicAdapter.ViewHolder> {
    Context context;
    ArrayList<BaiHat> baiHats;

    public SearchMusicAdapter(Context context, ArrayList<BaiHat> baiHats) {
        this.context = context;
        this.baiHats = baiHats;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.search_music_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BaiHat baiHat = baiHats.get(position);
        Picasso.get().load(baiHat.getHinhBaiHat()).into(holder.img);
        holder.tvSong.setText(baiHat.getTenBaiHat());
        holder.tvSinger.setText(baiHat.getCaSi());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, PlayMusicActivity.class);
            intent.putExtra("baihats", baiHats);
            intent.putExtra("position", position);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return baiHats.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvSong, tvSinger;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageViewSearchMusic);
            tvSong = itemView.findViewById(R.id.tvSearchMusic);
            tvSinger = itemView.findViewById(R.id.tvSearchMusicSinger);
        }
    }
}