package com.soft.security.common;

import lombok.Data;

/**
 * 分页请求参数
 * @author suphowe
 */
@Data
public class PageCondition {

    /**
     * 当前页码
     */
    private Integer currentPage;

    /**
     * 每页条数
     */
    private Integer pageSize;

}
