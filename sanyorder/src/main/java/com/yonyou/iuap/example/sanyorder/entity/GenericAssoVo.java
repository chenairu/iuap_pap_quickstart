package com.yonyou.iuap.example.sanyorder.entity;

import com.yonyou.iuap.baseservice.entity.AbsModel;

/**
 * 之子表查询Vo包装类
 * @param <T> 主表类对象
 */
public abstract class GenericAssoVo<T extends AbsModel> {
    private GenericAssoVo(){}// 强制要求继承VO的对象初始化主表

    public GenericAssoVo(T entity){
        this.entity = entity;
    }


    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    private T entity;

}
