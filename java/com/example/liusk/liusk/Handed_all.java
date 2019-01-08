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

public class Handed_all extends AppCompatActivity implements View.OnClickListener{
    private TextView mTvmessage;
    private TextView mTvintegral;
    private TextView mTvaddress;
    private TextView mTvphone;
    private TextView mTvtime;
    private TextView mTvbuy;
    private Button mBtnphone;
    private Button mBtnno;
    private Button mBtnsure;

    private ArrayList<String> list = new ArrayList<>();
    private TaskServer taskServer = new TaskServer(this);
    private UserServer userServer = new UserServer(this);
    private String tid;
    private String uid;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.handed_information);

        Bundle bundle = getIntent().getExtras();
        tid = bundle.getString("tid");
        uid = bundle.getString("uid");

        init();
    }

    private void init(){
        mTvmessage = findViewById(R.id.hand_message);
        mTvtime =findViewById(R.id.hand_time);
        mTvaddress = findViewById(R.id.hand_address);
        mTvintegral = findViewById(R.id.hand_integral);
        mTvphone = findViewById(R.id.hand_phone);
        mTvbuy = findViewById(R.id.hand_buy);

        mBtnphone = findViewById(R.id.btn_context_server);
        mBtnno = findViewById(R.id.btn_delete);
        mBtnsure = findViewById(R.id.btn_task_sure);
        mBtnphone.setOnClickListener(this);
        mBtnno.setOnClickListener(this);
        mBtnsure.setOnClickListener(this);

        list = taskServer.get(tid);
        mTvmessage.setText("任务内容:"+list.get(0));
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
            case R.id.btn_context_server:
                String id = taskServer.got_id(tid);
                if(id.equals("0")){
                    Toast.makeText(Handed_all.this,"该任务还未被接受",Toast.LENGTH_SHORT).show();
                }
                else{
                    String phone = userServer.server_phone(id);
                    call(phone);
                }
                break;
            case R.id.btn_task_sure:
                String id1 = taskServer.got_id(tid);
                if(id1.equals("0")){
                    Toast.makeText(Handed_all.this,"该任务还未被接受",Toast.LENGTH_SHORT).show();
                }
                else{
                    taskServer.make_sure(tid);
                    Toast.makeText(Handed_all.this,"任务确认完成",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Handed_all.this,Main_Activity.class);
                    intent.putExtra("uid",uid);
                    startActivity(intent);
                }
                break;
            case R.id.btn_delete:
                taskServer.delete_task(tid);
                Toast.makeText(Handed_all.this,"撤销任务成功",Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(Handed_all.this,Main_Activity.class);
                intent1.putExtra("uid",uid);
                startActivity(intent1);
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
