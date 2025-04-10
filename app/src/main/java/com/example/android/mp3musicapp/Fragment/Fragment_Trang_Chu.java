package com.example.android.mp3musicapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;

import com.example.android.mp3musicapp.R;

public class Fragment_Trang_Chu extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trang_chu, container, false);
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.fragmentPlayList, new Fragment_PlayList())
                .replace(R.id.fragmentChuDeTheLoai, new Fragment_ChuDe_TheLoai())
                .commit();
        return view;
    }
}