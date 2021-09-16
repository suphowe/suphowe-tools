package com.soft.dynamic.datasource;

/**
 * 数据源标识管理
 * 该类主要用于绑定当前线程所使用的数据源 id，通过 ThreadLocal 保证同一线程内不可被修改
 * @author suphowe
 */
public class DatasourceConfigContextHolder {
    private static final ThreadLocal<Long> DATASOURCE_HOLDER = ThreadLocal.withInitial(() -> DatasourceHolder.DEFAULT_ID);

    /**
     * 设置默认数据源
     */
    public static void setDefaultDatasource() {
        DATASOURCE_HOLDER.remove();
        setCurrentDatasourceConfig(DatasourceHolder.DEFAULT_ID);
    }

    /**
     * 获取当前数据源配置id
     *
     * @return 数据源配置id
     */
    public static Long getCurrentDatasourceConfig() {
        return DATASOURCE_HOLDER.get();
    }

    /**
     * 设置当前数据源配置id
     *
     * @param id 数据源配置id
     */
    public static void setCurrentDatasourceConfig(Long id) {
        DATASOURCE_HOLDER.set(id);
    }

}
