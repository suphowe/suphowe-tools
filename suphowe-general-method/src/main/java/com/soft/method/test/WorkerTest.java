package com.soft.method.test;


import com.soft.method.thread.batch.Master;
import com.soft.method.beans.Task;
import com.soft.method.thread.batch.impl.BusinessWorker;

import java.util.Random;

/**
 * 批量执行任务测试
 * @author suphowe
 */
public class WorkerTest {

    public static void main(String[] args) {
        System.out.println("我的机器可用的Processor数量:" + Runtime.getRuntime().availableProcessors());
        // 使用worker子类实现具体的业务,更加灵活
        Master master = new Master(new BusinessWorker(), Runtime.getRuntime().availableProcessors());
        Random r = new Random();
        //提交100个任务
        for (int i = 0; i <= 100; i++) {
            Task t = new Task();
            t.setId(i);
            t.setName("任务 " + i);
            t.setPrice(r.nextInt(1000));
            master.submit(t);
        }

        //执行所有的worker
        master.execute();

        //记录时间
        long start = System.currentTimeMillis();


        while (true) {
            //全部的worker执行结束的时候去计算最后的结果
            if (master.isCompleted()) {
                //计算耗时
                long end = System.currentTimeMillis() - start;
                //计算结果集，计算所有随机数之和
                int result = master.getResult();
                System.out.println("执行最终结果: " + result + " 执行耗时 " + end);
                break;
            }
        }

    }

}
