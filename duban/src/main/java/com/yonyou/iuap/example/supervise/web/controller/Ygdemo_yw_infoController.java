package com.yonyou.iuap.example.supervise.web.controller;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import javax.imageio.ImageIO;
import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Order;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.util.concurrent.RateLimiter;
import com.yonyou.iuap.CSRFToken;
import com.yonyou.iuap.base.utils.RestUtils;
import com.yonyou.iuap.base.web.BaseController;
import com.yonyou.iuap.common.BaseEntityUtils;
import com.yonyou.iuap.example.supervise.entity.Ygdemo_yw_info;
import com.yonyou.iuap.example.supervise.entity.Ygdemo_yw_sub;
import com.yonyou.iuap.example.supervise.service.Ygdemo_yw_infoService;
import com.yonyou.iuap.example.supervise.support.AppAsyncListener;
import com.yonyou.iuap.example.supervise.support.AsyncRequestProcessor;
import com.yonyou.iuap.example.supervise.utils.CommonUtils;
import com.yonyou.iuap.file.FileManager;
import com.yonyou.iuap.file.utils.BucketPermission;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.constants.RequestStatusEnum;
import com.yonyou.iuap.mvc.type.JsonResponse;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.persistence.vo.pub.BusinessException;
import com.yonyou.iuap.util.RSAUtils;
import com.yonyou.iuap.utils.PropertyUtil;

@RestController
@RequestMapping(value = "/ygdemo_yw_info")
public class Ygdemo_yw_infoController extends BaseController {
	private Logger logger = LoggerFactory.getLogger(Ygdemo_yw_infoController.class);

	@Autowired
	private Ygdemo_yw_infoService service;

	@Autowired
	private ThreadPoolTaskExecutor threadPoolTaskExecutor;

	@Autowired
	private com.yonyou.iuap.cache.CacheManager cacheManager;

	private AtomicInteger requestCount;

	private RateLimiter rateLimiter = RateLimiter.create(100); // 令牌桶容量为100，即每10毫秒产生1个令牌,TPS为100

