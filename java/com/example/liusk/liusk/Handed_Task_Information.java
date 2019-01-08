package com.example.liusk.liusk;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Handed_Task_Information extends AppCompatActivity {
    private String uid;

    private TaskServer taskServer = new TaskServer(this);
    private ArrayList<String> list = new ArrayList<>();
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置窗体为没有标题的模式
        this.supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.handed_task);

        uid = getIntent().getStringExtra("uid");

        listView = findViewById(R.id.mine_handed);
        list = taskServer.handed(uid);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(Handed_Task_Information.this,android.R.layout.simple_list_item_1,list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String tid,a;
                a = list.get(i);
                int first = a.indexOf("号");
                tid = a.substring(0,first);
                Bundle bundle = new Bundle();
                Intent intent = new Intent(Handed_Task_Information.this,Handed_all.class);
                bundle.putString("tid",tid);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }
}
