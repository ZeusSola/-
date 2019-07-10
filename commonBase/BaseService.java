package com.yuchen.catalog.service.common;

import com.yuchen.catalog.common.utils.StringUtils;
import com.yuchen.catalog.dao.common.BaseDao;
import com.yuchen.catalog.domain.common.BaseEntity;
import com.yuchen.catalog.domain.common.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 基础 Service (所有Service父类)
 * @param <D>
 * @param <T>
 */
public class BaseService<D extends BaseDao<T>,T extends BaseEntity<T>> {
    /**
     * 日志对象
     */
    protected Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    protected D dao;

    /**
     * 查询单条记录详情
     * @param id
     * @return
     */
    public T view(String id){
        T entity = dao.view(id);
        return entity;
    }
    /**
     * 查询集合
     * @param entity
     * @return
     */
    public List<T> findList(T entity){
        List<T> list = dao.findList(entity);
        return list;
    }

    /**
     * 分页查询集合
     * @param page
     * @return
     */
    public Page<T>findListByPage(Page<T> page){
        List<T> list = dao.findListByPage(page);
        page.setResults(list);
        return page;
    }

    /**
     * 新增、修改（区分：是否存在主键id）
     * @param entity
     */
    @Transactional
    public int save(T entity){
        if(StringUtils.isBlank(entity.getId())){//新增
            entity.preInsert();
            return dao.insert(entity);
        }else{//修改
            entity.preUpdate();
            return dao.update(entity);
        }
    }

    /**
     * 删除数据（物理删除）
     * @param id
     */
    @Transactional
    public int delete(String id){
        return dao.delete(id);
    }

    /**
     * 批量删除数据（物理删除）
     * @param list
     */
    @Transactional
    public int batchDelete(List<T> list){
        for(T element : list){
            if(StringUtils.isBlank(element.getId())){
                return 0;
            }
        }
        return dao.batchDelete(list);
    }

}
