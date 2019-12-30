package com.m4rmp.android.game2048;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class GameMain extends AppCompatActivity {
    private static final String TAG = "GameMain";

    // game manager
    private int mScore = 0;
    private int mRecord = 0;

    // views
    private TextView mScoreText;
    private TextView mRecordText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    private void init() {
        // UI component
        mScoreText = (TextView) findViewById(R.id.score);
        mScoreText.setText(Integer.toString(mScore));
        mRecordText = (TextView) findViewById(R.id.record);
        mRecordText.setText(Integer.toString(mRecord));


    }

}
