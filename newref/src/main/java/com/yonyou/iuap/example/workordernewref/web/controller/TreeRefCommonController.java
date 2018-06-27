package com.yonyou.iuap.example.workordernewref.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.iuap.example.workordernewref.entity.RefParamVO;
import com.yonyou.iuap.example.workordernewref.service.NewRefCommonService;
import com.yonyou.iuap.example.workordernewref.utils.SimpleParseXML;
import com.yonyou.iuap.example.workordernewref.utils.ValueConvertor;
import com.yonyou.iuap.ref.model.RefViewModelVO;
import com.yonyou.iuap.ref.sdk.refmodel.model.AbstractTreeGridRefModel;
/**
 * 树表通用参照
 * @author my
 *
 */
@Controller
@RequestMapping(value = "/common/newref")
public class TreeRefCommonController extends AbstractTreeGridRefModel{

	@Autowired
	private NewRefCommonService service;
	
	/*
	 * 获取表头信息
	 * @see com.yonyou.iuap.ref.sdk.refmodel.model.AbstractTreeGridRefModel#getRefModelInfo(com.yonyou.iuap.ref.model.RefViewModelVO)
	 */
	@Override
	@ResponseBody
	public RefViewModelVO getRefModelInfo(@RequestBody RefViewModelVO refViewModel) {
		
		RefViewModelVO refModel = super.getRefModelInfo(refViewModel);
		String transmitParam = refViewModel.getTransmitParam();
		String tableName = "";
		if(transmitParam.contains(",")){
			String[] tableNames = transmitParam.split(",");
			tableName = tableNames[0];
		}else{
			tableName = transmitParam;
		}
		
		RefParamVO refParamVO = SimpleParseXML.getInstance().getMSConfig(tableName);
		
		Map<String,String> showcolMap = refParamVO.getShowcol();
		String[] showcode = null;
		String[] showname = null;
		if (showcolMap != null) {
			showcode = new String[showcolMap.size()];
			showname = new String[showcolMap.size()];
			int i = 0;
			for (Entry<String, String> entry : showcolMap.entrySet()) {
				showcode[i] = entry.getKey();
				showname[i] = entry.getValue();
				i++;
			}
		}
		//显示列编码和名称
		refModel.setStrFieldCode(showcode);
		refModel.setStrFieldName(showname);
		refModel.setDefaultFieldCount(showcolMap.size());
		return refModel;
	}
	
