package com.soft.method.cache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ConcurrentHashMap 缓存使用
 * @author suphowe
 */
public class CacheMapUtil {

    /**
     * 预缓存信息
     */
    private static final ConcurrentHashMap<String, Object> CACHE_MAP = new ConcurrentHashMap<>();

    /**
     * 每个缓存生效时间12小时
     */
    public static final long CACHE_HOLD_TIME_12H = 12 * 60 * 60 * 1000L;

    /**
     * 每个缓存生效时间24小时
     */
    public static final long CACHE_HOLD_TIME_24H = 24 * 60 * 60 * 1000L;

    /**
     * 存放一个缓存对象，默认保存时间12小时
     *
     * @param cacheName 缓存名称
     * @param obj 缓存值
     */
    public static void put(String cacheName, Object obj) {
        put(cacheName, obj, CACHE_HOLD_TIME_12H);
    }

    /**
     * 存放一个缓存对象，保存时间为holdTime
     *
     * @param cacheName 缓存名称
     * @param obj 缓存值
     * @param holdTime 失效时间
     */
    public static void put(String cacheName, Object obj, long holdTime) {
        CACHE_MAP.put(cacheName, obj);
        //缓存失效时间
        CACHE_MAP.put(cacheName + "_HoldTime", System.currentTimeMillis() + holdTime);
    }

    /**
     * 取出一个缓存对象
     * @param cacheName 缓存名称
     * @return 对象
     */
    public static Object get(String cacheName) {
        if (checkCacheName(cacheName)) {
            return CACHE_MAP.get(cacheName);
        }
        return null;
    }

    /**
     * 删除所有缓存
     */
    public static void removeAll() {
        CACHE_MAP.clear();
    }

    /**
     * 删除某个缓存
     * @param cacheName 缓存名称
     */
    public static void remove(String cacheName) {
        CACHE_MAP.remove(cacheName);
        CACHE_MAP.remove(cacheName + "_HoldTime");
    }

    /**
     * 检查缓存对象是否存在，
     * 若不存在，则返回false
     * 若存在，检查其是否已过有效期，如果已经过了则删除该缓存并返回false
     *
     * @param cacheName 缓存名称
     * @return 检查结果
     */
    public static boolean checkCacheName(String cacheName) {
        Long cacheHoldTime = (Long) CACHE_MAP.get(cacheName + "_HoldTime");
        if (cacheHoldTime == null || cacheHoldTime == 0L) {
            return false;
        }
        if (cacheHoldTime < System.currentTimeMillis()) {
            remove(cacheName);
            return false;
        }
        return true;
    }
}
