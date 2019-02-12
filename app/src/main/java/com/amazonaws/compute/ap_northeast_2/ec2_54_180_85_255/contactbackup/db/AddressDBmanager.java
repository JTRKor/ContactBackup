package com.amazonaws.compute.ap_northeast_2.ec2_54_180_85_255.contactbackup.db;

import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.amazonaws.compute.ap_northeast_2.ec2_54_180_85_255.contactbackup.data.AddressOne;

import java.util.ArrayList;

public class AddressDBmanager extends SQLiteOpenHelper {
    public AddressDBmanager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public AddressDBmanager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE Addressdb (id	INTEGER PRIMARY KEY AUTOINCREMENT,name TEXT, number TEXT);";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean insertCheck(AddressOne addressOne) {
        SQLiteDatabase db = getReadableDatabase();
        String name = addressOne.getName();
        String number = addressOne.getNumber();
        String query = "select count(*) from Addressdb where name='" + name + "' and number='" + number + "';";
        Cursor cursor = db.rawQuery(query, null);
        Integer count = 0;
        while(cursor.moveToNext()) {
            count = cursor.getInt(0);
        }
        if (count > 0) {
            return false;
        }
        return true;
    }

    public void insertData(AddressOne addressOne) {
        SQLiteDatabase db = getReadableDatabase();
        String name = addressOne.getName();
        String number = addressOne.getNumber();
        if (insertCheck(addressOne)) {
            String query = "insert into Addressdb values (null, '" + name + "', '" + number + "');";
            db.execSQL(query);
        }
    }

    public ArrayList<AddressOne> getDataSearchListAddr(String search) {
        ArrayList<AddressOne> items = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from Addressdb where name like '%" + search + "%' or number like '%" + search + "%';";
        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()) {
            Integer id = cursor.getInt(0);
            String name = cursor.getString(1);
            String number = cursor.getString(2);
            AddressOne item = new AddressOne(id, name, number);
            items.add(item);
        }
        cursor.close();
        return items;
    }

    public ArrayList<AddressOne> getDataListAddr() {
        ArrayList<AddressOne> items = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        String query = "select * from Addressdb;";
        Cursor cursor = db.rawQuery(query, null);
        while(cursor.moveToNext()) {
            Integer id = cursor.getInt(0);
            String name = cursor.getString(1);
            String number = cursor.getString(2);
            AddressOne item = new AddressOne(id, name, number);
            items.add(item);
        }
        cursor.close();
        return items;
    }
}
