/*package com.yonyou.uap.wb.web.controller.ref;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.yonyou.uap.wb.common.BusinessException;
import com.yonyou.uap.wb.common.UserCenterIntegException;
import com.yonyou.uap.wb.entity.management.App;
import com.yonyou.uap.wb.entity.management.AppArea;
import com.yonyou.uap.wb.entity.management.AppGroup;
import com.yonyou.uap.wb.process.model.AppAreaGroup;
import com.yonyou.uap.wb.service.management.IAppAreaService;
import com.yonyou.uap.wb.service.management.IAppGroupService;
import com.yonyou.uap.wb.service.management.IAppService;
import com.yonyou.uap.wb.utils.CommonUtils;

import iuap.ref.ref.RefClientPageInfo;
import iuap.ref.sdk.refmodel.model.AbstractTreeGridRefModel;
import iuap.ref.sdk.refmodel.vo.RefViewModelVO;
import iuap.uitemplate.base.context.Platform;

*//**
 * ui模板组织参照
 * 
 * @project workbench-web
 * @company yonyou
 * @version 1.0
 * @author qianmz
 * @date 2016年11月2日
 *//*
@Controller
@RequestMapping(value = "/applicationRef")
public class WBAppTreeGridRefController extends AbstractTreeGridRefModel {

	private static final Logger log = LoggerFactory.getLogger(WBAppTreeGridRefController.class);

	@Autowired
	private IAppService appService;

	@Autowired
	private IAppAreaService appAreaService;
	@Autowired
	private IAppGroupService appGroupService;
	

	String REFNAME = "refname";
	String REFCODE = "refcode";
	String REFPK = "refpk";
	String REFAREANAME = "refareaname";
	String REFGROUPNAME = "refgroupname";
	
	public RefViewModelVO getRefModelInfo(@RequestBody RefViewModelVO refViewModel) {
		refViewModel = super.getRefModelInfo(refViewModel);
		refViewModel.setRefName("小应用");
		refViewModel.setRootName("全部");
		refViewModel.setStrFieldName(new String[] { "编码", "名称","领域","分组"});
		refViewModel.setStrFieldCode(new String[] { "refcode", "refname", "refareaname","refgroupname" });
		refViewModel.setDefaultFieldCount(4);
		refViewModel.setIsMultiSelectedEnabled(false);
		return refViewModel;
	}

	public List<Map<String, String>> blobRefClassSearch(@RequestBody RefViewModelVO refViewModel) {
		List<Map<String,String>> list = new ArrayList<Map<String,String>>();

		String REFNAME = "name";
		String REFCODE = "refcode";
		String REFPK = "refpk";
		String PID = "pid";
		String ID = "id";

		try {
			List<AppAreaGroup> appAreaGroup = appAreaService.listWithGroup();
			for(AppAreaGroup ag :appAreaGroup){
				AppArea area = ag.getArea();
				List<AppGroup> groups = ag.getGroup();
				
				HashMap<String,String> ret = new HashMap<String,String>();
				
				ret.put(REFPK, area.getId());
				ret.put(REFCODE, area.getId());
				ret.put(REFNAME, area.getAreaName());
				ret.put(PID, "");
				ret.put(ID, area.getId());
				list.add(ret);
				
				
				if(groups!=null && !groups.isEmpty()){
					for(AppGroup group : groups){
						HashMap<String,String> groupret = new HashMap<String,String>();
						groupret.put(REFPK, group.getId());
						groupret.put(REFCODE, group.getId());
						groupret.put(REFNAME, group.getName());
						groupret.put(PID, area.getId());
						groupret.put(ID, group.getId());
						list.add(groupret);
					}
				}
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		} catch (UserCenterIntegException e) {
			e.printStackTrace();
		}

		return list;
	}

	public Map<String, Object> blobRefSearch(@RequestBody RefViewModelVO refViewModel) {
		Map<String,Object> map = new HashMap<String,Object>();
		
		List<HashMap<String,String>> list  = new ArrayList<HashMap<String,String>>();
		try {
			List<App> apps = appService.queryAppTreeGridData(CommonUtils.getTenantId(), refViewModel.getCondition(), "", refViewModel.getContent());
			
			for(App app : apps){
				HashMap<String,String> ret = new HashMap<String,String>();
				ret.put(REFPK, app.getAppCode());
				ret.put(REFCODE, app.getAppCode());
				ret.put(REFNAME, app.getAppName());
				ret.put(REFAREANAME, app.getAreaName());
				ret.put(REFGROUPNAME, app.getGoupName());
				list.add(ret);
			}

			RefClientPageInfo refClientPageInfo = refViewModel.getRefClientPageInfo();
			refClientPageInfo.setPageCount(2);
			refClientPageInfo.setPageSize(50);
			refViewModel.setRefClientPageInfo(refClientPageInfo);

			map.put("dataList", list);
			map.put("refViewModel", refViewModel);
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return map;
	}

	protected JSONArray getJsonArray(List<HashMap<String, String>> list) {
		JSONArray arr = null;
		try {
			arr = new JSONArray();
			for (int i = 0; i < list.size(); ++i) {
				HashMap<String, String> result = (HashMap<String, String>) list.get(i);
				JSONObject json = new JSONObject();
				for (Map.Entry<String, String> entry : result.entrySet()) {
					json.putOpt((String) entry.getKey(),
							(entry.getValue() == null) ? "null" : (String) entry.getValue());
				}
				arr.put(json);
			}
		} catch (Exception e) {
			Platform.getUITemplatLogger().error(e.getMessage(), e);
		}
		return arr;
	}

	public List<Map<String, String>> filterRefJSON(@RequestBody RefViewModelVO refViewModel) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			List<App> apps = appService.queryAppTreeGridData(CommonUtils.getTenantId(), refViewModel.getCondition(), "", refViewModel.getContent());
			
			for(App app : apps){
				HashMap<String,String> ret = new HashMap<String,String>();
				ret.put(REFPK, app.getAppCode());
				ret.put(REFCODE, app.getAppCode());
				ret.put(REFNAME, app.getAppName());
				ret.put(REFAREANAME, app.getAreaName());
				ret.put(REFGROUPNAME, app.getGoupName());
				list.add(ret);
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Map<String, String>> matchPKRefJSON(@RequestBody RefViewModelVO refViewModel) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			List<App> apps = appService.findByAppCodesAndTenantId(Arrays.asList(refViewModel.getPk_val()), CommonUtils.getTenantId());
				for(App app : apps){
					HashMap<String,String> ret = new HashMap<String,String>();
					ret.put(REFPK, app.getAppCode());
					ret.put(REFCODE, app.getAppCode());
					ret.put(REFNAME, app.getAppName());
					ret.put(REFAREANAME, app.getAreaName());
					ret.put(REFGROUPNAME, app.getGoupName());
					list.add(ret);
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<Map<String, String>> matchBlurRefJSON(RefViewModelVO refViewModel) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			List<App> apps = appService.queryAppTreeGridData(CommonUtils.getTenantId(), refViewModel.getCondition(), "", refViewModel.getContent());
			
			for(App app : apps){
				HashMap<String,String> ret = new HashMap<String,String>();
				ret.put(REFPK, app.getAppCode());
				ret.put(REFCODE, app.getAppCode());
				ret.put(REFNAME, app.getAppName());
				ret.put(REFAREANAME, app.getAreaName());
				ret.put(REFGROUPNAME, app.getGoupName());
				list.add(ret);
			}
		} catch (BusinessException e) {
			e.printStackTrace();
		}
		return list;
	}
}*/