package com.example.android.mp3musicapp.Fragment;

import android.content.Intent;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mp3musicapp.db.DatabaseHelper;
import com.example.android.mp3musicapp.Adapter.SearchMusicAdapter;
import com.example.android.mp3musicapp.Model.BaiHat;
import com.example.android.mp3musicapp.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;

public class Fragment_Thu_Vien extends Fragment {
    RecyclerView recyclerView;
    DatabaseHelper db;
    ArrayList<BaiHat> baiHats;
    SearchMusicAdapter adapter;
    private ActivityResultLauncher<Intent> audioPickerLauncher;
    private Button buttonDeleteSongs;
    private boolean isSelectionMode = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thu_vien, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewSearchThuVienMusic);
        Button buttonAddSong = view.findViewById(R.id.buttonAddSong);
        buttonDeleteSongs = view.findViewById(R.id.buttonDeleteSongs);
        db = new DatabaseHelper(getContext());

        // Khởi tạo launcher để chọn file âm thanh
        audioPickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == getActivity().RESULT_OK && result.getData() != null) {
                        Uri audioUri = result.getData().getData();
                        if (audioUri != null) {
                            addSongToDatabase(audioUri);
                        }
                    }
                }
        );

        // Xử lý nút "Thêm bài hát"
        buttonAddSong.setOnClickListener(v -> {
            if (isSelectionMode) {
                toggleSelectionMode();
            }
            pickAudioFile();
        });

        // Xử lý nút "Xóa bài hát"
        buttonDeleteSongs.setOnClickListener(v -> {
            if (!isSelectionMode) {
                toggleSelectionMode();
            } else {
                deleteSelectedSongs();
            }
        });

        // Lấy tất cả bài hát
        baiHats = db.getAllSongs();
        adapter = new SearchMusicAdapter(getContext(), baiHats);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        return view;
    }

    private void toggleSelectionMode() {
        isSelectionMode = !isSelectionMode;
        adapter.setSelectionMode(isSelectionMode);
        buttonDeleteSongs.setText(isSelectionMode ? "Xóa" : "Xóa bài hát");
    }

    private void deleteSelectedSongs() {
        HashSet<Integer> selectedSongIds = adapter.getSelectedSongIds();
        if (selectedSongIds.isEmpty()) {
            Toast.makeText(getContext(), "Vui lòng chọn ít nhất một bài hát để xóa.", Toast.LENGTH_SHORT).show();
            return;
        }

        for (int songId : selectedSongIds) {
            db.deleteSong(songId);
        }

        loadAllSongs();
        toggleSelectionMode();
        Toast.makeText(getContext(), "Đã xóa các bài hát được chọn.", Toast.LENGTH_SHORT).show();
    }

    private void pickAudioFile() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("audio/*");
        audioPickerLauncher.launch(intent);
    }

    private void addSongToDatabase(Uri audioUri) {
        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            retriever.setDataSource(getContext(), audioUri);

            String title = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE);
            String artist = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST);

            if (title == null || title.isEmpty()) {
                title = getFileNameFromUri(audioUri);
            }

            long result = db.addSong(
                    title,
                    artist != null ? artist : "Unknown Artist",
                    null, // Không có hình ảnh, dùng mặc định trong DatabaseHelper
                    audioUri.toString(), // Lưu URI
                    0 // playListId = 0 (không thuộc playlist nào)
            );

            if (result != -1) {
                Toast.makeText(getContext(), "Đã thêm bài hát vào thư viện.", Toast.LENGTH_SHORT).show();
                loadAllSongs();
            } else {
                Toast.makeText(getContext(), "Thêm bài hát thất bại.", Toast.LENGTH_SHORT).show();
            }

        } catch (Exception e) {
            Toast.makeText(getContext(), "Lỗi khi đọc metadata bài hát.", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        } finally {
            try {
                retriever.release();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private String getFileNameFromUri(Uri uri) {
        Cursor cursor = getContext().getContentResolver().query(uri, null, null, null, null);
        String result = "Không rõ tên";
        if (cursor != null && cursor.moveToFirst()) {
            int nameIndex = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
            if (nameIndex >= 0) {
                result = cursor.getString(nameIndex);
            }
            cursor.close();
        }
        return result;
    }

    public void loadAllSongs() {
        baiHats = db.getAllSongs();
        adapter.updateList(baiHats);
    }
}