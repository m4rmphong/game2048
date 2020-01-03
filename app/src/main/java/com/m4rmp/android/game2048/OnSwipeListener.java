package com.m4rmp.android.game2048;

import android.content.Context;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class OnSwipeListener implements View.OnTouchListener {
    public static String TAG = "SwipeListener";

    public enum Direction {
        up,
        down,
        left,
        right
    }

    private final GestureDetector mGestureDetector;

    public OnSwipeListener(Context aContext) {
        mGestureDetector = new GestureDetector(aContext, new GestureListener());
    }

    public boolean onTouch(View view, MotionEvent event) {
        //Log.d(TAG, "Touch!");
        return mGestureDetector.onTouchEvent(event);
    }

    public void onSwipe(Direction aDir) {
    }

    private final class GestureListener extends GestureDetector.SimpleOnGestureListener {
        static final float SWIPE_THRESHOLD = 0;
        static final float SWIPE_V_THRESHOLD = 0;

        public boolean onDown(MotionEvent e){
            return true;
        }

        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            boolean result = false;
            try {
                float disX = e2.getX() - e1.getX();
                float disY = e2.getY() - e1.getY();
                // identify direction
                if (Math.abs(disX) > Math.abs(disY)) {
                    if (Math.abs(disX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_V_THRESHOLD) {
                        if (disX > 0) {
                            onSwipe(Direction.right);
                        } else {
                            onSwipe(Direction.left);
                        }
                    }

                } else if (Math.abs(disY) > Math.abs(disX)) {
                    if (Math.abs(disY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_V_THRESHOLD) {
                        if (disY > 0) {
                            onSwipe(Direction.down);
                        } else {
                            onSwipe(Direction.up);
                        }
                    }
                }

                result = true;

            } catch (Exception e) {
                Log.e(TAG, "something wrong");
                e.printStackTrace();
            }
            return result;
        }
    }
}
