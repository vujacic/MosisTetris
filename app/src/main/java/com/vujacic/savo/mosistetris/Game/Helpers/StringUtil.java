package com.vujacic.savo.mosistetris.Game.Helpers;

public class StringUtil {
    public static String checkEnding(boolean lost) {
        if(lost)
            return "GAME OVER";
        else
            return "";
    }
}
