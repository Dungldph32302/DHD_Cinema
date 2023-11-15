package com.example.dhd_cinema.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dhd_cinema.DataBase.Dbhelper;
import com.example.dhd_cinema.Model.Phim;

import java.util.ArrayList;

public class PhimDao {
    Dbhelper dbHelper;

    public PhimDao(Context context) {
        dbHelper = new Dbhelper(context);
    }
    public ArrayList<Phim> selectAllPhim(){
        ArrayList<Phim> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from Phim", null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new Phim(cursor.getInt(0), cursor.getInt(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5), cursor.getString(6)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public boolean insert(Phim phim){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues cs = new ContentValues();
        cs.put("ID_TL",phim.getID_TL());
        cs.put("TenPhim",phim.getTenPhim());
        cs.put("DaoDien",phim.getDaoDien());
        cs.put("NgayPhatHanh",phim.getNgayPhatHanh());
        cs.put("Mota",phim.getMota());
        cs.put("Anh",phim.getAnh());
        long row = db.insert("Phim", null, cs);
        return (row > 0);
    }
    // xoa loai sach
    // 1: xoa // 0: xoa that bai -1: co sach tin tai trong the loai
    public int delete(int ID_Phim){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from SuatChieu where ID_Phim = ?", new String[]{String.valueOf(ID_Phim)});
        if (cursor.getCount() != 0){
            return -1;
        }
        long check =  db.delete("Phim", "ID_Phim = ?", new String[]{String.valueOf(ID_Phim)});
        if (check == -1){
            return 0;
        }
        return 1;
    }


    public boolean update(Phim ls) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();//ghi du lieu vao database
        ContentValues values = new ContentValues();//đưa du lieu vao database
        values.put("ID_TL",ls.getID_TL());
        values.put("TenPhim",ls.getTenPhim());
        values.put("DaoDien",ls.getDaoDien());
        values.put("NgayPhatHanh",ls.getNgayPhatHanh());
        values.put("Mota",ls.getMota());
        values.put("Anh",ls.getAnh());
        long row = db.update("Phim", values, "ID_Phim=?", new String[]{String.valueOf(ls.getID_Phim())});
        return (row > 0);
    }
}
