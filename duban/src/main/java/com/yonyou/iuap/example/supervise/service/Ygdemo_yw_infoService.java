package com.yonyou.iuap.example.supervise.service;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.base.utils.RestUtils;
import com.yonyou.iuap.bpm.pojo.BPMFormJSON;
import com.yonyou.iuap.bpm.service.BPMSubmitBasicService;
import com.yonyou.iuap.bpm.util.BpmRestVarType;
import com.yonyou.iuap.common.utils.ExcelExportImportor;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.example.supervise.dao.Ygdemo_yw_infoMapper;
import com.yonyou.iuap.example.supervise.dao.Ygdemo_yw_subMapper;
import com.yonyou.iuap.example.supervise.entity.Ygdemo_yw_info;
import com.yonyou.iuap.example.supervise.entity.Ygdemo_yw_sub;
import com.yonyou.iuap.example.supervise.support.ParentService;
import com.yonyou.iuap.example.supervise.utils.CommonUtils;
import com.yonyou.iuap.message.CommonMessageSendService;
import com.yonyou.iuap.message.MessageEntity;
import com.yonyou.iuap.message.WebappMessageConst;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.persistence.vo.BaseEntity;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.iuap.persistence.vo.pub.VOStatus;
import com.yonyou.iuap.utils.PropertyUtil;
import com.yonyou.uap.ieop.busilog.config.annotation.BusiLogConfig;
import com.yonyou.uap.ieop.busilog.context.ThreadLocalBusiLogContext;

import yonyou.bpm.rest.request.RestVariable;

@Service
public class Ygdemo_yw_infoService extends ParentService<Ygdemo_yw_info>{
	private Logger logger = LoggerFactory.getLogger(Ygdemo_yw_infoService.class);

	@Autowired
	private Ygdemo_yw_infoMapper ywInfoMapper;

	@Autowired
	private Ygdemo_yw_subMapper ywSubMapper;
	
	@Autowired
	private Ygdemo_yw_subService ywSubService;

	@Autowired
	private BPMSubmitBasicService bpmSubmitBasicService;

	@Autowired
	private CommonMessageSendService messageservice;
	
	@Autowired
	private Ygdemo_Ref_Service ygdemo_Ref_Service;

	/**
	 * 根据传递的参数，进行分页查询
	 */
	protected Page<Ygdemo_yw_info> selectAllByPage(PageRequest pageRequest, SearchParams searchParams, String querySql) {
		
		Page<Ygdemo_yw_info> pageResult = ywInfoMapper.selectAllByPage(pageRequest, searchParams.getSearchMap(), querySql).getPage();

		if (pageResult != null && pageResult.getContent() != null && pageResult.getContent().size() > 0) {
			this.setRefName(pageResult.getContent());
		}

		return pageResult;
	}

