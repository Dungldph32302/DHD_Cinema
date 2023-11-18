package com.example.dhd_cinema.Dao;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.dhd_cinema.DataBase.Dbhelper;
import com.example.dhd_cinema.Model.GheModel;
import com.example.dhd_cinema.Model.SuatChieuModel;

import java.util.ArrayList;

public class SuatChieuDao {
    private final Context context;
    private final Dbhelper dbhelper;

    public SuatChieuDao(Context context) {
        this.context = context;
        this.dbhelper = new Dbhelper(context);
    }

    @SuppressLint("Range")
    public ArrayList<SuatChieuModel> getAllSuatChieuWithInfo() {
        ArrayList<SuatChieuModel> list = new ArrayList<>();
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        try {
            String query = "SELECT SuatChieu.*, Phim.TenPhim, Phim.Anh, PhongChieu.TenPhong " +
                    "FROM SuatChieu " +
                    "INNER JOIN Phim ON SuatChieu.ID_Phim = Phim.ID_Phim " +
                    "INNER JOIN PhongChieu ON SuatChieu.ID_Phong = PhongChieu.ID_Phong";

            Cursor cursor = db.rawQuery(query, null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    SuatChieuModel suatChieuModel = new SuatChieuModel();
                    suatChieuModel.setId(cursor.getInt(cursor.getColumnIndex("ID_SC")));
                    suatChieuModel.setIdPhim(cursor.getInt(cursor.getColumnIndex("ID_Phim")));
                    suatChieuModel.setIdPhong(cursor.getInt(cursor.getColumnIndex("ID_Phong")));
                    suatChieuModel.setGioChieu(cursor.getString(cursor.getColumnIndex("GioChieu")));
                    suatChieuModel.setGia(cursor.getInt(cursor.getColumnIndex("Gia")));
                    suatChieuModel.setTenPhim(cursor.getString(cursor.getColumnIndex("TenPhim")));
                    suatChieuModel.setAnh(cursor.getString(cursor.getColumnIndex("Anh")));
                    suatChieuModel.setTenPhong(cursor.getString(cursor.getColumnIndex("TenPhong")));

                    list.add(suatChieuModel);
                    cursor.moveToNext();
                }
            }
            cursor.close();
        } catch (Exception e) {
            Log.i(TAG, "Lỗi getAllSuatChieuWithInfo", e);
        } finally {
            db.close();
        }
        return list;
    }

    public boolean deleteSuatChieu(int id){
        SQLiteDatabase db= dbhelper.getWritableDatabase();
        long row=db.delete("SuatChieu","ID_SC=?",new String[]{String.valueOf(id)});
        return  (row>0);
    }
    public boolean updateSuatChieu(SuatChieuModel sp){
        ContentValues values=new ContentValues();
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        values.put("ID_SC",sp.getId());
        values.put("ID_Phim",sp.getIdPhim());
        values.put("ID_Phong",sp.getIdPhong());
        values.put("GioChieu",sp.getGioChieu());
        values.put("Gia",sp.getGia());
        long row=db.update("SuatChieu",values,"ID_SC=?",new String[]{String.valueOf(sp.getId())});
        return (row>0);
    }
    public boolean addSuatChieu(SuatChieuModel sp){
        ContentValues values=new ContentValues();
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        values.put("ID_Phim",sp.getIdPhim());
        values.put("ID_Phong",sp.getIdPhong());
        values.put("GioChieu",sp.getGioChieu());
        values.put("Gia",sp.getGia());
        long row=db.insert("SuatChieu",null,values);
        return (row>0);
    }

}
