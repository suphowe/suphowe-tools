package com.netty.server.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.msgpack.annotation.Message;

import java.io.Serializable;

/**
 * 序列化netty server消息
 * @author suphowe
 */
@Message
@Data
@NoArgsConstructor
@AllArgsConstructor
public class NettyServerMessageEntity implements Serializable {

    private static final long serialVersionUID = 1579258906605843062L;

    /**
     * 接收人
     */
    private String acceptName;

    /**
     * 内容
     */
    private String content;

    /**
     * 方式
     * client -> webClient
     * client -> client
     */
    private String method;
}
