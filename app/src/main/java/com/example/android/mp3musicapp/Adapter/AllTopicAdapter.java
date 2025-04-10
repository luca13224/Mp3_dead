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

import com.example.android.mp3musicapp.Activity.CategoryByTopicActivity;
import com.example.android.mp3musicapp.Model.ChuDe;
import com.example.android.mp3musicapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllTopicAdapter extends RecyclerView.Adapter<AllTopicAdapter.ViewHolder> {
    Context context;
    ArrayList<ChuDe> chuDes;

    public AllTopicAdapter(Context context, ArrayList<ChuDe> chuDes) {
        this.context = context;
        this.chuDes = chuDes;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.all_topic_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ChuDe chuDe = chuDes.get(position);
        Picasso.get().load(chuDe.getHinhChuDe()).into(holder.img);
        holder.tvTitle.setText(chuDe.getTenChuDe());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, CategoryByTopicActivity.class);
            intent.putExtra("chude", chuDe);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return chuDes.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageViewAllTopic);
            tvTitle = itemView.findViewById(R.id.tvAllTopic);
        }
    }
}