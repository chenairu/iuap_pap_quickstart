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

import com.yonyou.iuap.example.contacts.dao.ExampleInstitMapper;
import com.yonyou.iuap.example.contacts.entity.ExampleInstit;
import com.yonyou.iuap.mvc.type.SearchParams;

/**
 * <p>Title: InstitService</p>
 * <p>Description: </p>
 * @author zhukai
 */

@Service
public class ExampleInstitService {
	
    @Autowired
    private ExampleInstitMapper institMapper;
    

    public List<ExampleInstit> findByFid(String pk) {
        return institMapper.findByFid(pk);
    }

    public List<ExampleInstit> findByCode(String code) {
        return institMapper.findByCode(code);
    }

    /**
     * 查询组织分页数据
     * 
     * @param pageRequest
     * @param searchParams
     * @return
     */
    public Page<ExampleInstit> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {
        return institMapper.selectAllByPage(pageRequest, searchParams.getSearchMap()).getPage();
    }

    /**
     * 查询所有组织
     * 
     * @return
     */
    public List<ExampleInstit> selectAll() {
        return institMapper.findAll();
    }

    /**
     * 保存组织
     * 
     * @param recordList
     */
    @Transactional
    public void save(List<ExampleInstit> recordList) {
        List<ExampleInstit> addList = new ArrayList<>(recordList.size());
        List<ExampleInstit> updateList = new ArrayList<>(recordList.size());
        for (ExampleInstit instit : recordList) {

            if (instit.getInstitid() == null) {
            	instit.setInstitid(UUID.randomUUID().toString());
            	instit.setDr(0);
                addList.add(instit);
            } else {
                updateList.add(instit);
            }
        }
        if (CollectionUtils.isNotEmpty(addList)) {
        	institMapper.batchInsert(addList);
        }
        if (CollectionUtils.isNotEmpty(updateList)) {
        	institMapper.batchUpdate(updateList);
        }
    }

    /**
     * 删除组织
     * 
     * @param list
     */
    public void batchDeleteByPrimaryKey(List<ExampleInstit> list) {
    	institMapper.batchDelete(list);
    }

}
