package com.example.dhd_cinema.Spinne;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.dhd_cinema.Model.Phim;
import com.example.dhd_cinema.R;

import java.util.ArrayList;

public class AdapterSpinnerPhim extends BaseAdapter {
    private Context context;
    private ArrayList<Phim> list;

    public AdapterSpinnerPhim(Context context, ArrayList<Phim> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // khởi tạo và kiên kết layout
        LayoutInflater inflater= ((Activity)context).getLayoutInflater();// sinh layout
        convertView= inflater.inflate(R.layout.item_phim_in_suat_chieu,parent,false); // liên kết
        // ánh xạ thành phần từng thành phần trên  layout
        TextView txtName=convertView.findViewById(R.id.tvnamephim);
        ImageView img= convertView.findViewById(R.id.imganh);
        // điền dữ liệu
        txtName.setText(list.get(position).getTenPhim());
        String base64String = list.get(position).getAnh();

// Giải mã chuỗi Base64 thành mảng byte
        byte[] decodedByteArray = Base64.decode(base64String, Base64.DEFAULT);

// Chuyển đổi mảng byte thành Bitmap
        Bitmap bitmap = BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.length);

// Hiển thị Bitmap bằng Glide
        Glide.with(context)
                .load(bitmap)
                .into(img);
        return convertView;
    }
}
