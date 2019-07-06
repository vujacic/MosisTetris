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
    public Scoreboard scoreboard;

    public GameRules(TetrisGrid tg) {
        this.tg = tg;
        speed = defaultSpeed;
        scoreboard = new Scoreboard();
    }

    public void startLocking() {
        this.lockingIn = true;
    }

    public void stopLocking() {
        this.lockingIn = false;
        lockCount = 0;
    }

    public boolean locking(GameObject gm) {
        if(lockingIn) {
            lockCount += 1;
            gm.oldNewPaint(false);
            if (lockCount == lockinDelay) {
                gm.oldNewPaint(true);
                gm.drawGridLocation();
                int cleardLines=tg.clearLines();
                scoreboard.lineUp(cleardLines);
                this.stopLocking();
                if(tg.lost()){
                    scoreboard.setLost();
                }
                return true;
            }
        } else{
            gm.oldNewPaint(true);
        }
        return false;
    }

    public void resetSpeed() {
        this.speed = defaultSpeed;

    }

    public void softDropPts() {
        this.scoreboard.softDrop();
    }
}
