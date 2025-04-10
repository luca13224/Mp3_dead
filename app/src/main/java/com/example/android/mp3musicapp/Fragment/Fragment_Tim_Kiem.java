package com.example.android.mp3musicapp.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.mp3musicapp.db.DatabaseHelper;
import com.example.android.mp3musicapp.Adapter.SearchMusicAdapter;
import com.example.android.mp3musicapp.Model.BaiHat;
import com.example.android.mp3musicapp.R;

import java.util.ArrayList;

public class Fragment_Tim_Kiem extends Fragment {
    RecyclerView recyclerView;
    TextView tvNoResult;
    SearchMusicAdapter adapter;
    DatabaseHelper db;
    ArrayList<BaiHat> baiHats;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tim_kiem, container, false);
        recyclerView = view.findViewById(R.id.recyclerViewSearchMusic);
        tvNoResult = view.findViewById(R.id.tvNoResultForMusic);
        db = new DatabaseHelper(getContext());

        Toolbar toolbar = view.findViewById(R.id.toolBarSearchMusic);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        setHasOptionsMenu(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        baiHats = new ArrayList<>();
        adapter = new SearchMusicAdapter(getContext(), baiHats);
        recyclerView.setAdapter(adapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.search_view, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.menuSearch).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchSongs(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchSongs(newText);
                return true;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

    private void searchSongs(String query) {
        baiHats.clear();
        baiHats.addAll(db.searchSongs(query));
        adapter.notifyDataSetChanged();
        tvNoResult.setVisibility(baiHats.isEmpty() ? View.VISIBLE : View.GONE);
    }
}