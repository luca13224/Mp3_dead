package com.example.android.mp3musicapp.Fragment;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import androidx.fragment.app.Fragment;

import com.example.android.mp3musicapp.R;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class Fragment_Dia_Nhac extends Fragment {
    CircleImageView imgDiaNhac;
    ObjectAnimator animator;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dia_nhac, container, false);
        imgDiaNhac = view.findViewById(R.id.imageViewDiaNhac);
        animator = ObjectAnimator.ofFloat(imgDiaNhac, "rotation", 0f, 360f);
        animator.setDuration(10000);
        animator.setRepeatCount(ObjectAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        return view;
    }

    public void playMusic(String hinhAnh) {
        Log.d("Fragment_Dia_Nhac", "Loading image: " + hinhAnh);
        Picasso.get()
                .load(hinhAnh)
                .placeholder(R.drawable.ic_launcher_background) // Hiển thị ảnh tạm khi tải
                .error(R.drawable.ic_launcher_foreground) // Hiển thị ảnh lỗi nếu thất bại
                .into(imgDiaNhac, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        Log.d("Fragment_Dia_Nhac", "Image loaded successfully");
                        animator.start();
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("Fragment_Dia_Nhac", "Failed to load image: " + e.getMessage());
                    }
                });
    }

    @Override
    public void onPause() {
        super.onPause();
        animator.pause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (imgDiaNhac.getDrawable() != null) { // Chỉ quay nếu có ảnh
            animator.start();
        }
    }
}