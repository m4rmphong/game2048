package com.m4rmp.android.game2048;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.Random;

import android.util.Log;

public class Grid {
    public static String TAG = "Grid";
    public static int GRID_SIZE = 5;
    private static Random sRand;

    private GameMain mGame;
    private int mEmptyCell = GRID_SIZE * GRID_SIZE;
    private int[][] mGridData;
    private NumberTile[][] mGridUi;

    public Grid(Context aContext) {
        GameMain.log("i", "Grid initialize");
        mGame = (GameMain) aContext;
        sRand = new Random();
        mGridData = new int[GRID_SIZE][GRID_SIZE];
        mGridUi = new NumberTile[GRID_SIZE][GRID_SIZE];

        TableLayout grid = ((Activity) aContext).findViewById(R.id.grid);
        LayoutInflater layoutInflater = mGame.getLayoutInflater();
        for (int row = 0; row < GRID_SIZE; row++) {
            TableRow gridRow = (TableRow) layoutInflater.inflate(R.layout.grid_row, grid, false);
            for (int col = 0; col < GRID_SIZE; col++) {
                NumberTile tile = (NumberTile) layoutInflater.inflate(R.layout.number_tile, gridRow, false);
                mGridUi[row][col] = tile;
                gridRow.addView(tile);
            }
            grid.addView(gridRow);
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
        if (genTile(1)) {
            // grid is full check movable
            Log.d(TAG, "Grid is full!");
            if (!isMovable()) {
                // gameover
                mGame.gameOver();
            }
        }
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

    private boolean genTile(int aNum) {
        if (mEmptyCell == 0) {
            return true;
        }

        int i = 0;
        while (i < aNum) {
            //int level = sRand.nextInt(2) + 1;
            int level = randTileLevel();
            if (level < 0) {
                Log.d(TAG, "Generate obstacle");
            }
            int rowIdx = sRand.nextInt(GRID_SIZE);
            int colIdx = sRand.nextInt(GRID_SIZE);
            if (mGridData[rowIdx][colIdx] == 0) {
                mGridData[rowIdx][colIdx] = level;
                mEmptyCell--;
                i++;
            }

        }
        return mEmptyCell == 0;
    }

    private int[] initTileLevels = {1, 1, 1, 2, 2, 2, -1, -2};

    private int randTileLevel() {
        int randIdx = sRand.nextInt(initTileLevels.length);
        return initTileLevels[randIdx];
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
        if (aDir == OnSwipeListener.Direction.UP || aDir == OnSwipeListener.Direction.DOWN) {
            start = aDir == OnSwipeListener.Direction.UP ? 0 : GRID_SIZE - 1;
            move = aDir == OnSwipeListener.Direction.UP ? 1 : -1;
            for (int col = 0; col < GRID_SIZE; col++) {
                moveSingleCol(col, start, move);
            }
        } else if (aDir == OnSwipeListener.Direction.LEFT || aDir == OnSwipeListener.Direction.RIGHT) {
            start = aDir == OnSwipeListener.Direction.LEFT ? 0 : GRID_SIZE - 1;
            move = aDir == OnSwipeListener.Direction.LEFT ? 1 : -1;
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
                    Log.d(TAG, "Merge " + tileLevel);
                    arr[last]++;
                    mEmptyCell++;
                    mGame.updateScore(arr[last]);
                    if (tileLevel < 0) {
                        // generate two empty cell when eliminating obstacles
                        last--;
                        mEmptyCell++;
                    }
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
                    Log.d(TAG, "Merge " + tileLevel);
                    arr[last]++;
                    mEmptyCell++;
                    mGame.updateScore(arr[last]);
                    if (tileLevel < 0) {
                        // generate two empty cell when eliminating obstacles
                        last--;
                        mEmptyCell++;
                    }
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

    public boolean isMovable() {
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if (row != 0) {
                    if (mGridData[row][col] == mGridData[row - 1][col]) {
                        return true;
                    }
                }
                if (row != GRID_SIZE - 1) {
                    if (mGridData[row][col] == mGridData[row + 1][col]) {
                        return true;
                    }
                }
                if (col != 0) {
                    if (mGridData[row][col] == mGridData[row][col - 1]) {
                        return true;
                    }
                }
                if (col != GRID_SIZE - 1) {
                    if (mGridData[row][col] == mGridData[row][col + 1]) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}