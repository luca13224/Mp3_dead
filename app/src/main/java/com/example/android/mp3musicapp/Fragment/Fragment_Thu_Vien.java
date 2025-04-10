package com.example.android.mp3musicapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mp3musicapp.db.DatabaseHelper;
import com.example.android.mp3musicapp.Adapter.SearchMusicAdapter;
import com.example.android.mp3musicapp.Model.BaiHat;
import com.example.android.mp3musicapp.R;

import java.util.ArrayList;

public class Fragment_Thu_Vien extends Fragment {
    RecyclerView recyclerView;
    DatabaseHelper db;
    ArrayList<BaiHat> baiHats;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thu_vien, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewSearchThuVienMusic);
        db = new DatabaseHelper(getContext());

        baiHats = db.getLikedSongs();
        SearchMusicAdapter adapter = new SearchMusicAdapter(getContext(), baiHats);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }
}