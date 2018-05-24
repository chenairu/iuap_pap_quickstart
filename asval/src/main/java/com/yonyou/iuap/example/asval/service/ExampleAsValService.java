package com.yonyou.iuap.example.asval.service;

import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.example.asval.dao.ExampleAsValMapper;
import com.yonyou.iuap.example.asval.entity.ExampleAsVal;
import com.yonyou.iuap.mvc.type.SearchParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

    public List<ExampleAsVal> findProvince(String code){
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

    public List<ExampleAsVal> likeSarch(String keyword){
        List<ExampleAsVal> list = new ArrayList<>();
        list = exampleAsValMapper.queryByCaluse(keyword);
        return list;
    }

    public List<ExampleAsVal> getByIds(String tenantId,List<String> ids){
        return exampleAsValMapper.getByIds(tenantId,ids);
    }

    public Page<ExampleAsVal> selectAllByPage(PageRequest pageRequest, SearchParams searchParams){
        return exampleAsValMapper.selectAllByPage(pageRequest,searchParams);
    }
}
