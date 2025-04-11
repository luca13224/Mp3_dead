package com.example.android.mp3musicapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

public class MusicListAdapter extends RecyclerView.Adapter<MusicListAdapter.ViewHolder> {
    Context context;
    ArrayList<BaiHat> baiHats;

    public MusicListAdapter(Context context, ArrayList<BaiHat> baiHats) {
        this.context = context;
        this.baiHats = baiHats;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.music_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BaiHat baiHat = baiHats.get(position);
        holder.tvIndex.setText(String.valueOf(position + 1));
        holder.tvSong.setText(baiHat.getTenBaiHat());
        holder.tvSinger.setText(baiHat.getCaSi());
        Log.d("MusicListAdapter", "Loading image: " + baiHat.getHinhBaiHat());
        Picasso.get()
                .load(baiHat.getHinhBaiHat())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imgSong, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d("MusicListAdapter", "Image loaded: " + baiHat.getHinhBaiHat());
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("MusicListAdapter", "Failed to load image: " + e.getMessage());
                    }
                });
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
        TextView tvIndex, tvSong, tvSinger;
        ImageView imgSong;

        public ViewHolder(View itemView) {
            super(itemView);
            tvIndex = itemView.findViewById(R.id.tvMusicListIndex);
            tvSong = itemView.findViewById(R.id.tvTenBaiHatMusicList);
            tvSinger = itemView.findViewById(R.id.tvTenCaSiMusicList);
            imgSong = itemView.findViewById(R.id.imageViewMusicListItem);
        }
    }
}