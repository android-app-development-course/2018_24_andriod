package com.example.liusk.liusk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.SpannedString;
import android.text.TextUtils;
import android.text.style.AbsoluteSizeSpan;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Hand_Task extends AppCompatActivity{
    TaskServer taskServer = new TaskServer(this);

    private EditText et1;
    private EditText et2;
    private EditText et3;
    private TextView mTvbuy;
    private Button mBtnget;//设置收货地址按钮
    private Button mBtnbuy;
    private Button mBtnnear;

    private String message;
    private String temp;
    private String integral;
    private String time;
    private String buy_address;
    private String name;//名字
    private String phone;//电话
    private String get_address;//收货地址
    private int uid;//id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置窗体为没有标题的模式
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.hand_task);

        temp = getIntent().getStringExtra("uid");
        uid = Integer.parseInt(temp);

        et1=findViewById(R.id.et1);
        et2=findViewById(R.id.et2);
        et3=findViewById(R.id.et3);

        SpannableString ss=new SpannableString("输入商品名称或者要求，如：“购买全家桶”或到“代拿快递”");
        SpannableString ss1=new SpannableString("本次任务积分");
        SpannableString ss2=new SpannableString("截至任务时间");
        AbsoluteSizeSpan ass=new AbsoluteSizeSpan(25,true);
        ss1.setSpan(ass,0,ss1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(ass,0,ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss2.setSpan(ass,0,ss2.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        et1.setHint(new SpannedString(ss));
        et2.setHint(new SpannedString(ss1));
        et3.setHint(new SpannedString(ss2));

        mTvbuy = findViewById(R.id.tv_buy_address);

        mBtnget=findViewById(R.id.et4);
        mBtnbuy = findViewById(R.id.btn_buy);
        mBtnnear = findViewById(R.id.btn_buy_near);

        mBtnget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Hand_Task.this,Hand_Task_Information.class);
                startActivityForResult(intent,1);
            }
        });

        mBtnbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputDialog dialog = new InputDialog(Hand_Task.this, new InputDialog.OnEditInputFinishedListener(){
                    @Override
                    public void editInputFinished(String password) {
                        mTvbuy.setText(password);
                    }
                });
                dialog.setView(new EditText(Hand_Task.this));  //若对话框无法弹出输入法，加上这句话
                dialog.show();
            }
        });

        mBtnnear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTvbuy.setText("就近购买");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null){
            if(requestCode==1 && resultCode==RESULT_OK){
                name = data.getStringExtra("name") + data.getStringExtra("sex");
                phone = data.getStringExtra("phone");
                get_address = data.getStringExtra("address");
            }
        }
    }

    //发布任务按钮点击事件
    public void click1(View v){
        message = et1.getText().toString().trim();
        integral = et2.getText().toString().trim();
        time = et3.getText().toString().trim();
        buy_address = mTvbuy.getText().toString().trim();
        if(!TextUtils.isEmpty(message) && !TextUtils.isEmpty(get_address) && !TextUtils.isEmpty(time) && !TextUtils.isEmpty(integral) && !TextUtils.isEmpty(name)){
            Double integral1 = Double.valueOf(integral);
            taskServer.task1(name,message,uid,integral1,phone,get_address,buy_address,time);
            Toast.makeText(Hand_Task.this,"任务发布成功",Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(Hand_Task.this,Main_Activity.class);
            intent.putExtra("uid",temp);
            startActivity(intent);
        }
        else
            Toast.makeText(Hand_Task.this,"请填写完整信息",Toast.LENGTH_SHORT).show();
    }
}

