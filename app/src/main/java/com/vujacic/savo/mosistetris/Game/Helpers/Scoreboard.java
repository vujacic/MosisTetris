package com.vujacic.savo.mosistetris.Game.Helpers;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableInt;

import java.util.Observable;

public class Scoreboard {
    public ObservableInt score = new ObservableInt(0);
    public int[] lineMultiplier = {0,1,3,5,8};
    public ObservableBoolean lost = new ObservableBoolean(false);
    public ObservableInt enemyScore = new ObservableInt(0);
    public ObservableBoolean enemyExists = new ObservableBoolean(false);
    public ObservableInt time = new ObservableInt(120);
    public ObservableBoolean enemyLost = new ObservableBoolean(false);
    public volatile int linesOwed = 0;
    public ObservableInt enemyTime = new ObservableInt(120);

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

    public void setEnemyScore(int score) {
        enemyScore.set(score);
    }

    public void setEnemyExists() {
        enemyExists.set(true);
    }

    public void setEnemyLost() {enemyLost.set(true);}

    public void updateTime(int elapsed) {time.set(time.get() - elapsed);}

    public int getMyTime() {return time.get();}

    public int getEnemyTime() {return enemyTime.get();}

    public void setEnemyTime(int time) { enemyTime.set(time);}
}
