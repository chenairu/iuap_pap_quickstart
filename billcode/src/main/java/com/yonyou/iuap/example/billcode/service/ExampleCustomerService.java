package com.yonyou.iuap.example.billcode.service;

import com.yonyou.iuap.example.billcode.dao.ExampleCustomerMapper;
import com.yonyou.iuap.example.billcode.entity.ExampleCustomer;
import com.yonyou.iuap.mvc.type.SearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service
public class ExampleCustomerService {

    @Autowired
    private ExampleCustomerMapper exampleCustomerMapper;

    public Page<ExampleCustomer> selectAllByPage(PageRequest pageRequest, SearchParams searchParams){
        return exampleCustomerMapper.selectAllByPage(pageRequest,searchParams.getSearchMap()).getPage();
    }

    public List<ExampleCustomer> findByCode(String code){
        return exampleCustomerMapper.findByCode(code);
    }

    @Transactional
    public void save(List<ExampleCustomer> list){
        List<ExampleCustomer> addList = new ArrayList<>(list.size());
        List<ExampleCustomer> updateList = new ArrayList<>(list.size());

    }
}
