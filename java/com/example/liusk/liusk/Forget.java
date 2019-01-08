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

public class Forget extends AppCompatActivity {
    private String phone;

    private EditText mEtnewpass;
    private EditText mEtsure;
    private Button mGo;

    private String temp;
    private String newpass;
    private String surenew;

    private UserServer userServer = new UserServer(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.set_new_password);

        phone = getIntent().getStringExtra("phone");

        init();

        mGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newpass = mEtnewpass.getText().toString().trim();
                surenew = mEtsure.getText().toString().trim();
                if(!TextUtils.isEmpty(newpass) && !TextUtils.isEmpty(surenew) && newpass.equals(surenew) && userServer.forget2(newpass,phone)){
                    Toast.makeText(Forget.this,"密码重置成功",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Forget.this,MainActivity.class);
                    startActivity(intent);
                }
                else Toast.makeText(Forget.this,"两次输入的密码不一致",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void init(){
        mEtnewpass = findViewById(R.id.et_new_pass);
        mEtsure = findViewById(R.id.et_sure_new);

        mGo = findViewById(R.id.btn_goto_main);
    }
}
