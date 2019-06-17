package com.vujacic.savo.mosistetris.Game.Objects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.vujacic.savo.mosistetris.Game.TetrisGrid.TetrisGrid;

public abstract class GameObject {
    Point[] location;
    int[] gridLocation;
    float velocity;
    long lastDrawNanoTime=-1;
    TetrisGrid grid;
    int state = 0;//0 - pocetno, 1 - rota desno, 2 - 2 rota desno, 3 - 3 rota desno

    public GameObject() {
        location=new Point[8];
        gridLocation=new int[4];
    }

    public GameObject(TetrisGrid grid) {
        location=new Point[8];
        gridLocation=new int[4];
        this.grid = grid;
    }

    abstract void setRectLocation();
    abstract void setGridLocation();
    public abstract void update();
    public abstract void draw(Canvas mCanvas, Paint mPaint);
    public abstract void rotate();
    public abstract void translate();
}
