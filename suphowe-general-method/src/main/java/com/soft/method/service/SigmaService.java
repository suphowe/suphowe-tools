package com.soft.method.service;

import com.soft.method.dao.SigmaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * 6sigma 数据生成
 * @author suphowe
 */
@Service
public class SigmaService {

    private static final Logger logger = LoggerFactory.getLogger(SigmaService.class);
//    /** 时间范围区间*/
//    private final String BEGIN_DATE = "2021-1-1 00:00:00";
//    private final String END_DATE = "2022-1-1 00:00:00";
//    /** 浮动小时区间 */
//    private final float max = 4.0f;
//    private final float min = 1.9f;
//    /** 当天浮动时间 */
//    private final String upTime = "07:30:00";
//    private final String lowTime = "11:30:00";


    @Autowired
    SigmaMapper sigmaMapper;

    public void insertSigmaData(int rows, String beginDate, String endDate, float max, float min, String upTime, String lowTime) {
        try {
            int deleteRows = sigmaMapper.deleteSigmaTable();
            logger.info("删除样本数量:{}", deleteRows);
            logger.info("新增样本数量:{}", rows);


            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //指定开始日期
            long start = sdf.parse(beginDate).getTime();
            //指定结束日期
            long end = sdf.parse(endDate).getTime();

            //调用方法产生随机数
            int i = 0;
            while(i<rows) {
                long randomDate = nextLong(start, end, upTime, lowTime);
                if (randomDate == 0) {
                    return;
                }

                BigDecimal addHour = makeRandom(1, max, min);
                BigDecimal addSecond = addHour.multiply(new BigDecimal(1000)).multiply(new BigDecimal(3600));

                long endFloat = randomDate + addSecond.longValue();
                //格式化输出日期
                String beginTime = sdf.format(randomDate);
                String endTime = sdf.format(endFloat);
                String addHours = addHour.toString();
                String addSeconds = String.valueOf(addSecond);
                sigmaMapper.insertSigma(beginTime, endTime, addHours, addSeconds);
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取随机时间
     * @param start 开始
     * @param end 结束
     * @return 随机时间
     */
    private long nextLong(long start, long end, String upTime, String lowTime) {
        try {
            String randomDate = nextDate(start, end) + nextSecond(upTime, lowTime);
            SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            return sdf.parse(randomDate).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    private String nextDate(long start, long end) {
        Random random = new Random();
        long date = start + (long) (random.nextDouble() * (end - start + 1));
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String beginDate = sdf.format(date);
        return beginDate.substring(0, 10);
    }

    private String nextSecond(String upTime, String lowTime) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            long start = sdf.parse("2000-1-1 " + upTime).getTime();
            //指定结束日期
            long end = sdf.parse("2000-1-1 " + lowTime).getTime();

            Random random = new Random();
            long date = start + (long) (random.nextDouble() * (end - start + 1));
            String beginDate = sdf.format(date);
            return beginDate.substring(10, beginDate.length());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private BigDecimal makeRandom(int scale, float max, float min){
        BigDecimal cha = new BigDecimal(Math.random() * (max-min) + min);
        //保留 scale 位小数，并四舍五入
        return cha.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }

}
