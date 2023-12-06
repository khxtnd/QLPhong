package com.qlphong.dal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.qlphong.model.Phong;
import com.qlphong.model.TaiSan;

import java.util.ArrayList;
import java.util.List;

public class SQLiteDAO extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "QLPhong.db";
    private static int DATABASE_VERSION = 1;

    public SQLiteDAO(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlPhong = "CREATE TABLE phong(" +
                "ma INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ten TEXT, mota TEXT)";
        db.execSQL(sqlPhong);

        String sqlTaiSan = "CREATE TABLE taisan(" +
                "ma INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ten TEXT, loai TEXT, vitri TEXT, giatri INTEGER, maphong INTEGER)";
        db.execSQL(sqlTaiSan);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public void addPhong(Phong i) {
        String sql = "INSERT INTO phong(ten, mota)" +
                "VALUES(?,?)";
        String[] args = {i.getTen(), i.getMota()};
        SQLiteDatabase st = getWritableDatabase();
        st.execSQL(sql, args);
    }

    public List<Phong> getAllPhong() {
        List<Phong> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor rs = sqLiteDatabase.query("phong",
                null, null, null,
                null, null, null);
        while ((rs != null && (rs.moveToNext()))) {
            int ma = rs.getInt(0);
            String ten = rs.getString(1);
            String mota = rs.getString(2);
            list.add(new Phong(ma, ten, mota));
        }
        return list;
    }

    public int updatePhong(Phong i) {
        ContentValues values = new ContentValues();
        values.put("ten", i.getTen());
        values.put("mota", i.getMota());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "ma = ?";
        String[] whereArgs = {Integer.toString(i.getMa())};
        return sqLiteDatabase.update("phong",
                values, whereClause, whereArgs);
    }

    public int deletePhong(int ma) {
        String whereClause = "ma = ?";
        String[] whereArgs = {Integer.toString(ma)};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("phong", whereClause, whereArgs);
    }

    public void addTaiSan(TaiSan i) {
        ContentValues values = new ContentValues();
        values.put("ten", i.getTen());
        values.put("loai", i.getLoai());
        values.put("vitri", i.getVitri());
        values.put("giatri", i.getGiatri());
        values.put("maphong", -1);
        SQLiteDatabase db = getWritableDatabase();
        db.insert("taisan", null, values);
        db.close();
    }

    public List<TaiSan> getAllTaiSan() {
        List<TaiSan> list = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor rs = sqLiteDatabase.query("taisan",
                null, null, null,
                null, null, null);
        while ((rs != null && (rs.moveToNext()))) {
            int ma = rs.getInt(0);
            String ten = rs.getString(1);
            String loai = rs.getString(2);
            String vitri = rs.getString(3);
            int giatri = rs.getInt(4);
            list.add(new TaiSan(ma, ten, loai, vitri, giatri));
        }
        return list;
    }

    public int updateTaiSan(TaiSan i) {
        ContentValues values = new ContentValues();
        values.put("ten", i.getTen());
        values.put("loai", i.getLoai());
        values.put("vitri", i.getVitri());
        values.put("giatri", i.getGiatri());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "ma = ?";
        String[] whereArgs = {Integer.toString(i.getMa())};
        return sqLiteDatabase.update("taisan",
                values, whereClause, whereArgs);
    }

    public int deleteTaiSan(int ma) {
        String whereClause = "ma = ?";
        String[] whereArgs = {Integer.toString(ma)};
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("taisan", whereClause, whereArgs);
    }

    public List<TaiSan> getTaiSanOver10Million() {
        List<TaiSan> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM taisan WHERE giatri > 10000";
        Cursor rs = db.rawQuery(query, null);

        while ((rs != null && (rs.moveToNext()))) {
            int ma = rs.getInt(0);
            String ten = rs.getString(1);
            String loai = rs.getString(2);
            String vitri = rs.getString(3);
            int giatri = rs.getInt(4);
            list.add(new TaiSan(ma, ten, loai, vitri, giatri));
        }
        return list;
    }

    public List<TaiSan> getTaiSanKhongTrongPhong() {
        List<TaiSan> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sql = "SELECT * FROM taisan WHERE maphong = -1";
        Cursor rs = db.rawQuery(sql, null);
        while ((rs != null && (rs.moveToNext()))) {
            int ma = rs.getInt(0);
            String ten = rs.getString(1);
            String loai = rs.getString(2);
            String vitri = rs.getString(3);
            int giatri = rs.getInt(4);
            list.add(new TaiSan(ma, ten, loai, vitri, giatri));
        }
        return list;
    }

    public List<TaiSan> getTaiSanTrongPhong(int maPhong) {
        List<TaiSan> list = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String sqlSelectTaiSanTrongPhong = "SELECT * FROM taisan WHERE maphong = ?";
        String[] selectionArgs = {String.valueOf(maPhong)};
        Cursor rs = db.rawQuery(sqlSelectTaiSanTrongPhong, selectionArgs);
        while ((rs != null && (rs.moveToNext()))) {
            int ma = rs.getInt(0);
            String ten = rs.getString(1);
            String loai = rs.getString(2);
            String vitri = rs.getString(3);
            int giatri = rs.getInt(4);
            list.add(new TaiSan(ma, ten, loai, vitri, giatri));
        }
        return list;
    }

    public int updateTaiSanTrongPhong(int ma, int maPhong) {
        ContentValues values = new ContentValues();
        values.put("maphong", maPhong);
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        String whereClause = "ma = ?";
        String[] whereArgs = {Integer.toString(ma)};
        return sqLiteDatabase.update("taisan",
                values, whereClause, whereArgs);
    }
}

