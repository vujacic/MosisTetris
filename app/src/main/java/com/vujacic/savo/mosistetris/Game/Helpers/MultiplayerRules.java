package com.vujacic.savo.mosistetris.Game.Helpers;

import android.util.Log;

import com.vujacic.savo.mosistetris.Bluetooth.connections.ByteConverter;
import com.vujacic.savo.mosistetris.Bluetooth.connections.Client;
import com.vujacic.savo.mosistetris.Game.TetrisGrid.TetrisGrid;

public class MultiplayerRules extends GameRules {
    public MultiplayerRules(TetrisGrid tg) {
        super(tg);
    }

    int sent = 0;
    int recv = 0;

    @Override
    public boolean connect(){
        if(sent == 0) {
            Client.client.sendData("init", "POSLATO".getBytes());
            sent = 1;
            //return false;
        }else if(recv == 0){
            return false;
        }
        return true;
    }

    @Override
    public boolean sent(){
        if(sent == 0) {
            Client.client.sendData("init", "POSLATO".getBytes());
            sent = 1;
            return false;
        }
        return true;
    }

    @Override
    public boolean recv(){
        if(recv == 0) {
            return false;
        }
        return true;
    }

    @Override
    public void setRecv(){
        recv = 1;
    }

    @Override
    public void setEnemy(){
        this.scoreboard.setEnemyExists();
    }

    @Override
    public void updateTime(int elapsed){
        this.scoreboard.updateTime(elapsed);
    }

    @Override
    public void setEnemyScore(int eScore){
        this.scoreboard.setEnemyScore(eScore);
    }

    @Override
    public void setLinesOwed(int lines){
        this.scoreboard.linesOwed += lines;
    }

    @Override
    public int getLinesOwed(){
        int x= this.scoreboard.linesOwed;
        this.scoreboard.linesOwed = 0;
        return x;
    }

    @Override
    public boolean testEnd() {
        if(scoreboard.enemyLost.get()) {
            return true;
        }
        if(scoreboard.testEnd()){
            Client.client.sendData("lost",ByteConverter.intToByte(1));
            return true;
        }
        return false;
    }

    @Override
    public void sendTime() {
        Client.client.sendData("time", ByteConverter.intToByte(scoreboard.getMyTime()));
    }

    @Override
    public void sendScore() {
        Client.client.sendData("score", ByteConverter.intToByte(scoreboard.score.get()));
    }

    @Override
    public boolean myTimeout() {
        if(this.scoreboard.getMyTime() == 0)
            return true;
        return false;
    }

    @Override
    public boolean enemyTimeout() {
        if(this.scoreboard.getEnemyTime() == 0)
            return true;
        return false;
    }

    @Override
    public void calculateWinner() {
        if(this.scoreboard.score.get() > this.scoreboard.enemyScore.get()){
            this.scoreboard.setEnemyLost();
        }
        else {
            this.scoreboard.setLost();
        }
    }



}
