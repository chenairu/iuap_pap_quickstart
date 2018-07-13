package com.yonyou.iuap.example.service;

import com.yonyou.iuap.baseservice.service.GenericExService;
import com.yonyou.iuap.example.dao.ShowOffDetailMapper;
import com.yonyou.iuap.example.entity.ShowOffDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShowOffDetailService extends GenericExService<ShowOffDetail> {
    private ShowOffDetailMapper mapper;

    @Autowired
    public void setMapper(ShowOffDetailMapper mapper) {
        this.mapper = mapper;
        super.setIbatisMapperEx(this.mapper);
    }


}
