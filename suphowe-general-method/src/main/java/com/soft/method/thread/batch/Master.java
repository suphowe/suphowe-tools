package com.soft.method.thread.batch;


import com.soft.method.beans.Task;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Master 接收和分配任务
 *
 * @author suphowe
 */
public class Master {

    /**
     * 1:应该有一个承载任务的集合
     */
    private final ConcurrentLinkedQueue<Task> workQueue = new ConcurrentLinkedQueue<Task>();

    /**
     * 2:使用hashmap去承载所有的worker对象 ThreadName------Worker
     */
    private final HashMap<String, Thread> workers = new HashMap<>();

    /**
     * 3:使用一个容器承载每一个worker并行执行任务的结果集
     */
    private final ConcurrentHashMap<String, Object> resultMap = new ConcurrentHashMap<String, Object>();

    /**
     * 4:构造方法
     * @param worker 整体
     * @param workerCount 子节点个数
     */
    public Master(Worker worker, int workerCount) {
        //在worker中添加两个引用  workQueue用于任务的领取  resultMap用于任务的提交
        worker.setWorkerQueue(this.workQueue);
        worker.setResultMap(this.resultMap);

        for (int i = 0; i < workerCount; i++) {
            workers.put("子节点 " + i, new Thread(worker));
        }
    }

    /**
     * 5:提交方法,将任务添加到队列
     * @param task 任务
     */
    public void submit(Task task) {
        workQueue.add(task);
    }

    /**
     * 6:需要有一个执行的方法(启动应用程序 让所有的worker工作)
     */
    public void execute() {
        //遍历workers 分别去执行每一个worker
        for (Map.Entry<String, Thread> worker : workers.entrySet()) {
            worker.getValue().start();
        }
    }

    /**
     * 判断所有的worker是否执行完毕
     */
    public boolean isCompleted() {
        //遍历所有的worker 只要有一个没有停止 那么就代表没有结束
        for (Map.Entry<String, Thread> me : workers.entrySet()) {
            if (me.getValue().getState() != Thread.State.TERMINATED) {
                return false;
            }
        }
        return true;
    }

    /**
     * 计算最终的结果集,将所有的随机数相加之和
     */
    public int getResult() {
        int result = 0;
        for (Map.Entry<String, Object> method : resultMap.entrySet()) {
            result += (Integer) method.getValue();
        }
        return result;
    }
}