package com.yonyou.iuap.example.template.service;

import java.io.InputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.yonyou.iuap.example.template.entity.ExampleTemplate;
import com.yonyou.iuap.example.template.service.support.ParentService;
import com.yonyou.iuap.common.utils.ExcelExportImportor;
import com.yonyou.iuap.example.template.entity.ExampleTemplate;
import com.yonyou.iuap.example.template.dao.ExampleTemplateMapper;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.persistence.vo.pub.VOStatus;


@Service
public class ExampleTemplateService extends ParentService<ExampleTemplate> {
	@Autowired
	ExampleTemplateMapper mapper;
	

    /**
     * Description:分页查询
     * Page<CardTable>
     * @param str
     */
    public Page<ExampleTemplate> selectAllByPage(PageRequest pageRequest, SearchParams searchParams) {
        return mapper.selectAllByPage(pageRequest, searchParams).getPage();
    }
    
    
    /**
     * Description:批量保存（包括新增和更新）
     * void
     * @param str
     */
    public void save(List<ExampleTemplate> recordList) {
        List<ExampleTemplate> addList = new ArrayList<>(recordList.size());
        List<ExampleTemplate> updateList = new ArrayList<>(recordList.size());
        for (ExampleTemplate cardTable : recordList) {

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
    public void batchDeleteByPrimaryKey(List<ExampleTemplate> list) {
    	mapper.batchDeleteByPrimaryKey(list);
    }
    
    
    
    
	private static String values = "{'id':'主键','code':'编码','name':'名称','remark':'备注'}";
	private Map<String, String> headInfo;
	public Map<String, String> getHeadInfo() {
		if (headInfo == null) {
			headInfo = new HashMap<String, String>();
			net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(values);
			headInfo = (Map<String, String>) json;
		}
		return headInfo;
	}

    
    

	/**
	 * 下载excel模版
	 * 
	 * @param response
	 * @throws Exception
	 */
	public void downloadExcelTemplate(HttpServletResponse response) throws Exception {
		ExcelExportImportor.downloadExcelTemplate(response, getHeadInfo(), "任务模板", "测试模板");
	}

	
	
	/**
	 * 导入excel数据
	 * 
	 * @param excelStream
	 */
	public void importExcelData(InputStream excelStream) throws Exception {
		
		List<ExampleTemplate> list = ExcelExportImportor.loadExcel(excelStream, getHeadInfo(), ExampleTemplate.class);	
		//先查出所有的Id。
		List<String> ids= mapper.getIds();
		

		
		for (ExampleTemplate entity : list) {
			
			System.out.println("Entityid:"+entity.getId());
			
			
			if (ids.contains(entity.getId().trim())) {
	
				entity.setStatus(VOStatus.UPDATED);  //编辑
			}
			else{
		
				entity.setStatus(VOStatus.NEW);  //新增
			}
			saveEntity(entity);
		}
	}
	
	
	private ExampleTemplate saveEntity(ExampleTemplate entity) {	
		// 保存主表数据
		if (entity.getStatus() == VOStatus.NEW) {
			//entity.setId(UUID.randomUUID().toString());
			// 插入数据
			mapper.insert(entity);
		} else {
			// 更新数据
			mapper.update(entity);
		}
		return entity;
	}
	
	
	public List<ExampleTemplate> getByIds(List<String> ids) {
		List<ExampleTemplate> list = mapper.getByIds(ids);
		return list;
	}
	
	


	/**
	 * 导出excel数据
	 * 
	 * @param pageRequest
	 * @param response
	 * @throws Exception
	 */
	public void exportExcelData(PageRequest pageRequest, HttpServletResponse response, String ids) throws Exception {
		List<ExampleTemplate> list = null;
		
		if (null != ids && ids.length() > 0) {
			String[] pks = ids.split(",");
			list = this.getByIds(Arrays.asList(pks));
		} else {
			SearchParams searchParams = new SearchParams();
			
			Page<ExampleTemplate> page = selectAllByPage(pageRequest, searchParams, ExampleTemplate.class);
			list = page.getContent();
		}
		
		ExcelExportImportor.writeExcel(response, list, getHeadInfo(), "任务详细", "任务信息");
	}

	
}
