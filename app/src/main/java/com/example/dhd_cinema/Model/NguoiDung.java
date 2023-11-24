package com.example.dhd_cinema.Model;

public class NguoiDung {
    private String TenDangNhap;
    private String  HoTen;
    private String  Email;
    private String  SDT;
    private String  MatKhau;

    public NguoiDung(String tenDangNhap, String hoTen, String email, String SDT, String matKhau) {
        TenDangNhap = tenDangNhap;
        HoTen = hoTen;
        Email = email;
        this.SDT = SDT;
        MatKhau = matKhau;
    }

    public NguoiDung() {
    }

    public String getTenDangNhap() {
        return TenDangNhap;
    }

    public void setTenDangNhap(String tenDangNhap) {
        TenDangNhap = tenDangNhap;
    }

    public String getHoTen() {
        return HoTen;
    }

    public void setHoTen(String hoTen) {
        HoTen = hoTen;
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
}