	@Override
	public List<Map<String, String>> matchPKRefJSON(RefViewModelVO arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, String>> filterRefJSON(RefViewModelVO arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, String>> matchBlurRefJSON(RefViewModelVO arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Map<String, String>> blobRefClassSearch(RefViewModelVO arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/*
	 * 树
	 * @see com.yonyou.iuap.ref.sdk.refmodel.model.AbstractTreeGridRefModel#blobRefTree(com.yonyou.iuap.ref.model.RefViewModelVO)
	 */
	@ResponseBody
	public Map<String, Object> blobRefTree(@RequestBody RefViewModelVO refModel) {

		String transmitParam = refModel.getTransmitParam();
		String tableName = "";
		if(transmitParam.contains(",")){
			String[] tableNames = transmitParam.split(",");
			tableName = tableNames[1];
		}else{
			//前台没有传递树的表名
		}
		//构建表体，其中list中为要查询的字段，必须和表头设置的相同，并且必须为表中的字段值
		RefParamVO params = SimpleParseXML.getInstance().getMSConfigTree(tableName);

		Map<String, Object> mapList = new HashMap<String, Object>();
		List<Map<String, String>> results = new ArrayList<Map<String, String>>();
		try {
			int pageNum = refModel.getRefClientPageInfo().getCurrPageIndex();
			int pageSize = 10000; 

			PageRequest request = buildPageRequest(pageNum, pageSize, null);

			Map<String, String> conditions = new HashMap<String, String>();
			conditions.put("dr", "0");

			String idfield = StringUtils.isBlank(params.getIdfield()) ? "id"
					: params.getIdfield();
			String pidfield = StringUtils.isBlank(params.getPidfield()) ? "pid"
					: params.getPidfield();
			String codefield = StringUtils.isBlank(params.getCodefield()) ? "code"
					: params.getCodefield();
			String namefield = StringUtils.isBlank(params.getNamefield()) ? "name"
					: params.getNamefield();

			Page<Map<String, Object>> headpage = this.service.selectRefTree(
					request, params.getTablename(), idfield, pidfield,codefield,
					namefield, conditions, params.getExtcol());
			List<Map<String, Object>> headVOs = headpage.getContent();

			if (CollectionUtils.isNotEmpty(headVOs)) {
				results = buildRtnValsOfRefTree(headVOs);
			}
			
			mapList.put("dataList", results);
			mapList.put("refViewModel", refModel);
		} catch (Exception e) {
			System.out.println(e);
		}
		return mapList;
	}

	
	/*
	 * 获取表体信息
	 * @see com.yonyou.iuap.ref.sdk.refmodel.model.AbstractTreeGridRefModel#blobRefSearch(com.yonyou.iuap.ref.model.RefViewModelVO)
	 */
	@Override
	@ResponseBody
	public Map<String, Object> blobRefSearch(@RequestBody RefViewModelVO refModel) {

		String transmitParam = refModel.getTransmitParam();
		String tableName = "";
		if(transmitParam.contains(",")){
			String[] tableNames = transmitParam.split(",");
			tableName = tableNames[0];
		}else{
			tableName = transmitParam;
		}
		
		//构建表体，其中list中为要查询的字段，必须和表头设置的相同，并且必须为表中的字段值
		RefParamVO refParamVO = SimpleParseXML.getInstance().getMSConfig(tableName);
		
		Map<String, Object> mapList = new HashMap<String, Object>();
		List<Map<String, String>> results = new ArrayList<Map<String, String>>();
		try {
			//获取当前页
			int pageNum = refModel.getRefClientPageInfo().getCurrPageIndex();
			//每页显示的数量
			int pageSize = 10;
			//拼装分页请求对象
			PageRequest request = buildPageRequest(pageNum, pageSize, null);
			
			refModel.getRefClientPageInfo().setPageSize(pageSize);
			
			//获取查询条件
			String content = refModel.getContent();
			
			//树节点的ID
			String condition = refModel.getCondition();
			
			Map<String, String> conditions = new HashMap<String,String>();
			if(content != null && !"".equals(content)){
				//按照自定义第一个字段做搜索查询
				conditions.put(refParamVO.getExtcol().get(0), content);
			}
			conditions.put("dr", "0");
			
			String idfield = StringUtils.isBlank(refParamVO.getIdfield()) ? "id"
					: refParamVO.getIdfield();
			String pidfield = StringUtils.isBlank(refParamVO.getPidfield()) ? "pid"
					: refParamVO.getPidfield();

			//根据树节点 查找树下的列表
			if(condition != null && !"".equals(condition)){
				conditions.put(pidfield,condition);
			}
			
			Page<Map<String, Object>> headpage = this.service.getTreeRefData(
					request, refParamVO.getTablename(), idfield,pidfield, conditions, refParamVO.getExtcol());
			
			//总页数
			refModel.getRefClientPageInfo().setPageCount(headpage.getTotalPages());
			
			List<Map<String, Object>> headVOs = headpage.getContent();

			if (CollectionUtils.isNotEmpty(headVOs)) {
				results = buildRtnValsOfRef(headVOs);
			}
			mapList.put("dataList", results);
			mapList.put("refViewModel", refModel);
		} catch (Exception e) {
			System.out.println(e);
		}
		return mapList;
	}
	
	private PageRequest buildPageRequest(int pageNum, int pageSize,
			String sortColumn) {
		Sort sort = null;
		if (("auto".equalsIgnoreCase(sortColumn))
				|| (StringUtils.isEmpty(sortColumn))) {
			sort = new Sort(Sort.Direction.ASC, "ts");
		} else {
			sort = new Sort(Sort.Direction.DESC, sortColumn);
		}
		return new PageRequest(pageNum, pageSize, sort);
	}
	
	/**
	 * 过滤完的数据组装--表格
	 * 
	 * @param peoplelist
	 * @return
	 */
	private List<Map<String, String>> buildRtnValsOfRef(
			List<Map<String, Object>> headVOs) {

		List<Map<String, String>> results = new ArrayList<Map<String, String>>();
		if ((headVOs != null) && (!headVOs.isEmpty())) {
			ValueConvertor convertor = new ValueConvertor();
			for (Map<String, Object> entity : headVOs) {
				Map<String, String> refDataMap = new HashMap<String, String>();
				refDataMap.put("refpk", entity.get("ID").toString());
				for (String key : entity.keySet()) {
					refDataMap.put(key.toLowerCase(), 
							convertor.convertToJsonType(entity.get(key)).toString());
				}
				results.add(refDataMap);
			}
		}
		return results;
	}
	/**
	 * 过滤完的数据组装--树
	 * 
	 * @param peoplelist
	 * @return
	 */
	private List<Map<String, String>> buildRtnValsOfRefTree(
			List<Map<String, Object>> headVOs) {
		
		List<Map<String, String>> results = new ArrayList<Map<String, String>>();
		if ((headVOs != null) && (!headVOs.isEmpty())) {
			ValueConvertor convertor = new ValueConvertor();
			for (Map<String, Object> entity : headVOs) {
				Map<String, String> refDataMap = new HashMap<String, String>();
				refDataMap.put("refpk", entity.get("ID").toString());
				if(entity.get("PID") == null){
					refDataMap.put("pid","");
				}
				
				for (String key : entity.keySet()) {
					refDataMap.put(key.toLowerCase(), 
							convertor.convertToJsonType(entity.get(key)).toString());
				}
				results.add(refDataMap);
			}
		}
		return results;
	}
}
