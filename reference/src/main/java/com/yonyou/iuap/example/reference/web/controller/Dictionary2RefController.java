package com.yonyou.iuap.example.reference.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.iuap.example.dictionary.entity.Dictionary;
import com.yonyou.iuap.example.dictionary.service.DictionaryService;
import com.yonyou.iuap.mvc.type.SearchParams;

import iuap.ref.ref.RefClientPageInfo;
import iuap.ref.sdk.refmodel.model.AbstractCommonRefModel;
import iuap.ref.sdk.refmodel.vo.RefViewModelVO;

@RestController
@RequestMapping({"/reference/dictionary2"})
public class Dictionary2RefController extends AbstractCommonRefModel{
	
	private Logger logger = LoggerFactory.getLogger(Dictionary2RefController.class);
	
	public RefViewModelVO getRefModelInfo(@RequestBody RefViewModelVO refViewVo) {
		refViewVo = super.getRefModelInfo(refViewVo);
		refViewVo.setRefName("币种");
		refViewVo.setRootName("币种列表");
		refViewVo.setStrFieldName(new String[] { "币种编码", "币种名称", "备注" });
		refViewVo.setStrFieldCode(new String[] { "refcode", "refname", "remark" });
		refViewVo.setDefaultFieldCount(3);
		refViewVo.setIsMultiSelectedEnabled(false);
		return refViewVo;
	}

	@Override
	public Map<String, Object> getCommonRefData(@RequestBody RefViewModelVO refViewVo) {
		Map<String,Object> resultMap = new HashMap<String,Object>();
		int pageNum = refViewVo.getRefClientPageInfo().getCurrPageIndex()==0 ? 1:refViewVo.getRefClientPageInfo().getCurrPageIndex();
		int pageSize = refViewVo.getRefClientPageInfo().getPageSize();
		
		PageRequest request = buildPageRequest(pageNum, pageSize, null);
		
		String searchParams = StringUtils.isEmpty(refViewVo.getContent())? null :refViewVo.getContent();
		
		Page<Dictionary> pageData = dictionaryService.selectAllByPage(request, this.buildSearchParams(searchParams));
		List<Dictionary> listData = pageData.getContent();
		if(CollectionUtils.isNotEmpty(listData)) {
			List<Map<String, String>> listRefer = buildRtnValsOfRef(listData);
			
			RefClientPageInfo refPageInfo = refViewVo.getRefClientPageInfo();
			refPageInfo.setPageCount(pageData.getTotalPages());
			refPageInfo.setCurrPageIndex(pageData.getNumber());
			refPageInfo.setPageSize(pageSize);
			
			refViewVo.setRefClientPageInfo(refPageInfo);
			
			resultMap.put("dataList", listRefer);
			resultMap.put("refViewModel", refViewVo);
		}
		return resultMap;
	}
	
	@Override
	public List<Map<String, String>> filterRefJSON(@RequestBody RefViewModelVO refViewVo) {
		List<Map<String, String>> results = new ArrayList<Map<String,String>>();		
		List<Dictionary> rtnVal = this.dictionaryService.query4Refer(refViewVo.getContent());
		results = buildRtnValsOfRef(rtnVal);
		return results;
	}

	@Override
	public List<Map<String, String>> matchBlurRefJSON(@RequestBody RefViewModelVO refViewVo) {
		List<Map<String, String>> results = new ArrayList<Map<String,String>>();
		try {
			List<Dictionary> rtnVal = this.dictionaryService.query4Refer(refViewVo.getContent());
			results = buildRtnValsOfRef(rtnVal);
		} catch (Exception e) {
			logger.error("服务异常：", e);
		}
		return results;
	}

	@Override
	public List<Map<String, String>> matchPKRefJSON(@RequestBody RefViewModelVO refViewVo) {
		return null;
	}
	
	private SearchParams buildSearchParams(String searchParam) {
		SearchParams params = new SearchParams();
		Map<String,Object> results = new HashMap<String,Object>();
		if(!StringUtils.isEmpty(searchParam)) {
			results.put("refParam", searchParam);
		}
		params.setSearchMap(results);
		return params;
	}

	private PageRequest buildPageRequest(int pageNum, int pageSize, String sortColumn) {
		Sort sort = null;
		if(StringUtils.isEmpty(sortColumn) || "auto".equalsIgnoreCase(sortColumn)) {
			sort = new Sort(Sort.Direction.ASC, new String[] {"code"});
		}else {
			sort = new Sort(Sort.Direction.ASC, new String[] {sortColumn});
		}
		return new PageRequest(pageNum-1, pageSize, sort);
	}
	
	private List<Map<String,String>> buildRtnValsOfRef(List<Dictionary> listDictionary){
		List<Map<String,String>> listResult = new ArrayList<Map<String,String>>();
		if(CollectionUtils.isNotEmpty(listDictionary)) {
			for(Dictionary entity: listDictionary) {
				Map<String,String> refData = new HashMap<String,String>();
				refData.put("id", entity.getId());
				refData.put("refname", entity.getName());
				refData.put("refcode", entity.getCode());
				refData.put("remark", entity.getRemark());
				refData.put("refpk", entity.getId());
				listResult.add(refData);
			}
		}
		return listResult;
	}

	/****************************************************************/
	@Autowired
	private DictionaryService dictionaryService;

}