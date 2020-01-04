package com.m4rmp.android.game2048;

import android.app.Activity;
import android.content.Context;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.Random;

import android.util.Log;

public class Grid {
    public static String TAG = "Grid";
    public static int GRID_SIZE = 4;
    private static Random sRand;
    private Context mContext;

    private int mEmptyCell = GRID_SIZE * GRID_SIZE;
    private int[][] mGridData;
    private NumberTile[][] mGridUi;

    public Grid(Context aContext) {
        GameMain.log("i", "Grid initialize");
        mContext = aContext;
        sRand = new Random();
        mGridData = new int[GRID_SIZE][GRID_SIZE];
        mGridUi = new NumberTile[GRID_SIZE][GRID_SIZE];

        TableLayout grid = ((Activity) mContext).findViewById(R.id.grid);
        for (int row = 0; row < grid.getChildCount(); row++) {
            TableRow gridRow = (TableRow) grid.getChildAt(row);
            for (int col = 0; col < gridRow.getChildCount(); col++) {
                mGridUi[row][col] = (NumberTile) gridRow.getChildAt(col);
            }
        }
    }

    public void loadGridData(int[] aGridData) {
        int i = 0;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                mGridData[row][col] = aGridData[i++];
            }
        }
    }

    public int[][] getGridData() {
        return mGridData;
    }

    public void newGrid() {
        resetGrid();
        genTile(2);
        updateGridUi();
    }

    private void nextGrid() {
        genTile(1);
        updateGridUi();
    }

    private void resetGrid() {
        mEmptyCell = GRID_SIZE * GRID_SIZE;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                mGridData[row][col] = 0;
                mGridUi[row][col].resetTile();
            }
        }
    }

    private void genTile(int aNum) {
        if (mEmptyCell == 0) {
            return;
        }

        int i = 0;
        while (i < aNum) {
            int level = sRand.nextInt(2) + 1;
            int rowIdx = sRand.nextInt(GRID_SIZE);
            int colIdx = sRand.nextInt(GRID_SIZE);
            if (mGridData[rowIdx][colIdx] == 0) {
                mGridData[rowIdx][colIdx] = level;
                mEmptyCell--;
                i++;
            }

        }
    }

    public void updateGridUi() {
        Log.i(TAG, "Empty tiles: " + mEmptyCell);
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                mGridUi[row][col].setTile(mGridData[row][col]);
            }
        }
    }

    public void moveTiles(OnSwipeListener.Direction aDir) {
        Log.d(TAG, "Move tiles " + aDir.toString());
        int start, move;
        if (aDir == OnSwipeListener.Direction.up || aDir == OnSwipeListener.Direction.down) {
            start = aDir == OnSwipeListener.Direction.up ? 0 : GRID_SIZE - 1;
            move = aDir == OnSwipeListener.Direction.up ? 1 : -1;
            for (int col = 0; col < GRID_SIZE; col++) {
                moveSingleCol(col, start, move);
            }
        } else if (aDir == OnSwipeListener.Direction.left || aDir == OnSwipeListener.Direction.right) {
            start = aDir == OnSwipeListener.Direction.left ? 0 : GRID_SIZE - 1;
            move = aDir == OnSwipeListener.Direction.left ? 1 : -1;
            for (int row = 0; row < GRID_SIZE; row++) {
                moveSingleRow(row, start, move);
            }
        }

        nextGrid();
    }

    private void moveSingleCol(int colIdx, int start, int move) {
        int[] arr = new int[GRID_SIZE];
        int last = -1;
        int originLevel = 0;
        for (int i = 0, row = start; i < GRID_SIZE; i++, row += move) {
            int tileLevel = mGridData[row][colIdx];
            if (tileLevel != 0) {
                if (last != -1 && tileLevel == arr[last] && tileLevel == originLevel) {
                    // merge with same-level tile
                    arr[last]++;
                    mEmptyCell++;
                } else {
                    originLevel = tileLevel;
                    arr[++last] = tileLevel;
                }
                mGridData[row][colIdx] = 0;
            }
        }
        // update row
        if (last != -1) {
            int i = 0;
            while (i < last + 1) {
                mGridData[start + move * i][colIdx] = arr[i++];
            }
        }
    }

    private void moveSingleRow(int rowIdx, int start, int move) {
        int[] arr = new int[GRID_SIZE];
        int last = -1;
        int originLevel = 0;
        for (int i = 0, col = start; i < GRID_SIZE; i++, col += move) {
            int tileLevel = mGridData[rowIdx][col];
            if (tileLevel != 0) {
                if (last != -1 && tileLevel == arr[last] && tileLevel == originLevel) {
                    // merge with same-level tile
                    arr[last]++;
                    mEmptyCell++;
                } else {
                    originLevel = tileLevel;
                    arr[++last] = tileLevel;
                }
                mGridData[rowIdx][col] = 0;
            }
        }
        // update col
        if (last != -1) {
            int i = 0;
            while (i < last + 1) {
                mGridData[rowIdx][start + move * i] = arr[i++];
            }
        }
    }
}