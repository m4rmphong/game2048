package com.m4rmp.android.game2048;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;

import androidx.appcompat.widget.AppCompatTextView;

public class NumberTile extends AppCompatTextView {
    public static String TAG = "NumberTile";
    public static int[] sLevelColor;
    public static String[] sLevelName;
    public static int[] sObstacleColor;
    public static String[] sObstacleName;

    private int mLevel = 0;
    private Context mContext;

    public NumberTile(Context aContext) {
        super(aContext);
        init(aContext);
    }

    public NumberTile(Context aContext, AttributeSet aAttrs) {
        super(aContext, aAttrs);
        init(aContext);
    }

    public NumberTile(Context aContext, AttributeSet aAttrs, int aStyle) {
        super(aContext, aAttrs, aStyle);
        init(aContext);
    }

    private void init(Context aContext) {
        mContext = aContext;
        sLevelColor = mContext.getResources().getIntArray(R.array.levelColor);
        sLevelName = mContext.getResources().getStringArray(R.array.levelName);
        sObstacleColor = mContext.getResources().getIntArray(R.array.obstacleColor);
        sObstacleName = mContext.getResources().getStringArray(R.array.obstacleName);
        setTile(0);
    }

    public void resetTile() {
        setTile(0);
    }

    public void setTile(int aLevel) {
        mLevel = aLevel;
        if (aLevel < 0) {
            setText(sObstacleName[-mLevel]);
            ((GradientDrawable) this.getBackground()).setColor(sObstacleColor[-mLevel]);
        } else {
            setText(sLevelName[mLevel]);
            ((GradientDrawable) this.getBackground()).setColor(sLevelColor[mLevel]);
        }

        //setBackgroundColor(sLevelColor[mLevel]);
    }
}
