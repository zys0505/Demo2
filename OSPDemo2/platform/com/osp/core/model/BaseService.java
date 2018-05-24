package com.osp.core.model;

import java.util.List;

/**
 * 业务逻辑层基类
 * @author Jane Wang  2017年5月10日 下午3:29:05 
 * 
 * @param <T>
 * @param <PK>
 */
public interface BaseService<T, PK> {
    /**
     * 查询所有
     * @return
     */
    List<T> list();

    /**
     * 分页查询
     * @param index
     * @param size
     * @return  
     */
    PageTion<T> listByPage(int index, int size);

    /**
     * 获取详情
     * @param id
     * @return  
     */
    T queryOne(PK id);

    /**
     * 保存
     * @param t
     * @return  
     */
    int save(T t);

    /**
     * 更新
     * @param t
     * @return
     */
    int update(T t);

    /**
     * 刪除
     * @param id 注解
     * @return
     */
    int remove(PK id);

    /**
     * 批量刪除
     * @param ids id集合
     * @return
     */
    int[] removeForBatch(PK[] ids);
}
