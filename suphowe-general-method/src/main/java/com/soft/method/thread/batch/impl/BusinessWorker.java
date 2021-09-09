package com.soft.method.thread.batch.impl;

import com.soft.method.beans.Task;
import com.soft.method.thread.batch.Worker;
import org.springframework.stereotype.Service;

/**
 * 处理业务,业务逻辑实现
 * @author suphowe
 */
@Service
public class BusinessWorker extends Worker {

    @Override
    public Object handle(Task input) {
        Object outPut = null;
        if (null == input) {
            return null;
        }
        try {
            //表示处理task任务的耗时，可能是数据的加工，也可能是操作数据库
            Thread.sleep(50);
            //模拟真实的业务场景
            outPut = input.getPrice();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return outPut;
    }
}
