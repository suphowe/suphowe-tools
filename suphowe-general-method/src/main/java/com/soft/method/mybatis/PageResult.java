package com.soft.method.mybatis;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * 分页返回结果
 * @author suphowe
 */
@Data
@ToString
public class PageResult implements Serializable {

    private static final long serialVersionUID = -1833969896969421594L;

    /**
     * 当前页码
     */
    private int pageNum;
    /**
     * 每页数量
     */
    private int pageSize;
    /**
     * 当前页的数量
     */
    private long size;
    /**
     * 当前页面第一个元素在数据库中的行号
     */
    private int startRow;
    /**
     * 当前页面最后一个元素在数据库中的行号
     */
    private int endRow;
    /**
     * 页码总数
     */
    private int pages;
    /**
     * 前一页
     */
    private int prePage;
    /**
     * 下一页
     */
    private int nextPage;
    /**
     * 是否为第一页
     */
    private boolean isFirstPage;
    /**
     * 是否为最后一页
     */
    private boolean isLastPage;
    /**
     * 是否有前一页
     */
    private boolean hasPreviousPage;
    /**
     * 是否有下一页
     */
    private boolean hasNextPage;
    /**
     * 导航页码数
     */
    private boolean navigatePages;
    /**
     * 所有导航页号
     */
    private String[] navigatepageNums;
    /**
     * 导航页第一页
     */
    private int navigateFirstPage;
    /**
     * 导航页最后一页
     */
    private int navigateLastPage;
    /**
     * 数据对象
     */
    private List<?> list;

    /**
     * 记录总数
     */
    private long total;
}
