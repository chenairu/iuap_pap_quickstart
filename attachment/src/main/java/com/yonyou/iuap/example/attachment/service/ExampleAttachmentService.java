package com.yonyou.iuap.example.attachment.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.yonyou.iuap.example.attachment.entity.ExampleAttachment;
import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.base.utils.RestUtils;
import com.yonyou.iuap.example.asval.entity.ExampleAsVal;
import com.yonyou.iuap.example.asval.service.ExampleAsValService;
import com.yonyou.iuap.example.attachment.dao.ExampleAttachmentMapper;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.iuap.utils.PropertyUtil;

@Component
public class ExampleAttachmentService {
	
	private Logger logger = LoggerFactory.getLogger(ExampleAttachmentService.class);
	@Autowired
	ExampleAttachmentMapper mapper;
	

    /**
     * Description:分页查询
     * Page<CardTable>
     * @param str
     */
    public Page<ExampleAttachment> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {
        return mapper.selectAllByPage(pageRequest, searchParams.getSearchMap()).getPage();
    }
    
    
    /**
     * Description:批量保存（包括新增和更新）
     * void
     * @param str
     */
    public void save(List<ExampleAttachment> recordList) {
        List<ExampleAttachment> addList = new ArrayList<>(recordList.size());
        List<ExampleAttachment> updateList = new ArrayList<>(recordList.size());
        List<String> ids=mapper.getIds();
        for (ExampleAttachment cardTable : recordList) {	
        	String id=cardTable.getId();
            if (id == null) {
              
            } else {
            	if(ids.contains(id)){  //update
            		updateList.add(cardTable);
            	}else { //add
            	   addList.add(cardTable);
            	}              
            }   
        }
        if (CollectionUtils.isNotEmpty(addList)) {
        	mapper.batchInsert(addList);
        }
        if (CollectionUtils.isNotEmpty(updateList)) {
        	mapper.batchUpdate(updateList);
        }
    }
    
    /**
     * Description:批量删除
     * void
     * @param str
     */
    public void batchDeleteByPrimaryKey(List<ExampleAttachment> list) {
    	mapper.batchDeleteByPrimaryKey(list);
    }
    


}
