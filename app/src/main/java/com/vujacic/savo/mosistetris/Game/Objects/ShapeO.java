package com.vujacic.savo.mosistetris.Game.Objects;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.vujacic.savo.mosistetris.Game.TetrisGrid.TetrisGrid;

public class ShapeO extends GameObject {
    public ShapeO(TetrisGrid tg) {
        super(tg);
        setGridLocation();
        //setRectLocation();
        setWallKickData();
        setCentri();
    }

    @Override
    void setRectLocation() {
//        int x = -2,y=-1;
//        for(int i = 0 ;i<8 ;i+=2){
//            location[i]=new Point();
//            location[i].x=x;
//            location[i].y=y;
//
//            x+=1;
//            location[i+1]=new Point();
//            location[i+1].x=x;
//            location[i+1].y=y+1;
//        }
    }

    @Override
    void setGridLocation() {
//        int loc=-2;
//        for(int i = 0;i<4;i++){
//            gridLocation[i]=loc;
//            loc+=1;
//        }
        gridLocation = new int[]{0,4,0,5,1,4,1,5};
    }

    @Override
    public void update() {

    }

    @Override
    public void draw(Canvas mCanvas, Paint mPaint) {

//        for(int i = 0 ;i<8;i+=2)
//        {
//            mCanvas.drawRect(location[i].x,location[i].y,
//                    location[i+1].x,location[i+1].y,mPaint);
//        }
    }


    @Override
    public void state() {

    }

    @Override
    public void setCentri() {
//        centri[0]=4;
//        centri[1]=5;
    }

    public void rotate() {

    }
}
