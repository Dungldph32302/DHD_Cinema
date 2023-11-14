package com.example.dhd_cinema.Model;

public class PhongModel {
    private int id,loaiPhong,soCho;
    private String TenPhong;

    public PhongModel() {
    }

    public PhongModel(int id, int loaiPhong, int soCho, String tenPhong) {
        this.id = id;
        this.loaiPhong = loaiPhong;
        this.soCho = soCho;
        TenPhong = tenPhong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoaiPhong() {
        return loaiPhong;
    }

    public void setLoaiPhong(int loaiPhong) {
        this.loaiPhong = loaiPhong;
    }

    public int getSoCho() {
        return soCho;
    }

    public void setSoCho(int soCho) {
        this.soCho = soCho;
    }

    public String getTenPhong() {
        return TenPhong;
    }

    public void setTenPhong(String tenPhong) {
        TenPhong = tenPhong;
    }
}
