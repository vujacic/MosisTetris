package com.vujacic.savo.mosistetris.Game.Objects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.vujacic.savo.mosistetris.Game.Helpers.PaintObjects;
import com.vujacic.savo.mosistetris.Game.TetrisGrid.TetrisGrid;

import org.ejml.simple.SimpleMatrix;

public abstract class GameObject {
    Point[] location;
    int[] gridLocation;
    //int[][][] wallKickData;
    float velocity;
    long lastDrawNanoTime=-1;
    TetrisGrid grid;
    int state = 0;//0 - pocetno, 1 - rota desno, 2 - 2 rota desno, 3 - 3 rota desno
    int centri[] = new int[2];
    Paint paint;
    Paint oldPaint;
    Paint newPaint;

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
    public void setWallKickData(){};


    public static SimpleMatrix trans = new SimpleMatrix(3,3,true,new float[]{1,0,-1,0,1,-1,0,0,1});
    public static SimpleMatrix rotate = new SimpleMatrix(3,3,true, new float[]{0,1,0,-1,0,0,0,0,1});
    public static SimpleMatrix transR = new SimpleMatrix(3,3,true,new float[]{1,0,1,0,1,1,0,0,1});

    public void rotate(){
        int[] position = gridLocation.clone();
        int centarx = gridLocation[centri[0]];
        int centary = gridLocation[centri[1]];
        for(int i = 0 ; i< 8 ; i+=2){
            int xp = gridLocation[i]- centarx;
            int yp = gridLocation[i+1] - centary;

            position[i] = yp + centarx;
            position[i+1] = -xp + centary;
        }
        if(!wallKick(position)){
            this.grid.setAll(this.gridLocation, this.paint);
            return;
        }
        state = (state + 1) % 4;
        this.grid.setAll(this.gridLocation, this.paint);
    }

    public boolean wallKick(int[] positionTest) {
        int[] position;
        for(int i = 0;i<5;i++){
            position = positionTest.clone();
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

    public boolean translate(int x,int y){
        int[] position = gridLocation.clone();
        for(int i = 0; i< 8; i+=2){
            position[i]+=x;
            position[i+1]+=y;
        }
        if(!grid.testAll(position)){
            this.grid.setAll(this.gridLocation, this.paint);
            return false;
        }
        this.gridLocation= position.clone();
        this.grid.setAll(this.gridLocation, this.paint);
        return true;
    }

    int[][][] wallKickData = new int[][][]{
        {{0,0},{-1,0},{-1,-1},{0,2},{-1,2}},
        {{0,0},{1,0},{1,1},{0,-2},{1,-2}},
        {{0,0},{1,0},{1,-1},{0,2},{1,2}},
        {{0,0},{-1,0},{-1,1},{0,-2},{-1,2}}
    };

    public abstract void setCentri();
    public abstract void setPaint();
    public void oldNewPaint(boolean old){
        if(old)
            paint = oldPaint;
        else
            paint = newPaint;
    }

    public void drawGridLocation() {
        this.grid.setAll(this.gridLocation, this.paint);
    }

}
