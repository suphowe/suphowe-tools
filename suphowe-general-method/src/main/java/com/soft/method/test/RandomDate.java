package com.soft.method.test;

import com.soft.method.io.NioFileUtil;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Random;

/**
 * 随机生成日期
 */
public class RandomDate {

    public static void main(String[] args) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //指定开始日期
        long start = sdf.parse("2021-1-1 00:00:00").getTime();
        //指定结束日期
        long end = sdf.parse("2022-1-1 00:00:00").getTime();

        //调用方法产生随机数
        int i = 0;
        StringBuilder writer = new StringBuilder();
        while(i<=100) {
            long randomDate = nextLong(start, end);

            BigDecimal addHour = makeRandom(3.5f,1f,1);
            BigDecimal addSecond = addHour.multiply(new BigDecimal(1000)).multiply(new BigDecimal(3600));

            long endDate = randomDate + addSecond.longValue();
            //格式化输出日期
            String info = "开始时间:" + sdf.format(randomDate) + "    结束时间:" + sdf.format(endDate) + "    用时:" + addHour+ "    用时:" + addSecond;
            System.out.println(info);
            writer.append(info);
            i++;
        }
        NioFileUtil.mappedByteBufferFileWrite("C:\\test\\test.txt", writer.toString());
    }

    public static long nextLong(long start, long end) {
        Random random = new Random();
        return start + (long) (random.nextDouble() * (end - start + 1));
    }

    public static BigDecimal makeRandom(float max, float min, int scale){
        BigDecimal cha = new BigDecimal(Math.random() * (max-min) + min);
        //保留 scale 位小数，并四舍五入
        return cha.setScale(scale, BigDecimal.ROUND_HALF_UP);
    }
}
