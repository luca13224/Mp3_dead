package com.example.android.mp3musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.android.mp3musicapp.Adapter.MusicListAdapter;
import com.example.android.mp3musicapp.Model.BaiHat;
import com.example.android.mp3musicapp.Model.PlayList;
import com.example.android.mp3musicapp.Model.TheLoai;
import com.example.android.mp3musicapp.R;
import com.example.android.mp3musicapp.db.DatabaseHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Objects;

public class MusicListActivity extends AppCompatActivity {
    Toolbar toolbar;
    RecyclerView recyclerView;
    FloatingActionButton fab;
    DatabaseHelper db;
    ArrayList<BaiHat> baiHats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_list);

        // Ánh xạ giao diện
        toolbar = findViewById(R.id.toolBarMusicList);
        recyclerView = findViewById(R.id.recyclerViewMusicList);
        fab = findViewById(R.id.floatingActionButton);
        db = new DatabaseHelper(this);
        baiHats = new ArrayList<>(); // Khởi tạo để tránh NullPointerException

        // Thiết lập Toolbar
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> finish());

        // Xử lý Intent
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra("playlist")) {
                PlayList playList = (PlayList) intent.getSerializableExtra("playlist");
                if (playList != null) {
                    getSupportActionBar().setTitle(playList.getTen());
                    baiHats = db.getSongsByPlayList(playList.getIdPlayList());
                } else {
                    Toast.makeText(this, "Không tìm thấy playlist!", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
            } else if (intent.hasExtra("theloai")) {
                TheLoai theLoai = (TheLoai) intent.getSerializableExtra("theloai");
                if (theLoai != null) {
                    getSupportActionBar().setTitle(theLoai.getTenTheLoai());
                    baiHats = db.getSongsByTheLoai(theLoai.getIdTheLoai());
                } else {
                    Toast.makeText(this, "Không tìm thấy thể loại!", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
            } else {
                Toast.makeText(this, "Không có dữ liệu để hiển thị!", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
        } else {
            Toast.makeText(this, "Lỗi khi tải dữ liệu!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Thiết lập RecyclerView
        MusicListAdapter adapter = new MusicListAdapter(this, baiHats);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        // Xử lý FloatingActionButton
        fab.setOnClickListener(v -> {
            if (!baiHats.isEmpty()) {
                Intent playIntent = new Intent(this, PlayMusicActivity.class);
                playIntent.putExtra("baihats", baiHats);
                playIntent.putExtra("position", 0);
                startActivity(playIntent);
            } else {
                Toast.makeText(this, "Danh sách bài hát trống!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}