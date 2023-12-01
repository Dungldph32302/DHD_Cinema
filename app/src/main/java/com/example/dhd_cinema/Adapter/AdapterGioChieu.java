package com.example.dhd_cinema.Adapter;

import static com.example.dhd_cinema.Adapter.AdapterSuatChieu.compareDates;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dhd_cinema.Dao.SuatChieuDao;
import com.example.dhd_cinema.Framgment.Fragment_DatPhim;
import com.example.dhd_cinema.Framgment.Fragment_HoaDon;
import com.example.dhd_cinema.MainActivity;
import com.example.dhd_cinema.Model.SuatChieuModel;
import com.example.dhd_cinema.R;

import java.util.ArrayList;

public class AdapterGioChieu extends RecyclerView.Adapter<AdapterGioChieu.ViewHolder>{

    private final Context context;
    private final ArrayList<SuatChieuModel> list;
    private int idphim=0;

    SuatChieuDao dao;
    public AdapterGioChieu(Context context, ArrayList<SuatChieuModel> list) {
        this.context = context;
        this.list = list;
    }

    private AdapterGhe_in_Dat.OnQuantityChangeListener onQuantityChangeListener;
    public interface OnQuantityChangeListener {
        void onQuantityChanged();
    }


    public void setOnQuantityChangeListener(AdapterGhe_in_Dat.OnQuantityChangeListener listener) {
        this.onQuantityChangeListener = listener;
    }
    private int maphong,maphim;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=((Activity)context).getLayoutInflater();
        View view=inflater.inflate(R.layout.item_ngaychieu,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       holder.ngay.setText(list.get(position).getGioChieu());
       int vtr=holder.getAdapterPosition();
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               String ngay = list.get(position).getNgayChieu();
               @SuppressLint({"NewApi", "LocalSuppress"})
               String ngayht = String.valueOf(java.time.LocalDate.now());
               int result = compareDates(ngay, ngayht);
               if(result>0||result==0) {

                   Fragment_DatPhim frg = new Fragment_DatPhim();
                   Fragment_HoaDon frg1 = new Fragment_HoaDon();
                   MainActivity mainActivity = (MainActivity) context;
                   mainActivity.replec(frg);

                   String ng = list.get(position).getGioChieu() + "  " + list.get(position).getNgayChieu();
                   Bundle bundle = new Bundle();
                   bundle.putInt("scid", list.get(position).getId());
                   bundle.putString("ngaygio", ng);
                   bundle.putString("ten", list.get(position).getTenPhim());
                   bundle.putInt("gia", list.get(position).getGia());
                   frg.setArguments(bundle);
                   frg1.setArguments(bundle);
               }else {
                   Toast.makeText(context, "Xuất chiếu này đã được chiếu ", Toast.LENGTH_SHORT).show();
               }
           }
       });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView ngay;
        public ViewHolder(@NonNull View view) {
            super(view);
            ngay=view.findViewById(R.id.tvngay);
        }
    }

}

