package com.example.dhd_cinema.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class GheModel implements Parcelable {
    private int id, idPhong, trangThai;
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

    // Phương thức đọc dữ liệu từ Parcel
    protected GheModel(Parcel in) {
        id = in.readInt();
        idPhong = in.readInt();
        trangThai = in.readInt();
        soGhe = in.readString();
        chon = in.readByte() != 0;
    }

    // Phương thức tạo GheModel từ Parcel
    public static final Creator<GheModel> CREATOR = new Creator<GheModel>() {
        @Override
        public GheModel createFromParcel(Parcel in) {
            return new GheModel(in);
        }

        @Override
        public GheModel[] newArray(int size) {
            return new GheModel[size];
        }
    };

    // Phương thức ghi dữ liệu vào Parcel
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(idPhong);
        dest.writeInt(trangThai);
        dest.writeString(soGhe);
        dest.writeByte((byte) (chon ? 1 : 0));
    }

    // Phương thức mô tả nội dung của đối tượng Parcelable
    @Override
    public int describeContents() {
        return 0;
    }
}
