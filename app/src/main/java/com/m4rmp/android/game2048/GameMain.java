package com.m4rmp.android.game2048;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class GameMain extends AppCompatActivity {
    private static final String TAG = "GameMain";

    // game manager
    private boolean mWin = false;
    private int mScore = 0;
    private int mRecord = 0;
    private Random mRand;

    // views
    private TextView mScoreText;
    private TextView mRecordText;
    private Button mResetButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
    }

    static void log(String type, String aMessage) {
        if (type == "e") {
            Log.e(TAG, aMessage);
        } else if (type == "w") {
            Log.w(TAG, aMessage);
        } else if (type == "i") {
            Log.i(TAG, aMessage);
        } else if (type == "d") {
            Log.d(TAG, aMessage);
        } else if (type == "v") {
            Log.v(TAG, aMessage);
        }
    }

    private void init() {
        // UI component
        mScoreText = findViewById(R.id.score);
        mRecordText = findViewById(R.id.record);
        mResetButton = findViewById(R.id.reset_button);

        // game data
        mRand = new Random();
        initGame();
        initUI();
    }

    private void initGame() {
        // new game
        resetGame();
    }

    private void initUI() {
        mScoreText.setText(Integer.toString(mScore));
        mRecordText.setText(Integer.toString(mRecord));

        mResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
                updateUi();
            }
        });
    }

    private void resetGame() {
        mWin = false;
        mScore = 0;
        // TODO: resetGrid()
    }

    private void updateUi() {
        mScoreText.setText(Integer.toString(mScore));
    }

}
