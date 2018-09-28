package com.vujacic.savo.mosistetris;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.vujacic.savo.mosistetris.Game.GameSurface;

public class MainActivity extends AppCompatActivity {
    GameSurface gs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setContentView(new GameSurface(this));

        gs=findViewById(R.id.gs);

        Button rotate=findViewById(R.id.rotacija);
        rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler=new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        gs.queue.add(0);

                    }
                },67);
            }
        });

        Button left=findViewById(R.id.left);
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler=new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        gs.queueL.add(0);

                    }
                },67);
            }
        });

        Button right=findViewById(R.id.right);
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Handler handler=new Handler();
                handler.postDelayed(new Runnable(){
                    @Override
                    public void run() {
                        gs.queueR.add(0);

                    }
                },67);
            }
        });

    }
}
