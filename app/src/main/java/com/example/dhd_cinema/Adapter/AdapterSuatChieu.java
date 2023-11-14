package com.example.dhd_cinema.Adapter;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.dhd_cinema.Dao.PhongDao;
import com.example.dhd_cinema.Model.PhongModel;
import com.example.dhd_cinema.Model.SuatChieuModel;
import com.example.dhd_cinema.R;

import java.util.ArrayList;

public class AdapterSuatChieu extends RecyclerView.Adapter<AdapterSuatChieu.ViewHolder>{

    private final Context context;
    private final ArrayList<SuatChieuModel> list;
    PhongDao phongDao;

    public AdapterSuatChieu(Context context, ArrayList<SuatChieuModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=((Activity)context).getLayoutInflater();
        View view=inflater.inflate(R.layout.item_suat_chieu,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        phongDao= new PhongDao(context);
        SuatChieuModel suatChieuModel= list.get(position);
        holder.name.setText(String.valueOf(list.get(position).getTenPhim()));
        holder.ngay.setText("Giờ chiếu "+list.get(position).getGioChieu());
        int resourceId = context.getResources().getIdentifier(list.get(position).getAnh(), "drawable", context.getPackageName());

// Kiểm tra xem tài nguyên có tồn tại không
        if (resourceId != 0) {
            // Sử dụng Glide để tải ảnh từ tài nguyên drawable
            Glide.with(context)
                    .load(resourceId)
                    .into(holder.anh);
        } else {
            // Xử lý trường hợp tài nguyên không tồn tại
            Log.e(TAG, "Tài nguyên không tồn tại: link_anh1");
        }
//       Glide.with(context).load(list.get(position).getAnh()).into(holder.anh);
//        holder.cham.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                PopupMenu popupMenu = new PopupMenu(context, view);
//                popupMenu.getMenuInflater().inflate(R.menu.popumenu, popupMenu.getMenu());
//
//                // Bắt sự kiện khi một mục trong menu được chọn
//                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
//                    @Override
//                    public boolean onMenuItemClick(MenuItem item) {
//                        int itemId = item.getItemId();
//
//                        if (itemId == R.id.action_custom){
//                            // Xử lý khi chọn tùy chỉnh
//                            openDialogUpdate(phongModel);
//                            return true;
//                        } else if (itemId == R.id.action_delete) {
//                            AlertDialog.Builder builder = new AlertDialog.Builder(context);
//                            builder.setTitle("Cảnh báo!");
//                            builder.setIcon(R.drawable.baseline_warning_24);
//                            builder.setMessage("Bạn có muốn xoá?");
//                            builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int i) {
//                                    if (phongDao.deletePhongChieu(list.get(position).getId())) {
//                                        list.clear();
//                                        list.addAll(phongDao.getAllPhongChieu());
//                                        notifyDataSetChanged();
//                                        Toast.makeText(context, "Xoá thành công", Toast.LENGTH_SHORT).show();
//                                        // Sau khi xóa thành công sản phẩm
//                                        //  NotificationHelper.showNotification(context.getApplicationContext(), "Bạn đã xóa một sản phẩm.");
//
//                                    } else {
//                                        Toast.makeText(context, "Xoá thất bại", Toast.LENGTH_SHORT).show();
//                                    }
//
//                                }
//                            });
//
//                            builder.setNegativeButton("Huỷ", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int i) {
//                                    Toast.makeText(context, "Huỷ xoá", Toast.LENGTH_SHORT).show();
//                                }
//                            });
//                            AlertDialog dialog = builder.create();
//                            dialog.show();
//                            // Xử lý khi chọn xóa
//                            return true;
//                        }
//                        return  false;
//                    }
//                });
//
//                popupMenu.show();
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView anh;
        TextView ngay,name;
        public ViewHolder(@NonNull View view) {
            super(view);
            anh=view.findViewById(R.id.anhchieu);
            name=view.findViewById(R.id.namephim);
            ngay=view.findViewById(R.id.ngaychieu);
        }
    }
//    public void openDialogUpdate (PhongModel dv) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//
//        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
//        View view = inflater.inflate(R.layout.update_phong,null);
//        builder.setView(view);
//        Dialog dialog = builder.create();
//        dialog.show();
//        TextView id=view.findViewById(R.id.TVmaGhe);
//        AppCompatEditText name,soghe;
//        name=view.findViewById(R.id.txtIDP);
//        soghe=view.findViewById(R.id.txtSG);
//        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
//        AppCompatEditText trangthai=view.findViewById(R.id.txttt);
//        Button update,canel;
//        update=view.findViewById(R.id.btnUpdateTV);
//        canel=view.findViewById(R.id.btncanelTV);
//
//        id.setText("Mã: "+String.valueOf(dv.getId()));
//        name.setText(dv.getTenPhong());
//        soghe.setText(String.valueOf(dv.getSoCho()));
//        trangthai.setText(String.valueOf(dv.getLoaiPhong()));
//        // xử lý khi ấn hủy
//        canel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String ten=name.getText().toString().trim();
//                String ghe = soghe.getText().toString().trim();
//                String trangt=trangthai.getText().toString().trim();
//                if(ten.isEmpty()){
//                    Toast.makeText(context, "Không để trống tên  Phòng", Toast.LENGTH_SHORT).show();
//                } else if (ghe.isEmpty()) {
//                    Toast.makeText(context, "Không để trống Số ghế", Toast.LENGTH_SHORT).show();
//                } else if (trangt.isEmpty()) {
//                    Toast.makeText(context, "Không để trống Loại phòng", Toast.LENGTH_SHORT).show();
//                } else {
//                    int idghe=dv.getId();
//                    try {
//                        dv.setId(idghe);
//                        dv.setTenPhong(ten);
//                        dv.setSoCho(Integer.parseInt(ghe));
//                        dv.setLoaiPhong(Integer.parseInt(trangt));
//
//                        if (phongDao.updatePhongChieu(dv)) {
//                            list.clear();
//                            list.addAll(phongDao.getAllPhongChieu());
//                            notifyDataSetChanged();
//                            Toast.makeText(context, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
//                            dialog.dismiss();
//
//                        }
//                    }catch (Exception e){
//                        e.printStackTrace();
//                    }
//
//                }
//            }
//        });
//
//    }
}
