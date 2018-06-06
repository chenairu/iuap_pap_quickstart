package com.yonyou.iuap.example.attachment.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.yonyou.iuap.example.attachment.entity.ExampleAttachment;
import com.yonyou.iuap.example.attachment.dao.ExampleAttachmentMapper;
import com.yonyou.iuap.mvc.type.SearchParams;

@Component
public class ExampleAttachmentService {
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
        for (ExampleAttachment cardTable : recordList) {

            if (cardTable.getId() == null) {
            	cardTable.setId(UUID.randomUUID().toString());
//            	cardTable.setDr(new Integer(0));
                addList.add(cardTable);
            } else {
                updateList.add(cardTable);
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
    
    /**
     * Description:通过非主键字段查询
     * List<CardTable>
     * @param str
     */
}
