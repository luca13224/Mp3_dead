//MainActivity
package com.example.android.mp3musicapp.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.Button;

import com.example.android.mp3musicapp.Adapter.MainViewPagerAdapter;
import com.example.android.mp3musicapp.Fragment.Fragment_Thu_Vien;
import com.example.android.mp3musicapp.Fragment.Fragment_Tim_Kiem;
import com.example.android.mp3musicapp.Fragment.Fragment_Trang_Chu;
import com.example.android.mp3musicapp.R;
import com.google.android.material.tabs.TabLayout;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = findViewById(R.id.myViewPager);
        tabLayout = findViewById(R.id.myTabLayout);

        MainViewPagerAdapter adapter = new MainViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Fragment_Trang_Chu(), "Trang chủ");
        adapter.addFragment(new Fragment_Thu_Vien(), "Thư viện");
        adapter.addFragment(new Fragment_Tim_Kiem(), "Tìm kiếm");

        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_baseline_library_music_24);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_search);

    }
}