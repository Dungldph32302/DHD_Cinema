package com.example.dhd_cinema.Adapter;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dhd_cinema.Dao.PhimDao;
import com.example.dhd_cinema.Dao.TheLoaiPhimDao;
import com.example.dhd_cinema.Model.TheLoaiPhim;
import com.example.dhd_cinema.R;

import java.util.ArrayList;

public class TheLoaiPhimAdapter extends RecyclerView.Adapter<TheLoaiPhimAdapter.ViewHolder> {
    private Context context;
    private ArrayList<TheLoaiPhim> list;
    TheLoaiPhimDao theLoaiPhimDao;

    public TheLoaiPhimAdapter(Context context, ArrayList<TheLoaiPhim> list) {
        this.context = context;
        this.list = list;
        theLoaiPhimDao = new TheLoaiPhimDao(context);
    }

    @NonNull
    @Override
    public TheLoaiPhimAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = ((Activity)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_theloaiphim,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheLoaiPhimAdapter.ViewHolder holder, int position) {
        holder.txtID_TL_item.setText("Mã Loại Phim: "+ (list.get(position).getID_TL()));
        holder.txttenTheLoai_item.setText("Tên Loại Phim: "+ list.get(position).getTenTheLoai());

        TheLoaiPhim ls =list.get(position);
        holder.chon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(context, view);
                popupMenu.getMenuInflater().inflate(R.menu.popumenu, popupMenu.getMenu());

                // Bắt sự kiện khi một mục trong menu được chọn
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        int itemId = item.getItemId();

                        if (itemId == R.id.action_custom){
                            // Xử lý khi chọn tùy chỉnh
                            suaTheLoaiPhim(ls);
                            return true;
                        } else if (itemId == R.id.action_delete) {
                            AlertDialog.Builder builder =new AlertDialog.Builder(context);
                            builder.setTitle("Cảnh Báo"); //set tieu de cho hop thoai
                            builder.setIcon(R.drawable.baseline_warning_24); //icon cho hop thoai
                            builder.setMessage("Bạn Có Muốn Xóa Thể Loại Này Không?"); //chuoi thong bao
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    theLoaiPhimDao = new TheLoaiPhimDao(context);
                                    int check = theLoaiPhimDao.delete(list.get(holder.getAdapterPosition()).getID_TL());
                                    switch (check){
                                        case 1:
                                            list.clear();
                                            list = theLoaiPhimDao.selectAllTheLoaiPhim();
                                            notifyDataSetChanged();
                                            Toast.makeText(context, "Xóa thành công!!!", Toast.LENGTH_SHORT).show();
                                            break;
                                        case -1:
                                            Toast.makeText(context, "Không Thể Xóa vì đã có Phim thuộc thể loại!!!", Toast.LENGTH_SHORT).show();
                                            break;
                                        case 0:
                                            Toast.makeText(context, "Xóa không thành công!!!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                            //nut no
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Toast.makeText(context, "Không Xóa", Toast.LENGTH_SHORT).show();
                                }
                            });
                            // tao va hien thi hop thoai
                            AlertDialog dialog = builder.create();// tao hop thoai
                            dialog.show();//hien thi hop thoai
                        }
                        return  false;
                    }
                });

                popupMenu.show();
            }
        });




    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtID_TL_item, txttenTheLoai_item;
        ImageView chon;
        LinearLayout dsTheLoaiPhim;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtID_TL_item = itemView.findViewById(R.id.txtID_TL_item);
            txttenTheLoai_item = itemView.findViewById(R.id.txttenTheLoai_item);
            chon = itemView.findViewById(R.id.img_Chon);
            dsTheLoaiPhim = itemView.findViewById(R.id.DSTheLoaiPhim);
        }
    }

    private Dialog dialog;
    public void suaTheLoaiPhim(TheLoaiPhim theLoaiPhim) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);// tao doi tuong cua altertdialog
        // gan layout,tao view
        LayoutInflater inflater = ((Activity) context).getLayoutInflater();
        View view = inflater.inflate(R.layout.sua_theloaiphim, null);
        builder.setView(view); //gan view cho hop thoai

        //anh xa cac thanh phan widget
        EditText edt_ID_TL_sua = view.findViewById(R.id.edt_ID_TL_sua);
        EditText edt_tenTheLoai_sua = view.findViewById(R.id.edt_tenTheLoai_sua);
        Button btnLuu_sua = view.findViewById(R.id.btnLuu_sua);
        Button btnHuy_sua = view.findViewById(R.id.btnHuy_sua);

        //gan du lieu len cac o edittext
        edt_ID_TL_sua.setText(String.valueOf(theLoaiPhim.getID_TL()));
        edt_tenTheLoai_sua.setText(theLoaiPhim.getTenTheLoai());
        edt_ID_TL_sua.setEnabled(false);

        btnLuu_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                theLoaiPhim.setTenTheLoai(edt_tenTheLoai_sua.getText().toString());
                if (edt_tenTheLoai_sua.length() == 0) {
                    Toast.makeText(context, "Không được bỏ trống", Toast.LENGTH_SHORT).show();
                } else {
                    if (theLoaiPhimDao.update(theLoaiPhim)) {
                        list.clear();
                        list.addAll(theLoaiPhimDao.selectAllTheLoaiPhim());
                        notifyDataSetChanged();
                        Toast.makeText(context, "Sửa Thành Công", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    } else {
                        Toast.makeText(context, "Sửa Thất Bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //nut no
        btnHuy_sua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "Không Sửa", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }
        });
        dialog= builder.create(); //tao hop thoai
        dialog.show(); //hien thi hop thoai len man hinh
    }
}
