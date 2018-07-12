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

    @Override
    public SanyOrderContract insert(SanyOrderContract entity) {
        entity.setContractCode(entity.getBpmBillCode());//编码code生成
        return super.insert(entity);
    }
    @Override
    public SanyOrderContract save(SanyOrderContract entity) {
        entity.setContractCode(entity.getBpmBillCode());//编码code生成
        return super.save(entity);
    }
}
