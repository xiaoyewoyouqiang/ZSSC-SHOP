package com.qf.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;

// Map -->DataSoruce
//@Configuration
public class DynamicDataSourceConfig extends AbstractRoutingDataSource {

    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();

    @Nullable
    @Override
    protected Object determineCurrentLookupKey() {
//        return "db2"; // 设置别名
        return threadLocal.get();
    }

    public static void setDbName(Integer userId) {

        // 分库规则是:用户id后四位%数据库的数量
        int num = userId % 2; // 0,1

        // 设置数据源,ID为双数的放到db1，单数的放到db2
        threadLocal.set("db"+(num+1));

    }
}
