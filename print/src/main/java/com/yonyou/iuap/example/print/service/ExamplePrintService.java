package com.yonyou.iuap.example.print.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import com.yonyou.iuap.example.print.entity.ExamplePrint;

import com.yonyou.iuap.example.print.dao.ExamplePrintMapper;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.uap.ieop.busilog.config.annotation.BusiLogConfig;

@Component
public class ExamplePrintService {
	@Autowired
	ExamplePrintMapper mapper;
	

    /**
     * Description:分页查询
     * Page<CardTable>
     * @param str
     */
    public Page<ExamplePrint> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {
        return mapper.selectAllByPage(pageRequest, searchParams.getSearchMap()).getPage();
    }
    
    
    /**
     * Description:批量保存（包括新增和更新）
     * void
     * @param str
     */
    public void save(List<ExamplePrint> recordList) {
        List<ExamplePrint> addList = new ArrayList<>(recordList.size());
        List<ExamplePrint> updateList = new ArrayList<>(recordList.size());
        for (ExamplePrint cardTable : recordList) {

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
    
	@BusiLogConfig("print_delete")
    public void batchDeleteByPrimaryKey(List<ExamplePrint> list) {
    	mapper.batchDeleteByPrimaryKey(list);
    }
    
    /**
     * Description:通过非主键字段查询
     * List<CardTable>
     * @param str
     */
    
    
    
	public ExamplePrint queryByPK(String id) {
		List<String> ids = new ArrayList<String>();
		ids.add(id);
		List<ExamplePrint> list = mapper.getByIds(ids);
		if (list != null && list.size() > 0) {
			ExamplePrint entity = list.get(0);
			return entity;
		} else {
			return null;
		}
	}
}
