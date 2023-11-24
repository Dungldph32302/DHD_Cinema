package com.example.dhd_cinema.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dhd_cinema.Model.NguoiDung;
import com.example.dhd_cinema.Model.Phim;
import com.example.dhd_cinema.R;

import java.util.ArrayList;

public class AdapterNguoiDung extends RecyclerView.Adapter<AdapterNguoiDung.ViewHolder> {
    private Context context;
    private ArrayList<NguoiDung> list;

    public AdapterNguoiDung(Context context, ArrayList<NguoiDung> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public AdapterNguoiDung.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_nguoidung, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterNguoiDung.ViewHolder holder, int position) {
        holder.tenDN.setText("Tên DN: " + list.get(position).getTenDangNhap());
        holder.hoTen.setText("Họ Tên: " + list.get(position).getHoTen());
        holder.email.setText("Email: " + list.get(position).getEmail());
        holder.sDT.setText("SĐT: " + list.get(position).getSDT());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tenDN, hoTen, email, sDT;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tenDN = itemView.findViewById(R.id.txt_TenDangNhap_item);
            hoTen = itemView.findViewById(R.id.txt_HoTen_item);
            email = itemView.findViewById(R.id.txt_Email_item);
            sDT = itemView.findViewById(R.id.txt_SDT_item);

        }
    }
}
