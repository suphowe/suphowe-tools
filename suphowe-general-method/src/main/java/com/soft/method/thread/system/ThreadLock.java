package com.soft.method.thread.system;

/**
 * 线程锁
 * @author suphowe
 */
public class ThreadLock {

    private int defaultInt = 0;

    /**
     * 使用synchronized对整个方法进行加锁
     *
     * @return
     */
    public synchronized int lockFunction(int threadNum) {
        defaultInt++;
        System.out.println(String.format("synchronized lock %s %s %s", "对整个方法进行加锁", defaultInt, threadNum));
        return defaultInt;
    }

    /**
     * 使用synchronized减少锁持有的时间
     */
    public int lockSynchronized(int threadNum){
        synchronized (this) {
            defaultInt++;
        }
        System.out.println(String.format("synchronized lock %s %s %s", "对整个方法进行加锁", defaultInt, threadNum));
        return defaultInt;
    }
}
