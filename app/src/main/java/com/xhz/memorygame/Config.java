package com.xhz.memorygame;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * mail：727319870@qq.com
 * Created by ${轩韩子} on 2017/6/2.
 * 08:31
 */

public class Config {
    private static  int _cardWidthInPx,_cardHeightInPx,_textSize;

    public static void initConfig(Context context){
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        _cardWidthInPx= (int) (displayMetrics.density*CARD_WIDTH);
        _cardHeightInPx= (int) (displayMetrics.density*CARD_HEIGHT);
        _textSize= (int) (0.4*_cardHeightInPx);

    }


    public static final int CARD_WIDTH=120;
    public static final int CARD_HEIGHT=120;

    public static int getCardHeightInPx() {
        return _cardHeightInPx;
    }

    public static int gettextSize() {
        return _textSize;
    }

    public static int getCardWidthInPx() {
        return _cardWidthInPx;
    }
}
