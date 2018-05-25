package com.osp.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

import org.apache.log4j.Logger;

/**
 * httpClient简单实现
 * @author liudonghe  2017年5月11日 上午11:37:59 
 *
 */
public class HttpClient {
    private Logger log = Logger.getLogger(HttpClient.class);

    /**
     * 
     * @Title: post  
     * @Description: post请求工具方法
     * @param serverUrl 目标url
     * @param message 消息
     * @param header 头信息
     * @return post请求返回消息
     */
    public String post(String serverUrl, String message, Header header) {
        URL url;
        Writer wr = null;
        BufferedReader reader = null;
        try {
            url = new URL(serverUrl);
            URLConnection conn = url.openConnection();
            //post请求重点
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setConnectTimeout(1000 * 5);
            if (header != null) {
                header.setHeader(conn);
            }
            wr = new OutputStreamWriter(conn.getOutputStream());
            wr.write(message);
            wr.flush();
            reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = null;
            StringBuilder reviceMessage = new StringBuilder();
            if ((line = reader.readLine()) != null) {
                reviceMessage.append(line);
            }
            return reviceMessage.toString();
        } catch (Exception e) {
            log.error("目标地址" + serverUrl + "请求发生异常！！");
            e.printStackTrace();
        } finally {
            try {
                if (wr != null) {
                    wr.close();
                }
            } catch (IOException e1) {
                log.error(serverUrl + "：写资源关闭发生错误！！");
                e1.printStackTrace();
            }
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                log.error(serverUrl + "：读资源关闭发生错误！！");
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 
     * @Title: post  
     * @Description: post工具类
     * @param serverUrl 目标url
     * @param message 消息
     * @return post返回消息
     */
    public String post(String serverUrl, String message) {
        return post(serverUrl, message, null);
    }

    /**
     * 
     * @Title: mapToHttpString  
     * @Description: 将map变为字符串
     * @param paramter 一个参数map集合
     * @return  变为get参数
     */
    public static String mapToHttpString(Map<String, Object> paramter) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, Object> en : paramter.entrySet()) {
            sb.append(en.getKey() + "=" + en.getValue() + "&");
        }
        if (sb.lastIndexOf("&") > -1) {
            sb.deleteCharAt(sb.lastIndexOf("&"));
        }
        return sb.toString();
    }

    /**
     * 
     * @ClassName: Header 
     * @Description: 头文件模板接口
     * @author liudonghe
     * @date 2017年5月10日 下午3:43:09 
     *
     */
    public static interface Header {
        void setHeader(URLConnection con);
    }

    public static void main(String[] args) {
        HttpClient con = new HttpClient();
        con.post("http://localhost:8888/BaseProject/test/test.do", "name=ldh", null);
    }
}
