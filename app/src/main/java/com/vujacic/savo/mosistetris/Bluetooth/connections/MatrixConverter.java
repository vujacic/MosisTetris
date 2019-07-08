package com.vujacic.savo.mosistetris.Bluetooth.connections;

import android.util.Log;

import com.vujacic.savo.mosistetris.Game.Helpers.GameRules;
import com.vujacic.savo.mosistetris.Game.Helpers.MultiplayerRules;
import com.vujacic.savo.mosistetris.Game.TetrisGrid.TetrisGrid;

import java.io.ByteArrayInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.Queue;

public class MatrixConverter extends Thread {
    byte[] myBytes;
    int[][] matrix;
    TetrisGrid tgrid;
    GameRules rules;
    public MatrixConverter(/*byte[] bytes,*/ int[][] matrix,TetrisGrid tg, GameRules r){
        //myBytes = bytes;
        this.matrix = tg.mainGrid;
        this.tgrid = tg;
        this.rules = r;
    }

//    public void run() {
//        try (ByteArrayInputStream bis = new ByteArrayInputStream(myBytes);
//             ObjectInput in = new ObjectInputStream(bis)) {
//            bis.close();
//            in.close();
//            matrix = (int[][])in.readObject();
//        } catch (Exception ex) {
//
//        }
//        //return null;
//    }

    public void run() {
//        StringByteArr sba = null;//StaticQueue.remove();
        //synchronized (StaticQueue.lock1){
//            while(!StaticQueue.queue.isEmpty()){
//                sba = StaticQueue.remove();
//            }
        //}
//        for(int i=0;i<10;i++){
//            StringByteArr pom = StaticQueue.queue.poll();
//            if(pom == null){
//                break;
//            }
//            sba = pom;
//        }
        StringByteArr sba = StaticQueue.remove();

        if(sba!=null) {
            switch (sba.tag) {
                case "array": {
//                    try (ByteArrayInputStream bis = new ByteArrayInputStream(sba.bytes);
//                         ObjectInput in = new ObjectInputStream(bis)) {
////                        bis.close();
////                        in.close();
//                        Log.d("bajtovi",Integer.toString(sba.bytes.length));
//                        tgrid.mainGrid = (int[][])in.readObject();
//                        Log.d("zmaj", Arrays.deepToString(tgrid.mainGrid));
//                        tgrid.toTetrisGrid();
//
////                        synchronized (StaticQueue.lock1){
////                            StaticQueue.canSend=true;
////                        }
//
//                    } catch (Exception ex) {
//                        ex.printStackTrace();
//                    }
                    //Log.d("bajtovi",Integer.toString(sba.bytes.length));
                        tgrid.mainGrid = toMatrix(sba.bytes);
                        //Log.d("zmaj", Arrays.deepToString(tgrid.mainGrid));
                        tgrid.toTetrisGrid();
                }
                break;
                case "canSend":{
                    synchronized (StaticQueue.lock1){
                        StaticQueue.canSend = true;
                    }
                    break;
                }
                case "time":{
                    rules.scoreboard.setEnemyTime(parseInt(sba.bytes));
                    break;
                }
                case "score":
                    rules.setEnemyScore(parseInt(sba.bytes));
                    break;
                case "init":
                    rules.setRecv();
                    break;
                case "lost":{
                    rules.scoreboard.setEnemyLost();
                    break;
                }
                case "line":{
                    this.rules.setLinesOwed(parseInt(sba.bytes));
                    break;
                }
                case "timeout":{
                    rules.setEnemyScore(parseInt(sba.bytes));
                }
            }

        }
    }

    public int[][] toMatrix(byte[] yourBytes){
        ByteArrayInputStream bis = new ByteArrayInputStream(yourBytes);
        IntBuffer intBuffer = ByteBuffer.wrap(yourBytes)
                .order(ByteOrder.BIG_ENDIAN)
                .asIntBuffer();
        int[] array = new int[intBuffer.remaining()];
        intBuffer.get(array);
        int count = 0;
        int[][] matrix = new int[22][10];
        for (int i = 0; i < 22; i++) {
            for (int j = 0; j < 10; j++) {
                 matrix[i][j] = array[count];
                 count++;
            }
        }
        return  matrix;
    }

    public int parseInt(byte[] yourBytes){
        ByteBuffer buf = ByteBuffer.wrap(yourBytes);
        int res = buf.getInt();
        return res;
    }
}
