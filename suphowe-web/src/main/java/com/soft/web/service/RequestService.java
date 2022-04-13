package com.soft.web.service;

import com.soft.web.request.RequestDedupUtil;
import com.soft.web.util.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 请求服务
 * @author suphowe
 */
@Service
public class RequestService {

    private static final Logger logger = LoggerFactory.getLogger(RequestService.class);

    @Autowired
    RedisUtil redisUtil;

    /**
     * 去重判断
     * @param requestData 请求数据
     * @return md5请求数据
     */
    public Object dedup (String requestData) {
        String result1 = RequestDedupUtil.dedupParam(requestData);
        String result2 = RequestDedupUtil.dedupParam(requestData, "requestTime");
        Object redisCache = redisUtil.get(result2);
        if (redisCache != null) {
            logger.info("查询条件相同,从redis缓存中获取:\r\n{}", redisCache);
            return redisCache;
        }
        redisUtil.set(result2, requestData, 60);
        logger.info("result:\n\r{}", requestData);
        return requestData;
    }

}
