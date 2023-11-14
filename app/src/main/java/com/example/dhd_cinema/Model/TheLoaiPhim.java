package com.example.dhd_cinema.Model;

public class TheLoaiPhim {
    private int ID_TL;
    private String TenTheLoai;

    public TheLoaiPhim(int ID_TL, String tenTheLoai) {
        this.ID_TL = ID_TL;
        this.TenTheLoai = tenTheLoai;
    }

    public TheLoaiPhim(String tenTheLoai) {
        TenTheLoai = tenTheLoai;
    }

    public int getID_TL() {
        return ID_TL;
    }

    public void setID_TL(int ID_TL) {
        this.ID_TL = ID_TL;
    }

    public String getTenTheLoai() {
        return TenTheLoai;
    }

    public void setTenTheLoai(String tenTheLoai) {
        TenTheLoai = tenTheLoai;
    }
}
