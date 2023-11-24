package com.example.dhd_cinema.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dhd_cinema.DataBase.Dbhelper;
import com.example.dhd_cinema.Model.NguoiDung;
import com.example.dhd_cinema.Model.TheLoaiPhim;

import java.util.ArrayList;

public class NguoiDungDao {
    Dbhelper dbHelper;

    SharedPreferences sharedPreferences;
    public NguoiDungDao(Context context) {

        this.dbHelper = new Dbhelper(context);
        sharedPreferences = context.getSharedPreferences("ThongTin", Context.MODE_PRIVATE);
    }

    // dang nhap
    public boolean checkDangNhap(String TenDangNhap, String matKhau) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db. rawQuery("select * from NguoiDung where TenDangNhap=? and matKhau=?", new String[]{TenDangNhap, matKhau});
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            // lưu SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("TenDangNhap", cursor.getString(0));
            editor.putString("HoTen", cursor.getString(1));
            editor.putString("Email", cursor.getString(2));
            editor.putString("SDT", cursor.getString(3));
            editor.putString("MatKhau", cursor.getString(4));
            editor.commit();
            return true;
        }
        else {
            return false;
        }
    }

    public boolean capNhatMatKhau(String TenDangNhap,String MkCu, String MkMoi){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from NguoiDung where TenDangNhap=? and matKhau=?", new String[]{TenDangNhap,MkCu});
        if (cursor.getCount() > 0){
            ContentValues cs = new ContentValues();
            cs.put("MatKhau", MkMoi);
            long check = db.update("NguoiDung", cs, "TenDangNhap=?", new String[]{TenDangNhap});
            if (check == -1){
                return false;
            }
            return true;
        }
        return false;
    }

    public ArrayList<NguoiDung> selectAllNguoiDUng(){
        ArrayList<NguoiDung> list = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select * from NguoiDung", null);
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            do {
                list.add(new NguoiDung(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4)));

            }while (cursor.moveToNext());
        }
        return list;
    }

    public boolean insert(NguoiDung tt) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();//ghi du lieu vao database
        ContentValues values = new ContentValues();//đưa du lieu vao database
        values.put("TenDangNhap", tt.getTenDangNhap());
        values.put("HoTen", tt.getHoTen());
        values.put("Email", tt.getEmail());
        values.put("SDT", tt.getSDT());
        values.put("MatKhau", tt.getMatKhau());
        // nếu ép dữ liệu thành công thì sẽ chả về giá trị tương ứng với số dòng được chèn vào
        long row = db.insert("NguoiDung", null, values);// chèn dữ liệu vào bảng nguoidug
        return (row > 0);
    }


}
