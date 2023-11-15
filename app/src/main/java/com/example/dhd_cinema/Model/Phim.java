package com.example.dhd_cinema.Model;

public class Phim {
    private int ID_Phim;
    private int ID_TL;
    private String TenPhim;
    private String DaoDien;
    private String NgayPhatHanh;
    private String Mota;
    private String Anh;

    public Phim() {
    }

    public Phim(int ID_TL, String tenPhim, String daoDien, String ngayPhatHanh, String mota, String anh) {
        this.ID_TL = ID_TL;
        TenPhim = tenPhim;
        DaoDien = daoDien;
        NgayPhatHanh = ngayPhatHanh;
        Mota = mota;
        Anh = anh;
    }

    public Phim(int ID_Phim, int ID_TL, String tenPhim, String daoDien, String ngayPhatHanh, String mota, String anh) {
        this.ID_Phim = ID_Phim;
        this.ID_TL = ID_TL;
        TenPhim = tenPhim;
        DaoDien = daoDien;
        NgayPhatHanh = ngayPhatHanh;
        Mota = mota;
        Anh = anh;
    }

    public int getID_Phim() {
        return ID_Phim;
    }

    public void setID_Phim(int ID_Phim) {
        this.ID_Phim = ID_Phim;
    }

    public int getID_TL() {
        return ID_TL;
    }

    public void setID_TL(int ID_TL) {
        this.ID_TL = ID_TL;
    }

    public String getTenPhim() {
        return TenPhim;
    }

    public void setTenPhim(String tenPhim) {
        TenPhim = tenPhim;
    }

    public String getDaoDien() {
        return DaoDien;
    }

    public void setDaoDien(String daoDien) {
        DaoDien = daoDien;
    }

    public String getNgayPhatHanh() {
        return NgayPhatHanh;
    }

    public void setNgayPhatHanh(String ngayPhatHanh) {
        NgayPhatHanh = ngayPhatHanh;
    }

    public String getMota() {
        return Mota;
    }

    public void setMota(String mota) {
        Mota = mota;
    }

    public String getAnh() {
        return Anh;
    }

    public void setAnh(String anh) {
        Anh = anh;
    }
}
