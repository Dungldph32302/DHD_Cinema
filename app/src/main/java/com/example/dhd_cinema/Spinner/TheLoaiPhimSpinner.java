package com.example.dhd_cinema.Spinner;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.dhd_cinema.Model.TheLoaiPhim;
import com.example.dhd_cinema.R;

import java.util.ArrayList;

public class TheLoaiPhimSpinner extends BaseAdapter {
    Context context;
    ArrayList<TheLoaiPhim> list;

    public TheLoaiPhimSpinner(Context context, ArrayList<TheLoaiPhim> list) {
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
        return 0;
    }

//    private class TheLoaiPhimViewHolder {
//        TextView txtID_TL_Spinner, txtTenTheLoai_Spinner;
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // khởi tạo và kiên kết layout
        LayoutInflater inflater= ((Activity)context).getLayoutInflater();// sinh layout
        convertView= inflater.inflate(R.layout.item_theloaiphim_spinner,parent,false); // liên kết
        // ánh xạ thành phần từng thành phần trên  layout
        //TextView txtID_TL_Spinner=convertView.findViewById(R.id.txtID_TL_Spinner);
        TextView txtTenTheLoai_Spinner=convertView.findViewById(R.id.txtTenTheLoai_Spinner);


        // điền dữ liệu
        txtTenTheLoai_Spinner.setText(list.get(position).getTenTheLoai());
        //txtID_TL_Spinner.setText(String.valueOf(list.get(position).getID_TL()));
        return convertView;
    }
}
