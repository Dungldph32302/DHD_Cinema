package com.example.dhd_cinema.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dhd_cinema.DataBase.Dbhelper;
import com.example.dhd_cinema.Model.TheLoaiPhim;

import java.util.ArrayList;

public class TheLoaiPhimDao {
    Dbhelper dbHelper;

    public TheLoaiPhimDao(Context context) {
        dbHelper = new Dbhelper(context);
    }
    public ArrayList<TheLoaiPhim> selectAllTheLoaiPhim(){
        ArrayList<TheLoaiPhim> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from TheLoai", null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new TheLoaiPhim(cursor.getInt(0), cursor.getString(1)));

            }while (cursor.moveToNext());
        }
        return list;
    }

    public boolean insert(TheLoaiPhim theLoaiPhim){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cs = new ContentValues();
        cs.put("TenTheLoai",theLoaiPhim.getTenTheLoai());
        long row = db.insert("TheLoai", null, cs);
        return (row > 0);
    }
    // xoa loai sach
    // 1: xoa // 0: xoa that bai -1: co sach tin tai trong the loai
    public int delete(int ID_TL){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from Phim where ID_TL = ?", new String[]{String.valueOf(ID_TL)});
        if (cursor.getCount() != 0){
            return -1;
        }
        long check =  db.delete("TheLoai", "ID_TL = ?", new String[]{String.valueOf(ID_TL)});
        if (check == -1){
            return 0;
        }
        return 1;
    }


    public boolean update(TheLoaiPhim ls) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();//ghi du lieu vao database
        ContentValues values = new ContentValues();//đưa du lieu vao database
        values.put("TenTheLoai", ls.getTenTheLoai());
        long row = db.update("TheLoai", values, "ID_TL=?", new String[]{String.valueOf(ls.getID_TL())});
        return (row > 0);
    }
}
