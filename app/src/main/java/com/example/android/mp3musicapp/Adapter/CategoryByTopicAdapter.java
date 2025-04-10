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
import com.example.android.mp3musicapp.Model.ChuDe;
import com.example.android.mp3musicapp.Model.TheLoai;
import com.example.android.mp3musicapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryByTopicAdapter extends RecyclerView.Adapter<CategoryByTopicAdapter.ViewHolder> {
    Context context;
    ArrayList<TheLoai> theLoais;

    public CategoryByTopicAdapter(Context context, ArrayList<TheLoai> theLoais) {
        this.context = context;
        this.theLoais = theLoais;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_by_topic_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        TheLoai theLoai = theLoais.get(position);
        Picasso.get().load(theLoai.getHinhTheLoai()).into(holder.img);
        holder.tvTitle.setText(theLoai.getTenTheLoai());
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, MusicListActivity.class);
            intent.putExtra("theloai", theLoai);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return theLoais.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageViewCategoryByTopic);
            tvTitle = itemView.findViewById(R.id.tvCategoryByTopic);
        }
    }
}