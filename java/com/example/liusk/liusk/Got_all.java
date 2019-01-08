package com.example.liusk.liusk;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Got_all extends AppCompatActivity implements View.OnClickListener{
    private TextView mTvmessage;
    private TextView mTvintegral;
    private TextView mTvaddress;
    private TextView mTvphone;
    private TextView mTvname;
    private TextView mTvtime;
    private TextView mTvbuy;
    private Button mBtnphone;
    private Button mBtnno;

    private ArrayList<String> list = new ArrayList<>();
    private TaskServer taskServer = new TaskServer(this);
    private String tid;
    private String uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.got_information);

        Bundle bundle = getIntent().getExtras();
        tid = bundle.getString("tid");
        uid = bundle.getString("uid");

        init();
    }

    private void init(){
        mTvmessage = findViewById(R.id.got_message);
        mTvtime =findViewById(R.id.got_time);
        mTvaddress = findViewById(R.id.got_address);
        mTvintegral = findViewById(R.id.got_integral);
        mTvphone = findViewById(R.id.got_phone);
        mTvname = findViewById(R.id.got_name);
        mTvbuy = findViewById(R.id.got_buy);

        mBtnphone = findViewById(R.id.btn_context_hand);
        mBtnno = findViewById(R.id.btn_not);
        mBtnphone.setOnClickListener(this);
        mBtnno.setOnClickListener(this);

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

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_context_hand:
                String phone = taskServer.hand_id(tid);
                call(phone);
                break;
            case R.id.btn_not:
                taskServer.delete_got(tid);
                Toast.makeText(Got_all.this,"撤销接受成功",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Got_all.this,Main_Activity.class);
                intent.putExtra("uid",uid);
                startActivity(intent);
                break;
             default:
                 break;
        }
    }

    private void call(String phone){
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phone));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}
