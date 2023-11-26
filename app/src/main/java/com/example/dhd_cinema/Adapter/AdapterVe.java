package com.example.dhd_cinema.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dhd_cinema.DanhGiaPhim;
import com.example.dhd_cinema.Dao.GheDao;
import com.example.dhd_cinema.Dao.VeDao;
import com.example.dhd_cinema.Model.GheModel;
import com.example.dhd_cinema.Model.VeModel;
import com.example.dhd_cinema.R;

import java.util.ArrayList;

public class AdapterVe extends RecyclerView.Adapter<AdapterVe.ViewHolder>{
    private final Context context;
    private final ArrayList<VeModel> list;
 VeDao dao;

    public AdapterVe(Context context, ArrayList<VeModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=((Activity)context).getLayoutInflater();
        View view=inflater.inflate(R.layout.itemvephim,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {



        holder.thoigian.setText(list.get(position).getNgay());
        dao= new VeDao(context);
        String namephim=dao.getTenPhimByIdSC(list.get(position).getIdsc());
        holder.phim.setText(namephim);
        String namephon=dao.getTenPhongByIdSC(list.get(position).getIdsc());
        holder.phong.setText(namephon);
        String soghe=dao.getSoGheById(list.get(position).getIdghe());
        holder.soghe.setText(soghe);

        Drawable drawable1= ContextCompat.getDrawable(context, R.drawable.img_19);
        Drawable drawable2= ContextCompat.getDrawable(context, R.drawable.img_20);
        int trangthai=dao.getTrangThaiHoaDonByIdHD(list.get(position).getIdhd());
        if(trangthai==0){
            holder.trangthai.setBackground(drawable2);
        }else{
            holder.trangthai.setBackground(drawable1);

        }
        holder.dg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context, DanhGiaPhim.class);
                intent.putExtra("idsc",list.get(position).getIdsc());
                intent.putExtra("ng",list.get(position).getTendangnhap());
                intent.putExtra("tenphim",list.get(position).getTenphim());
                context.startActivity(intent);
            }
        });




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView trangthai;
        TextView phim,phong,thoigian,soghe,gia;
        AppCompatButton dg;
        public ViewHolder(@NonNull View view) {
            super(view);
            phim=view.findViewById(R.id.tv1);
            phong=view.findViewById(R.id.tv2);
            thoigian=view.findViewById(R.id.tv3);
            soghe=view.findViewById(R.id.tv4);
            gia=view.findViewById(R.id.tv5);
            trangthai=view.findViewById(R.id.img);
            dg=view.findViewById(R.id.btnDanhgia);
        }
    }
}
