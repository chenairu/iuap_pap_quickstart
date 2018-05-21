package com.yonyou.iuap.example.record.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.yonyou.iuap.example.record.dao.ExampleRecordMapper;
import com.yonyou.iuap.example.record.entity.ExampleRecord;
import com.yonyou.iuap.mvc.type.SearchParams;

/**
 * <p>Title: InstitService</p>
 * <p>Description: </p>
 * @author zhukai
 */

@Service
public class ExampleRecordService {
	
    @Autowired
    private ExampleRecordMapper example_recordMapper;
    

    public List<ExampleRecord> findByCode(String code) {
        return example_recordMapper.findByCode(code);
    }

    /**
     * 查询组织分页数据
     * 
     * @param pageRequest
     * @param searchParams
     * @return
     */
    public Page<ExampleRecord> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {
        return example_recordMapper.selectAllByPage(pageRequest, searchParams.getSearchMap()).getPage();
    }

    /**
     * 查询所有组织
     * 
     * @return
     */
    public List<ExampleRecord> selectAll() {
        return example_recordMapper.findAll();
    }

    /**
     * 保存组织
     * 
     * @param recordList
     */
    @Transactional
    public void save(List<ExampleRecord> recordList) {
        List<ExampleRecord> addList = new ArrayList<>(recordList.size());
        List<ExampleRecord> updateList = new ArrayList<>(recordList.size());
        for (ExampleRecord er : recordList) {

            if (er.getId() == null) {
            	er.setId(UUID.randomUUID().toString());

//            	er.setDr(0);
                addList.add(er);
            } else {
                updateList.add(er);
            }
        }
        if (CollectionUtils.isNotEmpty(addList)) {
        	example_recordMapper.batchInsert(addList);
        }
        if (CollectionUtils.isNotEmpty(updateList)) {
        	example_recordMapper.batchUpdate(updateList);
        }
    }

    /**
     * 删除组织
     * 
     * @param list
     */
    public void batchDeleteByPrimaryKey(List<ExampleRecord> list) {
    	example_recordMapper.batchDelete(list);
    }

}
