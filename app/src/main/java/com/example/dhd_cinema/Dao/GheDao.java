package com.example.dhd_cinema.Dao;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.dhd_cinema.DataBase.Dbhelper;
import com.example.dhd_cinema.Model.GheModel;

import java.util.ArrayList;

public class GheDao {
    private final Context context;
    private final Dbhelper dbhelper;

    public GheDao(Context context) {
        this.context = context;
        this.dbhelper = new Dbhelper(context);
    }

    public ArrayList<GheModel> getAllGhe(){
        ArrayList<GheModel>list= new ArrayList<>();
        SQLiteDatabase db=dbhelper.getReadableDatabase();
        try {
            Cursor cursor=db.rawQuery("Select * from Ghe  ",null);
            if(cursor.getCount()>0){
                cursor.moveToFirst();
                while (!cursor.isAfterLast()){
                    GheModel sp= new GheModel();
                    sp.setId(cursor.getInt(0));
                    sp.setIdPhong(cursor.getInt(1));
                    sp.setSoGhe(cursor.getString(2));
                    sp.setTrangThai(cursor.getInt(3));
                    list.add(sp);
                    cursor.moveToNext();
                }
            }
        }catch (Exception e){
            Log.i(TAG, "Lỗi allSP",e);
        }
        return list;
    }
    public boolean deleteGhe(int id){
        SQLiteDatabase db= dbhelper.getWritableDatabase();
        long row=db.delete("Ghe","ID_Ghe=?",new String[]{String.valueOf(id)});
        return  (row>0);
    }
    public boolean updateghe(GheModel sp){
        ContentValues values=new ContentValues();
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        values.put("ID_Ghe",sp.getId());
        values.put("ID_Phong",sp.getIdPhong());
        values.put("SoGhe",sp.getSoGhe());
        values.put("TrangThai",sp.getTrangThai());
        long row=db.update("Ghe",values,"ID_Ghe=?",new String[]{String.valueOf(sp.getId())});
        return (row>0);
    }
    public boolean addGhe(GheModel sp){
        ContentValues values=new ContentValues();
        SQLiteDatabase db=dbhelper.getWritableDatabase();
        values.put("ID_Phong",sp.getIdPhong());
        values.put("SoGhe",sp.getSoGhe());
        values.put("TrangThai",sp.getTrangThai());
        long row=db.insert("Ghe",null,values);
        return (row>0);
    }

}
