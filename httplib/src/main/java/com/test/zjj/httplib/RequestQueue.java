package com.test.zjj.httplib;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by Administrator on 2016/6/22.
 */
public class RequestQueue {
    private BlockingQueue<Request> queue = new LinkedBlockingQueue<>();
    private final int MAX_THREADS = 3;
    private NetworkDispatcher[] workers = new NetworkDispatcher[MAX_THREADS];

    public RequestQueue(){
        initDispatcher();
    }

    private void initDispatcher() {
        for (int i = 0; i < MAX_THREADS; i++) {
            workers[i] = new NetworkDispatcher(queue);
            workers[i].start();
        }
    }

    public void stopWork(){
        for (int i = 0; i < MAX_THREADS; i++) {
            workers[i].interrupt();
            workers[i].setFlag(false);
        }
    }

    public synchronized void addRequest(Request request){
        queue.add(request);
    }
}
