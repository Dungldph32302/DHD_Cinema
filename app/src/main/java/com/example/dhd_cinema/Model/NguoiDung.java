package com.example.dhd_cinema.Model;

public class NguoiDung {
    private int ID_ND;
    private String  TenDangNhap;
    private String  Email;
    private String  SDT;
    private String  MatKhau;

    public NguoiDung( String tenDangNhap, String email, String SDT, String matKhau) {
        TenDangNhap = tenDangNhap;
        Email = email;
        this.SDT = SDT;
        MatKhau = matKhau;
    }

    public int getID_ND() {
        return ID_ND;
    }

    public void setID_ND(int ID_ND) {
        this.ID_ND = ID_ND;
    }



    public String getTenDangNhap() {
        return TenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        TenDangNhap = tenDangNhap;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getSDT() {
        return SDT;
    }

    public void setSDT(String SDT) {
        this.SDT = SDT;
    }

    public String getMatKhau() {
        return MatKhau;
    }

    public void setMatKhau(String matKhau) {
        MatKhau = matKhau;
    }

    public NguoiDung(int ID_ND, String tenDangNhap, String email, String SDT, String matKhau) {
        this.ID_ND = ID_ND;
        TenDangNhap = tenDangNhap;
        Email = email;
        this.SDT = SDT;
        MatKhau = matKhau;
    }
}
