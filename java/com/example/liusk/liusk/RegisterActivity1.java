package com.example.liusk.liusk;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mob.MobSDK;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;

public class RegisterActivity1 extends AppCompatActivity implements View.OnClickListener{
    private EditText mEdtPhone; //电话号码
    private EditText mEdtCode; //验证码
    private Button mBtGet;  //获取验证码
    private Button mBtConfirm; //登录

    private boolean flag; //操作是否成功

    private TimeCount timeCount = new TimeCount(60000,1000);//计时器

    private String phone;
    private String next;
    private int a;

    UserServer userServer = new UserServer(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置窗体为没有标题的模式
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register_1);

        next = getIntent().getStringExtra("last_activity");
       // Toast.makeText(RegisterActivity1.this,next,Toast.LENGTH_SHORT).show();

        init();

        //初始化方式变了
        MobSDK.init(this,"28fc5aa495f70","2538030f6bc74a4e665c1270dc15f825");

        EventHandler eh = new EventHandler(){
            @Override
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    //回调完成
                    Message msg = new Message();
                    msg.arg1 = event;
                    msg.arg2 = result;
                    msg.obj = data;
                    handler.sendMessage(msg);
                }
                else ((Throwable)data).printStackTrace();
            }
        };

        SMSSDK.registerEventHandler(eh);
    }

    private void init(){
        mEdtPhone = findViewById(R.id.et_phone);
        mEdtCode = findViewById(R.id.et_code);
        mBtGet = findViewById(R.id.bt_get);
        mBtConfirm = findViewById(R.id.bt_confirm);

        mBtGet.setOnClickListener(this);
        mBtConfirm.setOnClickListener(this);
    }

    public void onClick(View v){
        String Phone = mEdtPhone.getText().toString().trim();
        String code = mEdtCode.getText().toString().trim();

        switch(v.getId()){
            case R.id.bt_get:
                if(!TextUtils.isEmpty(Phone)){
                    if(Phone.length() == 11){
                        phone = Phone;
                        a = userServer.forget1(phone);
                        if((a==0 && next.equals("new")) || (a!=0 && next.equals("forget"))){
                            SMSSDK.getVerificationCode("86", Phone);
                            mBtGet.requestFocus();
                        }
                        else if(a!=0 && next.equals("new")){
                            Toast.makeText(RegisterActivity1.this,"同一号码只能注册一个账号",Toast.LENGTH_SHORT).show();
                            mBtGet.requestFocus();
                        }
                        else if(a==0 && next.equals("forget")){
                            Toast.makeText(RegisterActivity1.this,"该号码未注册账号",Toast.LENGTH_SHORT).show();
                            mBtGet.requestFocus();
                        }
                    }
                    else{
                        Toast.makeText(this,"请输入正确的电话号码",Toast.LENGTH_SHORT).show();
                        mBtGet.requestFocus();
                    }
                }else{
                    Toast.makeText(this,"请输入电话号码",Toast.LENGTH_SHORT).show();
                    mBtGet.requestFocus();
                }
                break;

            case R.id.bt_confirm:
                if(!TextUtils.isEmpty(code)){
                    if(code.length() == 4){
                        SMSSDK.submitVerificationCode("86",Phone,code);
                        flag = false;
                    }
                    else{
                        Toast.makeText(this,"请输入完整的验证码",Toast.LENGTH_SHORT).show();
                        mBtConfirm.requestFocus();
                    }
                }else{
                    Toast.makeText(this,"请输入验证码",Toast.LENGTH_SHORT).show();
                    mBtConfirm.requestFocus();
                }
                break;

            default:
                break;
        }
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int event = msg.arg1;
            int result = msg.arg2;
            Object data = msg.obj;

            if (result == SMSSDK.RESULT_COMPLETE) {
                // 如果操作成功
                timeCount.start();
                if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                    // 校验验证码，返回校验的手机和国家代码
                    if(next.equals("new")){
                        Toast.makeText(RegisterActivity1.this, "验证成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity1.this, RegisterActivity2.class);
                        intent.putExtra("Phone", phone);
                        startActivity(intent);
                    }
                    else if(next.equals("forget")){
                        Toast.makeText(RegisterActivity1.this, "验证成功", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(RegisterActivity1.this, Forget.class);
                        intent.putExtra("phone",phone);
                        startActivity(intent);
                    }
                } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                    // 获取验证码成功，true为智能验证，false为普通下发短信
                    Toast.makeText(RegisterActivity1.this, "正在获取验证码", Toast.LENGTH_SHORT).show();

                } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                    // 返回支持发送验证码的国家列表
                }
            } else {
                // 如果操作失败
                if (flag) {
                    Toast.makeText(RegisterActivity1.this, "验证码获取失败，请重新获取", Toast.LENGTH_SHORT).show();
                    mEdtPhone.requestFocus();
                } else {
                    ((Throwable) data).printStackTrace();
                    Toast.makeText(RegisterActivity1.this, "验证码错误", Toast.LENGTH_SHORT).show();
                }
            }
        }
    };

    //时间类
    class TimeCount extends CountDownTimer {

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
            mBtGet.setClickable(false);
            mBtGet.setText(l/1000 + "秒");
        }

        @Override
        public void onFinish() {
            mBtGet.setClickable(true);
            mBtGet.setText("获取验证码");
        }
    }
}
