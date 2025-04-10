package com.example.android.mp3musicapp.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.android.mp3musicapp.Activity.AllPlayListActivity;
import com.example.android.mp3musicapp.db.DatabaseHelper;
import com.example.android.mp3musicapp.Activity.MusicListActivity;
import com.example.android.mp3musicapp.Adapter.PlayListAdapter;
import com.example.android.mp3musicapp.Model.PlayList;
import com.example.android.mp3musicapp.R;

import java.util.ArrayList;

public class Fragment_PlayList extends Fragment {
    ListView listView;
    TextView tvMore;
    DatabaseHelper db;
    ArrayList<PlayList> playLists;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist, container, false);
        listView = view.findViewById(R.id.listViewPlayList);
        tvMore = view.findViewById(R.id.tvMorePlayList);
        db = new DatabaseHelper(getContext());

        playLists = db.getAllPlayLists();
        PlayListAdapter adapter = new PlayListAdapter(getContext(), android.R.layout.simple_list_item_1, playLists);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view1, position, id) -> {
            Intent intent = new Intent(getActivity(), MusicListActivity.class);
            intent.putExtra("playlist", playLists.get(position));
            startActivity(intent);
        });

        tvMore.setOnClickListener(v -> startActivity(new Intent(getActivity(), AllPlayListActivity.class)));
        return view;
    }
}