package com.example.ray.school.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQuery;

/**
 * Created by ray on 30/1/18.
 */

public class DataManager extends SQLiteOpenHelper{
    public DataManager(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    @Override
    public String getDatabaseName() {
        return super.getDatabaseName();
    }

    @Override
    public SQLiteDatabase getWritableDatabase() {
        return super.getWritableDatabase();
    }

    @Override
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table classes(" +
                "cno INTEGER primary key," +
                "name TEXT," +
                "procterNo INTEGER" +
                ")");
        db.execSQL("create table students(" +
                "sid INTEGER primary key," +
                "name TEXT," +
                "email TEXT," +
                "mobile INTEGER" +
                ")");
        db.execSQL("create table period(" +
                "pno INTEGER primary key," +
                "cno INTEGER," +
                "subid INTEGER," +
                "status INTEGER," +
                "timefrom INTEGER," +
                "timeto INTEGER)");
        db.execSQL("create table attandance(" +
                "pno INTEGER," +
                "sno INTEGER," +
                "status INTEGER" +
                ")");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public long create_class(String className){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", className);
        return db.insert("classes", null, values);
    }
    public long create_student(String name, String email, String mobileNo){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("email", email);
        values.put("mobile", mobileNo);
        return db.insert("students", null, values);
    }
    public long create_period(String name, String email, String mobileNo){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("email", email);
        values.put("mobile", mobileNo);
        return db.insert("students", null, values);
    }
}
