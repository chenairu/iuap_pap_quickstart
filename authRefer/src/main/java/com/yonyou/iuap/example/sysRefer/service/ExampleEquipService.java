package com.yonyou.iuap.example.sysRefer.service;

import com.yonyou.iuap.base.utils.CommonConstants;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.example.commons.CommonUtils;
import com.yonyou.iuap.example.sysRefer.dao.ExampleEquipMapper;
import com.yonyou.iuap.example.sysRefer.entity.ExampleEquip;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.uap.ieop.security.entity.DataPermission;
import com.yonyou.uap.ieop.security.sdk.AuthRbacClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * com.yonyou.iuap.example.sysRefer.service
 *
 * @author guoxh
 * @date 2018/5/29 15:39
 * @description
 */
@Service
public class ExampleEquipService {

    @Autowired
    private ExampleEquipMapper exampleEquipMapper;

    public Page<ExampleEquip> selectAllByPage(PageRequest pageRequest, SearchParams searchParams){
        Map<String,String> fieldDataPermResTypeMap = new HashMap<>();
        fieldDataPermResTypeMap.put("org_id","organization");
        return exampleEquipMapper.selectAllByPage(pageRequest,searchParams.getSearchMap(),CommonUtils.buildPermSql(fieldDataPermResTypeMap)).getPage();
    }

    public List<ExampleEquip> findByCode(String code){
        return exampleEquipMapper.findByCode(code);
    }

    public void save(ExampleEquip exampleEquip){
        if(exampleEquip.getId() == null){
            String id = UUID.randomUUID().toString();
            exampleEquip.setId(id);
            exampleEquip.setTenantId("tenant");
            exampleEquipMapper.insert(exampleEquip);
        }else {
            exampleEquipMapper.updateByPrimaryKey(exampleEquip);
        }
    }

    public void delete(List<ExampleEquip> exampleEquips){
        for(ExampleEquip exampleEquip :exampleEquips){
            if(exampleEquip.getId() != null){
                exampleEquipMapper.deleteByPrimaryKey(exampleEquip.getId());
            }
        }
    }

}
