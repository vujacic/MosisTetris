package com.vujacic.savo.mosistetris.Game.Helpers;

import android.graphics.Color;
import android.graphics.Paint;

public class PaintObjects {

    public static class PaintColors{
        public static Paint lblue;
        public static Paint yellow;
        public static Paint purple;
        public static Paint green;
        public static Paint red;
        public static Paint blue;
        public static Paint orange;
        public static Paint white;
        public static Paint black;

        static {
            lblue = new Paint();
            lblue.setStyle(Paint.Style.FILL);
            lblue.setColor(Color.parseColor("#31C7EF"));

            yellow = new Paint(lblue);
            yellow.setColor(Color.parseColor("#F7D308"));

            purple = new Paint(lblue);
            purple.setColor(Color.parseColor("#AD4D9C"));

            green = new Paint(lblue);
            green.setColor(Color.parseColor("#42B642"));

            red = new Paint(lblue);
            red.setColor(Color.parseColor("#EF2029"));

            blue = new Paint(lblue);
            blue.setColor(Color.parseColor("#5A65AD"));

            orange = new Paint(lblue);
            orange.setColor(Color.parseColor("#EF7921"));

            white = new Paint(lblue);
            white.setColor(Color.parseColor("#FFFFFF"));

            black = new Paint();
            black.setStyle(Paint.Style.STROKE);
            black.setColor(Color.parseColor("#000000"));
        }
    }
}
