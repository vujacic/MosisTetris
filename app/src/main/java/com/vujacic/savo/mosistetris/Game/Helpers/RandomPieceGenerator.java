package com.vujacic.savo.mosistetris.Game.Helpers;

import com.vujacic.savo.mosistetris.Game.Objects.GameObject;
import com.vujacic.savo.mosistetris.Game.Objects.ShapeI;
import com.vujacic.savo.mosistetris.Game.Objects.ShapeJ;
import com.vujacic.savo.mosistetris.Game.Objects.ShapeL;
import com.vujacic.savo.mosistetris.Game.Objects.ShapeO;
import com.vujacic.savo.mosistetris.Game.Objects.ShapeS;
import com.vujacic.savo.mosistetris.Game.Objects.ShapeT;
import com.vujacic.savo.mosistetris.Game.Objects.ShapeZ;
import com.vujacic.savo.mosistetris.Game.TetrisGrid.TetrisGrid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class RandomPieceGenerator {
    public Queue<GameObject> queue;
    public Integer[] nizBrojeva = {1,2,3,4,5,6,7};
    public int minEle = 6;
    public TetrisGrid tg;

    public RandomPieceGenerator(TetrisGrid tg) {
        queue = new LinkedList<GameObject>();
        this.tg = tg;
        generateRandomSeq();
    }

    public void generateRandomSeq() {
        List<Integer> l = Arrays.asList(nizBrojeva);
        Collections.shuffle(l);
        nizBrojeva = (Integer[]) l.toArray();
        for (int broj: nizBrojeva) {
            switch (broj){
                case 1: queue.add(new ShapeI(tg));
                break;
                case 2: queue.add(new ShapeJ(tg));
                break;
                case 3: queue.add(new ShapeL(tg));
                break;
                case 4: queue.add(new ShapeO(tg));
                break;
                case 5: queue.add(new ShapeS(tg));
                break;
                case 6: queue.add(new ShapeT(tg));
                break;
                case 7: queue.add(new ShapeZ(tg));
                break;
            }
        }
    }

    public GameObject take() {
        GameObject g = queue.remove();
        if(queue.size() <= 6){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    generateRandomSeq();
                }
            }).start();
        }
        return g;
    }

}
