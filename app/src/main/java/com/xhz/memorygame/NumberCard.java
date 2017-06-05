package com.xhz.memorygame;

import android.graphics.Color;
import android.graphics.Path;

import top.yunp.androidgame2d.display.Shape;
import top.yunp.androidgame2d.display.Sprite;
import top.yunp.androidgame2d.display.TextLine;
import top.yunp.androidgame2d.events.TweenEvent;
import top.yunp.androidgame2d.tween.ScaleTween;
import top.yunp.androidgame2d.tween.Tween;
import top.yunp.lib.java.event.EventListener;

/**
 * mail：727319870@qq.com
 * Created by ${轩韩子} on 2017/6/2.
 * 08:26
 */

public class NumberCard extends Sprite {

    private int number = 0;
    private Sprite recto;//声明卡片正面
    private Shape verso;//声明卡片反面
    private ScaleTween scaleX1To0 = new ScaleTween(null, 1, 1, 0, 1);
    private ScaleTween scaleX0To1 = new ScaleTween(null, 0, 1, 1, 1);
    private boolean tweenRunning = false;



    public NumberCard(int number) {
        this.number = number;
        buildUI();
        addTweenListeners();
    }


    private void buildUI() {
        //build recto
        //创建卡片正面图形
        recto = new Sprite();
        //创建卡片正面图形的背景
        Shape rectoBg = new Shape();

        //build recto background
        //卡片的位宽
        int halfWidth = Config.CARD_WIDTH / 2;
        //卡片的位高
        int halfHeight = Config.CARD_HEIGHT / 2;
        int margin=5;
        //为正面背景设置位红色
        rectoBg.getPaint().setColor(Color.RED);
        rectoBg.getPath().addRect(-halfWidth+margin, -halfHeight+margin, halfWidth-margin, halfHeight-margin, Path.Direction.CW);
        //该背景添加到正面图形中
        recto.add(rectoBg);
        //将正面图形添加到该卡片中
        add(recto);

        //build recto text
        //创建卡片正面的数字文本对象
        TextLine tl = new TextLine(String.valueOf(getNumber()));
        //设置文字大小
        tl.setSize(Config.gettextSize());
        recto.add(tl);
        //计算文字的高度
        int lineHeight = tl.getLineHeight();
        //将文字相对于卡片垂直居中
        tl.setY(lineHeight / 2);
        //将文字相对于卡片水平居中
        tl.setX(-tl.getBounds().width() / 2);
        //设置文字的颜色为白色
        tl.getPaint().setColor(Color.WHITE);

        //bulid verso
        //创建卡片反面
        verso = new Shape();
        //设置卡片的反面背景颜色为蓝色
        verso.getPaint().setColor(Color.BLUE);
        //设置反面卡片矩形框
        verso.getPath().addRect(-halfHeight+margin, -halfHeight+margin, halfWidth-margin, halfHeight-margin, Path.Direction.CW);
        //将反面添加到卡片中
        add(verso);
        //呈现卡片正面
        showRecto();


    }

    private void addTweenListeners() {
        scaleX0To1.setFrames(5);
        scaleX1To0.setFrames(5);
        scaleX1To0.tweenEnd.add(new EventListener<TweenEvent,Tween>() {
            @Override
            public boolean onReceive(TweenEvent event, Tween tween) {
                if (getRecto().isVisible()) {
                    getVerso().setScaleX(0);
                    showVerso();
                    scaleX0To1.setTarget(getVerso()).start();
                } else {
                    getRecto().setScaleX(0);
                    showRecto();
                    scaleX0To1.setTarget(getRecto()).start();
                }
                return false;
            }
        });

        scaleX0To1.tweenEnd.add(new EventListener<TweenEvent,Tween>() {
            @Override
            public boolean onReceive(TweenEvent event,Tween tween) {
                tweenRunning = false;
                return false;
            }
        });
    }

    public void showRecto() {
        recto.setVisible(true);
        verso.setVisible(false);
    }

    public void showVerso() {
        recto.setVisible(false);
        verso.setVisible(true);
    }

    public Shape getVerso() {
        return verso;
    }

    public Sprite getRecto() {
        return recto;
    }

    public int getNumber() {
        return number;
    }

    public void turnToRecto() {
        if (!tweenRunning && getVerso().isVisible()) {
            scaleX1To0.setTarget(getVerso()).start();
            tweenRunning = true;
        }
    }

    public void turnToVersc() {
        if (!tweenRunning && getRecto().isVisible()) {
            scaleX1To0.setTarget(getRecto()).start();
            tweenRunning = true;

        }
    }


}
