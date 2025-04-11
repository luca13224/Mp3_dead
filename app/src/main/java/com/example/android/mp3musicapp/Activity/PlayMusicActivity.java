package com.example.android.mp3musicapp.Activity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.example.android.mp3musicapp.Adapter.ViewPagerPlayListMusicAdapter;
import com.example.android.mp3musicapp.Fragment.Fragment_Dia_Nhac;
import com.example.android.mp3musicapp.Fragment.Fragment_Play_Music_List;
import com.example.android.mp3musicapp.Model.BaiHat;
import com.example.android.mp3musicapp.R;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class PlayMusicActivity extends AppCompatActivity {
    Toolbar toolbar;
    ViewPager viewPager;
    SeekBar seekBar;
    ImageButton btnPlay, btnNext, btnPrev, btnShuffle, btnLoop;
    TextView tvTime, tvTotalTime;
    MediaPlayer mediaPlayer;
    ArrayList<BaiHat> baiHats;
    int position = 0;
    boolean isShuffle = false, isLoop = false;
    Handler handler = new Handler();

    Fragment_Dia_Nhac fragmentDiaNhac;
    Fragment_Play_Music_List fragmentPlayMusicList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_music);

        // Ánh xạ giao diện
        toolbar = findViewById(R.id.toolbarPlayMusic);
        viewPager = findViewById(R.id.viewPagerPlayMusic);
        seekBar = findViewById(R.id.seekBarSong);
        btnPlay = findViewById(R.id.imageViewButtonPlay);
        btnNext = findViewById(R.id.imageViewButtonNext);
        btnPrev = findViewById(R.id.imageViewButtonPrevious);
        btnShuffle = findViewById(R.id.imageviewButtonShuffle);
        btnLoop = findViewById(R.id.imageViewButtonLoop);
        tvTime = findViewById(R.id.tvTimeSong);
        tvTotalTime = findViewById(R.id.tvTotalTimeSong);

        // Toolbar
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        toolbar.setNavigationOnClickListener(v -> finish());

        // Nhận dữ liệu
        baiHats = (ArrayList<BaiHat>) getIntent().getSerializableExtra("baihats");
        if (baiHats == null || baiHats.isEmpty()) {
            Toast.makeText(this, "Không có bài hát để phát!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        position = getIntent().getIntExtra("position", 0);
        if (position < 0 || position >= baiHats.size()) position = 0;

        // Khởi tạo Fragment và truyền dữ liệu
        fragmentDiaNhac = new Fragment_Dia_Nhac();

        fragmentPlayMusicList = new Fragment_Play_Music_List();
        Bundle bundle = new Bundle();
        bundle.putSerializable("baihats", baiHats);
        fragmentPlayMusicList.setArguments(bundle);

        ViewPagerPlayListMusicAdapter adapter = new ViewPagerPlayListMusicAdapter(getSupportFragmentManager());
        adapter.addFragment(fragmentDiaNhac);
        adapter.addFragment(fragmentPlayMusicList);
        viewPager.setAdapter(adapter);

        // Phát bài hát đầu tiên
        playSong();

        // Sự kiện nút điều khiển
        btnPlay.setOnClickListener(v -> togglePlayPause());
        btnNext.setOnClickListener(v -> {
            position = (position + 1) % baiHats.size();
            playSong();
        });
        btnPrev.setOnClickListener(v -> {
            position = (position - 1 + baiHats.size()) % baiHats.size();
            playSong();
        });
        btnShuffle.setOnClickListener(v -> {
            isShuffle = !isShuffle;
            btnShuffle.setImageResource(isShuffle ? R.drawable.ic_shuffle_on : R.drawable.ic_shuffle);
        });
        btnLoop.setOnClickListener(v -> {
            isLoop = !isLoop;
            btnLoop.setImageResource(isLoop ? R.drawable.ic_loop_on : R.drawable.ic_loop);
            if (mediaPlayer != null) mediaPlayer.setLooping(isLoop);
        });

        // SeekBar
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mediaPlayer != null) mediaPlayer.seekTo(progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });
    }

    private void playSong() {
        if (mediaPlayer != null) mediaPlayer.release();

        BaiHat baiHat = baiHats.get(position);
        try {
            mediaPlayer = new MediaPlayer();
            String link = baiHat.getLinkBaiHat();

            if (link.startsWith("raw/")) {
                // Xử lý file trong res/raw
                String fileName = link.replace("raw/", "").replace(".mp3", "");
                int resId = getResources().getIdentifier(fileName, "raw", getPackageName());
                if (resId == 0) {
                    throw new IOException("File raw không tồn tại: " + fileName);
                }
                mediaPlayer = MediaPlayer.create(this, resId);
            } else if (link.startsWith("http://") || link.startsWith("https://")) {
                // Xử lý URL online
                mediaPlayer.setDataSource(link);
                mediaPlayer.prepareAsync(); // Dùng prepareAsync cho URL
                mediaPlayer.setOnPreparedListener(mp -> mp.start());
            } else {
                throw new IOException("Link không hợp lệ: " + link);
            }

            if (!link.startsWith("http")) { // Chỉ gọi start() trực tiếp cho file local
                mediaPlayer.start();
            }
            btnPlay.setImageResource(R.drawable.ic_pause);
            toolbar.setTitle(baiHat.getTenBaiHat());

            seekBar.setMax(mediaPlayer.getDuration());
            tvTotalTime.setText(formatTime(mediaPlayer.getDuration()));
            updateSeekBar();

            new Handler().postDelayed(() -> {
                if (fragmentDiaNhac != null) {
                    fragmentDiaNhac.playMusic(baiHat.getHinhBaiHat());
                }
            }, 100);

            mediaPlayer.setOnCompletionListener(mp -> {
                if (isShuffle) {
                    position = new Random().nextInt(baiHats.size());
                } else if (!isLoop) {
                    position = (position + 1) % baiHats.size();
                }
                playSong();
            });

        } catch (IOException e) {
            Toast.makeText(this, "Không thể phát bài hát: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void togglePlayPause() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.pause();
                btnPlay.setImageResource(R.drawable.ic_play);
            } else {
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.ic_pause);
                updateSeekBar();
            }
        }
    }

    private void updateSeekBar() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    tvTime.setText(formatTime(mediaPlayer.getCurrentPosition()));
                    handler.postDelayed(this, 1000);
                }
            }
        }, 0);
    }

    private String formatTime(int ms) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss", Locale.getDefault());
        return sdf.format(ms);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
        handler.removeCallbacksAndMessages(null);
    }
}