	@RequestMapping(value = "/doRequestWithLimiter", method = RequestMethod.GET)
	@ResponseBody
	public Object doRequestWithLimiter() {
		// 限制总请求数，防止并发太大       
		logger.debug("in doRequestWithLimiter.");
		String msg = "";

		if (requestCount == null) {
			// 查询库存数,然后初始化requestCount
			requestCount = new AtomicInteger(100);// 假定库存数为100
		}

		try {
			if (requestCount.decrementAndGet() < 0) {
				// 可根据业务情况减小数量
				msg = "当前请求人数过多，请稍后再试";
				return buildGlobalError(msg);
			} else {
				msg = "您的请求已受理";
				logger.debug("execute doRequestWithLimiter");
			}
		} finally {
			requestCount.incrementAndGet();// 可根据业务情况修改
		}

		return buildSuccess(msg);
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	@CSRFToken(verify = false)
	public Object list(PageRequest pageRequest,
			@FrontModelExchange(modelType = Ygdemo_yw_info.class) SearchParams searchParams) {
		logger.debug("execute data search.");

		// 判断传入的参数中是否有非法字符，防止SQL注入
		Iterator<Order> it = pageRequest.getSort().iterator();
		while (it.hasNext()) {
			Order order = it.next();
			String prop = order.getProperty();

			if (CommonUtils.sqlValidate(prop)) {
				String msg = "您发送请求的参数中含有非法字符";
				logger.warn(msg);
				return buildGlobalError(msg);
			}
		}

		// Codec ORACLE_CODEC = new OracleCodec();

		// 查询参数解码
		CommonUtils.decode(searchParams);
		Iterator<String> searchMapIt = searchParams.getSearchMap().keySet().iterator();
		while (searchMapIt.hasNext()) {
			String key = searchMapIt.next();
			String value = (String) searchParams.getSearchMap().get(key);
			// key = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, key);
			// value = ESAPI.encoder().encodeForSQL(ORACLE_CODEC, value);
			if (CommonUtils.sqlValidate(value)) {
				String msg = "您发送请求的参数中含有非法字符";
				logger.warn(msg);
				return buildGlobalError(msg);
			}
		}


		// 对突发请求进行整形，整形为平均速率请求处理
		// rateLimiter.acquire();//超过permits会被阻塞
		boolean isAcquired = rateLimiter.tryAcquire();// 非阻塞

		if (!isAcquired) {
			String msg = "当前下单人数过多，请稍后再试";
			logger.warn(msg);
			return buildGlobalError(msg);
		}

		Page<Ygdemo_yw_info> page = service.selectAllByPage(pageRequest, searchParams, Ygdemo_yw_info.class);

		JsonResponse jsonResponse = buildSuccess(page);
		return jsonResponse;
	}

	@RequestMapping(value = "list1", method = RequestMethod.GET)
	@ResponseBody
	public Object list1(PageRequest pageRequest,
			@FrontModelExchange(modelType = Ygdemo_yw_info.class) SearchParams searchParams) {
		logger.debug("execute data search.");

		Iterator<Order> it = pageRequest.getSort().iterator();
		while (it.hasNext()) {
			Order order = it.next();
			String prop = order.getProperty();
			if (CommonUtils.sqlValidate(prop)) {
				String msg = "您发送请求的参数中含有非法字符";
				logger.warn(msg);
				return buildGlobalError(msg);
			}
		}

		// 查询参数解码
		CommonUtils.decode(searchParams);

		Page<Ygdemo_yw_info> page = service.selectAllByPage(pageRequest, searchParams, Ygdemo_yw_info.class);
		return buildSuccess(page);
	}

	@RequestMapping(value = "/restWithSign/list2", method = RequestMethod.GET)
	@ResponseBody
	public Object list2(PageRequest pageRequest,
			@FrontModelExchange(modelType = Ygdemo_yw_info.class) SearchParams searchParams) {
		logger.debug("execute data search.");
		Page<Ygdemo_yw_info> page = service.selectAllByPage(pageRequest, searchParams, Ygdemo_yw_info.class);
		return buildSuccess(page);
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	@CSRFToken
	public Object add(@RequestBody Ygdemo_yw_info obj, HttpServletRequest request) {
		logger.debug("execute add operate.");
		JsonResponse jsonResponse;
		try {
			Ygdemo_yw_info u = service.save(obj);

			jsonResponse = buildSuccess(u);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			jsonResponse = buildError("msg", e.getMessage(), RequestStatusEnum.FAIL_FIELD);
		}
		return jsonResponse;
	}

	@RequestMapping(value = "/add1", method = RequestMethod.POST)
	@ResponseBody
	public Object add1(@RequestBody Ygdemo_yw_info obj, HttpServletRequest request) {
		logger.debug("execute add operate.");
		String url = PropertyUtil.getPropertyByKey("outerPrject1.base.url") + "ygdemo_yw_info/add";
		return RestUtils.getInstance().doPost(url, obj, JsonResponse.class);
	}

	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	@CSRFToken
	public Object update(@RequestBody Ygdemo_yw_info obj, HttpServletRequest request, HttpServletResponse response) {
		logger.debug("execute update operate.");

		Ygdemo_yw_info u = service.save(obj);

		JsonResponse jsonResponse = buildSuccess(u);

		// HttpHeaders tempHeaders = new HttpHeaders();
		// tempHeaders.add("x-xsrf-token", xsrfTokenStr);
		// ResponseEntity responseEntity = new ResponseEntity(jsonResponse, tempHeaders,
		// HttpStatus.OK);
		return jsonResponse;
	}

	@RequestMapping(value = "/delBatch", method = RequestMethod.POST)
	@ResponseBody
	@CSRFToken
	public Object deleteBatch(@RequestBody List<Ygdemo_yw_info> objs, HttpServletRequest request) throws Exception {
		logger.debug("execute del operate.");
		List<String> ids=new ArrayList<String>();
		//获取所有准备删除数据的id
		for (int i = 0; i <objs.size() ; i++) {
			ids.add(objs.get(i).getId());
		}
        //通过ID获取当前数据的状态
		List<Ygdemo_yw_info> ygdemoList=service.getByIds(ids);
		List<String>codeList=new ArrayList<String>();
		for (int i = 0; i < ygdemoList.size(); i++) {
            //如果state！=0 代表数据正在流程中
			if (ygdemoList.get(i).getState()!=0) {
				codeList.add(ygdemoList.get(i).getCode());
			}
		}

		if (codeList.size()>0){
			JsonResponse jsonResponse = buildGlobalError("督办编号为"+codeList.toString()+"的数据已经开启流程，无法进行删除操作");
			return jsonResponse;
		}
		service.batchDeleteWithChild(objs);

		return super.buildSuccess(objs);
	}

	@RequestMapping(value = { "/asyncServiceTest" }, method = {
			org.springframework.web.bind.annotation.RequestMethod.GET })
	@ResponseBody
	public void asyncServiceTest(HttpServletRequest request) {
		int secs = 10000;
		AsyncContext asyncCtx = request.startAsync();
		asyncCtx.addListener(new AppAsyncListener());
		asyncCtx.setTimeout(2000000);

		threadPoolTaskExecutor.execute(new AsyncRequestProcessor(asyncCtx, secs));
	}

	@RequestMapping(value = { "/imgUpload" }, method = { org.springframework.web.bind.annotation.RequestMethod.POST })
	@ResponseBody
	public JsonResponse imgUpload(HttpServletRequest request) {
		JsonResponse results = new JsonResponse();
		try {
			JSONObject obj = new JSONObject();
			CommonsMultipartResolver resolver = new CommonsMultipartResolver();
			if (resolver.isMultipart(request)) {
				MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
				Iterator<String> iterator = multipartRequest.getFileNames();
				while (iterator.hasNext()) {
					// 一次只会有一个图片
					MultipartFile multipartFile = multipartRequest.getFile(iterator.next());

					if (!(isFileSizeAcceptable(multipartFile))) {
						results.setSuccess(RequestStatusEnum.FAIL_GLOBAL.toString());
						results.setMessage("头像大小限制最大为1024KB！");
						return results;
					}
					if (!(isFileUploadable(multipartFile))) {
						results.setSuccess(RequestStatusEnum.FAIL_GLOBAL.toString());
						results.setMessage("此类文件不允许上传！");
						return results;
					}

					String filename = FileManager.uploadFile(BucketPermission.FULL.toString(), multipartFile.getName(),
							multipartFile.getBytes());
					obj.put("fileName", filename);

					int width = 0;
					int height = 0;
					try {
						ByteArrayInputStream bis = new ByteArrayInputStream(multipartFile.getBytes());
						BufferedImage bufferedImage = ImageIO.read(bis);
						if (bufferedImage == null) {
							results.setSuccess(RequestStatusEnum.FAIL_GLOBAL.toString());
							results.setMessage("文件不是图片！");
							return results;
						}
						width = bufferedImage.getWidth();
						height = bufferedImage.getHeight();
					} catch (Exception e) {
						results.setSuccess(RequestStatusEnum.FAIL_GLOBAL.toString());
						results.setMessage("文件不是图片！");
						return results;
					}
					obj.put("width", width);
					obj.put("height", height);
					if ((StringUtils.isNotBlank(filename))) {
						obj.put("accessAddress",
								FileManager.getImgUrl(BucketPermission.READ.toString(), filename.concat("@100h"), 0));
					} else {
						obj.put("accessAddress", null);
					}
					results.getDetailMsg().put("data", obj);
				}
			}

			results.setSuccess(RequestStatusEnum.SUCCESS.toString());
			results.setMessage("图片上传成功！");
		} catch (Exception e) {
			results.setSuccess(RequestStatusEnum.FAIL_GLOBAL.toString());
			results.setMessage("图片上传失败！");
			logger.error("图片上传失败！" + e.getMessage());
		}
		return results;
	}

	private String[] esc = new String[] { "sh", "so", "dll", "exe", "bat" };// 不允许上传的文件类型

	private boolean isFileUploadable(MultipartFile file) {
		for (String e : esc) {
			if (file.getName().endsWith(e)) {
				return false;
			}
		}
		return true;
	}

	private boolean isFileSizeAcceptable(MultipartFile file) {
		return (file.getSize() <= 1024000L);
	}

	/** 提交审批 */
	@RequestMapping(value = "/submit", method = RequestMethod.POST)
	@ResponseBody
	public Object submit(@RequestBody List<Ygdemo_yw_info> objs, HttpServletRequest request) {
		logger.debug("execute submit operate.");
		try {
			String processDefineCode = request.getParameter("processDefineCode");
			service.batchSubmitEntity(objs, processDefineCode);
			return super.buildSuccess(objs);
		} catch (BusinessException e) {
			logger.error(e.getMessage(), e);
			return super.buildGlobalError(e.getMessage());
		}
	}

	/** 收回 */
	@RequestMapping(value = "/unsubmit", method = RequestMethod.POST)
	@ResponseBody
	public Object unsubmit(@RequestBody List<Ygdemo_yw_info> objs, HttpServletRequest request) {
		logger.debug("execute unsubmit operate.");
		Object unsubmitJson = service.batchUnsubmitEntity(objs);
		return super.buildSuccess(unsubmitJson);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/getvo")
	@ResponseBody
	@CSRFToken(verify = false)
	public Object getYgdemo_yw_info(@RequestBody Map<String, String> map, HttpServletRequest request,
			HttpServletResponse response) {
		String ygdemoId = map.get("id");
		Ygdemo_yw_info vo = service.queryByPK(ygdemoId);

		JsonResponse jsonResponse = buildSuccess(vo);
		return jsonResponse;
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
		String ygdemoId = (String) jsonObj.get("id");

		Ygdemo_yw_info vo = service.queryByPK(ygdemoId);

		JSONArray mainDataJson = new JSONArray();// 主实体数据
		List<String> lsAttr = vo.getAllAttributeNames();
		JSONObject mainData = new JSONObject();

		for (String attr : lsAttr) {
			if (BaseEntityUtils.lsAttrExclude.contains(attr) || attr.equals("id_ygdemo_yw_sub")) {
				continue;
			}
			mainData.put(attr, vo.getAttribute(attr));
			/*
			 *因云打印将打印模板的字段与本方法传入的数值一一对应显示，出现显示不正确问题
			 * 例如：责任单位    显示为‘af267958-7ac1-43f3-aa97-ceee2b3c5db9’。显示的是vo中zr_dw字段的值，而我们期望为zr_dw_name字段
			 *       是否为KPI   显示为1或者0，而我们期望为是或者否
			 *       各类日期     由于字段为date类型  显示为	1522512000000，而我们期望为2018-04-01
			 *       所以转换为JSONObject时需要进行处理，增加下面一行代码    
			 * */
			mainData=CommonUtils.formatDataForPrint(vo, attr,  mainData);//为使打印显示正常
		}

		mainDataJson.add(mainData);// 主表只有一行

		JSONArray childrenDataJson = new JSONArray();// 第一个子实体数据
		List<Ygdemo_yw_sub> ls_ygdemo_yw_sub = vo.getId_ygdemo_yw_sub();

		if (ls_ygdemo_yw_sub != null) {
			for (Ygdemo_yw_sub ygdemo_yw_sub : ls_ygdemo_yw_sub) {
				JSONObject childData = new JSONObject();
				List<String> lsChildAttr = ygdemo_yw_sub.getAllAttributeNames();

				for (String childAttr : lsChildAttr) {
					if (childAttr.equals("allAttributeNames") || childAttr.equals("beanMap")) {
						continue;
					}

					childData.put(childAttr, ygdemo_yw_sub.getAttribute(childAttr));
					childData=CommonUtils.formatSubDataForPrint(ygdemo_yw_sub, childAttr,  childData);//为使打印显示正常
				}

				childrenDataJson.add(childData);// 每行一个json对象
			}
		}

		JSONObject boAttr = new JSONObject();
		boAttr.put("ygdemo_yw_info", mainDataJson);
		boAttr.put("ygdemo_yw_sub", childrenDataJson);

		return boAttr.toString();
	}

	/**
	 * 督办任务信息导出
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
			result.put("msg", "督办任务信息导出成功");
		} catch (Exception e) {
			logger.error("督办任务信息Excel下载失败", e);
			result.put("status", "failed");
			result.put("msg", "督办任务信息Excel下载失败");
		}

		return result;
	}

	/**
	 * 督办任务信息模版导出
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
	 * 督办任务信息模版导出
	 * @param request
	 * @param response
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/excelTemplate2Download", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> excelTemplate2Download(HttpServletRequest request,
			HttpServletResponse response) throws BusinessException {
		Map<String, String> result = new HashMap<String, String>();

		try {
			service.downloadExcelTemplate2(response);
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
	 * 督办任务信息excel导入
	 * @param model
	 * @param request
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/excelDataImport", method = RequestMethod.POST)
	public @ResponseBody Map<String, String> excelDataImport(
			@RequestParam(value = "fileName", required = false) MultipartFile fileName, ModelMap model,
			HttpServletRequest request) throws BusinessException {
		Map<String, String> result = new HashMap<String, String>();

		try {
			service.importExcelData(fileName.getInputStream());
			result.put("status", "success");
			result.put("msg", "督办任务信息Excel导入成功");
		} catch (Exception e) {
			logger.error("督办任务信息Excel导入失败", e);
			result.put("status", "failed");
			result.put("msg", "督办任务信息Excel导入失败");
		}

		return result;
	}

	/**
	 * 督办任务信息excel导入
	 * @param model
	 * @param request
	 * @throws BusinessException
	 */
	@RequestMapping(value = "/excelDataImportTest", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> excelDataImport(ModelMap model, HttpServletRequest request)
			throws BusinessException {
		Map<String, String> result = new HashMap<String, String>();

		try {
			InputStream excelStream = new FileInputStream(
					new File("C:\\Users\\Administrator\\Downloads\\督办任务信息 (5).xls"));
			service.importExcelData(excelStream);
			result.put("status", "success");
			result.put("msg", "督办任务信息Excel导入成功");
		} catch (Exception e) {
			logger.error("督办任务信息Excel导入失败", e);
			result.put("status", "failed");
			result.put("msg", "督办任务信息Excel导入失败");
		}

		return result;
	}

	@RequestMapping(value = "/testRSA", method = RequestMethod.GET)
	public @ResponseBody Map<String, String> testRSA(@RequestParam(value = "name") String name,
			@RequestParam(value = "pwd") String pwd, ModelMap model, HttpServletRequest request)
			throws BusinessException {
		KeyPair kp = cacheManager.get("KEY_PAIR");
		 RSAPublicKey pubkey = (RSAPublicKey) kp.getPublic();

		try {
			pwd = StringUtils.reverse(pwd);
			String encpwd = RSAUtils.encryptString(pubkey, pwd);

			String rtn = RSAUtils.decryptString(kp.getPrivate(), encpwd);
			rtn = StringUtils.reverse(rtn);
			logger.error("decryptStringByJs rtn = " + rtn);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Map<String, String> result = new HashMap<String, String>();
		return result;
	}

}
