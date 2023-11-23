package com.example.dhd_cinema.Dao;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import com.example.dhd_cinema.DataBase.Dbhelper;
import com.example.dhd_cinema.Model.GheModel;
import com.example.dhd_cinema.Model.HoaDonModel;

import java.util.ArrayList;

public class HoaDonDao {
    private final Context context;
    private final Dbhelper dbhelper;

    public HoaDonDao(Context context) {
        this.context = context;
        this.dbhelper = new Dbhelper(context);
    }




    // lấy dữ liệu
    public ArrayList<HoaDonModel> getAllHoaDon(){
        ArrayList<HoaDonModel>list= new ArrayList<>();
        SQLiteDatabase db=dbhelper.getReadableDatabase();
        try {
            Cursor cursor=db.rawQuery("Select * from HoaDon  ",null);
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    HoaDonModel sp= new HoaDonModel();
                    sp.setId(cursor.getInt(0));
                    sp.setIdND(cursor.getInt(1));
                    sp.setSl(cursor.getInt(2));
                    sp.setTongtien(cursor.getInt(3));
                    sp.setPhuongthuc(cursor.getInt(4));
                    sp.setTrangthai(cursor.getInt(5));
                    list.add(sp);
                    cursor.moveToNext();
                }
            }
        }catch (Exception e){
            Log.i(TAG, "Lỗi allSP",e);
        }
        return list;
    }

    @SuppressLint("Range")
    public String getHoTenById(int idND) {
        String hoTen = null;

        SQLiteDatabase db = dbhelper.getReadableDatabase();

        try {
            String query = "SELECT HoTen FROM NguoiDung WHERE ID_ND = " + idND;
            Cursor cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                hoTen = cursor.getString(cursor.getColumnIndex("HoTen"));
            }

            cursor.close();
        } catch (Exception e) {
            Log.i(TAG, "Lỗi getHoTenById", e);
        } finally {
            db.close();
        }

        return hoTen;
    }

    public int getMaxHoaDonId() {
        SQLiteDatabase db = dbhelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT MAX(ID_HD) FROM HoaDon", null);
        int maxId = -1;

        if (cursor.moveToFirst()) {
            maxId = cursor.getInt(0);
        }
        cursor.close();
        db.close();

        return maxId+1;
    }

    public boolean addHoaDonandve(HoaDonModel hoaDon, ArrayList<GheModel> gheModels) {
        SQLiteDatabase db = dbhelper.getWritableDatabase();
        db.beginTransaction();

        try {
            ContentValues hoaDonValues = new ContentValues();
            hoaDonValues.put("ID_HD",hoaDon.getId());
            hoaDonValues.put("ID_ND", hoaDon.getIdND());
            hoaDonValues.put("sl", hoaDon.getSl());
            hoaDonValues.put("TongTien", hoaDon.getTongtien());
            hoaDonValues.put("PhuongThuc", hoaDon.getPhuongthuc());
            hoaDonValues.put("TrangThai", hoaDon.getTrangthai());
            long hoaDonId = db.insert("HoaDon", null, hoaDonValues);
            if (hoaDonId != -1) {
                if(gheModels.size()>=0) {
                    for (GheModel gheModel : gheModels) {
                        ContentValues chiTietValues = new ContentValues();
                        chiTietValues.put("ID_ND", hoaDon.getIdND());
                        chiTietValues.put("ID_SC", hoaDon.getIdsc());
                        chiTietValues.put("ID_Ghe",gheModel.getId());
                        chiTietValues.put("ID_HD", hoaDon.getId());
                        chiTietValues.put("gia", hoaDon.getGia());
                        chiTietValues.put("ThoiGian", hoaDon.getThoigian());
                        long row = db.insert("Ve", null, chiTietValues);
                        if (row == -1) {
                            db.endTransaction();
                            return false;
                        }
                    }
                }else {
                    Toast.makeText(context, "Vui lòng chọn Ghế ", Toast.LENGTH_SHORT).show();
                }

                db.setTransactionSuccessful();
                return true;
            } else {
                Log.e("AddHoaDonAndChiTiet", "Lỗi khi thêm hóa đơn");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
            db.close();
        }

        return false;
    }
}
