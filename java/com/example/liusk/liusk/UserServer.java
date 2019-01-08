package com.example.liusk.liusk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class UserServer {
    private MyHelper dbHelper;
    public UserServer(Context context){
        dbHelper=new MyHelper(context);
    }

    //登录用
    public int login(String username,String password){
        int a;
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        String sql="select * from information where name=? and password=?";
        Cursor cursor=sdb.rawQuery(sql, new String[]{username,password});
        if(cursor.moveToFirst()==true){
            a = cursor.getInt(cursor.getColumnIndex("_id"));
            cursor.close();
            sdb.close();
            return a;
        }
        sdb.close();
        return 0;
    }

    //注册用
    public boolean register(String name, String password, String phone){
        SQLiteDatabase sdb=dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("password", password);
        values.put("phone", phone);
        long id = sdb.insert("information", null, values);
        sdb.close();
        return true;
    }

    //忘记密码用
    public int forget1(String phone){
        int a;
        SQLiteDatabase sdb = dbHelper.getReadableDatabase();
        Cursor cursor = sdb.query("information",null,"phone=?",new String[]{phone+""},null,null,null);
        if(cursor.moveToFirst()==true){
            a = cursor.getInt(cursor.getColumnIndex("_id"));
            cursor.close();
            sdb.close();
            return a;
        }
        sdb.close();
        return 0;
    }

    //设置新密码用
    public boolean forget2(String password, String phone){
        SQLiteDatabase sdb = dbHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("password", password);
        long id = sdb.update("information",values,"phone=?",new String[]{phone});
        return true;
    }

    //主界面用name
    public String name(int id){
        String a="";
        SQLiteDatabase sdb = dbHelper.getReadableDatabase();
        Cursor cursor =sdb.query("information",null,"_id=?",new String[]{id+""},null,null,null);
        if(cursor.moveToFirst()){
            a = cursor.getString(cursor.getColumnIndex("name"));
            cursor.close();
        }
        sdb.close();
        return a;
    }

    //主界面用phone
    public String phone(int id){
        String a="";
        Double b;
        SQLiteDatabase sdb = dbHelper.getReadableDatabase();
        Cursor cursor =sdb.query("information",null,"_id=?",new String[]{id+""},null,null,null);
        if(cursor.moveToFirst()){
            b = cursor.getDouble(cursor.getColumnIndex("integral"));
            a = b.toString();
            cursor.close();
        }
        sdb.close();
        return a;
    }

    //找接受者电话用
    public String server_phone(String uid){
        String a="";
        SQLiteDatabase sdb = dbHelper.getReadableDatabase();
        Cursor cursor = sdb.query("information",null,"_id=?",new String[]{uid},null,null,null);
        if(cursor.moveToFirst()){
            a = cursor.getString(cursor.getColumnIndex("phone"));
            cursor.close();
        }
        sdb.close();
        return a;
    }
}