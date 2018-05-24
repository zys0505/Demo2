package com.osp.core.datasource;

/**
 * 线程安全的动态路由辅助类
 * @author liudonghe  2017年5月10日 下午3:37:49 
 *
 */
public class DynamicDataSourceHolder {
    //线程安全使用
    private static ThreadLocal<String> local = new ThreadLocal<String>();

    /**
     * 
     * @Title: getDataSouceKey  
     * @Description: 得到datasource的key
     * @return
     */
    public static String getDataSouceKey() {
        return local.get() == null ? "master" : local.get();
    }

    /**
     * 
     * @Title: set  
     * @Description: 设置当前事务datasource的key
     * @param key key值
     */
    public static void set(String key) {
        local.set(key);
    }
}