package com.yonyou.iuap.example.template.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.base.web.BaseController;
import com.yonyou.iuap.common.BaseEntityUtils;
import com.yonyou.iuap.example.template.entity.ExampleTemplate;
import com.yonyou.iuap.example.template.service.ExampleTemplateService;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/exampleTemplate")
public class ExampleTemplateController extends BaseController {

	private Logger logger = LoggerFactory.getLogger(ExampleTemplateController.class);

	@Autowired
	private ExampleTemplateService service;

	/**
	 * 查询分页数据
	 * 
	 * @param pageRequest
	 * @param searchParams
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public @ResponseBody Object page(PageRequest pageRequest, SearchParams searchParams) {
		Page<ExampleTemplate> data = service.selectAllByPage(pageRequest, searchParams);
		return buildSuccess(data);
	}

	/**
	 * 查询分页数据
	 * 
	 * @param pageRequest
	 * @param searchParams
	 * @return
	 */
	@RequestMapping(value = "/list1", method = RequestMethod.GET)
	public @ResponseBody Object page1(PageRequest pageRequest, SearchParams searchParams) {
		Page<ExampleTemplate> data = service.selectAllByPage(pageRequest, searchParams);
		String result = JSON.toJSONString(data);
		return result;
	}

	/**
	 * 保存数据
	 * 
	 * @param list
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public @ResponseBody Object save(@RequestBody List<ExampleTemplate> list) {
		service.save(list);
		return buildSuccess();
	}

	/**
	 * 删除数据
	 * 
	 * @param list
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public @ResponseBody Object del(@RequestBody List<ExampleTemplate> list) {
		service.batchDeleteByPrimaryKey(list);
		return buildSuccess();
	}

	/**
	 * 任务信息模版导出
	 * 
	 * @param request
	 * @param response
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/excelTemplateDownload", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Map<String, String> excelTemplateDownload(HttpServletRequest request,
			HttpServletResponse response) throws BusinessException {
		Map<String, String> result = new HashMap<String, String>();

		try {
			service.downloadExcelTemplate(response);
			result.put("status", "success");
			result.put("msg", "督办任务信息Excel模版下载成功");
		} catch (Exception e) {
			logger.error("督办任务信息Excel模版下载失败", e);
			result.put("status", "failed");
			result.put("msg", "督办任务信息Excel模版下载失败");
		}

		return result;
	}

	/**
	 * 任务信息excel导入
	 * 
	 * @param model
	 * @param request
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/excelDataImport", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> excelDataImport(
			@RequestParam(value = "excelImpFile", required = false) MultipartFile fileName, ModelMap model,
			HttpServletRequest request) throws BusinessException {
		Map<String, String> result = new HashMap<String, String>();
		try {
			service.importExcelData(fileName.getInputStream());
			result.put("status", "success");
			result.put("msg", "Excel导入成功");
		} catch (Exception e) {
			logger.error("Excel导入失败", e);
			result.put("status", "failed");
			result.put("msg", "Excel导入失败");
		}
		return result;
	}

	/**
	 * 督办任务信息导出
	 * 
	 * @param request
	 * @param response
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/excelDataExport", method = { RequestMethod.GET, RequestMethod.POST })
	public @ResponseBody Map<String, String> excelDataExport(PageRequest pageRequest, HttpServletRequest request,
			@RequestParam String ids, HttpServletResponse response) throws BusinessException {
		Map<String, String> result = new HashMap<String, String>();

		try {
			service.exportExcelData(pageRequest, response, ids);
			result.put("status", "success");
			result.put("msg", "信息导出成功");
		} catch (Exception e) {
			logger.error("Excel下载失败", e);
			result.put("status", "failed");
			result.put("msg", "Excel下载失败");
		}

		return result;
	}

	/**
	 * 打印时获取数据
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/dataForPrint", method = RequestMethod.POST)
	@ResponseBody
	public Object getDataForPrint(HttpServletRequest request) {

		String params = request.getParameter("params");
		JSONObject jsonObj = JSON.parseObject(params);
		String id = (String) jsonObj.get("id");

		ExampleTemplate vo = service.queryByPK(id);

		JSONArray mainDataJson = new JSONArray();// 主实体数据
		List<String> lsAttr = vo.getAllAttributeNames();
		JSONObject mainData = new JSONObject();

		for (String attr : lsAttr) {
			if (BaseEntityUtils.lsAttrExclude.contains(attr) || attr.equals("id_ygdemo_yw_sub")) {
				continue;
			}

			System.out.println("attr" + attr + "------" + vo.getAttribute(attr));
			mainData.put(attr, vo.getAttribute(attr));
			/*
			 * 因云打印将打印模板的字段与本方法传入的数值一一对应显示，出现显示不正确问题 例如：责任单位
			 * 显示为‘af267958-7ac1-43f3-aa97-ceee2b3c5db9’。显示的是vo中zr_dw字段的值，而我们期望为zr_dw_name字段
			 * 是否为KPI 显示为1或者0，而我们期望为是或者否 各类日期 由于字段为date类型 显示为 1522512000000，而我们期望为2018-04-01
			 * 所以转换为JSONObject时需要进行处理，增加下面一行代码
			 */
			// mainData=CommonUtils.formatDataForPrint(vo, attr, mainData);//为使打印显示正常
		}

		mainDataJson.add(mainData);// 主表只有一行

		JSONObject boAttr = new JSONObject();
		boAttr.put("ygdemo_yw_info", mainDataJson);

		System.out.println(boAttr.toString());

		return boAttr.toString();
	}

}
