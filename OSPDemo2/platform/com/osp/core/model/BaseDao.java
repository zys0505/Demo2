package com.osp.core.model;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 基本数据持久层借口
 * @author liudonghe 2017年5月10日 下午3:32:22 
 * @param <T> 对应实体类
 * @param <PK> 主键类型
 */
public interface BaseDao<T, PK> {
    /**
     * 根据主键进行删除
     * @param id 主键
     * @return  更新记录条数
     */
    int removeByKey(PK id);

    /**
     * 批量删除
     * @param ids id集合
     * @return  更新记录条数
     */
    int removeForBatch(@Param("data") List<PK> ids);

    /**
     * 插入
     * @param record 实体类
     * @return  更新记录条数
     */
    int save(T record);

    /**
     * 插入不为空的
     * @param record 实体类
     * @return  更新记录条数
     */
    int saveSelective(T record);

    /**
     * 根据主键查询一个
     * @param id 主键
     * @return  更新记录条数
     */
    T queryByKey(PK id);

    /**
     * 更新不为空的字段
     * @param record 更新的实体类
     * @return  更新记录条数
     */
    int updateByKeySelective(T record);

    /**
     * 更新的实体类
     * @param record 
     * @return  更新记录条数
     */
    int updateByKey(T record);

    /**
     * 展示所有 
     * @return  对象集合
     */
    List<T> list();

    /**
     * 根据条件查询cription:  
     * @param key 字段名称
     * @param value 字段值
     * @return  对象集合
     */
    List<T> listByCondition(String key, String value);

    /**
     * 多条件查询
     * @param params 字段名称，字段值集合
     * @return  查询对象集合
     * @throws
     */
    List<T> listByMoreCondition(@Param("data") Map<String, Object> params);

    /**
     * 根据条件查询单条 
     * @param params 查询条件
     * @return  查询对象
     */
    T queryOneByCondition(@Param("data") Map<String, Object> params);

    /**
     * 按条件更新
     * @param record 实体
     * @param params 查询条件集合
     * @return  更新记录条数
     */
    int updateByCondition(@Param("data") T record, @Param("par") Map<String, Object> params);
}
