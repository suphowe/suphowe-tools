package com.soft.method.mybatis;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * 分页工具类
 * @author suphowe
 */
public class PageUtils {

    /**
     * 分页信息封装
     * @param pageInfo 分页信息
     * @return 分页结果
     */
    public static PageResult getPageResult(PageInfo<?> pageInfo) {
        PageResult pageResult = new PageResult();
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setTotal(pageInfo.getTotal());
        pageResult.setPages(pageInfo.getPages());
        pageResult.setList(pageInfo.getList());
        return pageResult;
    }

    /**
     * 分页信息封装
     * @param list 查询结果
     * @return 分页结果
     */
    public static PageResult getPageResult(List<?> list) {
        //如果直接返回list，得到了分页的数据，如果添加下面步骤，返回pageInfo，则能得到包括list在内的分页信息
        PageInfo<?> pageInfo = new PageInfo<>(list);
        //返回信息
        PageResult pageResult = new PageResult();
        pageResult.setPageNum(pageInfo.getPageNum());
        pageResult.setPageSize(pageInfo.getPageSize());
        pageResult.setSize(pageInfo.getTotal());
        pageResult.setPages(pageInfo.getPages());
        pageResult.setList(pageInfo.getList());
        return pageResult;
    }
}
