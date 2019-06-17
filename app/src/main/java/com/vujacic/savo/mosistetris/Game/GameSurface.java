package com.vujacic.savo.mosistetris.Game;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;
import android.widget.Toast;

import com.vujacic.savo.mosistetris.Game.Objects.GameObject;
import com.vujacic.savo.mosistetris.Game.Objects.ShapeI;
import com.vujacic.savo.mosistetris.Game.TetrisGrid.TetrisGrid;

import java.util.LinkedList;
import java.util.Queue;

public class GameSurface extends SurfaceView implements SurfaceHolder.Callback {
    public GameThread gameThread;
    Bitmap mBitmap;
    public Queue<Integer> queue=new LinkedList<>();
    public Queue<Integer> queueL=new LinkedList<>();
    public Queue<Integer> queueR=new LinkedList<>();

//    private boolean blocked=false;
//    private Handler handler=new Handler();
//    @Override
//    public boolean onTouchEvent(final MotionEvent event) {
//        x+=1;
//        if(event.getAction()==MotionEvent.ACTION_DOWN)
//        {
//            if (!blocked)
//            {
//                blocked = true;
//                handler.postDelayed(new Runnable()
//                {
//                    @Override
//                    public void run()
//                    {
//                        queue.add(MotionEvent.ACTION_DOWN);
//                        blocked = false;
//                    }
//                }, 67);
//            } else
//            {
//                return false;
//            }
//
//
//        }
//
//        return true;
//    }


    Paint mBitmapPaint= new Paint(Paint.DITHER_FLAG);
    Canvas mCanvas;
    Paint mPaint,mPaintRed;
    float x,y;
    int width=0;
    int height=0;
    float dx=0,dy=0;
    float alfa=0;
    boolean bitmapInit=false;
    public boolean canDraw=false;
    long cumulativno=0;
    GameObject gm;
    TetrisGrid tetrisGrid = new TetrisGrid();
    //private ChibiCharacter chibi1;

    public GameSurface(Context context)  {
        super(context);

        // Make Game Surface focusable so it can handle events. .
        this.setFocusable(true);

        // Sét callback.
        this.getHolder().addCallback(this);
    }

    public GameSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        // Make Game Surface focusable so it can handle events. .
        this.setFocusable(true);

