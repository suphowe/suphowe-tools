package com.hadoop.mapreduce.reducer;

import com.hadoop.mapreduce.model.Order;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * mapreduce的表join操作
 * @author suphowe
 */
public class JoinReduce extends Reducer<Text, Order, Order, NullWritable> {

    private final static String ORDER_FLAG = "0";
    private final static String PRODUCT_FLAG = "1";

    /**
     * 解析mapper读取后的文件格式 产品pid orderInfo对象
     * @param key 传入的单词名称，是Mapper中传入的
     * @param values LongWritable 是该单词出现了多少次，这个是mapreduce计算出来的，比如 hello出现了11次
     * @param context 输出单词的名称 ，这里是要输出到文本中的内容
     */
    @Override
    protected void reduce(Text key, Iterable<Order> values, Context context)
            throws IOException, InterruptedException {
        // 这个对象用来存放产品的数据，一个产品所以只有一个对象
        Order product = new Order();
        // 这个list用来存放所有的订单数据，订单肯定是有多个的
        List<Order> list = new ArrayList<>();

        // 循环map输出
        for (Order info : values) {
            // 判断是订单还是产品的map输出
            if (ORDER_FLAG.equals(info.getFlag())) {
                // 订单表数据
                Order tmp = new Order();
                try {
                    tmp = (Order) info.clone();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                list.add(tmp);
            } else {
                // 产品表数据
                try {
                    product = (Order) info.clone();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        // 经过上面的操作，就把订单与产品完全分离出来了，订单在list集合中，产品在单独的一个对象中
        // 然后可以分别综合设置进去
        for (Order tmp : list) {
            tmp.setPname(product.getPname());
            tmp.setCategoryId(product.getCategoryId());
            tmp.setPrice(product.getPrice());
            // 最后输出
            context.write(tmp, NullWritable.get());
        }

    }
}

