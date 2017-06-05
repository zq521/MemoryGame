package com.xhz.memorygame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.PointF;

import java.util.ArrayList;
import java.util.List;

import top.yunp.androidgame2d.display.Display;
import top.yunp.androidgame2d.display.GameView;
import top.yunp.androidgame2d.events.TouchEvent;
import top.yunp.lib.java.event.EventListener;

/**
 * mail：727319870@qq.com
 * Created by ${轩韩子} on 2017/6/2.
 * 08:12
 */

public class MainView extends GameView {

    private int currentNum = 1;
    private List<NumberCard> cards = new ArrayList<>();
    private List<PointF> points = new ArrayList<>();
    private int level = 3, topLevel = 6;

    public MainView(Context context) {
        super(context);
        Config.initConfig(context);
        shouldStartGame();
    }

    private void shouldStartGame() {
        new AlertDialog.Builder(getContext())
                .setTitle("欢迎玩我")
                .setMessage("点击按钮开始游戏")
                .setPositiveButton("开始", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        restartGame();
                    }
                }).setNegativeButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        }).setCancelable(false)
                .setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        getActivity().finish();
                    }
                }).show();
    }

    private void restartGame() {
        level = 3;
        startGameByCurrentLevel();
    }

    private void nextLevel() {
        level++;
        if (level <= topLevel) {
            startGameByCurrentLevel();
        }

    }

    private void startGameByCurrentLevel() {
        currentNum = 1;
        clearGameData();
        addCard();
    }

    private void clearGameData() {
        while (cards.size() > 0) {
            cards.remove(0).removeFromParent();
            //重置坐标点数组
            points.clear();
        }
    }

    private void addCard() {
        int hCount = getWidth() / Config.getCardWidthInPx();
        int vCount = getHeight() / Config.getCardHeightInPx();
        topLevel = hCount * vCount;
        float cardsMapWidth = hCount * Config.getCardWidthInPx();
        float cardsMapHeight = vCount * Config.getCardHeightInPx();
        float xRemain = getWidth() - cardsMapWidth;
        float yRemain = getHeight() - cardsMapHeight;
        float globalOffsetX = Config.getCardWidthInPx() / 2 + xRemain / 2;
        float globalOffsetY = Config.getCardHeightInPx() / 2 + yRemain / 2;

        //生成可用坐标点
        for (int i = 0; i < hCount; i++) {
            for (int j = 0; j < vCount; j++) {
                points.add(new PointF(i * Config.getCardWidthInPx() + globalOffsetX, j * Config.getCardHeightInPx() + globalOffsetY));

            }
        }
        PointF p;
        NumberCard nc;
        for (int i = 1; i <= level; i++) {
            p = points.remove((int) (points.size() * Math.random()));//(0~9)
            nc = new NumberCard(i);
            nc.setX(p.x);
            nc.setY(p.y);
            add(nc);
            cards.add(nc);
            nc.touchDown.add(ncTouchDownHandler);
        }
    }

    private EventListener<TouchEvent, Display> ncTouchDownHandler = new EventListener<TouchEvent, Display>() {
        @Override
        public boolean onReceive(TouchEvent event, Display display) {
            if (display instanceof NumberCard) {
                if (((NumberCard) display).getNumber() == currentNum) {
                    remove(display);
                    cards.remove(display);
                    //将所有卡片翻转到反面
                    if (currentNum == 1) {
                        for (NumberCard c : cards) {
                            c.turnToVersc();
                        }
                    }
                    currentNum++;
                    //当卡片数组中数量为0时，则意味着你完成了游戏
                    if (cards.size() <= 0) {
                        if (level < topLevel) {
                            showGoNextLevelDialog();
                        } else {
                            showWinDialog();
                        }
                    }

                } else {
                    showFailDialog();
                }
            }
            return false;
        }
    };

    private void showFailDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("提醒")
                .setMessage("点错了，游戏重新开始")
                .setCancelable(false)
                .setPositiveButton("开始", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        restartGame();
                    }
                }).show();
    }

    private void showWinDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("恭喜")
                .setMessage("你赢了")
                .setPositiveButton("在玩一次", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        restartGame();
                    }
                }).setNegativeButton("关闭", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        }).show();

    }

    private void showGoNextLevelDialog() {
        new AlertDialog.Builder(getContext())
                .setTitle("提醒")
                .setMessage("下一关" + " " + (level + 1) + " " + "个卡片")
                .setCancelable(false)
                .setPositiveButton("开始", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        nextLevel();
                    }
                }).show();
    }

    public MainActivity getActivity() {
        return (MainActivity) getContext();
    }
}
