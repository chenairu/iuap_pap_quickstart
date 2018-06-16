package com.yonyou.iuap.example.sysRefer.service;

import com.yonyou.iuap.base.utils.CommonConstants;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.example.commons.CommonUtils;
import com.yonyou.iuap.example.sysRefer.dao.ExampleEquipMapper;
import com.yonyou.iuap.example.sysRefer.entity.ExampleEquip;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.uap.ieop.security.entity.DataPermission;
import com.yonyou.uap.ieop.security.sdk.AuthRbacClient;

import org.apache.commons.collections.CollectionUtils;
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
        return exampleEquipMapper.selectAllByPage(pageRequest,searchParams.getSearchMap(),buildPermSql()).getPage();
    }

    public List<ExampleEquip> findByCode(String code){
        return exampleEquipMapper.findByCode(code);
    }

    public void save(List<ExampleEquip> exampleEquips){
    	List<ExampleEquip> addList = new ArrayList<>();
    	List<ExampleEquip> updateList = new ArrayList<>();
    	for(ExampleEquip exampleEquip :exampleEquips){
    		
    		if(exampleEquip.getId() == null){
                String id = UUID.randomUUID().toString();
                exampleEquip.setId(id);
                exampleEquip.setTenantId("tenant");
                addList.add(exampleEquip);
            }else {
                updateList.add(exampleEquip);
            }
    	}
    	
    	if(CollectionUtils.isNotEmpty(addList)){
    		exampleEquipMapper.batchInsert(addList);
    	}
    	if(CollectionUtils.isNotEmpty(updateList)){
    		exampleEquipMapper.batchUpdate(updateList);
    	}
    	
        
    }

    public void delete(List<ExampleEquip> exampleEquips){
        for(ExampleEquip exampleEquip :exampleEquips){
            if(exampleEquip.getId() != null){
                exampleEquipMapper.deleteByPrimaryKey(exampleEquip.getId());
            }
        }
    }

   
    /**
     * 构造数据权限SQL
     * @return
     */
    public String buildPermSql() {
        StringBuilder sqlBuilder = new StringBuilder();

        sqlBuilder.append(String.format(" and %s='%s'", CommonConstants.TENANT_FiELEDNAME, InvocationInfoProxy.getTenantid()));

        HashMap<String, String> fieldDataPermResTypeMap = processFieldDataPermResTypeMap();

        Set<String> keySet = fieldDataPermResTypeMap.keySet();
        Map<String, Set<String>> resTypeDataPermMap = new HashMap<String, Set<String>>();

        for (String columnname : keySet) {
            String resourceTypeCode = fieldDataPermResTypeMap.get(columnname);
            Set<String> set = resTypeDataPermMap.get(resourceTypeCode);

            if(set == null){
                set = getAuthData(resourceTypeCode);
                resTypeDataPermMap.put(resourceTypeCode, set);
            }

            if(!set.isEmpty()){
                sqlBuilder.append(" and ").append(columnname).append(" in (");

                for (String param : set) {
                    sqlBuilder.append(String.format("'%s',", param));
                }

                sqlBuilder.deleteCharAt(sqlBuilder.length() - 1);
                sqlBuilder.append(")");
            }
        }

        String querySql = sqlBuilder.toString();

        return querySql;
    }
    /**
     * 需要支持数据权限的资源
     * @return
     */
    private HashMap<String, String> processFieldDataPermResTypeMap(){
        HashMap<String, String> fieldDataPermResTypeMap = new HashMap<String, String>();
        fieldDataPermResTypeMap.put("org_id", "organization"); //字段与权限资源名称的对应关系
        return fieldDataPermResTypeMap;
    }
    /**
     * 调用API获取有权限的数据
     * @param resourceTypeCode
     * @return
     */
    private Set<String> getAuthData(String resourceTypeCode) {
        String tenantId = InvocationInfoProxy.getTenantid();
        String sysId = InvocationInfoProxy.getSysid();
        String userId = InvocationInfoProxy.getUserid();

        List<DataPermission> dataPerms = AuthRbacClient.getInstance().queryDataPerms(tenantId, sysId, userId, resourceTypeCode);

        Set<String> set = new HashSet<String>();
        for (Iterator<DataPermission> iterator = dataPerms.iterator(); iterator.hasNext();) {
            DataPermission dataPermission = (DataPermission) iterator.next();
            set.add(dataPermission.getResourceId());
        }

        return set;
    }
}
