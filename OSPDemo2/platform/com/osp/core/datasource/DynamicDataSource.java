package com.osp.core.datasource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 路由数据源
 * @ClassName: DynamicDataSource 
 * @author liudonghe  2017年5月10日 下午3:37:22 
 *
 */
public class DynamicDataSource extends AbstractRoutingDataSource {
    private Logger log = Logger.getLogger(DynamicDataSource.class);

    @Override
    protected Object determineCurrentLookupKey() {
        String dataSouceKey = DynamicDataSourceHolder.getDataSouceKey();
        log.info("选择路由:" + dataSouceKey);
        return dataSouceKey;
    }
}
