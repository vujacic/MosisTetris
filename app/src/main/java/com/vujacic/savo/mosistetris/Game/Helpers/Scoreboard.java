package com.vujacic.savo.mosistetris.Game.Helpers;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;

import java.util.Observable;

public class Scoreboard {
    public ObservableInt score = new ObservableInt(0);
    public int[] lineMultiplier = {0,1,3,5,8};
    public ObservableBoolean lost = new ObservableBoolean(false);

    public void lineUp(int noLines){
        score.set( score.get()+ lineMultiplier[noLines]*100);
    }

    public void softDrop(){
        score.set(score.get() + 1);
    }

    public void setLost() {
        lost.set(true);
    }

    public boolean testEnd() {
        return lost.get();
    }
}
