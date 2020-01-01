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
        mContext = aContext;
        sRand = new Random();
        GameMain.log("i", "Grid initialize");
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
}