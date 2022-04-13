package com.soft.elasticsearch.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 统一的返回对象VO
 *
 * @author 154594742@qq.com
 * @date: 2021/2/22 10:02:00
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseVo<T> implements Serializable {
    private static final long serialVersionUID = 7748070653645596712L;
    /**
     * 状态码
     */
    @ApiModelProperty(value = "状态码")
    private String code;

    /**
     * 状态码对应描述信息
     */
    @ApiModelProperty(value = "状态码对应描述信息")
    private String msg;

    /**
     * 数据
     */
    @ApiModelProperty(value = "数据")
    private T data;
}