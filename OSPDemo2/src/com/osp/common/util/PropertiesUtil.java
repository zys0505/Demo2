package com.osp.common.util;

import java.io.IOException;
import java.util.Properties;
/**
 * java 操作配置文件
 * @author liudonghe 2017年5月10日 下午4:08:04 
 *
 */
public class PropertiesUtil {
    /**
     * 获取system.properties 文件中的值
     * @param key 文件中的key值
     * @return
     */
    public static String getValue(String key) {
        Properties p = new Properties();
        try {
            p.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("config/application.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p.getProperty(key);
    }
    
    /**
     * 获取system.properties 文件中的值
     * @param key 文件中的key值
     * @return
     * @throws IOException 
     */
    public static String getValue(String key,String path) throws IOException {
        Properties p = new Properties();
        p.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(path));
        return p.getProperty(key);
    }
}