	/** 参照id和显示字段 这里进行转换 */
	protected void setRefName(List<Ygdemo_yw_info> list) {
		
		String url1 = "/wbalone/organization/getByIds";
		String url2 = "/wbalone/userRest/getByIds";
		String url3 = "/basedoc/peopledocRef/getByIds";
		
		List<String> listZr_dw = new ArrayList<String>();
		List<String> listXb_dw = new ArrayList<String>();
		List<String> listUnitid = new ArrayList<String>();
		List<String> listCreate_name = new ArrayList<String>();
		List<String> listUpdate_name = new ArrayList<String>();
		List<String> listZbr = new ArrayList<String>();
		
		for (Ygdemo_yw_info item : list) {

			if (item.getZr_dw() != null && !listZr_dw.contains(item.getZr_dw()))
			{
				listZr_dw.add(item.getZr_dw());
			}
			
			if (item.getXb_dw() != null && !listXb_dw.contains(item.getXb_dw()))
			{
				listXb_dw.add(item.getXb_dw());
			}
			
			if (item.getUnitid() != null && !listUnitid.contains(item.getUnitid()))
			{
				listUnitid.add(item.getUnitid());
			}
			
			if (item.getCreate_name() != null && !listCreate_name.contains(item.getCreate_name()))
			{
				listCreate_name.add(item.getCreate_name());
			}
			
			if (item.getUpdate_name() != null && !listUpdate_name.contains(item.getUpdate_name()))
			{
				listUpdate_name.add(item.getUpdate_name());
			}
			
			if (item.getZbr() != null && !listZbr.contains(item.getZbr()))
			{
				listZbr.add(item.getZbr());
			}
		}
		
		Map<String, String> mapParameter1 = new ConcurrentHashMap<String, String>();
		mapParameter1.put("tenantId", "tenant");
		mapParameter1.put("data", JSONArray.toJSON(listZr_dw).toString());
		
		Map<String, String> mapParameter2 = new ConcurrentHashMap<String, String>();
		mapParameter2.put("tenantId", "tenant");
		mapParameter2.put("data", JSONArray.toJSON(listXb_dw).toString());
		
		Map<String, String> mapParameter3 = new ConcurrentHashMap<String, String>();
		mapParameter3.put("tenantId", "tenant");
		mapParameter3.put("data", JSONArray.toJSON(listUnitid).toString());
		
		Map<String, String> mapParameter4 = new ConcurrentHashMap<String, String>();
		mapParameter4.put("tenantId", "tenant");
		mapParameter4.put("userIds", JSONArray.toJSON(listCreate_name).toString());
		
		Map<String, String> mapParameter5 = new ConcurrentHashMap<String, String>();
		mapParameter5.put("tenantId", "tenant");
		mapParameter5.put("userIds", JSONArray.toJSON(listUpdate_name).toString());
		
		Map<String, String> mapParameter6 = new ConcurrentHashMap<String, String>();
		mapParameter6.put("tenantId", "tenant");
		mapParameter6.put("data", JSONArray.toJSON(listZbr).toString());
		
		Map<String, String> mapZr_dw = ygdemo_Ref_Service.convertRefName(url1, mapParameter1);
		Map<String, String> mapXb_dw = ygdemo_Ref_Service.convertRefName(url1, mapParameter2);
		Map<String, String> mapUnitid = ygdemo_Ref_Service.convertRefName(url1, mapParameter3);
		Map<String, String> mapCreate_name = ygdemo_Ref_Service.convertRefName(url2, mapParameter4);
		Map<String, String> mapUpdate_name = ygdemo_Ref_Service.convertRefName(url2, mapParameter5);
		Map<String, String> mapZbr = ygdemo_Ref_Service.convertRefName(url3, mapParameter6);
		
		for (Ygdemo_yw_info item : list) {
			if (item.getZr_dw() != null)
			{				
				item.setZr_dw_name(mapZr_dw.get(item.getZr_dw()));
			}
			
			if (item.getXb_dw() != null)
			{				
				item.setXb_dw_name(mapXb_dw.get(item.getXb_dw()));
			}
			
			if (item.getUnitid() != null)
			{
				item.setUnitid_name(mapUnitid.get(item.getUnitid()));
			}
			
			if (item.getCreate_name() != null)
			{				
				item.setCreate_name_name(mapCreate_name.get(item.getCreate_name()));
			}
			
			if (item.getUpdate_name() != null)
			{
				item.setUpdate_name_name(mapUpdate_name.get(item.getUpdate_name()));
			}
			
			if (item.getZbr() != null)
			{
				item.setZbr_name(mapZbr.get(item.getZbr()));
			}
		}
	}

	@SuppressWarnings("unchecked")
	protected <TChildEntity extends BaseEntity> Class<TChildEntity>[] getChildTypes() {
		return new Class[] { Ygdemo_yw_sub.class };
	}

