package com.example.dhd_cinema.Model;

public class Phong_Ghe_chitiet {
    private int id,idphong,idghe,trangthai;
    private String soGhe;

    public Phong_Ghe_chitiet() {
    }

    public String getSoGhe() {
        return soGhe;
    }

    public void setSoGhe(String soGhe) {
        this.soGhe = soGhe;
    }

    public Phong_Ghe_chitiet(int id, int idphong, int idghe, int trangthai, String soGhe) {
        this.id = id;
        this.idphong = idphong;
        this.idghe = idghe;
        this.trangthai = trangthai;
        this.soGhe = soGhe;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdphong() {
        return idphong;
    }

    public void setIdphong(int idphong) {
        this.idphong = idphong;
    }

    public int getIdghe() {
        return idghe;
    }

    public void setIdghe(int idghe) {
        this.idghe = idghe;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }
}
