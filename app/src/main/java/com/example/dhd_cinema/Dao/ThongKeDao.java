package com.example.dhd_cinema.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dhd_cinema.DataBase.Dbhelper;
import com.example.dhd_cinema.Model.Phim;
import com.example.dhd_cinema.Model.SuatChieuModel;

import java.util.ArrayList;

public class ThongKeDao {
    Dbhelper dbHelper;
    private Context context;


    public ThongKeDao(Context context){
        this.context = context;
        dbHelper = new Dbhelper(context);
    }

    public ArrayList<Phim> selectTop10(){
        ArrayList<Phim> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Phim.TenPhim, Phim.Anh, COUNT(Ve.ID_Ve) AS SoLuongVeDat " +
                "FROM Phim " +
                "INNER JOIN SuatChieu ON Phim.ID_Phim = SuatChieu.ID_Phim " +
                "INNER JOIN Ve ON SuatChieu.ID_SC = Ve.ID_SC " +
                "GROUP BY Phim.ID_Phim " +
                "ORDER BY SoLuongVeDat DESC " +
                "LIMIT 5", null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new Phim(cursor.getString(0), cursor.getString(1), cursor.getInt(2)));
            }while (cursor.moveToNext());
        }
        return list;
    }

    public ArrayList<Phim> selectTopPhimDanhGiaCao(){
        ArrayList<Phim> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT Phim.Anh, AVG(DanhGiaPhim.Sao) AS TrungBinhCong\n" +
                "FROM Phim\n" +
                "INNER JOIN DanhGiaPhim ON Phim.ID_Phim = DanhGiaPhim.ID_Phim\n" +
                "GROUP BY Phim.ID_Phim, Phim.Anh\n" +
                "HAVING COUNT(DanhGiaPhim.ID_DG) > 0\n" +
                "ORDER BY TrungBinhCong DESC\n" +
                "LIMIT 10;", null);

        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new Phim(cursor.getString(0), cursor.getInt(1)));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public int DoanhThu(String tuNgay, String denNgay) {
        SQLiteDatabase sqLiteDatabase = dbHelper.getWritableDatabase();
        String sql = "SELECT SUM(TongTien) as doanhThu FROM HoaDon WHERE ngay BETWEEN ? AND ?";
        String dk[] = {tuNgay, denNgay};
        int doanhThu = 0;
        Cursor cursor = sqLiteDatabase.rawQuery(sql, dk);
        if (cursor.moveToFirst()) {
            try {
                doanhThu = cursor.getInt(cursor.getColumnIndexOrThrow("doanhThu"));
            } catch (Exception e) {
                doanhThu = 0;
            }
        }
        cursor.close();
        return doanhThu;
    }
}
