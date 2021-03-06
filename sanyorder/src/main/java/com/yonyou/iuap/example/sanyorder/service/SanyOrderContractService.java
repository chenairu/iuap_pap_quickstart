package com.yonyou.iuap.example.sanyorder.service;

import com.yonyou.iuap.baseservice.service.GenericExService;
import com.yonyou.iuap.example.sanyorder.dao.SanyOrderContractMapper;
import com.yonyou.iuap.example.sanyorder.entity.SanyOrderContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SanyOrderContractService extends GenericExService<SanyOrderContract> {

    private SanyOrderContractMapper sanyOrderContractMapper;

    @Autowired
    public void setSanyOrderContractMapper(SanyOrderContractMapper sanyOrderContractMapper) {
        this.sanyOrderContractMapper = sanyOrderContractMapper;
        super.setIbatisMapperEx(this.sanyOrderContractMapper);
    }

}
