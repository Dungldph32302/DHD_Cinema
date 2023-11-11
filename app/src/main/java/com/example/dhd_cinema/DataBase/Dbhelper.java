package com.example.dhd_cinema.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Dbhelper extends SQLiteOpenHelper {
    private static final String DB="hh";
    public Dbhelper(@Nullable Context context) {
        super(context, DB,null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table NguoiDung(\n" +
                "      ID_ND integer primary key autoincrement,\n" +
                "      TenDangNhap TEXT not null,\n" +
                "      Email TEXT not null,\n" +
                "      SDT TEXT not null,\n" +
                "      MatKhau TEXT not null,\n" +
                "      Quyen integer not null)");

        db.execSQL("create table TheLoai(\n" +
                "      ID_TL integer primary key autoincrement,\n" +
                "      TenTheLoai TEXT not null)");

        db.execSQL("create table Phim(\n" +
                "      ID_Phim integer primary key autoincrement,\n" +
                "      ID_TL integer REFERENCES TheLoai(ID_TL),\n" +
                "      TenPhim TEXT not null,\n" +
                "      DaoDien TEXT not null,\n" +
                "      NgayPhatHanh TEXT not null,\n" +
                "      Mota TEXT not null,\n" +
                "      Anh TEXT)");

        db.execSQL("create table PhongChieu(\n" +
                "      ID_Phong integer primary key autoincrement,\n" +
                "      TenPhong TEXT not null,\n" +
                "      SoCho TEXT not null,\n" +
                "      LoaiPhong integer not null)");

        db.execSQL("create table Ghe(\n" +
                "      ID_Ghe integer primary key autoincrement,\n" +
                "      ID_Phong integer REFERENCES PhongChieu(ID_Phong),\n" +
                "      SoGhe TEXT not null,\n" +
                "      TrangThai integer not null)");

        db.execSQL("create table SuatChieu(\n" +
                "      ID_SC integer primary key autoincrement,\n" +
                "      ID_Phim integer REFERENCES Phim(ID_Phim),\n" +
                "      ID_Phong integer REFERENCES PhongChieu(ID_Phong),\n" +
                "      GioChieu TEXT not null,\n" +
                "      Gia integer not null)");

        db.execSQL("create table Ve(\n" +
                "      ID_Ve integer primary key autoincrement,\n" +
                "      ID_ND integer REFERENCES NguoiDung(ID_ND),\n" +
                "      ID_SC integer REFERENCES SuatChieu(ID_SC),\n" +
                "      ID_Ghe integer REFERENCES Ghe(ID_Ghe),\n" +
                "      ThoiGian TEXT not null)");

        db.execSQL("create table DanhGiaPhim(\n" +
                "      ID_DG integer primary key autoincrement,\n" +
                "      ID_ND integer REFERENCES NguoiDung(ID_ND),\n" +
                "      ID_Phim integer REFERENCES Phim(ID_Phim),\n" +
                "      NoiDung TEXT,\n" +
                "      Sao int not null)");

        db.execSQL("create table HoaDon(\n" +
                "      ID_HD integer primary key autoincrement,\n" +
                "      ID_ND integer REFERENCES NguoiDung(ID_ND),\n" +
                "      ID_Ve integer REFERENCES Ve(ID_Ve),\n" +
                "      TongTien Integer not null,\n" +
                "      PhuongThuc integer not null,\n" +
                "      TrangThai int not null)");

        db.execSQL("INSERT INTO NguoiDung (TenDangNhap, Email, SDT, MatKhau, Quyen) " +
                "VALUES (1,'user1', 'user1@example.com', '123456789', 'password1', 1)");

        db.execSQL("INSERT INTO TheLoai (TenTheLoai) " +
                "VALUES (1,'Hành động')");

        db.execSQL("INSERT INTO Phim (ID_TL, TenPhim, DaoDien, NgayPhatHanh, Mota, Anh) " +
                "VALUES (1, 'Phim 1', 'Đạo diễn 1', '2021-01-01', 'Mô tả phim 1', 'anh1.jpg')");

        db.execSQL("INSERT INTO PhongChieu (TenPhong, SoCho, LoaiPhong) " +
                "VALUES ('Phòng 1', '100', 1)");

        db.execSQL("INSERT INTO Ghe (ID_Phong, SoGhe, TrangThai) " +
                "VALUES (1, 'A1', 1)");

        db.execSQL("INSERT INTO SuatChieu (ID_Phim, ID_Phong, GioChieu, Gia) " +
                "VALUES (1, 1, '18:00', 10000)");

        db.execSQL("INSERT INTO Ve (ID_ND, ID_SC, ID_Ghe, ThoiGian) " +
                "VALUES (1, 1, 1, '2021-01-01 17:30')");

        db.execSQL("INSERT INTO DanhGiaPhim (ID_ND, ID_Phim, NoiDung, Sao) " +
                "VALUES (1, 1, 'Bộ phim rất hay!', 5)");

        db.execSQL("INSERT INTO HoaDon (ID_ND, ID_Ve, TongTien, PhuongThuc, TrangThai) " +
                "VALUES (1, 1, 10000, 1, 1)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion){
            db.execSQL("DROP TABLE IF EXISTS NguoiDung");
            db.execSQL("DROP TABLE IF EXISTS TheLoai");
            db.execSQL("DROP TABLE IF EXISTS Phim");
            db.execSQL("DROP TABLE IF EXISTS PhongChieu");
            db.execSQL("DROP TABLE IF EXISTS Ghe");
            db.execSQL("DROP TABLE IF EXISTS SuatChieu");
            db.execSQL("DROP TABLE IF EXISTS Ve");
            db.execSQL("DROP TABLE IF EXISTS DanhGiaPhim");
            db.execSQL("DROP TABLE IF EXISTS HoaDon");

            onCreate(db);// goi lai ham  onCreate


        }
    }
}