	@BusiLogConfig("Ygdemo_yw_info_save")
	public Ygdemo_yw_info save(Ygdemo_yw_info entity) {
		//entity.setCreate_name(InvocationInfoProxy.getUserid());
		//entity.setUpdate_name(InvocationInfoProxy.getUserid());
		
		entity.setTenant_id(InvocationInfoProxy.getTenantid());

		// 获取单据号
		String billObjCode = "ygdemo";
		String pkAssign = "";
		String billCode = null;
		
		if (entity.getId() == null) {
			//插入数据时，获取编码规则
			billCode = this.getBillCode(billObjCode, pkAssign, entity);
			
			entity.setCode(billCode);
			
			entity.setStatus(VOStatus.NEW);  //新增
		}
		else
		{
			entity.setStatus(VOStatus.UPDATED);  //编辑
		}

		try {
			entity = saveEntity(entity);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			if (entity.getId() == null)
			{
				// 事物补偿机制 回退单据号
				this.returnBillCode(billObjCode, pkAssign, entity, billCode);
			}
			
			throw new BusinessException("督办任务保存失败", e);
		}
		
		try
		{
			if (entity.getStatus() == VOStatus.NEW)
			{
				//发送站内消息
				sendMessage(entity);
				
				//发送消息到消息队列（rabbitmq消息队列）
				//sendMqMessage(entity);
			}
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage(), ex);
		}

