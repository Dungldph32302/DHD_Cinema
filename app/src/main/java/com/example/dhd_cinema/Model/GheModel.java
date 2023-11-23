package com.example.dhd_cinema.Model;

public class GheModel {
    private int id,idPhong,trangThai;
    private String soGhe;
    private boolean chon;

    public GheModel() {
    }

    public boolean isChon() {
        return chon;
    }

    public void setChon(boolean chon) {
        this.chon = chon;
    }

    public GheModel(int idPhong, int trangThai, String soGhe) {
        this.idPhong = idPhong;
        this.trangThai = trangThai;
        this.soGhe = soGhe;
    }

    public int getIdPhong() {
        return idPhong;
    }

    public void setIdPhong(int idPhong) {
        this.idPhong = idPhong;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }

    public String getSoGhe() {
        return soGhe;
    }

    public void setSoGhe(String soGhe) {
        this.soGhe = soGhe;
    }
}
