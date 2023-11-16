package com.example.dhd_cinema.Dao;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dhd_cinema.DataBase.Dbhelper;
import com.example.dhd_cinema.Model.NguoiDung;

public class NguoiDungDao {
    Dbhelper dbHelper;

    SharedPreferences sharedPreferences;
    public NguoiDungDao(Context context) {

        this.dbHelper = new Dbhelper(context);

        sharedPreferences = context.getSharedPreferences("ThongTin", Context.MODE_PRIVATE);
    }

    // dang nhap
    public boolean checkDangNhap(String TenDangNhap, String MatKhau) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db. rawQuery("select * from NguoiDung where TenDangNhap=? and MatKhau=?", new String[]{TenDangNhap, MatKhau});
        if (cursor.getCount() != 0){
            cursor.moveToFirst();
            // lưu SharedPreferences
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("TenDangNhap", cursor.getString(1));
            //editor.putString("quyen", cursor.getString(3));
            //editor.putString("HoTen", cursor.getString(1));
            editor.commit();
            return true;
        }
        else {
            return false;
        }
    }

    public boolean capNhatMatKhau(String TenDangNhap,String oldPass, String newPass){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from NguoiDung where TenDangNhap=? and MatKhau=?", new String[]{TenDangNhap, oldPass});
        if (cursor.getCount() > 0){
            ContentValues cs = new ContentValues();
            cs.put("MatKhau", newPass);
            long check = db.update("NguoiDung", cs, "TenDangNhap=?", new String[]{TenDangNhap});
            if (check == -1){
                return false;
            }
            return true;
        }
        return false;
    }

    public boolean insert(NguoiDung tt) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();//ghi du lieu vao database
        ContentValues values = new ContentValues();//đưa du lieu vao database
        values.put("TenDangNhap", tt.getTenDangNhap());
        values.put("Email", tt.getEmail());
        values.put("SDT", tt.getSDT());
        values.put("MatKhau", tt.getMatKhau());
        // nếu ép dữ liệu thành công thì sẽ chả về giá trị tương ứng với số dòng được chèn vào
        long row = db.insert("NguoiDung", null, values);// chèn dữ liệu vào bảng nguoidug
        return (row > 0);
    }
}
