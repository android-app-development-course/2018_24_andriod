package com.example.liusk.liusk;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Finished_all extends AppCompatActivity {
    private TextView mTvmessage;
    private TextView mTvintegral;
    private TextView mTvaddress;
    private TextView mTvphone;
    private TextView mTvname;
    private TextView mTvtime;
    private TextView mTvbuy;

    private ArrayList<String> list = new ArrayList<>();
    private TaskServer taskServer = new TaskServer(this);
    private String tid;
    private String uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.finished_information);

        Bundle bundle = getIntent().getExtras();
        tid = bundle.getString("tid");
        uid = bundle.getString("uid");

        init();
    }

    private void init(){
        mTvmessage = findViewById(R.id.finish_message);
        mTvtime =findViewById(R.id.finish_time);
        mTvaddress = findViewById(R.id.finish_address);
        mTvintegral = findViewById(R.id.finish_integral);
        mTvphone = findViewById(R.id.finish_phone);
        mTvname = findViewById(R.id.finish_name);
        mTvbuy = findViewById(R.id.finish_buy);

        list = taskServer.get(tid);
        mTvmessage.setText("任务内容:"+list.get(0));
        mTvname.setText("发布人："+list.get(1));
        mTvphone.setText("电话号码："+list.get(2));
        mTvaddress.setText("收货地址："+list.get(3));
        mTvintegral.setText("任务积分："+list.get(4));
        mTvtime.setText("截至时间："+list.get(5));
        String temp = list.get(6);
        if(temp.equals("")){
            mTvbuy.setText("购买地址：没有规定");
        }
        else
            mTvbuy.setText("购买地址："+temp);
    }
}
