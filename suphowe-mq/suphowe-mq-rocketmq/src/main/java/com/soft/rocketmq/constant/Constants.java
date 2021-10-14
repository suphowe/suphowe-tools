package com.soft.rocketmq.constant;

import com.soft.rocketmq.utils.SysUtils;

/**
 * 常量设置
 * @author suphowe
 */
public class Constants {

    // consumer

    public static final String CONSUMER_ISONOFF = SysUtils.getProperties("properties/consumer", "rocketmq.consumer.isOnOff");

    public static final String CONSUMER_GROUPNAME = SysUtils.getProperties("properties/consumer", "rocketmq.consumer.groupName");

    public static final String CONSUMER_NAMESERVERS = SysUtils.getProperties("properties/consumer", "rocketmq.consumer.nameservers");

    public static final String CONSUMER_MAXMESSAGESIZE = SysUtils.getProperties("properties/consumer", "rocketmq.consumer.maxMessageSize");

    public static final String CONSUMER_SENDMSGTIMEOUT = SysUtils.getProperties("properties/consumer", "rocketmq.consumer.sendMsgTimeOut");

    public static final String CONSUMER_RETRYTIMESWHENSENDFAILED = SysUtils.getProperties("properties/consumer", "rocketmq.consumer.retryTimesWhenSendFailed");

    public static final String CONSUMER_TOPIC = SysUtils.getProperties("properties/consumer", "rocketmq.consumer.topic");


    // producer

    public static final String PRODUCER_ISONOFF = SysUtils.getProperties("properties/producer", "rocketmq.producer.isOnOff");

    public static final String PRODUCER_GROUPNAME = SysUtils.getProperties("properties/producer", "rocketmq.producer.groupName");

    public static final String PRODUCER_NAMESERVERS = SysUtils.getProperties("properties/producer", "rocketmq.producer.nameservers");

    public static final String PRODUCER_MAXMESSAGESIZE = SysUtils.getProperties("properties/producer", "rocketmq.producer.maxMessageSize");

    public static final String PRODUCER_SENDMSGTIMEOUT = SysUtils.getProperties("properties/producer", "rocketmq.producer.sendMsgTimeOut");

    public static final String PRODUCER_RETRYTIMESWHENSENDFAILED = SysUtils.getProperties("properties/producer", "rocketmq.producer.retryTimesWhenSendFailed");

    public static final String PRODUCER_TOPIC = SysUtils.getProperties("properties/producer", "rocketmq.producer.topic");

    public static final String PRODUCER_TAG = SysUtils.getProperties("properties/producer", "rocketmq.producer.tag");
}
