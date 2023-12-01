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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;

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
                    suatChieuModel.setNgayChieu(cursor.getString(cursor.getColumnIndex("NgayChieu")));
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

    @SuppressLint("Range")
    public SuatChieuModel getSuatChieuById(int suatChieuId) {
        SuatChieuModel suatChieuModel = null;
        SQLiteDatabase db = dbhelper.getReadableDatabase();

        try {
            String query = "SELECT SuatChieu.*, Phim.TenPhim, Phim.Anh, PhongChieu.TenPhong " +
                    "FROM SuatChieu " +
                    "INNER JOIN Phim ON SuatChieu.ID_Phim = Phim.ID_Phim " +
                    "INNER JOIN PhongChieu ON SuatChieu.ID_Phong = PhongChieu.ID_Phong " +
                    "WHERE SuatChieu.ID_SC = ?";

            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(suatChieuId)});

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                suatChieuModel = new SuatChieuModel();
                suatChieuModel.setId(cursor.getInt(cursor.getColumnIndex("ID_SC")));
                suatChieuModel.setIdPhim(cursor.getInt(cursor.getColumnIndex("ID_Phim")));
                suatChieuModel.setIdPhong(cursor.getInt(cursor.getColumnIndex("ID_Phong")));
                suatChieuModel.setNgayChieu(cursor.getString(cursor.getColumnIndex("NgayChieu")));
                suatChieuModel.setGioChieu(cursor.getString(cursor.getColumnIndex("GioChieu")));
                suatChieuModel.setGia(cursor.getInt(cursor.getColumnIndex("Gia")));
                suatChieuModel.setTenPhim(cursor.getString(cursor.getColumnIndex("TenPhim")));
                suatChieuModel.setAnh(cursor.getString(cursor.getColumnIndex("Anh")));
                suatChieuModel.setTenPhong(cursor.getString(cursor.getColumnIndex("TenPhong")));
            }

            cursor.close();
        } catch (Exception e) {
            Log.i(TAG, "Lỗi getSuatChieuById", e);
        } finally {
            db.close();
        }

        return suatChieuModel;
    }


    public boolean deleteSuatChieu(int id) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        long row = db.delete("SuatChieu", "ID_SC=?", new String[]{String.valueOf(id)});
        return (row > 0);
    }

    public boolean updateSuatChieu(SuatChieuModel sp) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        values.put("ID_SC", sp.getId());
        values.put("ID_Phim", sp.getIdPhim());
        values.put("ID_Phong", sp.getIdPhong());
        values.put("NgayChieu", sp.getNgayChieu());
        values.put("GioChieu", sp.getGioChieu());
        values.put("Gia", sp.getGia());
        long row = db.update("SuatChieu", values, "ID_SC=?", new String[]{String.valueOf(sp.getId())});
        return (row > 0);
    }

    public boolean addSuatChieu(SuatChieuModel sp) {
        ContentValues values = new ContentValues();
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        values.put("ID_Phim", sp.getIdPhim());
        values.put("ID_Phong", sp.getIdPhong());
        values.put("NgayChieu", sp.getNgayChieu());
        values.put("GioChieu", sp.getGioChieu());
        values.put("Gia", sp.getGia());
        long row = db.insert("SuatChieu", null, values);
        return (row > 0);
    }

