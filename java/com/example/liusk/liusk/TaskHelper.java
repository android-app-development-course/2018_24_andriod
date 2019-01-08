package com.example.liusk.liusk;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class TaskHelper extends SQLiteOpenHelper{

    public TaskHelper(@Nullable Context context) {
        super(context, "tasks.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE task_information(_id INTEGER PRIMARY KEY AUTOINCREMENT, hand_id INTEGER, server_id INTEGER DEFAULT 0, buy_address VARCHAR(30) DEFAULT null," +
                "name VARCHAR(10),get_address VARCHAR(30), hand_phone VARCHAR(11), message VARCHAR(50), integral DOUBLE, Time VARCHAR(10), flag INTEGER DEFAULT 0)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}