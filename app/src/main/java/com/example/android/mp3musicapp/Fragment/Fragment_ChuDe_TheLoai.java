package com.example.android.mp3musicapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.android.mp3musicapp.Activity.CategoryByTopicActivity;
import com.example.android.mp3musicapp.db.DatabaseHelper;
import com.example.android.mp3musicapp.Model.ChuDe;
import com.example.android.mp3musicapp.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Fragment_ChuDe_TheLoai extends Fragment {
    HorizontalScrollView scrollView;
    DatabaseHelper db;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chu_de_the_loai, container, false);
        scrollView = view.findViewById(R.id.horizontalScrollView);
        db = new DatabaseHelper(getContext());

        LinearLayout layout = new LinearLayout(getContext());
        layout.setOrientation(LinearLayout.HORIZONTAL);

        ArrayList<ChuDe> chuDes = db.getAllChuDe();
        if (chuDes != null && !chuDes.isEmpty()) {
            for (ChuDe chuDe : chuDes) {
                View item = inflater.inflate(R.layout.all_topic_item, null);
                ImageView img = item.findViewById(R.id.imageViewAllTopic);
                TextView tv = item.findViewById(R.id.tvAllTopic);
                Picasso.get().load(chuDe.getHinhChuDe()).into(img);
                tv.setText(chuDe.getTenChuDe());
                item.setOnClickListener(v -> {
                    Intent intent = new Intent(getActivity(), CategoryByTopicActivity.class);
                    intent.putExtra("chude", chuDe);
                    startActivity(intent);
                });
                layout.addView(item);
            }
        }
        scrollView.addView(layout);
        return view;
    }
}