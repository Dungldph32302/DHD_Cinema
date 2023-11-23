package com.example.dhd_cinema.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dhd_cinema.Dao.GheDao;
import com.example.dhd_cinema.Dao.VeDao;
import com.example.dhd_cinema.Model.GheModel;
import com.example.dhd_cinema.R;

import java.util.ArrayList;

public class AdapterGhe_in_Dat extends RecyclerView.Adapter<AdapterGhe_in_Dat.ViewHolder>{
    private final Context context;
    private final ArrayList<GheModel> list;
 GheDao dao;
VeDao veDao;
private int index=0;
    public int getTongTatCa() {
        int tong = 0;
        for (GheModel gheModel : list) {
            if (gheModel.isChon()) {
               tong++;
            }
        }
        return tong;
    }
    public ArrayList<GheModel> getSelectedListGhe() {
        ArrayList<GheModel>list1= new ArrayList<>();

        for (GheModel gheModel : list) {
            if (gheModel.isChon()) {
                list1.add(gheModel);
            }
        }
        return list1;
    }

private  ArrayList<GheModel> list1;
    public AdapterGhe_in_Dat(Context context, ArrayList<GheModel> list) {
        this.context = context;
        this.list = list;
        dao = new GheDao(context);
        veDao = new VeDao(context);
    }
    // bắt sự kiện lắng nghe
    private OnQuantityChangeListener onQuantityChangeListener;
    public interface OnQuantityChangeListener {
        void onQuantityChanged();
    }


    public void setOnQuantityChangeListener(OnQuantityChangeListener listener) {
        this.onQuantityChangeListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=((Activity)context).getLayoutInflater();
        View view=inflater.inflate(R.layout.item_ghe_in_dat,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Drawable drawable= ContextCompat.getDrawable(context, R.drawable.img_ghe1);
        Drawable drawable1= ContextCompat.getDrawable(context,R.drawable.img_11);
        Drawable drawable2= ContextCompat.getDrawable(context,R.drawable.img_12);
            holder.name.setText(list.get(position).getSoGhe());
            GheModel ghe=list.get(position);
            if(veDao.isSeatBooked(list.get(position).getId())){
                holder.ghe.setBackground(drawable2);
            }
            holder.ghe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    index++;
                    if(veDao.isSeatBooked(list.get(position).getId())){
                        holder.ghe.setBackground(drawable2);
                        list.get(position).setChon(false);
                    }else {
                        if(index>1){
                            index=0;
                            list.get(position).setChon(true);
                            holder.ghe.setBackground(drawable1);

                        }else {
                            list.get(position).setChon(false);
                            holder.ghe.setBackground(drawable);
                        }
                    }
                    if(onQuantityChangeListener != null) {
                        onQuantityChangeListener.onQuantityChanged();
                    }
                }
            });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ghe;
        TextView id,phong,trangThai,name;
        public ViewHolder(@NonNull View view) {
            super(view);
            ghe=view.findViewById(R.id.imageView);
            name=view.findViewById(R.id.tvNameGhe);
        }
    }


}
