package com.example.android.mp3musicapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mp3musicapp.Adapter.PlayMusicListAdapter;
import com.example.android.mp3musicapp.Model.BaiHat;
import com.example.android.mp3musicapp.R;

import java.util.ArrayList;

public class Fragment_Play_Music_List extends Fragment {
    View view;
    RecyclerView recyclerViewPlayMusicList;
    PlayMusicListAdapter playMusicListAdapter;
    ArrayList<BaiHat> arrayBaiHat; // Khai báo đúng tên biến

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_play_music_list, container, false);
        recyclerViewPlayMusicList = view.findViewById(R.id.recyclerViewPlayMusicList);
        if (getArguments() != null) {
            arrayBaiHat = (ArrayList<BaiHat>) getArguments().getSerializable("baihatlist");
            if (arrayBaiHat != null && arrayBaiHat.size() > 0) {
                playMusicListAdapter = new PlayMusicListAdapter(getActivity(), arrayBaiHat);
                recyclerViewPlayMusicList.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerViewPlayMusicList.setAdapter(playMusicListAdapter);
            }
        }
        return view;
    }
}

