package com.vujacic.savo.mosistetris;

import android.content.pm.ActivityInfo;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.vujacic.savo.mosistetris.Game.GameSurface;
import com.vujacic.savo.mosistetris.Game.GameThread;

import java.util.Queue;

public class MainActivity extends AppCompatActivity {
    GameSurface gs;
    GameThread gt;
    int delay;
    boolean doubleBackToExitPressedOnce = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setContentView(new GameSurface(this));
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        gs=findViewById(R.id.gs);
        gt=gs.gameThread;

        ImageButton rotate=findViewById(R.id.rotacija);
        buttonListeners(rotate,gs.queue);
        ImageButton down=findViewById(R.id.down);
        buttonListeners(down,gs.queueD);
//        rotate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable(){
//                    @Override
//                    public void run() {
//                        gs.queue.add(0);
//
//                    }
//                },67);
//            }
//        });

        ImageButton left=findViewById(R.id.left);
        buttonListeners(left,gs.queueL);
//        left.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable(){
//                    @Override
//                    public void run() {
//                        gs.queueL.add(0);
//
//                    }
//                },67);
//            }
//        });

        ImageButton right=findViewById(R.id.right);
        buttonListeners(right,gs.queueR);
//        right.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable(){
//                    @Override
//                    public void run() {
//                        gs.queueR.add(0);
//
//                    }
//                },67);
//            }
//        });
        Toast.makeText(this,"activ created",Toast.LENGTH_SHORT).show();
    }
    private void buttonListeners(ImageButton b,final Queue<Integer> q)
    {
//        b.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Handler handler=new Handler();
//                handler.postDelayed(new Runnable(){
//                    @Override
//                    public void run() {
//                        q.add(0);
//
//                    }
//                },67);
//            }
//        });
        b.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Handler handler=new Handler();
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    handler.postDelayed(new Runnable(){
                        @Override
                        public void run() {
                            q.add(0);

                        }
                    },67);
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    handler.postDelayed(new Runnable(){
                        @Override
                        public void run() {
                            q.add(1);

                        }
                    },67);
                }

//                handler.postDelayed(new Runnable(){
//                    @Override
//                    public void run() {
//                        if(event.getAction() == MotionEvent.ACTION_DOWN) {
//                            q.add(1);
//                        }
//                        else if (event.getAction() == MotionEvent.ACTION_UP) {
//                            q.add(2);
//                        }
//
//                    }
//                },67);
                return false;
            }
        });
    }





    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(this,"activ start",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this,"activ resumed",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this,"activ restart",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this,"activ paused",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this,"activ stopped",Toast.LENGTH_SHORT).show();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"activ destroyed",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }
}
