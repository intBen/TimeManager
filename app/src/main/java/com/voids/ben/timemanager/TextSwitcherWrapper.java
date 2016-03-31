package com.voids.ben.timemanager;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.ViewSwitcher;



    /**
     * Created by Ben
     * 自定义时间控制
     */
    public class TextSwitcherWrapper extends TextSwitcher implements ViewSwitcher.ViewFactory {

        public boolean loop = false;
        private Context context;
        public int id;
        private int oldid = 0;
        private int newid = 1;
        public int length = 0;
        private String[] res;

        final String[] res1 = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        final String[] res2 = {"0", "1", "2", "3", "4", "5"};

        public TextSwitcherWrapper(Context p) {
            super(p);
            this.context = p;

        }

        public TextSwitcherWrapper(Context p, AttributeSet attributeSet) {
            super(p, attributeSet);
            this.context = p;
        }

        //设置控制switcher 当后方执行一轮 进行改变内容
        public void controlBy(TextSwitcherWrapper textSwitcherWrapper) {
            if (textSwitcherWrapper.loop) {
                updateText();
            }

        }

        public void controlBy(TextSwitcherWrapper textSwitcherWrapper, TextSwitcherWrapper textSwitcherWrapper1) {
            if (textSwitcherWrapper.id == 0 && textSwitcherWrapper1.loop) {

                updateText();
            }

        }

        public void controlBy(TextSwitcherWrapper r1, TextSwitcherWrapper r2, TextSwitcherWrapper r3) {
            if (r1.id == 0 && r2.id == 0 && r3.loop) {
                updateText();
            }
        }

        public void controlBy(TextSwitcherWrapper r1, TextSwitcherWrapper r2, TextSwitcherWrapper r3, TextSwitcherWrapper r4) {
            if (r1.id == 0 && r2.id == 0 && r3.id == 0 && r4.loop) {
                updateText();
            }
        }

        public void controlBy(TextSwitcherWrapper r1, TextSwitcherWrapper r2, TextSwitcherWrapper r3, TextSwitcherWrapper r4, TextSwitcherWrapper r5) {
            if (r1.id == 0 && r2.id == 0 && r3.id == 0 && r4.id == 0 && r5.loop) {
                updateText();
            }
        }

        //更新当前text内容 SetCurrenText为默认无动画效果
        public void updateText() {
            this.id = next();
            this.setText(res[id]);

        }
        //重置
        public void reset()
        {
            this.id=0;
            this.newid=1;
            this.oldid=0;
            this.setText("0");
        }

        //设置setFactory 动画
        public void init() {
            initRes();
            this.setFactory(this);
            this.setInAnimation(AnimationUtils.loadAnimation(context, R.anim.topin));
            this.setOutAnimation(AnimationUtils.loadAnimation(context, R.anim.bottomout));
        }

        //判断位制
        private void initRes() {
            if (length == 6) {
                this.res = res2;
            }
            if (length == 10) {
                this.res = res1;
            }
        }

        public void setLength(int length) {
            this.length = length;
        }

        public void setTime(int time) {
            try {
                id = time;
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }

        public int getTime() {
            return id;
        }


        public int next() {

            int flag = id + 1;
            if (flag > length - 1) {
                flag = flag - length;
            }

            return flag;
        }

        //返回 view 显示 可 Imageview 自定义view
        @Override
        public View makeView() {
            // TODO Auto-generated method stub
            TextView tv = new TextView(context);
            tv.setTextSize(30);

            tv.setTextColor(getResources().getColor(R.color.abc_secondary_text_material_dark));
            tv.setText(res[id]);
            return tv;
        }

        private void timeLoop() {
            newid = id + 1;
            if (newid > res.length - 1) {
                newid = newid - res.length;
            }
        }

        //判断swticher是否执行一轮
        public void updateState() {

            timeLoop();

            // if (newid==oldid)
            if (newid == oldid && !loop) {
                this.loop = true;
            } else {
                this.loop = false;
            }
        }

    }

