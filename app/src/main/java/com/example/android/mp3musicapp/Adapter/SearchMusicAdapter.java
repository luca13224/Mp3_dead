package com.example.android.mp3musicapp.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mp3musicapp.Activity.PlayMusicActivity;
import com.example.android.mp3musicapp.Model.BaiHat;
import com.example.android.mp3musicapp.R;
import com.example.android.mp3musicapp.db.DatabaseHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashSet;

public class SearchMusicAdapter extends RecyclerView.Adapter<SearchMusicAdapter.ViewHolder> {
    Context context;
    ArrayList<BaiHat> baiHats;
    DatabaseHelper db;
    private boolean isSelectionMode = false;
    private HashSet<Integer> selectedSongIds = new HashSet<>();

    public SearchMusicAdapter(Context context, ArrayList<BaiHat> baiHats) {
        this.context = context;
        this.baiHats = (baiHats != null) ? baiHats : new ArrayList<>();
        this.db = new DatabaseHelper(context);
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

        // Hiển thị checkbox nếu đang ở chế độ chọn
        holder.checkBoxSelect.setVisibility(isSelectionMode ? View.VISIBLE : View.GONE);
        holder.checkBoxSelect.setChecked(selectedSongIds.contains(baiHat.getIdBaiHat()));
        holder.checkBoxSelect.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                selectedSongIds.add(baiHat.getIdBaiHat());
            } else {
                selectedSongIds.remove(baiHat.getIdBaiHat());
            }
        });

        // Kiểm tra trạng thái "thích"
        boolean isLiked = db.isSongInFavorites(baiHat.getIdBaiHat());
        holder.imgLike.setImageResource(isLiked ? R.drawable.ic_red_love : R.drawable.ic_white_love);

        // Xử lý sự kiện nhấn nút "thích"
        holder.imgLike.setOnClickListener(v -> {
            if (isLiked) {
                db.removeSongFromFavorites(baiHat.getIdBaiHat());
                holder.imgLike.setImageResource(R.drawable.ic_white_love);
                baiHat.setIdPlayList(0);
                Log.d("SearchMusicAdapter", "Removed from Favorites: " + baiHat.getTenBaiHat());
            } else {
                db.addSongToFavorites(baiHat.getIdBaiHat());
                holder.imgLike.setImageResource(R.drawable.ic_red_love);
                baiHat.setIdPlayList(3);
                Log.d("SearchMusicAdapter", "Added to Favorites: " + baiHat.getTenBaiHat());
            }
            notifyDataSetChanged();
        });

        // Xử lý sự kiện nhấn nút "xóa" trong mỗi item
        holder.imgDelete.setOnClickListener(v -> {
            long result = db.deleteSong(baiHat.getIdBaiHat());
            if (result > 0) {
                baiHats.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, baiHats.size());
                Log.d("SearchMusicAdapter", "Deleted song: " + baiHat.getTenBaiHat());
            } else {
                Log.e("SearchMusicAdapter", "Failed to delete song: " + baiHat.getTenBaiHat());
            }
        });

        holder.itemView.setOnClickListener(v -> {
            if (!isSelectionMode) {
                Intent intent = new Intent(context, PlayMusicActivity.class);
                intent.putExtra("baihats", baiHats);
                intent.putExtra("position", position);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return baiHats.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img, imgLike, imgDelete;
        TextView tvSong, tvSinger;
        CheckBox checkBoxSelect;

        public ViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.imageViewSearchMusic);
            tvSong = itemView.findViewById(R.id.tvSearchMusic);
            tvSinger = itemView.findViewById(R.id.tvSearchMusicSinger);
            imgLike = itemView.findViewById(R.id.imageViewLike);
            imgDelete = itemView.findViewById(R.id.imageViewDelete);
            checkBoxSelect = itemView.findViewById(R.id.checkBoxSelectSong);
        }
    }

    public void updateList(ArrayList<BaiHat> newList) {
        this.baiHats = (newList != null) ? newList : new ArrayList<>();
        notifyDataSetChanged();
    }

    public void setSelectionMode(boolean selectionMode) {
        this.isSelectionMode = selectionMode;
        selectedSongIds.clear();
        notifyDataSetChanged();
    }

    public HashSet<Integer> getSelectedSongIds() {
        return selectedSongIds;
    }
}