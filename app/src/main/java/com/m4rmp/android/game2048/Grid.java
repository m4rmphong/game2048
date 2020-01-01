package com.m4rmp.android.game2048;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.TableLayout;
import android.widget.TableRow;

public class Grid {
    public static int GRID_SIZE = 4;
    private Context mContext;

    private int[][] mGridData;
    private NumberTile[][] mGridUi;

    public Grid(Context aContext) {
        mContext = aContext;
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

    public void loadGridData() {

    }

    public int[][] getGridData() {
        return mGridData;
    }

    public void resetGrid() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                mGridData[row][col] = 0;
                mGridUi[row][col].resetTile();
            }
        }
    }

    public void updateGridUi() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                mGridUi[row][col].setTile(mGridData[row][col]);
            }
        }
    }
}