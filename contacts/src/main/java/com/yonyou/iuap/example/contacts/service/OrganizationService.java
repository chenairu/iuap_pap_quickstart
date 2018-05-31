package com.yonyou.iuap.example.contacts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.example.common.service.GenericService;
import com.yonyou.iuap.example.contacts.dao.OrganizationMapper;
import com.yonyou.iuap.example.contacts.entity.Organization;

@Service
public class OrganizationService extends GenericService<Organization>{
	
    public List<Organization> findByFid(String pk) {
        return oranizationMapper.findByFid(pk);
    }

    /**
     * 保存组织
     * @param recordList
     */
    @Transactional
    public void save(List<Organization> recordList) {
        List<Organization> addList = new ArrayList<>(recordList.size());
        List<Organization> updateList = new ArrayList<>(recordList.size());
        for (Organization instit : recordList) {

            if (instit.getInstitid() == null) {
            	instit.setInstitid(UUID.randomUUID().toString());
            	instit.setDr(0);
                addList.add(instit);
            } else {
                updateList.add(instit);
            }
        }
        if (CollectionUtils.isNotEmpty(addList)) {
        	oranizationMapper.batchInsert(addList);
        }
        if (CollectionUtils.isNotEmpty(updateList)) {
        	oranizationMapper.batchUpdate(updateList);
        }
    }

    /**
     * 删除组织
     * 
     * @param list
     */
    public void batchDeleteByPrimaryKey(List<Organization> list) {
    	oranizationMapper.batchDelete(list);
    }
    
    /************************************************************/
    private OrganizationMapper oranizationMapper;

    @Autowired
	public void setOranizationMapper(OrganizationMapper oranizationMapper) {
		this.oranizationMapper = oranizationMapper;
    	super.ibatisMapper = oranizationMapper;
	}

}