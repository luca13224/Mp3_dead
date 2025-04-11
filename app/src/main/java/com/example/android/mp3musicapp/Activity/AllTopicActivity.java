package com.example.android.mp3musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.android.mp3musicapp.Adapter.AllTopicAdapter;
import com.example.android.mp3musicapp.Model.ChuDe;
import com.example.android.mp3musicapp.R;
import com.example.android.mp3musicapp.db.DatabaseHelper;

import java.util.ArrayList;

public class AllTopicActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseHelper db;
    ArrayList<ChuDe> chuDes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_topic);

        Toolbar toolbar = findViewById(R.id.toolBarAllCategory);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Tất cả Chủ đề");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerViewAllCategory);
        db = new DatabaseHelper(this);

        chuDes = db.getAllChuDe();
        AllTopicAdapter adapter = new AllTopicAdapter(this, chuDes);
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