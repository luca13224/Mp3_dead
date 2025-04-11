package com.example.android.mp3musicapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.android.mp3musicapp.Model.BaiHat;
import com.example.android.mp3musicapp.Model.PlayList;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MusicApp.db";
    private static final int DATABASE_VERSION = 7;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Users
        db.execSQL("CREATE TABLE Users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT, " +
                "email TEXT UNIQUE, " +
                "password TEXT)");

        // Tạo bảng PlayLists
        db.execSQL("CREATE TABLE PlayLists (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "ten TEXT, " +
                "hinhNen TEXT)");

        // Tạo bảng Songs
        db.execSQL("CREATE TABLE Songs (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "tenBaiHat TEXT, " +
                "hinhBaiHat TEXT, " +
                "caSi TEXT, " +
                "linkBaiHat TEXT, " +
                "playListId INTEGER, " +
                "FOREIGN KEY (playListId) REFERENCES PlayLists(id))");

        // Thêm dữ liệu mẫu
        db.execSQL("INSERT INTO Users (username, email, password) VALUES " +
                "('user1', 'user1@example.com', '123456'), " +
                "('user2', 'user2@example.com', '654321')");

        db.execSQL("INSERT INTO PlayLists (ten, hinhNen) VALUES " +
                "('Playlist 1', 'android.resource://com.example.android.mp3musicapp/drawable/playlist'), " +
                "('Playlist 2', 'android.resource://com.example.android.mp3musicapp/drawable/playlist_2'), " +
                "('Favorites', 'android.resource://com.example.android.mp3musicapp/drawable/playlist')");

        db.execSQL("INSERT INTO Songs (tenBaiHat, hinhBaiHat, caSi, linkBaiHat, playListId) VALUES " +
                "('Hay Trao Cho Anh', 'android.resource://com.example.android.mp3musicapp/drawable/hay_trao_cho_anh', 'Sơn Tùng M-TP', 'raw/hay_trao_cho_anh', 1), " +
                "('Tái Sinh', 'android.resource://com.example.android.mp3musicapp/drawable/tai_sinh', 'Tùng Dương', 'raw/tai_sinh', 2), " +
                "('Đi Giữa Trời Rực Rỡ', 'android.resource://com.example.android.mp3musicapp/drawable/di_giua_troi_ruc_ro', 'Ngô Lan Hương', 'raw/di_giua_troi_ruc_ro', 1), " +
                "('Mất Kết Nối', 'android.resource://com.example.android.mp3musicapp/drawable/mat_ket_noi', 'Dương Domic', 'raw/mat_ket_noi', 2)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Users");
        db.execSQL("DROP TABLE IF EXISTS Songs");
        db.execSQL("DROP TABLE IF EXISTS PlayLists");
        db.execSQL("DROP TABLE IF EXISTS ChuDe");
        db.execSQL("DROP TABLE IF EXISTS TheLoai");
        onCreate(db);
    }

    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Users WHERE email = ? AND password = ?", new String[]{email, password});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    public void addUser(String email, String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("INSERT INTO Users (email, username, password) VALUES (?, ?, ?)", new String[]{email, username, password});
    }

    public ArrayList<PlayList> getAllPlayLists() {
        ArrayList<PlayList> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM PlayLists", null);
        if (cursor.moveToFirst()) {
            do {
                PlayList playList = new PlayList();
                playList.setIdPlayList(cursor.getInt(0));
                playList.setTen(cursor.getString(1));
                playList.setHinhNen(cursor.getString(2));
                list.add(playList);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public ArrayList<BaiHat> getSongsByPlayList(int playListId) {
        ArrayList<BaiHat> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Songs WHERE playListId = ?", new String[]{String.valueOf(playListId)});
        if (cursor.moveToFirst()) {
            do {
                BaiHat baiHat = new BaiHat();
                baiHat.setIdBaiHat(cursor.getInt(cursor.getColumnIndex("id")));
                baiHat.setTenBaiHat(cursor.getString(cursor.getColumnIndex("tenBaiHat")));
                baiHat.setHinhBaiHat(cursor.getString(cursor.getColumnIndex("hinhBaiHat")));
                baiHat.setCaSi(cursor.getString(cursor.getColumnIndex("caSi")));
                baiHat.setLinkBaiHat(cursor.getString(cursor.getColumnIndex("linkBaiHat")));
                baiHat.setIdPlayList(cursor.getInt(cursor.getColumnIndex("playListId")));
                list.add(baiHat);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public ArrayList<BaiHat> searchSongs(String query) {
        ArrayList<BaiHat> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Songs WHERE tenBaiHat LIKE ? OR caSi LIKE ?", new String[]{"%" + query + "%", "%" + query + "%"});
        if (cursor.moveToFirst()) {
            do {
                BaiHat baiHat = new BaiHat();
                baiHat.setIdBaiHat(cursor.getInt(cursor.getColumnIndex("id")));
                baiHat.setTenBaiHat(cursor.getString(cursor.getColumnIndex("tenBaiHat")));
                baiHat.setHinhBaiHat(cursor.getString(cursor.getColumnIndex("hinhBaiHat")));
                baiHat.setCaSi(cursor.getString(cursor.getColumnIndex("caSi")));
                baiHat.setLinkBaiHat(cursor.getString(cursor.getColumnIndex("linkBaiHat")));
                baiHat.setIdPlayList(cursor.getInt(cursor.getColumnIndex("playListId")));
                list.add(baiHat);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public ArrayList<BaiHat> getAllSongs() {
        ArrayList<BaiHat> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Songs", null);
        if (cursor.moveToFirst()) {
            do {
                BaiHat baiHat = new BaiHat();
                baiHat.setIdBaiHat(cursor.getInt(cursor.getColumnIndex("id")));
                baiHat.setTenBaiHat(cursor.getString(cursor.getColumnIndex("tenBaiHat")));
                baiHat.setHinhBaiHat(cursor.getString(cursor.getColumnIndex("hinhBaiHat")));
                baiHat.setCaSi(cursor.getString(cursor.getColumnIndex("caSi")));
                baiHat.setLinkBaiHat(cursor.getString(cursor.getColumnIndex("linkBaiHat")));
                baiHat.setIdPlayList(cursor.getInt(cursor.getColumnIndex("playListId")));
                list.add(baiHat);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public boolean isSongInFavorites(int idBaiHat) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM Songs WHERE id = ? AND playListId = 3", new String[]{String.valueOf(idBaiHat)});
        boolean isInFavorites = cursor.getCount() > 0;
        cursor.close();
        return isInFavorites;
    }

    public void addSongToFavorites(int idBaiHat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("playListId", 3); // idPlayList của Favorites
        db.update("Songs", values, "id = ?", new String[]{String.valueOf(idBaiHat)});
        db.close();
    }

    public void removeSongFromFavorites(int idBaiHat) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("playListId", 0); // Đặt lại về 0 (không thuộc playlist nào)
        db.update("Songs", values, "id = ?", new String[]{String.valueOf(idBaiHat)});
        db.close();
    }

    // Thêm phương thức xóa bài hát
    public long deleteSong(int songId) {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("Songs", "id = ?", new String[]{String.valueOf(songId)});
        db.close();
        return result;
    }

    // Thêm phương thức thêm bài hát
    public long addSong(String tenBaiHat, String caSi, String hinhBaiHat, String linkBaiHat, int playListId) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tenBaiHat", tenBaiHat);
        values.put("caSi", caSi);
        values.put("hinhBaiHat", hinhBaiHat != null ? hinhBaiHat : "android.resource://com.example.android.mp3musicapp/drawable/default_song_image");
        values.put("linkBaiHat", linkBaiHat);
        values.put("playListId", playListId);
        long result = db.insert("Songs", null, values);
        db.close();
        return result;
    }
}