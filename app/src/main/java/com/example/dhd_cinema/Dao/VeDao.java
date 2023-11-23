package com.example.dhd_cinema.Dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.dhd_cinema.DataBase.Dbhelper;

public class VeDao {

    private final Context context;
    private final Dbhelper dbhelper;

    public VeDao(Context context) {
        this.context = context;
        this.dbhelper = new Dbhelper(context);
    }
    public boolean isSeatBooked(int seatId) {
        SQLiteDatabase db = dbhelper.getReadableDatabase();

        String query = "SELECT COUNT(*) FROM Ve WHERE ID_Ghe = ?";
        Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(seatId)});

        boolean isBooked = false;

        if (cursor.moveToFirst()) {
            int count = cursor.getInt(0);
            isBooked = count > 0;
        }

        cursor.close();
        db.close();

        return isBooked;
    }
}
