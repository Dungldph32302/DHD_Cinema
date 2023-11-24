package com.example.dhd_cinema.Model;

public class VeModel {
    private int id,idsc,idnd,idghe,gia,idhd,trangthai;
    private String ngay,tendangnhap,tenphim,tenphong;

    public VeModel() {
    }

    public VeModel(int id, int idsc, int idnd, int idghe, int gia, int idhd, String ngay) {
        this.id = id;
        this.idsc = idsc;
        this.idnd = idnd;
        this.idghe = idghe;
        this.gia = gia;
        this.idhd = idhd;
        this.ngay = ngay;
    }

    public String getTenphim() {
        return tenphim;
    }

    public void setTenphim(String tenphim) {
        this.tenphim = tenphim;
    }

    public String getTenphong() {
        return tenphong;
    }

    public void setTenphong(String tenphong) {
        this.tenphong = tenphong;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }

    public String getTendangnhap() {
        return tendangnhap;
    }

    public void setTendangnhap(String tendangnhap) {
        this.tendangnhap = tendangnhap;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdsc() {
        return idsc;
    }

    public void setIdsc(int idsc) {
        this.idsc = idsc;
    }

    public int getIdnd() {
        return idnd;
    }

    public void setIdnd(int idnd) {
        this.idnd = idnd;
    }

    public int getIdghe() {
        return idghe;
    }

    public void setIdghe(int idghe) {
        this.idghe = idghe;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public int getIdhd() {
        return idhd;
    }

    public void setIdhd(int idhd) {
        this.idhd = idhd;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }
}
