package com.example.liusk.liusk;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class TaskServer {
    TaskHelper taskHelper;
    public TaskServer(Context context){
        taskHelper=new TaskHelper(context);
    }

    //任务填写界面1用
    public boolean task1(String name,String message, int hand_id ,double integral, String hand_phone, String get_address, String buy_address, String time){
        SQLiteDatabase sdb = taskHelper.getReadableDatabase();
        ContentValues values = new ContentValues();
        values.put("name",name);
        values.put("message",message);
        values.put("hand_id",hand_id);
        values.put("integral",integral);
        values.put("hand_phone",hand_phone);
        values.put("get_address", get_address);
        values.put("buy_address",buy_address);
        values.put("time",time);
        long id = sdb.insert("task_information", null, values);
        sdb.close();
        return true;
    }

    //主界面任务界面用
    public ArrayList<String> query(){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase sdb = taskHelper.getReadableDatabase();
        String sql = "select * from task_information where flag==0";
        Cursor cursor = sdb.rawQuery(sql,null);
        while (cursor.moveToNext()){
            String tid = cursor.getString(cursor.getColumnIndex("_id"));
            String message = cursor.getString(cursor.getColumnIndex("message"));
            String all = tid+"号任务："+message;
            list.add(all);
        }
        cursor.close();
        sdb.close();
        return list;
    }

    //任务内容显示用
    public ArrayList<String>  get(String tid){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase sdb = taskHelper.getReadableDatabase();
        int a = Integer.parseInt(tid);
        Cursor cursor=sdb.query("task_information",null,"_id=?",new String[]{a+""},null,null,null);
        if(cursor.moveToFirst()){
            String message = cursor.getString(cursor.getColumnIndex("message"));
            String name = cursor.getString(cursor.getColumnIndex("name"));
            String phone = cursor.getString(cursor.getColumnIndex("hand_phone"));
            String getaddress = cursor.getString(cursor.getColumnIndex("get_address"));
            String integral = cursor.getString(cursor.getColumnIndex("integral"));
            String time = cursor.getString(cursor.getColumnIndex("Time"));
            String buyaddress = cursor.getString(cursor.getColumnIndex("buy_address"));
            list.add(message);
            list.add(name);
            list.add(phone);
            list.add(getaddress);
            list.add(integral);
            list.add(time);
            list.add(buyaddress);
        }
        cursor.close();
        sdb.close();
        return list;
    }

    //接受任务确定用
    public void start(String tid, String uid){
        SQLiteDatabase sdb = taskHelper.getReadableDatabase();
        int a = Integer.parseInt(tid);
        ContentValues values = new ContentValues();
        values.put("flag",1);
        values.put("server_id",uid);
        int number = sdb.update("task_information",values,"_id=?",new String[]{a+""});
        sdb.close();
    }

    //显示我发布的任务
    public ArrayList<String> handed(String uid){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase sdb = taskHelper.getReadableDatabase();
        String sql = "select * from task_information where hand_id=? and flag!=2";
        Cursor cursor = sdb.rawQuery(sql,new String[]{uid});
        while (cursor.moveToNext()){
            String tid = cursor.getString(cursor.getColumnIndex("_id"));
            String message = cursor.getString(cursor.getColumnIndex("message"));
            String all = tid+"号任务："+message;
            list.add(all);
        }
        cursor.close();
        sdb.close();
        return list;
    }

    //撤销任务用
    public void delete_task(String tid){
        SQLiteDatabase sdb = taskHelper.getReadableDatabase();
        int num = sdb.delete("task_information","_id=?",new String[]{tid});
        sdb.close();
    }

    //找接受者的id
    public String got_id(String tid){
        String temp = "";
        SQLiteDatabase sdb = taskHelper.getReadableDatabase();
        int a = Integer.parseInt(tid);
        Cursor cursor=sdb.query("task_information",null,"_id=?",new String[]{a+""},null,null,null);
        if(cursor.moveToFirst()){
            temp = cursor.getString(cursor.getColumnIndex("server_id"));
        }
        cursor.close();
        sdb.close();
        return temp;
    }

    //显示我接受的任务
    public ArrayList<String> got(String uid){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase sdb = taskHelper.getReadableDatabase();
        String sql = "select * from task_information where server_id=? and flag!=2";
        Cursor cursor = sdb.rawQuery(sql,new String[]{uid});
        while (cursor.moveToNext()){
            String tid = cursor.getString(cursor.getColumnIndex("_id"));
            String message = cursor.getString(cursor.getColumnIndex("message"));
            String all = tid+"号任务："+message;
            list.add(all);
        }
        cursor.close();
        sdb.close();
        return list;
    }

    //发布者电话
    public String hand_id(String tid){
       String temp = "";
        SQLiteDatabase sdb = taskHelper.getReadableDatabase();
        int a = Integer.parseInt(tid);
        Cursor cursor=sdb.query("task_information",null,"_id=?",new String[]{a+""},null,null,null);
        if(cursor.moveToFirst()){
            temp = cursor.getString(cursor.getColumnIndex("hand_phone"));
        }
        cursor.close();
        sdb.close();
        return temp;
    }

    //撤销接受用
    public void delete_got(String tid){
        SQLiteDatabase sdb = taskHelper.getReadableDatabase();
        int a = Integer.parseInt(tid);
        ContentValues values = new ContentValues();
        values.put("flag",0);
        values.put("server_id",0);
        int number = sdb.update("task_information",values,"_id=?",new String[]{a+""});
        sdb.close();
    }

    //确认任务完成用
    public void make_sure(String tid){
        SQLiteDatabase sdb = taskHelper.getReadableDatabase();
        int a = Integer.parseInt(tid);
        ContentValues values = new ContentValues();
        values.put("flag",2);
        int number = sdb.update("task_information",values,"_id=?",new String[]{a+""});
        sdb.close();
    }

    //显示已完成任务用
    public ArrayList<String>  finish_1(String uid){
        ArrayList<String> list = new ArrayList<>();
        SQLiteDatabase sdb = taskHelper.getReadableDatabase();
        String sql = "select * from task_information where flag==2 and (server_id=? or hand_id=?)";
        Cursor cursor = sdb.rawQuery(sql,new String[]{uid,uid});
        while (cursor.moveToNext()){
            String tid = cursor.getString(cursor.getColumnIndex("_id"));
            String message = cursor.getString(cursor.getColumnIndex("message"));
            String all = tid+"号任务："+message;
            list.add(all);
        }
        cursor.close();
        sdb.close();
        return list;
    }
}
