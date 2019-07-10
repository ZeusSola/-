package com.yuchen.catalog.dao.common;

import com.yuchen.catalog.domain.common.Page;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 基础 Dao (所有Dao父类)
 * @param <T>
 */
public interface BaseDao<T> {
    /**
     * 查询单条记录详情
     * @param id
     * @return
     */
    public T view(String id);

    /**
     * 查询集合
     * @param entity
     * @return
     */
    public List<T> findList(T entity);

    /**
     * 分页查询集合
     * @param page
     * @return
     */
    public List<T> findListByPage(Page<T> page);

    /**
     * 新增数据
     * @param entity
     * @return
     */
    public int insert(T entity);

    /**
     * 修改数据
     * @param entity
     * @return
     */
    public int update(T entity);

    /**
     * 删除数据（物理删除）
     * @param id
     * @return
     */
    public int delete(String id);

    /**
     * 批量删除数据（物理删除）
     * @param list
     * @return
     */
    public int batchDelete(List<T> list);

}
