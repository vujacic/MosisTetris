package com.vujacic.savo.mosistetris.Bluetooth.connections;

import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;

public class ByteConverter extends Thread {

    public byte[] myBytes;
    int[][] matrix;
    public ByteConverter(/*byte[] bytes,*/ int[][] matrix){
        //myBytes = bytes;
        this.matrix = matrix;
    }

//    public void run() {
//        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
//             ObjectOutput out = new ObjectOutputStream(bos)) {
//            out.writeObject(matrix);
//            this.myBytes = bos.toByteArray();
//        }catch (IOException ex){
//            ex.toString();
//        }
//        //return null;
//    }

//    public void run(){
//        if(StaticQueue.canSend) {
//            try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
//                 ObjectOutput out = new ObjectOutputStream(bos)) {
//                out.writeObject(matrix);
//                this.myBytes = bos.toByteArray();
//                Log.d("sendmat", Integer.toString(myBytes.length));
//                Client.client.sendData("array", myBytes);
//                synchronized (StaticQueue.lock1){
//                    StaticQueue.canSend =false;
//                }
//                Client.client.sendData("canSend", new byte[]{0,0});
//            } catch (IOException ex) {
//                ex.toString();
//            }
//            //return null;
////        synchronized (StaticQueue.lock1) {
////            if(StaticQueue.canSend){
////                Client.client.sendData("array", myBytes);
////                StaticQueue.canSend = false;
////            }
////        }
//        }
//    }

    public void run() {
        if (StaticQueue.canSend) {
            myBytes = toByte(this.matrix);
            Log.d("sendmat", Integer.toString(myBytes.length));
            Client.client.sendData("array", myBytes);
            synchronized (StaticQueue.lock1) {
                StaticQueue.canSend = false;
            }
            Client.client.sendData("canSend", new byte[]{0, 0});
        }
    }

    public byte[] toByte(int matrix[][]){
        int intArray[] = new int[220];
        int k = 0;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                intArray[k++] = matrix[i][j];
            }
        }
        ByteBuffer byteBuffer = ByteBuffer.allocate(220* 4);
        IntBuffer intBuffer = byteBuffer.asIntBuffer();
        intBuffer.put(intArray);

        byte[] array = byteBuffer.array();
        return array;
    }

    public static byte[] intToByte(int broj){
        return ByteBuffer.allocate(4).putInt(broj).array();
    }


    public static byte[] converttoByte(int matrix[][]) {
//        int intArray[] = new int[220];
//        int k = 0;
//        for (int i = 0; i < matrix.length; i++) {
//            for (int j = 0; j < matrix[i].length; j++) {
//                intArray[k++] = matrix[i][j];
//            }
//        }
//        ByteBuffer byteBuffer = ByteBuffer.allocate(matrix.length* 4);
//        IntBuffer intBuffer = byteBuffer.asIntBuffer();
//        intBuffer.put(intArray);
//
//        byte[] array = byteBuffer.array();
//        return array;
//        intArray.s
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            ObjectOutput out = null;
//            try {
//                out = new ObjectOutputStream(bos);
//                out.writeObject(matrix);
//                out.flush();
//                byte[] yourBytes = bos.toByteArray();
//                bos.close();
//                return yourBytes;
//            } catch (IOException ex) {
//            // ignore close exception
//            }
//            return null;
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream();
             ObjectOutput out = new ObjectOutputStream(bos)) {
            out.writeObject(matrix);
            return bos.toByteArray();
        }catch (IOException ex){
            ex.toString();
        }
        return null;
    }

    public static int[][] convertToMatrix(byte[] yourBytes) {
//        ByteArrayInputStream bis = new ByteArrayInputStream(yourBytes);
//        ObjectInput in = null;
//        try {
//            in = new ObjectInputStream(bis);
//            Object o = in.readObject();
//        } finally {
//            try {
//                if (in != null) {
//                    in.close();
//                }
//            } catch (IOException ex) {
//                // ignore close exception
//            }
//        }

        try (ByteArrayInputStream bis = new ByteArrayInputStream(yourBytes);
            ObjectInput in = new ObjectInputStream(bis)) {
            bis.close();
            in.close();
            return (int[][])in.readObject();
        } catch (Exception ex) {

        }
        return null;
    }


}
