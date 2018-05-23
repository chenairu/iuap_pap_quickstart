package com.yonyou.iuap.example.reference.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.iuap.example.contacts.entity.Organization;
import com.yonyou.iuap.example.contacts.service.OrganizationService;

import iuap.ref.sdk.refmodel.model.AbstractTreeRefModel;
import iuap.ref.sdk.refmodel.vo.RefViewModelVO;

@RestController
@RequestMapping({"/reference/organization"})
public class OrganizationRefController extends AbstractTreeRefModel{

	@Override
	public Map<String, Object> getCommonRefData(@RequestBody RefViewModelVO refViewVo) {
	    List<Map<String, Object>> list4Tree = new ArrayList<Map<String,Object>>();
	    List<Organization> listData = this.organizationService.findAll();
	    for(Organization organization : listData) {
		    Map<String, Object> curData = new HashMap<String, Object>();
		    curData.put("id", organization.getInstitid());
		    curData.put("pid", organization.getParent_id());
		    curData.put("refcode", organization.getInstit_code());
		    curData.put("refname", organization.getInstit_name());
		    curData.put("refpk", organization.getInstitid());
		    
		    //isLeaf
		    list4Tree.add(curData);
	    }
	    
	    Map<String,Object> result = new HashMap<String,Object>();
	    result.put("dataList", list4Tree);
	    result.put("refViewModel", refViewVo);
	    return result;
	}
	
	@Override
	public List<Map<String, String>> filterRefJSON(@RequestBody RefViewModelVO refViewVo) {
	    List<Map<String, String>> list4Tree = new ArrayList<Map<String,String>>();
	    List<Organization> listData = organizationService.queryList("searchParam", refViewVo.getContent());
	    for(Organization organization : listData) {
		    Map<String, String> curData = new HashMap<String, String>();
		    curData.put("id", organization.getInstitid());
		    curData.put("pid", organization.getParent_id());
		    curData.put("refcode", organization.getInstit_code());
		    curData.put("refname", organization.getInstit_name());
		    curData.put("refpk", organization.getInstitid());
		    
		    //isLeaf
		    list4Tree.add(curData);
	    }
	    return list4Tree;
	}

	@Override
	public List<Map<String, String>> matchBlurRefJSON(@RequestBody RefViewModelVO refViewVo) {
	    List<Map<String, String>> list4Tree = new ArrayList<Map<String,String>>();
	    List<Organization> listData = organizationService.queryList("searchParam", refViewVo.getContent());
	    for(Organization organization : listData) {
		    Map<String, String> curData = new HashMap<String, String>();
		    curData.put("id", organization.getInstitid());
		    curData.put("pid", organization.getParent_id());
		    curData.put("refcode", organization.getInstit_code());
		    curData.put("refname", organization.getInstit_name());
		    curData.put("refpk", organization.getInstitid());
		    
		    //isLeaf
		    list4Tree.add(curData);
	    }
	    return list4Tree;
	}

	@Override
	public List<Map<String, String>> matchPKRefJSON(@RequestBody RefViewModelVO refViewVo) {
		return null;
	}
	
	/*********************************************************************/
	@Autowired
	private OrganizationService organizationService;

}