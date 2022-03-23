package com.soft.method.cache;

import cn.hutool.cache.CacheUtil;
import cn.hutool.cache.impl.TimedCache;

/**
 * 缓存
 * @author suphowe
 */
public class CacheUtils {

    public static final TimedCache<String, String> TIMED_CACHE = CacheUtil.newTimedCache(CacheConstant.CACHE_TIMEOUT);

    /**
     * 放入缓存
     * @param key 缓存键
     * @param value 缓存值
     * @param timeout 过期时间
     */
    public static void setCache(String key, String value, long timeout) {
        TIMED_CACHE.put(key, value, timeout);
    }

    /**
     * 获取缓存,刷新过期时间(默认缓存时间)
     * @param key 缓存键
     * @return 缓存值
     */
    public static String getCache(String key) {
        return TIMED_CACHE.get(key);
    }

    /**
     * 获取缓存
     * @param key 缓存键
     * @param refreshTime 是否刷新缓存超时时间
     * @return 缓存值
     */
    public static String getCache(String key, boolean refreshTime) {
        return TIMED_CACHE.get(key, refreshTime);
    }

    /**
     * 开启定时检查过期
     * @param delay 定时时长(毫秒)
     */
    public static void schedulePrune(long delay) {
        TIMED_CACHE.schedulePrune(delay);
    }

    /**
     * 取消定时检查过期
     */
    public static void cancelPruneSchedule() {
        TIMED_CACHE.cancelPruneSchedule();
    }
}