        // Sét callback.
        this.getHolder().addCallback(this);
    }

    public void update(long elapsed)  {
        // this.chibi1.update();
        if(!queue.isEmpty())
        {
            queue.remove();
            alfa+=90;
        }
        cumulativno+=elapsed;
        if(cumulativno/1000000>1000) {
            if (y < 20) {
                y += 1;
            }
            else{
                y=-2;
            }
            cumulativno=0;
        }
        if(!queueL.isEmpty())
        {
            queueL.remove();
            x-=1;
        }
        if(!queueR.isEmpty())
        {
            queueR.remove();
            x+=1;
        }
    }



    @Override
    public void draw(Canvas canvas)  {
        super.draw(canvas);
        mCanvas.save();
        mCanvas.drawColor(Color.WHITE);


//        for(int i=0;i<20;i++)
//        {
//            for (int j=0;j<10;j++)
//            {
//                mCanvas.translate(dx,0);
//                mCanvas.drawRect(0,0,width,height,mPaint);
//
//                dx+=width;
//
//            }
//            dx=0;
//            dy+=height;
//            mCanvas.translate(0,dy);
//        }
//            mCanvas.translate(0,y);
//            for (int i = 0; i < 20; i++) {
//                mCanvas.save();
//                for (int j = 0; j < 10; j++) {
//
//                    mCanvas.drawRect(0, 0, width, height, mPaint);
//                    mCanvas.translate(width, 0 );
//                }
//                mCanvas.restore();
//                mCanvas.translate(0, height );
//            }
        float ar=(float)height/(float) width;
        float sheight=868,swidth=434;
        float dx=width-swidth;
        mCanvas.translate(dx/2.f,0);
        mCanvas.scale(swidth/10.f,sheight   /20.f);
//        mCanvas.drawRect(0,0,1,1,mPaint);
//        for(int i = 0;i<10;i++)
//        {
//            for(int j=0;j<20;j++)
//            {
//                mCanvas.drawRect(i,j,i+1,j+1,mPaint);
//            }
//        }
        tetrisGrid.drawGrid(mCanvas);
        mCanvas.translate(5+x,y);
        mCanvas.rotate(alfa);
        gm.draw(mCanvas,mPaintRed);
        //mCanvas.translate(width,height+y);
//        mCanvas.drawRect(0,0,width,height,mPaint);
//        mCanvas.translate(50,50);
//        mCanvas.rotate(alfa);
//      mCanvas.drawRect(0,0,width,height,mPaint);
//      mCanvas.translate(width,height);
//      mCanvas.drawRect(0,0,width,height,mPaint);
        // mCanvas.drawRect(width,height,width+width,height+height,mPaint);
//        mCanvas.translate(x,y);
//        mCanvas.drawRect(20,20,50,50,mPaint);
//
//        for(int i=0;i<100;i++)
//        {
//            mCanvas.translate(30,30);
//            mCanvas.drawRect(20,20,50,50,mPaint);
//        }
        //draw();
        canvas.drawBitmap(mBitmap,0,0,mBitmapPaint);
        mCanvas.restore();
        // this.chibi1.draw(canvas);
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //Bitmap chibiBitmap1 = BitmapFactory.decodeResource(this.getResources(),R.drawable.chibi1);
        // this.chibi1 = new ChibiCharacter(this,chibiBitmap1,100,50);

        //init();
        this.gameThread = new GameThread(this,holder);
//        this.gameThread.setRunning(true);
//        this.gameThread.start();
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        this.width=width;
        this.height=height;
        init();
        this.gameThread.setRunning(true);
        this.gameThread.start();
    }

    // Implements method of SurfaceHolder.Callback
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry= true;
        while(retry) {
            try {
                this.gameThread.setRunning(false);

                // Parent thread must wait until the end of GameThread.
                this.gameThread.join();
            }catch(InterruptedException e)  {
                e.printStackTrace();
            }

            retry= false;
        }
    }

    private void init(){
        mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        mCanvas=new Canvas(mBitmap);

        mPaint=new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLACK);

        mPaintRed=new Paint();
        mPaintRed.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaintRed.setColor(Color.RED);

        gm=new ShapeI();
    }

    void draw(){


        int[] color =new int[12];
        float[] vertices = new float [20];
        short [] indices = new short[14];

        int x=0,y=0;
        for(int i=0;i<16;i+=4)
        {

            vertices[i]=x;
            vertices[i+1]=y;

            vertices[i+2]=x;
            vertices[i+3]=y+50;
            x+=50;
        }
        y=2*50;
        x=0;
        vertices[16]=x;
        vertices[17]=y;
        vertices[18]=x+50;
        vertices[19]=y;

        for(int i =0 ;i<12;i++)
        {
            indices[i]=(short)i;
            color[i]=0xffff0000;

        }
        indices[8]=7;
        indices[9]=1;
        indices[10]=1;
        indices[11]=8;
        indices[12]=3;
        indices[13]=9;
        mCanvas.translate(-50,-50);
        mCanvas.drawVertices(Canvas.VertexMode.TRIANGLE_STRIP,20,vertices,0,
                null,0,null,0,indices,0,14,mPaint);
        mCanvas.translate(50,50);



    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        canDraw=true;
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    //    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
////        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
////        width=widthMeasureSpec;
////        height=heightMeasureSpec;
////        if(!bitmapInit) {
////            init();
////            bitmapInit=true;
////        }
//    }
//
//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
////        width=w;
////        height=h;
////        if(!bitmapInit) {
////            init();
////            bitmapInit=true;
//////            this.gameThread.setRunning(true);
//////            this.gameThread.start();
////        }
//    }
}
