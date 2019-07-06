package com.vujacic.savo.mosistetris.Game.Helpers;

import com.vujacic.savo.mosistetris.Game.Objects.GameObject;
import com.vujacic.savo.mosistetris.Game.TetrisGrid.TetrisGrid;

public class GameRules {
    public long defaultSpeed = 250;
    public long speed;
    TetrisGrid tg;
    int lockinDelay = 15;
    public boolean lockingIn = false;
    int lockCount = 0;

    public GameRules(TetrisGrid tg) {
        this.tg = tg;
        speed = defaultSpeed;
    }

    public void startLocking() {
        this.lockingIn = true;
    }

    public void stopLocking() {
        this.lockingIn = false;
        lockCount = 0;
    }

    public boolean locking() {
        if(lockingIn) {
            lockCount += 1;
            if (lockCount == 30) {
                tg.clearLines();
                this.stopLocking();
                return true;
            }
        }
        return false;
    }

    public void resetSpeed() {
        this.speed = defaultSpeed;

    }
}
