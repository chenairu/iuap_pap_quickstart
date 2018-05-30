package com.yonyou.iuap.example.userDefRef.service;

import com.yonyou.iuap.example.userDefRef.dao.ExampleBillMapper;
import com.yonyou.iuap.example.userDefRef.entity.ExampleBill;
import com.yonyou.iuap.mvc.type.SearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

/**
 * com.yonyou.iuap.example.userDefRef.service
 *
 * @author guoxh
 * @date 2018/5/30 10:18
 * @description
 */
@Service
public class ExampleBillService {

    @Autowired
    private ExampleBillMapper exampleBillMapper;

    public Page<ExampleBill> selectAllByPage(PageRequest pageRequest, SearchParams searchParams){
        return exampleBillMapper.selectAllByPage(pageRequest,searchParams.getSearchMap()).getPage();
    }

}