//    @SuppressLint("Range")
//    public ArrayList<SuatChieuModel> getsuatchieusapchieu() {
//        ArrayList<SuatChieuModel> list = new ArrayList<>();
//        SQLiteDatabase db = dbhelper.getReadableDatabase();
//        try {
//            String query = "SELECT SuatChieu.*, Phim.TenPhim, Phim.Anh, PhongChieu.TenPhong " +
//                    "FROM SuatChieu " +
//                    "INNER JOIN Phim ON SuatChieu.ID_Phim = Phim.ID_Phim " +
//                    "INNER JOIN PhongChieu ON SuatChieu.ID_Phong = PhongChieu.ID_Phong " +
//                    "WHERE SuatChieu.NgayChieu >= date('now') " +
//                    "ORDER BY SuatChieu.NgayChieu, SuatChieu.GioChieu";
//
//            Cursor cursor = db.rawQuery(query, null);
//
//            if (cursor.getCount() > 0) {
//                cursor.moveToFirst();
//                while (!cursor.isAfterLast()) {
//                    SuatChieuModel suatChieuModel = new SuatChieuModel();
//                    suatChieuModel.setId(cursor.getInt(cursor.getColumnIndex("ID_SC")));
//                    suatChieuModel.setIdPhim(cursor.getInt(cursor.getColumnIndex("ID_Phim")));
//                    suatChieuModel.setIdPhong(cursor.getInt(cursor.getColumnIndex("ID_Phong")));
//                    suatChieuModel.setNgayChieu(cursor.getString(cursor.getColumnIndex("NgayChieu")));
//                    suatChieuModel.setGioChieu(cursor.getString(cursor.getColumnIndex("GioChieu")));
//                    suatChieuModel.setGia(cursor.getInt(cursor.getColumnIndex("Gia")));
//                    suatChieuModel.setTenPhim(cursor.getString(cursor.getColumnIndex("TenPhim")));
//                    suatChieuModel.setAnh(cursor.getString(cursor.getColumnIndex("Anh")));
//                    suatChieuModel.setTenPhong(cursor.getString(cursor.getColumnIndex("TenPhong")));
//                    list.add(suatChieuModel);
//                    cursor.moveToNext();
//                }
//            }
//            cursor.close();
//        } catch (Exception e) {
//            Log.i(TAG, "Lỗi getUpcomingSuatChieuWithInfo", e);
//        } finally {
//            db.close();
//        }
//        return list;
//    }


    @SuppressLint("Range")
    public ArrayList<SuatChieuModel> dachieu() {
        ArrayList<SuatChieuModel> list = new ArrayList<>();
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        try {
            String query = "SELECT SuatChieu.*, Phim.TenPhim, Phim.Anh, PhongChieu.TenPhong " +
                    "FROM SuatChieu " +
                    "INNER JOIN Phim ON SuatChieu.ID_Phim = Phim.ID_Phim " +
                    "INNER JOIN PhongChieu ON SuatChieu.ID_Phong = PhongChieu.ID_Phong " +
                    "WHERE SuatChieu.NgayChieu < date('now') " +
                    "OR (SuatChieu.NgayChieu = date('now') AND SuatChieu.GioChieu < time('now')) " +
                    "ORDER BY SuatChieu.NgayChieu DESC, SuatChieu.GioChieu DESC";
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    SuatChieuModel suatChieuModel = new SuatChieuModel();
                    suatChieuModel.setId(cursor.getInt(cursor.getColumnIndex("ID_SC")));
                    suatChieuModel.setIdPhim(cursor.getInt(cursor.getColumnIndex("ID_Phim")));
                    suatChieuModel.setIdPhong(cursor.getInt(cursor.getColumnIndex("ID_Phong")));
                    suatChieuModel.setNgayChieu(cursor.getString(cursor.getColumnIndex("NgayChieu")));
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
            Log.i(TAG, "Lỗi getPastSuatChieuWithInfo", e);
        } finally {
            db.close();
        }
        return list;
    }

    public boolean isSuatChieuDaChieu(String ngayChieu, String gioChieu) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault());
            String suatChieuDateTimeStr = ngayChieu + " " + gioChieu;
            Date suatChieuDateTime = sdf.parse(suatChieuDateTimeStr);

            long suatChieuTimeMillis = suatChieuDateTime.getTime();
            long currentTimeMillis = System.currentTimeMillis();

            // Nếu thời gian chiếu đã qua, đánh giá là đã chiếu
            return suatChieuTimeMillis < currentTimeMillis;
        } catch (ParseException e) {
            // Xử lý lỗi định dạng ngày giờ nếu cần
            e.printStackTrace();
            return false;
        }
    }

    @SuppressLint("Range")
    public ArrayList<SuatChieuModel> getSuatChieuByPhimId(int phimId, String currentDate) {
        ArrayList<SuatChieuModel> list = new ArrayList<>();
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        HashSet<String> ngayChieuSet = new HashSet<>();

        try {
            String query = "SELECT SuatChieu.*, Phim.TenPhim, Phim.Anh, PhongChieu.TenPhong " +
                    "FROM SuatChieu " +
                    "INNER JOIN Phim ON SuatChieu.ID_Phim = Phim.ID_Phim " +
                    "INNER JOIN PhongChieu ON SuatChieu.ID_Phong = PhongChieu.ID_Phong " +
                    "WHERE SuatChieu.ID_Phim = ? AND SuatChieu.NgayChieu >= ?";

            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(phimId), currentDate});

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    String ngayChieu = cursor.getString(cursor.getColumnIndex("NgayChieu"));

                    // Kiểm tra nếu ngày chiếu đã xuất hiện, thì bỏ qua
                    if (!ngayChieuSet.contains(ngayChieu)) {
                        SuatChieuModel suatChieuModel = new SuatChieuModel();
                        suatChieuModel.setId(cursor.getInt(cursor.getColumnIndex("ID_SC")));
                        suatChieuModel.setIdPhim(cursor.getInt(cursor.getColumnIndex("ID_Phim")));
                        suatChieuModel.setIdPhong(cursor.getInt(cursor.getColumnIndex("ID_Phong")));
                        suatChieuModel.setNgayChieu(ngayChieu);
                        suatChieuModel.setGioChieu(cursor.getString(cursor.getColumnIndex("GioChieu")));
                        suatChieuModel.setGia(cursor.getInt(cursor.getColumnIndex("Gia")));
                        suatChieuModel.setTenPhim(cursor.getString(cursor.getColumnIndex("TenPhim")));
                        suatChieuModel.setAnh(cursor.getString(cursor.getColumnIndex("Anh")));
                        suatChieuModel.setTenPhong(cursor.getString(cursor.getColumnIndex("TenPhong")));

                        list.add(suatChieuModel);
                        ngayChieuSet.add(ngayChieu);
                    }

                    cursor.moveToNext();
                }
            }
            cursor.close();
        } catch (Exception e) {
            Log.i(TAG, "Lỗi getSuatChieuByPhimId", e);
        } finally {
            db.close();
        }
        return list;
    }


    @SuppressLint("Range")
    public ArrayList<SuatChieuModel> getSuatChieuByPhimIdAndDate(int phimId, String ngayChieu) {
        ArrayList<SuatChieuModel> list = new ArrayList<>();
        SQLiteDatabase db = dbhelper.getReadableDatabase();

        try {
            String query = "SELECT SuatChieu.*, Phim.TenPhim, Phim.Anh, PhongChieu.TenPhong " +
                    "FROM SuatChieu " +
                    "INNER JOIN Phim ON SuatChieu.ID_Phim = Phim.ID_Phim " +
                    "INNER JOIN PhongChieu ON SuatChieu.ID_Phong = PhongChieu.ID_Phong " +
                    "WHERE SuatChieu.ID_Phim = ? AND SuatChieu.NgayChieu = ?";

            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(phimId), ngayChieu});

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    SuatChieuModel suatChieuModel = new SuatChieuModel();
                    suatChieuModel.setId(cursor.getInt(cursor.getColumnIndex("ID_SC")));
                    suatChieuModel.setIdPhim(cursor.getInt(cursor.getColumnIndex("ID_Phim")));
                    suatChieuModel.setIdPhong(cursor.getInt(cursor.getColumnIndex("ID_Phong")));
                    suatChieuModel.setNgayChieu(cursor.getString(cursor.getColumnIndex("NgayChieu")));
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
            Log.i(TAG, "Lỗi getSuatChieuByPhimIdAndDate", e);
        } finally {
            db.close();
        }
        return list;
    }

    @SuppressLint("Range")
    public ArrayList<SuatChieuModel> getOneSuatChieuForEachPhim() {
        ArrayList<SuatChieuModel> list = new ArrayList<>();
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        try {
            String query = "SELECT SuatChieu.*, Phim.TenPhim, Phim.Anh, PhongChieu.TenPhong " +
                    "FROM SuatChieu " +
                    "INNER JOIN Phim ON SuatChieu.ID_Phim = Phim.ID_Phim " +
                    "INNER JOIN PhongChieu ON SuatChieu.ID_Phong = PhongChieu.ID_Phong " +
                    "GROUP BY Phim.ID_Phim";

            Cursor cursor = db.rawQuery(query, null);

            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                while (!cursor.isAfterLast()) {
                    SuatChieuModel suatChieuModel = new SuatChieuModel();
                    suatChieuModel.setId(cursor.getInt(cursor.getColumnIndex("ID_SC")));
                    suatChieuModel.setIdPhim(cursor.getInt(cursor.getColumnIndex("ID_Phim")));
                    suatChieuModel.setIdPhong(cursor.getInt(cursor.getColumnIndex("ID_Phong")));
                    suatChieuModel.setNgayChieu(cursor.getString(cursor.getColumnIndex("NgayChieu")));
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
            Log.i(TAG, "Lỗi getOneSuatChieuForEachPhim", e);
        } finally {
            db.close();
        }
        return list;
    }











}
