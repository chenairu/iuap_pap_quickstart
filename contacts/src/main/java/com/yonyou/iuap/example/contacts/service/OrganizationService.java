package com.yonyou.iuap.example.contacts.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.example.contacts.dao.OrganizationMapper;
import com.yonyou.iuap.example.contacts.entity.Organization;
import com.yonyou.iuap.mvc.type.SearchParams;

/**
 * <p>Title: InstitService</p>
 * <p>Description: </p>
 * @author zhukai
 */

@Service
public class OrganizationService {
	
    @Autowired
    private OrganizationMapper oranizationMapper;


    /**
     * 查询组织分页数据
     * 
     * @param pageRequest
     * @param searchParams
     * @return
     */
    public Page<Organization> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {
        return oranizationMapper.selectAllByPage(pageRequest, searchParams.getSearchMap()).getPage();
    }
    
    public List<Organization> findByFid(String pk) {
        return oranizationMapper.findByFid(pk);
    }

    public List<Organization> findByCode(String code) {
        return oranizationMapper.findByCode(code);
    }

    /**
     * 查询所有组织
     * 
     * @return
     */
    public List<Organization> selectAll() {
        return oranizationMapper.findAll();
    }

    /**
     * 保存组织
     * 
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

}