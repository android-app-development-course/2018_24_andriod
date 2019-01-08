package com.example.liusk.liusk;

import android.content.Intent;
import android.os.Bundle;
import android.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Get_Task extends AppCompatActivity {
    private TextView mTvmessage;
    private TextView mTvname;
    private TextView mTvphone;
    private TextView mTvget;
    private TextView mTvbuy;
    private TextView mTvtime;
    private TextView mTvintegral;

    private Button ok;
    private Button cancel;

    private TaskServer taskServer = new TaskServer(Get_Task.this);
    private ArrayList<String> list;
    private String tid;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置窗体为没有标题的模式
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.get_task);

        Bundle bundle = getIntent().getExtras();
        tid = bundle.getString("tid");
        uid = bundle.getString("uid");

        init();

        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    taskServer.start(tid,uid);
                    Intent intent = new Intent(Get_Task.this,Main_Activity.class);
                    intent.putExtra("uid", uid);
                    startActivity(intent);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Get_Task.this,Main_Activity.class);
                intent.putExtra("uid", uid);
                startActivity(intent);
            }
        });
    }

    private void init(){
        mTvmessage = findViewById(R.id.get_message);
        mTvname = findViewById(R.id.get_name);
        mTvphone = findViewById(R.id.get_phone);
        mTvintegral = findViewById(R.id.get_integral);
        mTvget = findViewById(R.id.get_address);
        mTvtime = findViewById(R.id.get_time);
        mTvbuy = findViewById(R.id.get_buy);

        ok = findViewById(R.id.btn_sure);
        cancel = findViewById(R.id.btn_no);

        list = taskServer.get(tid);
        mTvmessage.setText("任务内容:"+list.get(0));
        mTvname.setText("发布人："+list.get(1));
        mTvphone.setText("电话号码："+list.get(2));
        mTvget.setText("收货地址："+list.get(3));
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
