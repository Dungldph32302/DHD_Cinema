package com.example.dhd_cinema.Model;

public class HoaDonModel {

    private int id,idND,sl,tongtien,phuongthuc,trangthai,gia,idsc;
    private String thoigian,tennguoidung;


    public HoaDonModel(int id, int idND, int sl, int tongtien, int phuongthuc, int trangthai) {
        this.id = id;
        this.idND = idND;
        this.sl = sl;
        this.tongtien = tongtien;
        this.phuongthuc = phuongthuc;
        this.trangthai = trangthai;
    }

    public String getTennguoidung() {
        return tennguoidung;
    }

    public void setTennguoidung(String tennguoidung) {
        this.tennguoidung = tennguoidung;
    }

    public int getIdsc() {
        return idsc;
    }

    public void setIdsc(int idsc) {
        this.idsc = idsc;
    }

    public int getGia() {
        return gia;
    }

    public void setGia(int gia) {
        this.gia = gia;
    }

    public String getThoigian() {
        return thoigian;
    }

    public void setThoigian(String thoigian) {
        this.thoigian = thoigian;
    }

    public HoaDonModel() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdND() {
        return idND;
    }

    public void setIdND(int idND) {
        this.idND = idND;
    }

    public int getSl() {
        return sl;
    }

    public void setSl(int sl) {
        this.sl = sl;
    }

    public int getTongtien() {
        return tongtien;
    }

    public void setTongtien(int tongtien) {
        this.tongtien = tongtien;
    }

    public int getPhuongthuc() {
        return phuongthuc;
    }

    public void setPhuongthuc(int phuongthuc) {
        this.phuongthuc = phuongthuc;
    }

    public int getTrangthai() {
        return trangthai;
    }

    public void setTrangthai(int trangthai) {
        this.trangthai = trangthai;
    }
}
