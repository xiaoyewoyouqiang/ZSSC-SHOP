package com.qf.config;

import com.qf.constants.ShopConstants;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;

// Map -->DataSoruce
//@Configuration
public class DynamicDataSourceConfig extends AbstractRoutingDataSource {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    @Nullable
    @Override
    protected Object determineCurrentLookupKey() {
        return threadLocal.get();
    }

    public static Integer setDbName(Integer userId) {

        // 1.计算插入到那个数据库
        int num = userId % ShopConstants.ORDER_DB_COUNT; // 0,1

        // 2.设置数据源
        threadLocal.set("db" + (num + 1));

        // 3.计算插入到那个表
        Integer tableIndex = userId / ShopConstants.ORDER_DB_COUNT % ShopConstants.ORDER_TABLE_COUNT;

        // 4.返回
        return ++tableIndex ;
    }
}
