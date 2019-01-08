package com.example.liusk.liusk;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    private  TextView mTvNew;
    private TextView mTvForget;
    private EditText mEtName;
    private EditText mEtPassWord;

    private CustomVideoView videoview;

    private int count=3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置窗体为没有标题的模式
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        init();

        mTvNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示方式声明Intent，直接启动SecondActivity
                Intent intent = new Intent(MainActivity.this, RegisterActivity1.class);
                intent.putExtra("last_activity","new");
                startActivity(intent);
            }
        });

        mTvForget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,RegisterActivity1.class);
                intent.putExtra("last_activity","forget");
                startActivity(intent);
            }
        });
    }

    public void init(){
        mTvNew = findViewById(R.id.tv_new);
        mTvForget = findViewById(R.id.tv_forget);
        mEtName = findViewById(R.id.et_name);
       mEtPassWord = findViewById(R.id.et_password);

        mTvNew.setClickable(true);
        mTvForget.setClickable(true);

        videoview = findViewById(R.id.videoview);
        videoview.setVideoURI(Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.beiing));

        //播放
        videoview.start();
        //循环播放
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                videoview.start();
                mediaPlayer.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        return false;
                    }
                });
            }
        });
    }

    //返回重启加载
    @Override
    protected void onRestart() {
        super.onRestart();
        init();
    }

    //防止锁屏或者切出的时候，音乐在播放
    @Override
    protected void onStop() {
        super.onStop();
        videoview.stopPlayback();
    }

    public void onClick(View v){
        UserServer users = new UserServer(this);
        String name = mEtName.getText().toString();
        String password = mEtPassWord.getText().toString();

        int flag = users.login(name,password);

        if(v.getId() == R.id.bt_sign && count>0){
            if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(password)){
                if(flag!=0){
                    Toast.makeText(MainActivity.this,"登录成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, Main_Activity.class);
                    intent.putExtra("uid",flag+"");
                    startActivity(intent);
                }
                else{
                    count--;
                    Toast.makeText(MainActivity.this,"用户名和密码不匹配",Toast.LENGTH_SHORT).show();
                }
            }
            else{
                count--;
                Toast.makeText(MainActivity.this,"用户名或者密码不能为空",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
