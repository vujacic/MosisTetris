package com.vujacic.savo.mosistetris.Game.Helpers;

import com.vujacic.savo.mosistetris.Game.Objects.GameObject;

import java.util.Queue;

public class Das {
    public Queue<Integer> kju;
    public int delay = 7;
    public int counter=0;
    public boolean hold;
    public int translate;
    GameObject gm;
    public Das(Queue<Integer> kju, GameObject gm) {
        this.kju = kju;
        this.gm = gm;
    }

    public void setObject(GameObject gm) {
        this.gm = gm;
    }

    public void handleInput(){
        if(gm == null)
            return;
        if (!kju.isEmpty() || hold) {
            if(kju.isEmpty()){
                if(counter >= delay){
                    gm.translate(0,translate);
//                    counter = 0;
                }
                counter++;
            }else {
                int retQ= kju.remove();
                if(retQ == 0){
                    gm.translate(0, translate);
                    hold = true;
                }
                if(retQ == 1){
                    hold = false;
                    counter = 0;
                }

            }
        }
    }

    public static class LDas extends Das{

        public LDas(Queue<Integer> kju, GameObject gm) {
            super(kju, gm);
            translate = -1;
        }
    }

    public static class RDas extends Das{

        public RDas(Queue<Integer> kju, GameObject gm) {
            super(kju, gm);
            translate = 1;
        }
    }

    public static class RotateDas extends Das{

        public RotateDas(Queue<Integer> kju, GameObject gm) {
            super(kju, gm);
            delay = 9;
        }

        @Override
        public void handleInput(){
            if(gm == null)
                return;
            if (!kju.isEmpty() || hold) {
                if(kju.remove() == 0){
                    gm.rotate();
                }
            }
        }
    }



}


