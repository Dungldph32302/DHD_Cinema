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
    public boolean checkDangNhapGmail(String TenDangNhap, String MatKhau) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from NguoiDung where Email=? and MatKhau=?",new String[]{TenDangNhap,MatKhau});
        int row=cursor.getCount();
        cursor.close();
        db.close();
        return (row>0);
    }
    public boolean checkDangNhapSDT(String SDT, String MatKhau) {
        SQLiteDatabase db=dbHelper.getWritableDatabase();
        Cursor cursor=db.rawQuery("select * from NguoiDung where SDT=? and MatKhau=?",new String[]{SDT,MatKhau});
        int row=cursor.getCount();
        cursor.close();
        db.close();
        return (row>0);
    }

    public boolean capNhatMatKhau(String TenDangNhap,String oldPass, String newPass){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from NguoiDung where HoTen=? and MatKhau=?", new String[]{TenDangNhap, oldPass});
        if (cursor.getCount() > 0){
            ContentValues cs = new ContentValues();
            cs.put("MatKhau", newPass);
            long check = db.update("NguoiDung", cs, "HoTen=?", new String[]{TenDangNhap});
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
        values.put("HoTen", tt.getTenDangNhap());
        values.put("Email", tt.getEmail());
        values.put("SDT", tt.getSDT());
        values.put("MatKhau", tt.getMatKhau());
        // nếu ép dữ liệu thành công thì sẽ chả về giá trị tương ứng với số dòng được chèn vào
        long row = db.insert("NguoiDung", null, values);// chèn dữ liệu vào bảng nguoidug
        return (row > 0);
    }
}
