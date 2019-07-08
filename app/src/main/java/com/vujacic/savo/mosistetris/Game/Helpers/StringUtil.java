package com.vujacic.savo.mosistetris.Game.Helpers;

public class StringUtil {
    public static String checkEnding(boolean lost,boolean enemy) {
        if(lost)
            return "GAME OVER";
        else if(enemy)
            return "VICTORY";
            return "";
    }

    public static String checkEnemy(boolean exists) {
        if(exists)
            return "ENEMY:";
        else
            return "";
    }

    public static String getTime(int time) {
        int min = time/60;
        int sec = time%60;
        return String.format("%02d : %02d",min,sec);
    }

    public static String empty(){
        return "";
    }


}
