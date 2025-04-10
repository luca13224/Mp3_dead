package com.example.android.mp3musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.android.mp3musicapp.Adapter.CategoryByTopicAdapter;
import com.example.android.mp3musicapp.Model.ChuDe;
import com.example.android.mp3musicapp.Model.TheLoai;
import com.example.android.mp3musicapp.R;
import com.example.android.mp3musicapp.db.DatabaseHelper;

import java.util.ArrayList;

public class CategoryByTopicActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    DatabaseHelper db;
    ArrayList<TheLoai> theLoais;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_by_topic);

        Toolbar toolbar = findViewById(R.id.toolBatCategoryByTopic);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recyclerViewCategoryByTopic);
        db = new DatabaseHelper(this);

        ChuDe chuDe = (ChuDe) getIntent().getSerializableExtra("chude");
        getSupportActionBar().setTitle(chuDe.getTenChuDe());
        theLoais = db.getTheLoaiByChuDe(chuDe.getIdChuDe());

        CategoryByTopicAdapter adapter = new CategoryByTopicAdapter(this, theLoais);
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