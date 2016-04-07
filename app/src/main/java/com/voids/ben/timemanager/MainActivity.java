package com.voids.ben.timemanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends Activity {

    @Bind(R.id.btn_push)
    Button btnPush;
    private TextSwitcherWrapper secSingle;
    private TextSwitcherWrapper secTens;
    private TextSwitcherWrapper minSingle;
    private TextSwitcherWrapper minTens;
    private TextSwitcherWrapper hourSingle;
    private TextSwitcherWrapper hourTens;
    private TimeManager timeManager;

    private int progress = 0;  //当前使用秒数


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        init();
        initTimeManager();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTask(), 0, 1000);

    }

    private void init() {
        btnPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ButterKnifeActivity.class);
                startActivity(intent);
            }
        });
    }

    private void initTimeManager() {
        secSingle = (TextSwitcherWrapper) findViewById(R.id.text_switcher1);
        secTens = (TextSwitcherWrapper) findViewById(R.id.text_switcher2);
        minSingle = (TextSwitcherWrapper) findViewById(R.id.text_switcher3);
        minTens = (TextSwitcherWrapper) findViewById(R.id.text_switcher4);
        hourSingle = (TextSwitcherWrapper) findViewById(R.id.text_switcher5);
        hourTens = (TextSwitcherWrapper) findViewById(R.id.text_switcher6);

        timeManager = new TimeManager(hourTens, hourSingle, minTens, minSingle, secTens, secSingle);
        //timeManager.setTotalTime(3744);

        timeManager.setTime(progress);//设置初始时间方式1 setTime（int total）总秒数    e.g 3744=01小时02分44秒

        //timeManager.setTime(0,9,5,9,5,1);//分别设置h1 h2 小时,m1,m2分钟,s1，s2秒数    e.g  09小时59分51秒 用于数据测试

        timeManager.initTimeManager();//设置时间需在初始化前  setFactory(this)？导致

        timeManager.Start();//是否需要暂停
    }

    private Handler mHandler = new Handler() {

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    timeManager.updateState();//更新状态
                    timeManager.loopTime();//进行滚动
                    progress = timeManager.getTotalTime();//保存时间
                    Log.i("time", progress + "");
//                    if (config.getIsScreenOn())
//                        timeManager.Start();
//                    else timeManager.Paused();
                    break;

            }
        }

        ;
    };

    private class MyTask extends TimerTask {
        @Override
        public void run() {
            Message message = new Message();
            message.what = 1;
            mHandler.sendMessage(message);
        }
    }
}