		return entity;
	}

	private Ygdemo_yw_info saveEntity(Ygdemo_yw_info entity) {
		entity.setTs(new Timestamp((new Date()).getTime()));
		
		// 保存主表数据
		if (entity.getStatus() == VOStatus.NEW) {
			entity.setId(UUID.randomUUID().toString());
			entity.setDr(0);// 未删除标识

			// 插入数据
			ywInfoMapper.insert(entity);
		} else {
			// 更新数据
			ywInfoMapper.update(entity);
		}

		// 保存子表数据
		if (entity.getId_ygdemo_yw_sub() != null && entity.getId_ygdemo_yw_sub().size() > 0) {
			for (Ygdemo_yw_sub child : entity.getId_ygdemo_yw_sub()) {
				child.setFk_id_ygdemo_yw_sub(entity.getId());
				
				child.setTs(new Date());

				if (child.getSub_id() == null) {
					child.setDr(entity.getDr());

					child.setSub_id(UUID.randomUUID().toString());

					// 插入数据
					ywSubMapper.insert(child);
				} else {
					// 更新数据
					ywSubMapper.update(child);
				}
			}
		}

		return entity;
	}
	
	/**
	 * 获取编码规则
	 * 
	 * @param billObjCode 编码对象code
	 * @param pkAssign 分配关系
	 * @param entity
	 * @return
	 */
	private String getBillCode(String billObjCode,String pkAssign,Ygdemo_yw_info entity){
		String billvo = JSONObject.toJSONString(entity);
		
		//调用iuap-saas-billcode-service服务
		String getcodeurl = PropertyUtil.getPropertyByKey("billcodeservice.base.url")  + "/billcoderest/getBillCode";
		
		Map<String,String> data = new HashMap<String,String>();
		data.put("billObjCode", billObjCode);  //编码对象code编码，传入ygdemo
		data.put("pkAssign", pkAssign);   //分配对象主键 ，传入空值
		data.put("billVo", billvo);
		
		JSONObject getbillcodeinfo = RestUtils.getInstance().doPost(getcodeurl, data, JSONObject.class);
		logger.debug(getbillcodeinfo.toJSONString());
		String getflag = getbillcodeinfo.getString("status");
		String billcode = getbillcodeinfo.getString("billcode");
		
		if("failed".equalsIgnoreCase(getflag)){
			String errormsg = getbillcodeinfo.getString("msg");
			logger.error("{billObjCode:" + billObjCode + ",pkAssign:" + pkAssign + ",billvo:" + billvo + "},错误信息:" + errormsg);
			throw new BusinessException("获取编码规则发生错误", errormsg);
		}
		
		return billcode;
	}


	/**
	 * 回退单据号，以保证单据号连号的业务需要
	 * 
	 * @param billObjCode
	 *            编码对象code
	 * @param pkAssign
	 *            分配关系
	 * @param entity
	 * @return
	 */
	private void returnBillCode(String billObjCode, String pkAssign, Ygdemo_yw_info entity, String billCode) {
		String billvo = JSONObject.toJSONString(entity);

		String returncodeurl = PropertyUtil.getPropertyByKey("billcodeservice.base.url")
				+ "/billcoderest/returnBillCode";

		Map<String, String> data = new HashMap<String, String>();
		data.put("billObjCode", billObjCode);
		data.put("pkAssign", pkAssign);
		data.put("billVo", billvo);
		data.put("billCode", billCode);

		JSONObject returnbillcodeinfo = RestUtils.getInstance().doPost(returncodeurl, data, JSONObject.class);

		logger.debug(returnbillcodeinfo.toJSONString());
		String returnflag = returnbillcodeinfo.getString("status");

		if ("failed".equalsIgnoreCase(returnflag)) {
			String errormsg = returnbillcodeinfo.getString("msg");
			logger.error("{billObjCode:" + billObjCode + ",pkAssign:" + pkAssign + ",billCode:" + billCode + ",billvo:"
					+ billvo + "},错误信息:" + errormsg);
			throw new BusinessException("退回单据号失败", errormsg);
		}
	}

	private void sendMqMessage(Ygdemo_yw_info entity) {
		messageservice.sendMqMessage("iuap-direct-exchange", "simple_queue_key", entity);// 点对点发送
		messageservice.sendMqMessage("iuap-fanout-exchange", null, entity);// 广播订阅发送
		messageservice.sendMqMessage("iuap-topic-exchange", "iuap_qy.demo", entity);// 主题订阅发送。选择键（routing_key）必须是由点隔开的一系列的标识符组成
	}

	private void sendMessage(Ygdemo_yw_info entity) {
		MessageEntity msg = new MessageEntity();
		
		msg.setSendman(InvocationInfoProxy.getUserid());// 发送者：当前登录人
		msg.setChannel(new String[] { WebappMessageConst.CHANNEL_SYS, WebappMessageConst.CHANNEL_SMS });// sys是消息中心消息  note 短信
		msg.setRecevier(new String[] { InvocationInfoProxy.getUserid() });// 接受者：当前登录人、督办人、主办人、责任人、协办人
		
		msg.setTemplatecode("ygdemo");// 消息模板编号
		
		//单据ID
		msg.setBillid(entity.getCode());
		msg.setTencentid(InvocationInfoProxy.getTenantid());
		msg.setMsgtype(WebappMessageConst.MESSAGETYPE_NOTICE);
		msg.setSubject("站内消息标题");
		msg.setContent("您新建了一条单据，单据号是:" + entity.getCode() + ", 单据名称是: " + entity.getName());

		JSONObject busiData = new JSONObject();
		busiData.put("dbrw_info.code", entity.getCode());
		busiData.put("dbrw_info.name", entity.getName());
		// busidate.put("dbrw_info.id", entity.getId());

		messageservice.sendTemplateMessage(msg, busiData);
		
		//messageservice.sendTextMessage(msg, busiData);
	}

	@BusiLogConfig("Ygdemo_yw_info_batchDeleteEntity")
	public void batchDeleteWithChild(List<Ygdemo_yw_info> list) {		
		// 删除主表数据
		ywInfoMapper.batchDeleteByPK(list);

		List<Ygdemo_yw_sub> subList = new ArrayList<Ygdemo_yw_sub>();
		Ygdemo_yw_sub subEntity = null;

		for (Ygdemo_yw_info obj : list) {
			subEntity = new Ygdemo_yw_sub();
			subEntity.setFk_id_ygdemo_yw_sub(obj.getId());

			subList.add(subEntity);
		}

		// 删除子表数据
		ywSubMapper.batchDeleteByFK(subList);

		String billObjCode = "ygdemo";
		String pkAssign = "";
		String returncodeurl = PropertyUtil.getPropertyByKey("billcodeservice.base.url")
				+ "/billcoderest/returnBillCode";

		Map<String, String> errorBillCodeMap = new HashMap<String, String>();
		for (Ygdemo_yw_info info : list) {
			try
			{
				String billvo = JSONObject.toJSONString(info);
				Map<String, String> data = new HashMap<String, String>();
				data.put("billObjCode", billObjCode);
				data.put("pkAssign", pkAssign);
				data.put("billVo", billvo);
				data.put("billCode", info.getCode());
	
				JSONObject returnbillcodeinfo = RestUtils.getInstance().doPost(returncodeurl, data, JSONObject.class);
	
				String returnflag = returnbillcodeinfo.getString("status");
				if ("failed".equalsIgnoreCase(returnflag)) {
					String errormsg = returnbillcodeinfo.getString("msg");
					String tip = "编码对象：" + billObjCode + " 分配关系：" + pkAssign + " 单据主键：" + info.getId() + " 单据号："
							+ info.getCode();
					errorBillCodeMap.put(tip, errormsg);
				}
			}
			catch(Exception ex)
			{
				logger.error(ex.getMessage(), ex);
			}
		}
	}

	/**
	 * 提交流程
	 * 
	 * @param objs
	 * @param processDefineCode
	 * @throws BusinessException
	 */
	public void batchSubmitEntity(List<Ygdemo_yw_info> objs, String processDefineCode) throws BusinessException {
		Ygdemo_yw_info yg = objs.get(0);
		BPMFormJSON bpmform = buildBPMFormJSON(processDefineCode, yg);

		JSONObject resultJsonObject = bpmSubmitBasicService.submit(bpmform);

		if (isSuccess(resultJsonObject)) {
			yg.setState(1);// 从未提交状态改为已提交状态;
			
			//修改DB表数据
			save(yg);
		} else if (isFail(resultJsonObject)) {
			String msg = resultJsonObject.get("msg").toString();
			throw new BusinessException("提交启动流程实例发生错误，请联系管理员！错误原因：" + msg);
		}
	}
	
	//收回
	public JSONObject batchUnsubmitEntity(List<Ygdemo_yw_info> objs) {
		Ygdemo_yw_info yg = objs.get(0);
		yg.setState(0);// 从已提交状态改为未提交状态;
	
		//修改DB表数据
		save(yg);
		
		return bpmSubmitBasicService.unsubmit(yg.getId());
	}
	
	private boolean isSuccess(JSONObject resultJsonObject){
		return resultJsonObject.get("flag").equals("success");
	}
	private boolean isFail(JSONObject resultJsonObject){
		return resultJsonObject.get("flag").equals("fail");
	}

	/**
	 * 设置BPMFormJSON
	 * 
	 * @param processDefineCode
	 * @param ygdemo
	 * @return
	 * @throws  
	 */
	public BPMFormJSON buildBPMFormJSON(String processDefineCode, Ygdemo_yw_info ygdemo){
		try
		{
			BPMFormJSON bpmform = new BPMFormJSON();
			bpmform.setProcessDefinitionKey(processDefineCode);
			
			String userName = InvocationInfoProxy.getUsername();
			
			userName = java.net.URLDecoder.decode(userName,"utf-8");
			
			userName = java.net.URLDecoder.decode(userName,"utf-8");
			
			String title = userName + "提交的【督办】,单号 是" + ygdemo.getCode() + ", 请审批";
			
			bpmform.setTitle(title);
			// 单据id
			bpmform.setFormId(ygdemo.getId());
			// 单据号
			bpmform.setBillNo(ygdemo.getCode());
			// 制单人
			bpmform.setBillMarker(InvocationInfoProxy.getUserid());
			// 组织
			String orgId = "";// usercxt.getSysUser().getOrgId() ;
			bpmform.setOrgId(orgId);
			// 其他变量
			bpmform.setOtherVariables(assemblingOtherVariables(ygdemo));
			// 单据url
			bpmform.setFormUrl("/example/pages/ygdemo_yw_info/ygdemo_yw_info.js");
			// 流程实例名称
			bpmform.setProcessInstanceName(title);
			// 流程审批后，执行的业务处理类(controller对应URI前缀)
			String url = "/example/ygdemo_yw_info";
			bpmform.setServiceClass(url);
			return bpmform;
		}
		catch(Exception ex)
		{
			logger.error(ex.getMessage(), ex);
			
			throw new RuntimeException(ex.getMessage());
		}
	}

	/**
	 * 拼装其他变量
	 * 
	 * @param fields
	 * @return
	 */
	public List<RestVariable> assemblingOtherVariables(Ygdemo_yw_info yg) {
		Field[] fields = yg.getClass().getDeclaredFields();
		List<RestVariable> otherVariables = new ArrayList<RestVariable>();

		for (Field field : fields) {
			Class<?> type = field.getType();
			String restVariavleType = BpmRestVarType.ClassToRestVariavleTypeMap.get(type);
			Object fieldValue = yg.getAttribute(field.getName());

			if (restVariavleType == null || fieldValue == null) {
				continue;
			}

			RestVariable var = new RestVariable();
			var.setName(field.getName());
			
			if (restVariavleType.equals("date"))
			{
				//var.setValue("2017-10-10 10:30:30");
			}
			else
			{
				var.setValue(fieldValue);
			}
			
			var.setType(restVariavleType);
			
			otherVariables.add(var);
		}
		return otherVariables;
	}

	public List<Ygdemo_yw_info> getByIds(List<String> ids) {
		List<Ygdemo_yw_info> list = ywInfoMapper.getByIds(ids);
		return list;
	}

	public Ygdemo_yw_info queryByPK(String ygdemoId) {
		List<String> ids = new ArrayList<String>();

		ids.add(ygdemoId);

		List<Ygdemo_yw_info> list = ywInfoMapper.getByIds(ids);
		
		if (list != null && list.size() > 0)
		{
			this.setRefName(list);
		}

		if (list != null && list.size() > 0) {
			Ygdemo_yw_info entity = list.get(0);
			
			List<Ygdemo_yw_sub> subList = ywSubMapper.findYgdemo_yw_subByFK(entity.getId());
			
			if (subList != null && subList.size() > 0)
			{
				ywSubService.setRefName(subList);
			}
			
			for(Ygdemo_yw_sub ygdemo_yw_sub : subList)
			{
				ygdemo_yw_sub.setId(ygdemo_yw_sub.getFk_id_ygdemo_yw_sub());
			}
			
			if (subList != null && subList.size() > 0)
			{
				entity.setId_ygdemo_yw_sub(subList);
			}
			
			return entity;
		} else {
			return null;
		}
	}

	private static String values = "{'id':'主键','code':'编码','name':'名称','state':'状态','tenant_id':'租户','create_time':'创建时间'}";

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
		ExcelExportImportor.downloadExcelTemplate(response, getHeadInfo(), "督办任务详细", "督办任务模版");
	}

	/**
	 * 下载excel模版文件
	 * 
	 * @param response
	 * @throws Exception
	 */
	public void downloadExcelTemplate2(HttpServletResponse response) throws Exception {
		ExcelExportImportor.downloadExcelFileTemplate(response, "督办任务模版.xls", "dubanrenwu.xls");
	}

	/**
	 * 导出excel数据
	 * 
	 * @param pageRequest
	 * @param response
	 * @throws Exception
	 */
	public void exportExcelData(PageRequest pageRequest, HttpServletResponse response, String ids) throws Exception {
		List<Ygdemo_yw_info> list = null;
		
		if (null != ids && ids.length() > 0) {
			String[] pks = ids.split(",");
			list = this.getByIds(Arrays.asList(pks));
		} else {
			SearchParams searchParams = new SearchParams();
			
			Page<Ygdemo_yw_info> page = selectAllByPage(pageRequest, searchParams, Ygdemo_yw_info.class);
			list = page.getContent();
		}
		
		ExcelExportImportor.writeExcel(response, list, getHeadInfo(), "督办任务详细", "督办任务信息");
	}

	/**
	 * 导入excel数据
	 * 
	 * @param excelStream
	 */
	public void importExcelData(InputStream excelStream) throws Exception {
		List<Ygdemo_yw_info> list = ExcelExportImportor.loadExcel(excelStream, getHeadInfo(), Ygdemo_yw_info.class);

		for (Ygdemo_yw_info entity : list) {
			if (entity.getId() == null) {
				entity.setStatus(VOStatus.NEW);  //新增
			}
			else
			{
				entity.setStatus(VOStatus.UPDATED);  //编辑
			}
			saveEntity(entity);
		}
	}
	
	private void setLocalIp()
	{
		String localIp = CommonUtils.getLocalIp();
		InvocationInfoProxy.setExtendAttribute("_ip", localIp);
		ThreadLocalBusiLogContext.get().put("_ip", localIp);
	}
	
	protected void processFieldDataPermResTypeMap(Map<String, String> fieldDataPermResTypeMap){
		fieldDataPermResTypeMap.put("zr_dw", "organization"); //字段与权限资源名称的对应关系
	}

}
