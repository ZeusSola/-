package com.yuchen.catalog.domain.common;

import com.yuchen.catalog.common.utils.StringUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体类(所有实体父类)
 * @param <T>
 */
public class BaseEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public String id;   //主键id（唯一标识）
    public String createBy; // 创建者
    public Date createDate; // 创建日期
    public String updateBy; // 更新者
    public Date updateDate; // 更新日期
    public String fieldSort;    //排序字段
    public Integer pageNo;  //页码
    public Integer pageSize;    //每页显示条数

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUpdateBy() {
        return updateBy;
    }

    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getFieldSort() {
        return fieldSort;
    }

    public void setFieldSort(String fieldSort) {
        this.fieldSort = fieldSort;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 插入前方法，需手动调用
     */
    public void preInsert(){
        String userId = "";     //后期补充userId获取方式
        if(StringUtils.isNotBlank(userId)){
            this.createBy = userId;
            this.updateBy = userId;
        }
        this.createDate = new Date();
        this.updateDate = this.createDate;
    }

    /**
     * 更新前方法，需手动调用
     */
    public void preUpdate(){
        String userId = "";     //后期补充userId获取方式
        if(StringUtils.isNotBlank(userId)){
            this.updateBy = userId;
        }
        this.updateDate = new Date();
    }


}
