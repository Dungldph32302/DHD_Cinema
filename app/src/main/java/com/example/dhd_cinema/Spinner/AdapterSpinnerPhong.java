package com.example.dhd_cinema.Spinner;

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
import com.example.dhd_cinema.Model.PhongModel;
import com.example.dhd_cinema.R;

import java.util.ArrayList;

public class AdapterSpinnerPhong extends BaseAdapter {
    private Context context;
    private ArrayList<PhongModel> list;

    public AdapterSpinnerPhong(Context context, ArrayList<PhongModel> list) {
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
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();// sinh layout
        convertView = inflater.inflate(R.layout.phon_in_spinner, parent, false); // liên kết
        // ánh xạ thành phần từng thành phần trên  layout
        TextView txtName = convertView.findViewById(R.id.tvname);
        TextView Loai = convertView.findViewById(R.id.tvloai);
        // điền dữ liệu
        txtName.setText(list.get(position).getTenPhong());
        if(list.get(position).getLoaiPhong()==0){
            Loai.setText("Phòng chiếu phim thường");
        }else if(list.get(position).getLoaiPhong()==1){
            Loai.setText("Phòng chiếu phim 3D");
        }else if(list.get(position).getLoaiPhong()==2){
            Loai.setText("Phòng chiếu phim 3D");
        }else if(list.get(position).getLoaiPhong()==3){
            Loai.setText("Phòng chiếu phim Vip");
        }
        else {
           Loai.setText("Phòng Chiếu phim gia đình");
        }

return convertView;
    }
}
