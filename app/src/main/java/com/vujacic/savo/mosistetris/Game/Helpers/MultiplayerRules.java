package com.vujacic.savo.mosistetris.Game.Helpers;

import android.util.Log;

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
}
