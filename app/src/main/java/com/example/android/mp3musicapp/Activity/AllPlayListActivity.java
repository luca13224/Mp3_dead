package com.example.android.mp3musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.android.mp3musicapp.Adapter.AllPlayListAdapter;
import com.example.android.mp3musicapp.Model.PlayList;
import com.example.android.mp3musicapp.R;
import com.example.android.mp3musicapp.db.DatabaseHelper;

import java.util.ArrayList;

public class AllPlayListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseHelper db;
    ArrayList<PlayList> playLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_play_list);

        Toolbar toolbar = findViewById(R.id.toolBarAllPlayList);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tất cả Playlist");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerViewAllPlayList);
        db = new DatabaseHelper(this);

        playLists = db.getAllPlayLists();
        AllPlayListAdapter adapter = new AllPlayListAdapter(this, playLists);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}