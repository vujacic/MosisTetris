package com.vujacic.savo.mosistetris.Game.TetrisGrid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.vujacic.savo.mosistetris.Game.Helpers.PaintObjects;

public class TetrisGrid {
    public TetrisGridObject[] grid;
    public int x,y,yExt;
    public int[][] mainGrid;
    //uvek +2 jer imamo dve vrste na pocetku

    public TetrisGrid() {
        x=10;
        y=20;
        grid=new TetrisGridObject[220];
        for(int i=0; i<220 ; i++)
        {
            grid[i] = new TetrisGridObject();
        }
        mainGrid = new int[y+2][x];
        yExt = y + 2;
    }

    public TetrisGrid(int sizeX, int sizeY) {
        x=sizeX;
        y=sizeY;
        int gs=sizeX*(sizeY+2);
        grid=new TetrisGridObject[gs];
    }

    public void drawGrid(Canvas canvas) {
        for(int i = 0;i<x;i++)
        {
            for(int j=0;j<y;j++)
            {
                canvas.drawRect(i,j,i+1,j+1,grid[i*y + (j+2)].paintObject);
                //canvas.drawRect(i+0.1f,j+0.1f,i+0.9f,j+0.9f,grid[i*y + (j+2)].paintObject);
            }
        }
        for(int i = 0;i<x;i++)
        {
            for(int j=0;j<y;j++)
            {
                canvas.drawRect(i,j,i+1,j+1, PaintObjects.PaintColors.black);
            }
        }
    }

    public boolean testSquare(int vrsta, int kolona){
        try{
            int zauzet = mainGrid[vrsta][kolona];
            if(zauzet == 1) {
                return false;
            }
            return true;
        }catch (Exception any) {
            return false;
        }
    }
}
