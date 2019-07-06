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
        public static Paint grey;
        public static Paint ljubicica;

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
            black.setStrokeWidth(0.1f);
//            black.setColor(Color.parseColor("#FFFFFF"));
            white.setColor(Color.parseColor("#242424"));

            grey = new Paint(lblue);
//            grey.setColor(Color.parseColor("#646a75"));
            grey.setColor(Color.parseColor("#C1C1C1"));
            //grey.setAlpha(100);

            ljubicica = new Paint(lblue);
            ljubicica.setColor(Color.parseColor("#3d3038"));
            ljubicica.setAlpha(100);
        }
    }
}
