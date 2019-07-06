package com.vujacic.savo.mosistetris.Game.TetrisGrid;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import com.vujacic.savo.mosistetris.Game.Helpers.PaintObjects;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class TetrisGrid {
    public TetrisGridObject[] grid;
    public int x,y,yExt;
    public int[][] mainGrid;
    public int[] lastState = new int[8];
    public float[] lineArray = new float [128];
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
        generateLines();
    }

    public TetrisGrid(int sizeX, int sizeY) {
        x=sizeX;
        y=sizeY;
        int gs=sizeX*(sizeY+2);
        grid=new TetrisGridObject[gs];
    }

    public void drawGrid(Canvas canvas) {
        for(int i = 0;i<y;i++)
        {
            for(int j=0;j<x;j++)
            {
                canvas.drawRect(j,i,j+1,i+1,grid[(i+2)*x + j].paintObject);
                //canvas.drawRect(i+0.1f,j+0.1f,i+0.9f,j+0.9f,grid[i*y + (j+2)].paintObject);
            }
        }
//        for(int i = 0;i<x;i++)
//        {
//            for(int j=0;j<y;j++)
//            {
//                canvas.drawRect(i,j,i+1,j+1, PaintObjects.PaintColors.black);
//            }
//        }
        canvas.drawLines(lineArray, PaintObjects.PaintColors.black);
        //canvas.drawLines(new float[]{0,0,10,20,10,0,0,20},PaintObjects.PaintColors.black);
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

    public boolean testAll(int position[]){
        this.posCleanRestore(0);
        for(int i = 0; i<8 ; i+=2){
            if(testSquare(position[i],position[i+1]) == false){
                this.posCleanRestore(1);
                return false;
            }
        }
        this.posCleanRestore(1);
        return true;
    }

    public void setOne(int vrsta, int kolona, int one, Paint p) {
        this.mainGrid[vrsta][kolona] = one;
        this.grid[vrsta*x + kolona].paintObject = p;
    }

    public void setAll(int position[], Paint p) {
        //prvo ocisti
        for(int i = 0; i<8 ; i+=2){
            setOne(lastState[i],lastState[i+1],0, PaintObjects.PaintColors.grey);
        }
        lastState = position.clone();

        for(int i = 0; i<8 ; i+=2){
            setOne(position[i],position[i+1],1, p);
        }
    }

    public void generateLines() {
        int count = 0;
        for(int i = 0 ;i<=x;i++)
        {
            lineArray[count] = i;
            lineArray[count+1] = 0;
            lineArray[count+2] = i;
            lineArray[count+3] = y;
            count+=4;
        }

        for(int i = 0 ;i<=y;i++)
        {
            lineArray[count] = 0;
            lineArray[count+1] = i;
            lineArray[count+2] = x;
            lineArray[count+3] = i;
            count+=4;
        }
    }

    public void posCleanRestore(int cleanRestore) {
        for(int i = 0; i<8 ; i+=2){
            this.mainGrid[lastState[i]][lastState[i+1]] = cleanRestore;
        }
    }

    public int clearLines() {
        List<Integer> exes = new ArrayList<>();
        exes.add(lastState[0]);exes.add(lastState[2]);exes.add(lastState[4]);exes.add(lastState[6]);
        Integer[] unique = new HashSet<Integer>(exes).toArray(new Integer[] {});
        Arrays.sort(unique);
        int linesCleared=0;
        for(int i = 0; i<unique.length;i++){
            boolean isFilled = true;
            for(int j = 0; j<10 ;j++) {
                if(mainGrid[unique[i]][j] == 0) {
                    isFilled = false;
                    break;
                }
            }
            if(isFilled){
                linesCleared++;
                int[] newLine = new int[10];
                //List<int[]>lista= Arrays.asList(mainGrid);
                //lista.remove(i);
                //lista.add(0,newLine);
                //mainGrid = (int[][])lista.toArray();
                for(int j = unique[i]; j>=1; j--){
                 mainGrid[j]=mainGrid[j-1];
                }
                mainGrid[0]=new int[10];

                //;List<TetrisGridObject> lista1 = Arrays.asList(grid);
//                for(int j = 0; j<9 ;j++) {
//                    lista1.remove(i*10+j);
//                    lista1.add(0,new TetrisGridObject());
//                }
//                grid = (TetrisGridObject[])lista1.toArray();
                for(int j = unique[i]*10+9; j >= 20; j--){
                    grid[j]=grid[j-10];
                }
                for(int j = 0; j<20; j++){
                    grid[j]= new TetrisGridObject();
                }

            }
        }
        lastState = new int[8];
        return linesCleared;
    }

    public boolean lost() {
        boolean lost = false;
        outerloop:
        for(int i =0 ;i<2;i++) {
            for (int j=0; j<10;j++){
                if(mainGrid[i][j] != 0) {
                    lost =true;
                    break outerloop;
                }
            }
        }
        return lost;
    }

}
