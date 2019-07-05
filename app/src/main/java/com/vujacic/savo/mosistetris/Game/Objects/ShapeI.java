package com.vujacic.savo.mosistetris.Game.Objects;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;

import com.vujacic.savo.mosistetris.Game.TetrisGrid.TetrisGrid;

import org.ejml.simple.SimpleMatrix;

public class ShapeI extends GameObject {
    private int stateCorrection[][] = {{1,0},{0,-1},{-1,0},{0,1}};
    public ShapeI() {
        super();
        setGridLocation();
        setRectLocation();
    }

    public ShapeI(TetrisGrid tg) {
        super(tg);
        setGridLocation();
        setRectLocation();
        setWallKickData();
        setCentri();
    }

    @Override
    void setRectLocation() {
        int x = -2,y=-1;
        for(int i = 0 ;i<8 ;i+=2){
            location[i]=new Point();
            location[i].x=x;
            location[i].y=y;

            x+=1;
            location[i+1]=new Point();
            location[i+1].x=x;
            location[i+1].y=y+1;
        }
    }

    @Override
    void setGridLocation() {
//        int loc=-2;
//        for(int i = 0;i<4;i++){
//            gridLocation[i]=loc;
//            loc+=1;
//        }
        gridLocation = new int[]{0,3,0,4,0,5,0,6};
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas mCanvas, Paint mPaint) {

        for(int i = 0 ;i<8;i+=2)
        {
            mCanvas.drawRect(location[i].x,location[i].y,
                    location[i+1].x,location[i+1].y,mPaint);
        }
    }

    @Override
    public void rotate() {
//        SimpleMatrix mnoziti;
//        transR.set(0,2,gridLocation[4]);
//        transR.set(1,2,gridLocation[5]);
//        trans.set(0,2,-gridLocation[4]);
//        trans.set(1,2,-gridLocation[5]);
//        mnoziti=transR.mult(rotate).mult(trans);
        int centarx = gridLocation[centri[0]];
        int centary = gridLocation[centri[1]];
        for(int i = 0 ; i< 8 ; i+=2){
//            SimpleMatrix s = mnoziti.mult(new SimpleMatrix(3,1,true,new float[]{gridLocation[i],gridLocation[i+1],1}));
//            gridLocation[i] = (int)s.get(0,0);
//            gridLocation[i+1] = (int)s.get(1,0);
            int xp = gridLocation[i]- centarx;
            int yp = gridLocation[i+1] - centary;

            gridLocation[i] = yp + centarx + stateCorrection[state][0];
            gridLocation[i+1] = -xp + centary + stateCorrection[state][1];
        }
        if(!wallKick()){
            return;
        }
        state = (state + 1) % 4;
        this.grid.setAll(this.gridLocation);
        
    }

//    @Override
//    public void translate(int x,int y) {
//        for(int i = 0; i< 8; i+=2){
//            gridLocation[i]+=x;
//            gridLocation[i+1]+=y;
//        }
//        this.grid.setAll(this.gridLocation);
//    }

    @Override
    public void state() {

    }

    @Override
    public void setWallKickData() {
        //[4][5][2]
        wallKickData = new int[][][]{
            {{0,0},{-2,0},{1,0},{-2,1},{1,-2}},
            {{0,0},{-1,0},{2,0},{-1,-2},{2,1}},
            {{0,0},{2,0},{-1,0},{2,-1},{-1,2}},
            {{0,0},{1,0},{-2,0},{1,2},{-2,-1}}
        };
    }

    @Override
    public void setCentri() {
        centri[0]=4;
        centri[1]=5;
    }

}
