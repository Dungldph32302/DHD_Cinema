package com.example.dhd_cinema.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dhd_cinema.DataBase.Dbhelper;
import com.example.dhd_cinema.Model.GheModel;
import com.example.dhd_cinema.Model.danhgiamodel;

public class DanhgiaDao { private final Context context;
    private final Dbhelper dbhelper;

    public DanhgiaDao(Context context) {
        this.context = context;
        this.dbhelper = new Dbhelper(context);
    }

    public boolean adđg(danhgiamodel sp){
        ContentValues values=new ContentValues();
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        values.put("TenDangNhap",sp.getId());
        values.put("ID_Phim",sp.getIdphim());
        values.put("NoiDung",sp.getNoidung());
        values.put("Sao",sp.getSao());
        long row=db.insert("DanhGiaPhim",null,values);
        return (row>0);
    }

    public int getPhimIdBySuatChieuId(int idSuatChieu) {
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        int phimId = -1; // Giá trị mặc định nếu không tìm thấy

        try {
            String query = "SELECT Phim.ID_Phim " +
                    "FROM Phim " +
                    "INNER JOIN SuatChieu ON Phim.ID_Phim = SuatChieu.ID_Phim " +
                    "WHERE SuatChieu.ID_SC = ?";
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idSuatChieu)});

            if (cursor.moveToFirst()) {
                phimId = cursor.getInt(0);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }

        return phimId;
    }


}
