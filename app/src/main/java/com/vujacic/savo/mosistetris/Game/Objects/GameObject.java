package com.vujacic.savo.mosistetris.Game.Objects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.vujacic.savo.mosistetris.Game.TetrisGrid.TetrisGrid;

import org.ejml.simple.SimpleMatrix;

public abstract class GameObject {
    Point[] location;
    int[] gridLocation;
    int[][][] wallKickData;
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
        gridLocation=new int[8];
        this.grid = grid;
    }

    abstract void setRectLocation();
    abstract void setGridLocation();
    public abstract void update();
    public abstract void draw(Canvas mCanvas, Paint mPaint);
    //public abstract void rotate();
    //public abstract void translate(int x,int y);
    public abstract void state();
    public abstract void setWallKickData();


    public static SimpleMatrix trans = new SimpleMatrix(3,3,true,new float[]{1,0,-1,0,1,-1,0,0,1});
    public static SimpleMatrix rotate = new SimpleMatrix(3,3,true, new float[]{0,1,0,-1,0,0,0,0,1});
    public static SimpleMatrix transR = new SimpleMatrix(3,3,true,new float[]{1,0,1,0,1,1,0,0,1});

    public void rotate(){
        int centarx = gridLocation[4];
        int centary = gridLocation[5];
        for(int i = 0 ; i< 8 ; i+=2){
            int xp = gridLocation[i]- centarx;
            int yp = gridLocation[i+1] - centary;

//            gridLocation[i] = yp + centarx + stateCorrection[state][0];
//            gridLocation[i+1] = -xp + centary + stateCorrection[state][1];
        }
        state = (state + 1) % 4;
        this.grid.setAll(this.gridLocation);
        }

    public boolean wallKick() {
        int[] position;
        for(int i = 0;i<5;i++){
            position = gridLocation.clone();
            for(int j=0;j<8;j+=2) {
                position[j]+=wallKickData[state][i][1];
                position[j+1]+=wallKickData[state][i][0];
            }
            if(grid.testAll(position)) {
                gridLocation = position.clone();
                return true;
            }
        }
        return false;
    }

    public void translate(int x,int y){
        int[] position = gridLocation.clone();
        for(int i = 0; i< 8; i+=2){
            position[i]+=x;
            position[i+1]+=y;
        }
        if(!grid.testAll(position)){
            return;
        }
        this.gridLocation= position.clone();
        this.grid.setAll(this.gridLocation);
    }

}
