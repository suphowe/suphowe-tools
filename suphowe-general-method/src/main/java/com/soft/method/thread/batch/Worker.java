package com.soft.method.thread.batch;


import com.soft.method.beans.Task;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Worker 负责处理子任务
 *
 * @author suphowe
 */
@Slf4j
public class Worker implements Runnable {

    private ConcurrentLinkedQueue<Task> workQueue;

    private ConcurrentHashMap<String, Object> resultMap;

    public void setWorkerQueue(ConcurrentLinkedQueue<Task> workQueue) {
        this.workQueue = workQueue;
    }

    public void setResultMap(ConcurrentHashMap<String, Object> resultMap) {
        this.resultMap = resultMap;
    }

    @Override
    public void run() {
        //处理一个个任务
        while (true) {
            //从队列中取出一个元素
            Task input = this.workQueue.poll();
            if (null == input){
                break;
            }
            //真正的去做业务处理
            Object outPut = handle(input);
            //存放任务的结果
            log.info("Thread--id:{}, outPut:{}", input.getId(), outPut);
            this.resultMap.put(String.valueOf(input.getId()), outPut);
        }
    }

    /**
     *单独抽出来 给子类重写，更加灵活
     */
    public Object handle(Task input) {
        return null;
    }

}
