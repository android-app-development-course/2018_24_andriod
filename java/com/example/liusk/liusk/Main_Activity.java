package com.example.liusk.liusk;

import java.util.ArrayList;
import java.util.List;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Main_Activity extends Activity implements View.OnClickListener,ViewPager.OnPageChangeListener{
    //底部菜单3个Linearlayout
    private LinearLayout ll_task;
    private LinearLayout ll_me;
    //底部菜单3个ImageViewr
    private ImageView iv_task;
    private ImageView iv_me;
    //底部菜单3个TextView
    private TextView tv_task;
    private TextView tv_me;
    //中间内容区域
    private ViewPager viewPager;

    //ViewPager适配器ContentAdapter
    private ContentAdapter adapter;
    private List<View> views;

    private ActionBar actionBar;
    private String temp;//uid
    private int uid;
    private TaskServer taskServer = new TaskServer(this);
    private UserServer userServer = new UserServer(this);

    //任务用
    private ListView lv;
    private ArrayList<String> list;

    //我的用
    private TextView mTvname;
    private TextView mTvid;
    private TextView mTvintegral;
    private TextView mTvhand;
    private TextView mTvget;
    private TextView mTvfinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        temp = getIntent().getStringExtra("uid");
        uid = Integer.parseInt(temp);
        //Toast.makeText(Main_Activity.this,uid+"",Toast.LENGTH_SHORT).show();

        actionBar = getActionBar();
        actionBar.show();

        //初始化控件
        initView();
        //初始化底部按钮事件
        initEvent();

        //我的发布的任务
    }

    private void initView(){
        //底部菜单3个Linearlayout
        ll_task = findViewById(R.id.ll_task);
        ll_me = findViewById(R.id.ll_me);

        //底部3个ImageView
        iv_task = findViewById(R.id.iv_task);
        iv_me = findViewById(R.id.iv_me);

        //底部3个TextView
        tv_task = findViewById(R.id.tv_task);
        tv_me = findViewById(R.id.tv_me);

        //中间区域ViewPager
        viewPager = findViewById(R.id.vp_content);

        //适配器
        View Page_task = View.inflate(Main_Activity.this, R.layout.main_activity_task, null);
        View Page_me = View.inflate(Main_Activity.this, R.layout.main_activity_me, null);

        views = new ArrayList<>();
        views.add(Page_task);
        views.add(Page_me);

        adapter = new ContentAdapter(views);
        viewPager.setAdapter(adapter);

        //为ViewPager设置适配器
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                //这个方法是返回总共有几个滑动的页面（）
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                //该方法判断是否由该对象生成界面。
                return view==object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                //这个方法返回一个对象，该对象表明PagerAapter选择哪个对象放在当前的ViewPager中。这里我们返回当前的页面
                viewPager.addView(views.get(position));
                return views.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                //这个方法从viewPager中移动当前的view。（划过的时候）
                viewPager.removeView(views.get(position));
            }
        });

        //--------------------------------------任务界面------------------------------------
        //——————————————————————————————————重点理解——————————————————————————————————
        // 原来findviewById是View这个类中的方法，默认调用时其实应该是：this.findviewById();
        //由于listview标签的声明并不在当前的viewPager所在的xml布局中，所以直接通过findviewById方法是不能得到该listview的实例的。所以我们要用view1.findViewById（）方法找到listview
        lv = Page_task.findViewById(R.id.lv_task);

        list = taskServer.query();
        //android.R.layout.simple_list_item_1是android自带的一个布局，只有一个textview
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.task_item,android.R.id.text1,list);

        //为ListView设置适配器
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String tid,a;
                a = list.get(i);
                int first = a.indexOf("号");
                tid = a.substring(0,first);
                Bundle bundle = new Bundle();
                Intent intent = new Intent(Main_Activity.this,Get_Task.class);
                bundle.putString("tid",tid);
                bundle.putString("uid",temp);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        //--------------------------------------任务界面------------------------------------

        //--------------------------------------我的界面------------------------------------
        mTvname = Page_me.findViewById(R.id.mine_name);
        mTvid = Page_me.findViewById(R.id.mine_id);
        mTvintegral = Page_me.findViewById(R.id.mine_integral);
        mTvhand = Page_me.findViewById(R.id.mine_hand);
        mTvget = Page_me.findViewById(R.id.mine_get);
        mTvfinish = Page_me.findViewById(R.id.mine_finished);

        mTvhand.setClickable(true);
        mTvget.setClickable(true);
        mTvfinish.setClickable(true);
        mTvname.setText("昵称 "+ userServer.name(uid));
        mTvid.setText("积分 "+userServer.phone(uid));
        mTvhand.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_Activity.this,Handed_Task_Information.class);
                intent.putExtra("uid",temp);
                startActivity(intent);
            }
        });
        mTvget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_Activity.this,Got_Task.class);
                intent.putExtra("uid",temp);
                startActivity(intent);
            }
        });
        mTvfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Main_Activity.this,Finised_Task.class);
                intent.putExtra("uid",temp);
                startActivity(intent);
            }
        });
        //--------------------------------------我的界面------------------------------------
    }

    private void initEvent(){
        ll_task.setOnClickListener(this);
        ll_me.setOnClickListener(this);

        //设置滑动监听
        viewPager.setOnPageChangeListener(this);
    }

    //重置底部颜色
    private void restartButton(){
        tv_task.setTextColor(getResources().getColor(R.color.black));
        tv_me.setTextColor(getResources().getColor(R.color.black));
        iv_task.setColorFilter(getResources().getColor(R.color.black));
        iv_me.setColorFilter(getResources().getColor(R.color.black));
    }

    @Override// 在每次点击后将所有的底部按钮(ImageView,TextView)颜色改为绿色，然后根据点击着色
    public void onClick(View v) {
        restartButton();

        switch (v.getId()){
            case R.id.ll_task:
                tv_task.setTextColor(getResources().getColor(R.color.green));
                iv_task.setColorFilter(getResources().getColor(R.color.green));
                viewPager.setCurrentItem(0);
                break;
            case R.id.ll_me:
                tv_me.setTextColor(getResources().getColor(R.color.green));
                iv_me.setColorFilter(getResources().getColor(R.color.green));
                viewPager.setCurrentItem(1);
                break;
            default:
                 break;
        }
    }

    @Override//滑动设置底部颜色
    public void onPageSelected(int i) {
        restartButton();

        switch (i){
            case 0:
                tv_task.setTextColor(getResources().getColor(R.color.green));
                iv_task.setColorFilter(getResources().getColor(R.color.green));
                break;
            case 1:
                tv_me.setTextColor(getResources().getColor(R.color.green));
                iv_me.setColorFilter(getResources().getColor(R.color.green));
                break;
            default:
                break;
        }
    }

    @Override//设置overflow按钮
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override//overflow两个按钮的点击事件
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_new:
                Intent intent_new_task = new Intent(Main_Activity.this,Hand_Task.class);
                intent_new_task.putExtra("uid",temp);
                startActivity(intent_new_task);
                return true;
            case R.id.item_reset:
                initView();
                Toast.makeText(this, "你点击了“刷新”按键！", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onPageScrolled(int i, float v, int i1) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

    }
}
