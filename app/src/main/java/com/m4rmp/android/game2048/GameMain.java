package com.m4rmp.android.game2048;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameMain extends AppCompatActivity {
    private static final String TAG = "GameMain";
    private static int[] sLevelScore;
    private static int[] sObstacleScore;
    public static int WIN_LEVEL = 11;

    public enum sGameStatus {WIN, LOSE}

    // game manager
    private boolean mWin = false;
    private int mScore = 0;
    private int mRecord = 0;
    private Grid mGrid;

    // views
    private ConstraintLayout mCanvas;
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
        mCanvas = findViewById(R.id.canvas);
        mCanvas.setOnTouchListener(new OnSwipeListener(this) {
            @Override
            public void onSwipe(Direction aDir) {
                super.onSwipe(aDir);
                mGrid.moveTiles(aDir);
            }
        });

        mScoreText = findViewById(R.id.score);
        mRecordText = findViewById(R.id.record);
        mResetButton = findViewById(R.id.reset_button);

        // game data
        mGrid = new Grid(this);
        sLevelScore = getResources().getIntArray(R.array.levelScore);
        sObstacleScore = getResources().getIntArray(R.array.obstacleScore);

        //initialize
        initUI();
        initGame();
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
        log("i", "game reset");
        mWin = false;
        mScore = 0;
        mGrid.newGrid();
    }

    private void updateUi() {
        mScoreText.setText(Integer.toString(mScore));
        mRecordText.setText(Integer.toString(mRecord));
    }

    public void updateScore(int aLevel) {
        if (aLevel > 0) {
            mScore += sLevelScore[aLevel];
        } else {
            mScore += sObstacleScore[-aLevel];
        }
        mRecord = Math.max(mScore, mRecord);
        updateUi();
        if (aLevel == WIN_LEVEL && !mWin) {
            Log.i(TAG, sGameStatus.WIN.toString());
            mWin = true;
            // TODO: show win message
        }
    }

    public void gameOver() {
        Log.i(TAG, sGameStatus.LOSE.toString());
        // TODO: show lose message
    }

}
