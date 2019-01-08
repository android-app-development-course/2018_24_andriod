package com.example.liusk.liusk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Checkable;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;


public class Hand_Task_Information extends AppCompatActivity{
    private EditText mEtname;
    private EditText mEtphone;
    private EditText mEtaddress;
    private RadioGroup radioGroup;

    private Button mBtnfinish;

    private String name, phone, address, sex="";

    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        //设置窗体为没有标题的模式
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.hand_task_information);

        init();

        mBtnfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                name = mEtname.getText().toString();
                phone = mEtphone.getText().toString();
                address = mEtaddress.getText().toString();

                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(sex) && !TextUtils.isEmpty(phone) && !TextUtils.isEmpty(address)){
                    if(phone.length() == 11){
                        Intent intent = new Intent();
                        intent.putExtra("name",name);
                        intent.putExtra("sex",sex);
                        intent.putExtra("phone",phone);
                        intent.putExtra("address",address);
                        setResult(RESULT_OK,intent);
                        finish();
                    }
                    else
                        Toast.makeText(Hand_Task_Information.this,"请输入11位的号码",Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(Hand_Task_Information.this,"信息未填写完整",Toast.LENGTH_SHORT).show();
                }
            }
        });

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.rdg_sex_man){
                    sex ="先生";
                }else sex ="女士";
            }
        });
    }

    private void init(){
        mEtname = findViewById(R.id.et_hander_name);
        mEtphone = findViewById(R.id.et_hander_phone);
        mEtaddress = findViewById(R.id.et_get_address);

        radioGroup = findViewById(R.id.rdg_sex);
        mBtnfinish = findViewById(R.id.btn_get_confirm);
    }
}