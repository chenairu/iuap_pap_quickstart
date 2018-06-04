package com.yonyou.iuap.example.asyncOrg.service;

import com.yonyou.iuap.example.asyncOrg.dao.ExampleTreeMapper;
import com.yonyou.iuap.example.asyncOrg.entity.ExampleTree;
import com.yonyou.iuap.mvc.type.SearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
public class ExampleTreeService {

    @Autowired
    private ExampleTreeMapper exampleTreeMapper;

    public Page<ExampleTree> selectAllByPage(PageRequest pageRequest, SearchParams searchParams){
        return exampleTreeMapper.selectAllByPage(pageRequest,searchParams.getSearchMap()).getPage();
    }

}
