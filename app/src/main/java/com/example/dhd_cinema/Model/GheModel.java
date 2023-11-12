package com.example.dhd_cinema.Model;

public class GheModel {
    private int id,trangThai;
    private String soGhe;

    public GheModel() {
    }

    public GheModel(int id, int trangThai, String soGhe) {
        this.id = id;
        this.trangThai = trangThai;
        this.soGhe = soGhe;
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
