package com.soft.method.beans;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 返回报文实体
 * @param <T>
 * @author suphowe
 */
@Data
@ToString
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 641354447944900181L;

    /**
     * error_code 状态值：0 极为成功，其他数值代表失败
     */
    private Integer status;

    /**
     * error_msg 错误信息，若status为0时，为success
     */
    private String msg;

    /**
     * content 返回体报文的出参，使用泛型兼容不同的类型
     */
    private T data;

}
