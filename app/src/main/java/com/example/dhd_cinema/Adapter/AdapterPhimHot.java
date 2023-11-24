package com.example.dhd_cinema.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dhd_cinema.Model.Phim;
import com.example.dhd_cinema.R;

import java.util.ArrayList;

public class AdapterPhimHot extends RecyclerView.Adapter<AdapterPhimHot.ViewHolder> {

    private Context context;
    private ArrayList<Phim> list;

    public AdapterPhimHot(Context context, ArrayList<Phim> list) {
        this.context = context;
        this.list = list;
    }
    @NonNull
    @Override
    public AdapterPhimHot.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_phimhot, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterPhimHot.ViewHolder holder, int position) {
        holder.ten.setText("Tên: " + list.get(position).getTenPhim());
        holder.soLuong.setText("Số Lượng: " + String.valueOf(list.get(position).getSoLuongVeDat()));

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView anh;
        TextView ten, soLuong;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            anh = itemView.findViewById(R.id.img_AnhTop10);
            ten = itemView.findViewById(R.id.txtTenPhimTop10);
            soLuong = itemView.findViewById(R.id.txtSoLuongTop10);
        }
    }
}
