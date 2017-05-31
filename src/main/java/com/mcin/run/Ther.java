package com.mcin.run;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Mcin on 2017/5/16.
 */
public class Ther  {
    public Threads listener;
    int i = 1;
    private ExecutorService pool = Executors.newFixedThreadPool(i);

    public void runPool (final Threads listener){
        pool.execute(new Runnable() {
            public void run() {
                listener.setThread();
            }
        });
    }

    public static void main(String[] args) {
       new Ther().runPool(new Threads() {
           public void setThread() {

           }
       });
    }

}

interface Threads {
    void setThread();
}
