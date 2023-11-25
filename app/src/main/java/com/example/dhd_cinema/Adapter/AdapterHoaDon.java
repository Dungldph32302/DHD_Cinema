package com.example.dhd_cinema.Adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dhd_cinema.Dao.HoaDonDao;
import com.example.dhd_cinema.Dao.NguoiDungDao;
import com.example.dhd_cinema.Dao.PhimDao;
import com.example.dhd_cinema.Dao.PhongDao;
import com.example.dhd_cinema.Dao.SuatChieuDao;
import com.example.dhd_cinema.Framgment.Fragment_DatPhim;
import com.example.dhd_cinema.Framgment.Fragment_HoaDon;
import com.example.dhd_cinema.MainActivity;
import com.example.dhd_cinema.Model.HoaDonModel;
import com.example.dhd_cinema.Model.Phim;
import com.example.dhd_cinema.Model.PhongModel;
import com.example.dhd_cinema.Model.SuatChieuModel;
import com.example.dhd_cinema.R;
import com.example.dhd_cinema.Spinner.AdapterSpinnerPhim;
import com.example.dhd_cinema.Spinner.AdapterSpinnerPhong;

import java.util.ArrayList;
import java.util.Calendar;

public class AdapterHoaDon extends RecyclerView.Adapter<AdapterHoaDon.ViewHolder>{

    private final Context context;
    private final ArrayList<HoaDonModel> list;
    private int idphong=0,idphim=0;


    HoaDonDao hoaDonDao;

    public AdapterHoaDon(Context context, ArrayList<HoaDonModel> list) {
        this.context = context;
        this.list = list;
    }

    private int maphong,maphim;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=((Activity)context).getLayoutInflater();
        View view=inflater.inflate(R.layout.itemhoadon,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


              hoaDonDao= new HoaDonDao(context);
        // xử lý khi click vào item suất chiếu
               holder.maHD.setText(String.valueOf("Mã hóa đơn "+list.get(position).getId()));
               holder.maND.setText(String.valueOf("Người Thanh toán: "+list.get(position).getTennguoidung()));
               holder.maSC.setText(String.valueOf("Số Lượng vé: "+list.get(position).getSl()));
               holder.ngay.setText(String.valueOf(list.get(position).getThoigian()));
               holder.tongtien.setText(String.valueOf(list.get(position).getTongtien())+ "$");
               holder.tongtien.setTextColor(Color.parseColor("#0000FF"));;
               int quyen=-1;
        SharedPreferences sharedPreferences = context.getSharedPreferences("login", context.MODE_PRIVATE);
        String tendangnhap = sharedPreferences.getString("username", "");
        NguoiDungDao nguoiDungDao= new NguoiDungDao(context);
        quyen=nguoiDungDao.layQuyenTuDangNhap(tendangnhap);
        if(quyen==0){
            holder.thanhtoan.setVisibility(View.GONE);
            holder.delete.setVisibility(View.GONE);
        }
               if(list.get(position).getTrangthai()==1){
                   holder.trangthai.setText("Đã thanh toán");
                   holder.thanhtoan.setVisibility(View.GONE);
               }else {
                   holder.trangthai.setText("Chưa thanh toán");
               }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView delete,chinh;
        AppCompatButton thanhtoan;
        TextView maHD,maND,maSC,ngay,trangthai,tongtien;
        public ViewHolder(@NonNull View view) {
            super(view);
            delete=view.findViewById(R.id.imgDeletePM);
            thanhtoan=view.findViewById(R.id.btnthanhtoan);
            maHD=view.findViewById(R.id.tvmahd);
            maND=view.findViewById(R.id.tvmand);
            maSC=view.findViewById(R.id.tvsc);
            ngay=view.findViewById(R.id.tvNgay);
            trangthai=view.findViewById(R.id.tvTrangThai);
            tongtien=view.findViewById(R.id.tvtongtien);

        }
    }
}
