package com.example.liusk.liusk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity2 extends AppCompatActivity implements View.OnClickListener {
    private EditText mEtName;
    private EditText mEtPass;
    private EditText mEtSure;
    private Button mBtnSign;
    UserServer use = new UserServer(this);

    private String phone;

    private String name;
    private String password;
    private String sure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置窗体为没有标题的模式
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.register_2);

        Intent intent = getIntent();
        phone = intent.getStringExtra("Phone");
        init();
    }

    private void init() {
        mEtName = findViewById(R.id.et_name);
        mEtPass = findViewById(R.id.et_password);
        mEtSure = findViewById(R.id.et_sure);
        mBtnSign = findViewById(R.id.btn_sign);

        mBtnSign.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        name = mEtName.getText().toString();
        password = mEtPass.getText().toString();
        sure = mEtSure.getText().toString();

        if(view.getId() == R.id.btn_sign){
            if(name.length()>10){
                Toast.makeText(RegisterActivity2.this,"昵称长度应在1-10个字符之间",Toast.LENGTH_SHORT).show();
            }
            else if(TextUtils.isEmpty(name) || TextUtils.isEmpty(password) || TextUtils.isEmpty(sure)){
                Toast.makeText(RegisterActivity2.this,"请填写完整的信息", Toast.LENGTH_SHORT).show();
            }
            else{
                if(password.length()>=6 && password.length()<=20){
                    if(password.equals(sure)){
                        use.register(name,password,phone);
                        Toast.makeText(RegisterActivity2.this,"注册成功",Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(RegisterActivity2.this, MainActivity.class);
                        startActivity(intent1);
                    }
                    else{
                        Toast.makeText(RegisterActivity2.this,"两次密码不相同，请重新输入",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(RegisterActivity2.this,"密码长度应在6-20个字符之间",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
