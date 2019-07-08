package com.vujacic.savo.mosistetris.Bluetooth.connections;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.vujacic.savo.mosistetris.Game.GameSurface;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class StaticQueue {
    public static Queue<StringByteArr> queue = new LinkedList<>();
    public static Object lock1 = new Object();
    public static boolean canSend = true;
    public static Stack<StringByteArr> stack = new Stack<>();

    public static void add(StringByteArr e){
        //synchronized (lock1) {
            if (queue != null)
                queue.add(e);
        //}
    }

    public static  StringByteArr remove() {
        StringByteArr s = null;
        //synchronized (lock1) {
            if (queue != null && !queue.isEmpty()) {
                s = queue.remove();
            }
        //}


//        switch (s) {
//            case "init":
//                gs.rules.increaseRun();
//        }
        return s;
    }

    public static StringByteArr pop(){
        StringByteArr s = null;
        if (stack != null && !stack.isEmpty()) {
            s = stack.pop();
        }

        return s;
    }
}
