package com.yonyou.iuap.example.asval.service;

import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.example.asval.dao.ExampleAsValMapper;
import com.yonyou.iuap.example.asval.entity.ComboboxEntity;
import com.yonyou.iuap.example.asval.entity.ExampleAsVal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ExampleAsValService {

    @Autowired
    private ExampleAsValMapper exampleAsValMapper;

    public List<ExampleAsVal> findAll(){
        return exampleAsValMapper.findAll();
    }

    public List<ComboboxEntity> findProvince(String code){
        return exampleAsValMapper.findProvince(code);
    }
    public List<ExampleAsVal> getCurrtypeByIds(String[] strArray) {
        String tenantId = InvocationInfoProxy.getTenantid();
        ArrayList<String> ids = new ArrayList<String>();
        for (String key : strArray) {
            ids.add(key);
        }
        return exampleAsValMapper.getByIds(tenantId, ids);
    }
}
