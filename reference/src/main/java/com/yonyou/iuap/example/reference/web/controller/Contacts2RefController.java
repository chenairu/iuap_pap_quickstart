package com.yonyou.iuap.example.reference.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yonyou.iuap.example.contacts.entity.Contacts;
import com.yonyou.iuap.example.contacts.entity.Organization;
import com.yonyou.iuap.example.contacts.service.ContactsService;
import com.yonyou.iuap.example.contacts.service.OrganizationService;

import iuap.ref.ref.RefClientPageInfo;
import iuap.ref.sdk.refmodel.model.AbstractTreeGridRefModel;
import iuap.ref.sdk.refmodel.vo.RefViewModelVO;

/**
 * 树表参照
 * @author Aton
 */
@Controller
@RequestMapping(value = "/reference/treegrid")
public class Contacts2RefController extends AbstractTreeGridRefModel {
	
	private Logger logger = LoggerFactory.getLogger(Contacts2RefController.class);
	
	private static final String REF_CODE = "refcode";
	private static final String REF_NAME = "refname";
	private static final String REF_PK = "refpk";
	private static final String REF_TEL = "reftel";
	private static final String REF_EMAIL = "refemail";
	
	public RefViewModelVO getRefModelInfo(@RequestBody RefViewModelVO refViewModel) {
		refViewModel = super.getRefModelInfo(refViewModel);
		refViewModel.setRefName("联系人");
		refViewModel.setRootName("全部");
		refViewModel.setStrFieldName(new String[] { "编码", "名称","电话","邮箱"});
		refViewModel.setStrFieldCode(new String[] { REF_CODE, REF_NAME, REF_TEL, REF_EMAIL });
		refViewModel.setDefaultFieldCount(4);
		refViewModel.setIsMultiSelectedEnabled(false);
		return refViewModel;
	}

	@Override
	public List<Map<String, String>> blobRefClassSearch(@RequestBody RefViewModelVO refViewVo) {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();

		String REFNAME = "name";
		String REFCODE = "refcode";
		String REFPK = "refpk";
		String PID = "pid";
		String ID = "id";

		try {
			Map<String,Object> params = new HashMap<String,Object>();
			List<Organization> listOrgs = organizationService.queryList(params);
			for(Organization org : listOrgs){
				HashMap<String,String> ret = new HashMap<String,String>();
				
				ret.put(REFPK, org.getInstitid());
				ret.put(REFCODE, org.getInstitcode());
				ret.put(REFNAME, org.getInstitname());
				ret.put(PID, org.getParentid());
				ret.put(ID, org.getInstitid());
				list.add(ret);
			}
		} catch (Exception e) {
			logger.error("联系人参照信息——Tree数据构造出错!", e);
		}
		return list;
	}



	@Override
	public Map<String, Object> blobRefSearch(@RequestBody RefViewModelVO refViewVo) {
		Map<String,Object> map = new HashMap<String,Object>();
		
		List<HashMap<String,String>> list  = new ArrayList<HashMap<String,String>>();
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("institid", refViewVo.getCondition());				//节点ID
			params.put("refParam", refViewVo.getContent());					//查询条件			
			List<Contacts> listContacts = contactsService.queryList(params);
			
			for(Contacts contacts : listContacts){
				HashMap<String,String> ret = new HashMap<String,String>();
				ret.put(REF_PK, contacts.getId());
				ret.put(REF_CODE, contacts.getPeocode());
				ret.put(REF_NAME, contacts.getPeoname());
				ret.put(REF_TEL, contacts.getWorktel());
				ret.put(REF_EMAIL, contacts.getEmail());
				list.add(ret);
			}

			RefClientPageInfo refClientPageInfo = refViewVo.getRefClientPageInfo();
			refClientPageInfo.setPageCount(2);
			refClientPageInfo.setPageSize(50);
			refViewVo.setRefClientPageInfo(refClientPageInfo);

			map.put("dataList", list);
			map.put("refViewModel", refViewVo);
		} catch (Exception e) {
			logger.error("联系人参照信息——列表数据构造出错!", e);
		}
		return map;
	}


	@Override
	public List<Map<String, String>> filterRefJSON(@RequestBody RefViewModelVO refViewVo) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("institid", refViewVo.getCondition());				//节点ID
			params.put("refParam", refViewVo.getContent());					//查询条件
			List<Contacts> listContacts = contactsService.queryList(params);
			for(Contacts contacts : listContacts){
				HashMap<String,String> ret = new HashMap<String,String>();
				ret.put(REF_PK, contacts.getId());
				ret.put(REF_CODE, contacts.getPeocode());
				ret.put(REF_NAME, contacts.getPeoname());
				ret.put(REF_TEL, contacts.getWorktel());
				ret.put(REF_EMAIL, contacts.getEmail());
				list.add(ret);
			}
		} catch (Exception e) {
			logger.error("联系人参照信息出错!", e);
		}
		return list;
	}



	@Override
	public List<Map<String, String>> matchBlurRefJSON(RefViewModelVO arg0) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			Map<String,Object> params = new HashMap<String,Object>();
			List<Contacts> listContacts = contactsService.queryList(params);
			for(Contacts contacts : listContacts){
				HashMap<String,String> ret = new HashMap<String,String>();
				ret.put(REF_PK, contacts.getId());
				ret.put(REF_CODE, contacts.getPeocode());
				ret.put(REF_NAME, contacts.getPeoname());
				ret.put(REF_TEL, contacts.getWorktel());
				ret.put(REF_EMAIL, contacts.getEmail());
				list.add(ret);
			}
		} catch (Exception e) {
			logger.error("联系人参照信息出错!", e);
		}
		return list;
	}



	@Override
	public List<Map<String, String>> matchPKRefJSON(RefViewModelVO arg0) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			List<Contacts> listContacts = contactsService.queryList("1", "");
			for(Contacts contacts : listContacts){
				HashMap<String,String> ret = new HashMap<String,String>();
				ret.put(REF_PK, contacts.getId());
				ret.put(REF_CODE, contacts.getPeocode());
				ret.put(REF_NAME, contacts.getPeoname());
				ret.put(REF_TEL, contacts.getWorktel());
				ret.put(REF_EMAIL, contacts.getEmail());
				list.add(ret);
			}
		} catch (Exception e) {
			logger.error("联系人参照信息出错!", e);
		}
		return list;
	}

	
	/*****************************************************************************/
	@Autowired
	private ContactsService contactsService;
	@Autowired
	private OrganizationService organizationService;

}