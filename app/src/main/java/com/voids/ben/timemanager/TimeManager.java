package com.voids.ben.timemanager;

/**
 * Created by ben
 * 时间管理器类
 */
public class TimeManager {
    TextSwitcherWrapper[] switcherList = new TextSwitcherWrapper[6];
    public TextSwitcherWrapper secSingle;
    public TextSwitcherWrapper secTens;
    public TextSwitcherWrapper minSingle;
    public TextSwitcherWrapper minTens;
    public TextSwitcherWrapper hourSingle;
    public TextSwitcherWrapper hourTens;

    int secSingleTime = 0;
    int secTensTime = 0;
    int minSingleTime = 0;
    int minTensTime = 0;
    int hourSingleTime = 0;
    int hourTensTime = 0;

    private boolean isPaused;
    private int totalTime = 0;
    private static TimeManager instance = new TimeManager();

    protected TimeManager() {
    }

    //Single？
    public static TimeManager getInstance() {
        return instance;
    }

    public TimeManager(TextSwitcherWrapper t1, TextSwitcherWrapper t2, TextSwitcherWrapper t3, TextSwitcherWrapper t4, TextSwitcherWrapper t5, TextSwitcherWrapper t6) {

        switcherList[0] = t1;
        switcherList[1] = t2;
        switcherList[2] = t3;
        switcherList[3] = t4;
        switcherList[4] = t5;
        switcherList[5] = t6;

    }
    public void reSetTimeManager()
    {
        switcherList[5].reset();
        switcherList[4].reset();
        switcherList[3].reset();
        switcherList[2].reset();
        switcherList[1].reset();
        switcherList[0].reset();
    }


    public void initTimeManager() {

        initSwitcher();

    }

    //初始化Switcher LOOP长度 默认 从上往下滚动动画
    private void initSwitcher() {
        switcherList[5].setLength(10);
        switcherList[4].setLength(6);
        switcherList[3].setLength(10);
        switcherList[2].setLength(6);
        switcherList[1].setLength(10);
        switcherList[0].setLength(6);
        switcherList[5].init();
        switcherList[4].init();
        switcherList[3].init();
        switcherList[2].init();
        switcherList[1].init();
        switcherList[0].init();
    }

    public void Paused() {
        this.isPaused = true;
    }

    public  void Start() {
        this.isPaused = false;

    }

    public int getTotalTime() {
        int total;
        total = switcherList[0].id * 36000 + switcherList[1].id * 3600 + switcherList[2].id * 600 + switcherList[3].id * 60 + switcherList[4].id * 10 + switcherList[5].id;
        this.totalTime=total;
        return total;
    }

    public void setTotalTime(int totalTime) {
        this.totalTime = totalTime;
    }

    //时间的设置 获取
    public void setTime(int totalTime) {

        this.totalTime = totalTime;
        timeDeal(totalTime);
        switcherList[5].setTime(secSingleTime);
        switcherList[4].setTime(secTensTime);
        switcherList[3].setTime(minSingleTime);
        switcherList[2].setTime(minTensTime);
        switcherList[1].setTime(hourSingleTime);
        switcherList[0].setTime(hourTensTime);

    }

    //用于测试
    public void setTime(int h2, int h1, int m2, int m1, int s2, int s1) {

        switcherList[5].setTime(s1);
        switcherList[4].setTime(s2);
        switcherList[3].setTime(m1);
        switcherList[2].setTime(m2);
        switcherList[1].setTime(h1);
        switcherList[0].setTime(h2);

    }

    private void timeDeal(int totalTime) {
        secSingleTime = totalTime % 10;
        secTensTime = (totalTime % 60) / 10;
        minSingleTime = (totalTime / 60) % 10;
        minTensTime = (totalTime / 60)% 60 / 10;
        hourSingleTime = (totalTime / 3600) % 10;
        hourTensTime = (totalTime / 3600)% 60 / 10;
    }

    public void updateState() {
        switcherList[5].updateState();
        switcherList[4].updateState();
        switcherList[3].updateState();
        switcherList[2].updateState();
        switcherList[1].updateState();
        switcherList[0].updateState();
    }

    public void loopTime() {
        if (!isPaused) {
            switcherList[5].next();
            switcherList[5].updateText();
            switcherList[4].controlBy(switcherList[5]);
            switcherList[3].controlBy(switcherList[4], switcherList[5]);
            switcherList[2].controlBy(switcherList[3], switcherList[4], switcherList[5]);
            switcherList[1].controlBy(switcherList[2], switcherList[3], switcherList[4], switcherList[5]);
            switcherList[0].controlBy(switcherList[1], switcherList[2], switcherList[3], switcherList[4], switcherList[5]);

        }
        //这里可以设置计时的最大值
//        if (totalTime > 43200)//大于12小时
//        {
//            setTime(1, 2, 0, 0, 0, 0);
//            Paused();
//        }
//        if (switcherList[0].id == 1 && switcherList[1].id == 2)//最高为12小时
//        {
//            Paused();
//        }
    }
}

