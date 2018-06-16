/*
 * （C）2018 用友网络科技股份有限公司. yonyou Group, All right reserved.
 *
 * 项目名称 : example
 * 创建日期 : 2018年6月1日
 */

package com.yonyou.iuap.example.base.business.notice.web;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections4.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.example.base.business.notice.entity.ExNotice;
import com.yonyou.iuap.example.base.business.notice.service.ExNoticeService;
import com.yonyou.iuap.example.base.common.constant.SysConstant;
import com.yonyou.iuap.example.base.common.entity.ProcessEntity;
import com.yonyou.iuap.example.base.common.service.CommonService;
import com.yonyou.iuap.example.base.utils.reflect.EntityUtils;
import com.yonyou.iuap.example.base.utils.reflect.ParamsDisposeUtils;
import com.yonyou.iuap.example.base.utils.result.ResultUtil;
import com.yonyou.iuap.mvc.type.SearchParams;

/**
  * @description 通知公告请求控制
  * @author 姚春雷
  * @date 2018年6月1日 下午1:26:17
  * @version 1.0.0
  *
  *                    修改信息说明：
  * @updateDescription
  * @updateAuthor
  * @updateDate
  */
@RestController
@RequestMapping(value = "/exnoticeweb")
public class ExNoticeWeb {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExNoticeWeb.class);

    @Autowired
    private ExNoticeService exNoticeService;

    @Autowired
    private CommonService commonService;

    /**
      * 查询数据
      * @param pageRequest  分页对象
      * @param searchParams 查询参数
      * @param request  http请求
      * @return 数据集合
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object selectAllByPage(PageRequest pageRequest, SearchParams searchParams, HttpServletRequest request) {
        //创建结果map
        Map<String, Object> result = new HashedMap<String, Object>();
        try {
            //处理时间查询参数
            ParamsDisposeUtils.searchParamsDateConvert(searchParams, "dspDate", "yyyy-MM-dd");

            //获得分页数据
            Page<ExNotice> exNoticePager = exNoticeService.selectAllByPage(pageRequest, searchParams);

            //封装执行信息
            result = ResultUtil.getWebResult(result, SysConstant.SUCCESS_FLAG, SysConstant.QUERY_DATA_SUCCESS, exNoticePager);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = ResultUtil.getWebResult(result, SysConstant.FAILED_FLAG, SysConstant.QUERY_DATA_FAILED);
        }
        return result;
    }

    /**
      * 查询单条数据，根据主键
      * @param request  http请求
      * @return 数据集合
     */
    @RequestMapping(value = "/singlepk", method = RequestMethod.GET)
    public Object selectByPrimaryKey(HttpServletRequest request) {
        //创建结果map
        Map<String, Object> result = new HashedMap<String, Object>();
        try {
            String pkNotice = request.getParameter("pkNotice");

            ExNotice exNotice = exNoticeService.selectByPrimaryKey(pkNotice);
            //封装执行信息
            result = ResultUtil.getWebResult(result, SysConstant.SUCCESS_FLAG, SysConstant.QUERY_DATA_SUCCESS, exNotice);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = ResultUtil.getWebResult(result, SysConstant.FAILED_FLAG, SysConstant.QUERY_DATA_FAILED);
        }
        return result;
    }

    /**
      * 保存数据
      * @param exNotice 业务实体
      * @param request  http请求
      * @return 执行信息
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public Object saveOrUpdateData(@RequestBody ExNotice exNotice, HttpServletRequest request) {
        Map<String, Object> result = new HashedMap<String, Object>();
        try {
            exNoticeService.save(exNotice);
            result = ResultUtil.getWebResult(result, SysConstant.SUCCESS_FLAG, SysConstant.DATA_SAVE_SUCCESS);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = ResultUtil.getWebResult(result, SysConstant.FAILED_FLAG, SysConstant.DATA_SAVE_FAILED);
        }
        return result;
    }

    /**
      * 更新数据
      * @param exNotice 业务实体
      * @param request  http请求
      * @return 执行信息
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public Object updateData(@RequestBody ExNotice exNotice, HttpServletRequest request) {
        Map<String, Object> result = new HashedMap<String, Object>();
        try {
            exNoticeService.update(exNotice);
            result = ResultUtil.getWebResult(result, SysConstant.SUCCESS_FLAG, SysConstant.DATA_UPDATE_SUCCESS);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = ResultUtil.getWebResult(result, SysConstant.FAILED_FLAG, SysConstant.DATA_UPDATE_FAILED);
        }
        return result;
    }

    /**
      * 删除数据
      * @param exNotice 业务实体
      * @param request  http请求
      * @return 执行信息
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public Object deleteData(@RequestBody ExNotice exNotice, HttpServletRequest request) {
        Map<String, Object> result = new HashedMap<String, Object>();
        try {
            exNoticeService.delete(exNotice.getPkNotice());
            result = ResultUtil.getWebResult(result, SysConstant.SUCCESS_FLAG, SysConstant.DATA_DELETE_SUCCESS);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = ResultUtil.getWebResult(result, SysConstant.FAILED_FLAG, SysConstant.DATA_DELETE_FAILED);
        }
        return result;
    }

    /**
      * 打印数据
      * @param request  http请求
      * @return 需要打印数据信息
     */
    @RequestMapping(value = "/printData", method = RequestMethod.POST)
    public Object printData(HttpServletRequest request) {
        JSONObject jsonObj = JSON.parseObject(request.getParameter("params"));
        String id = (String) jsonObj.get("id");

        //根据主键获得业务数据
        ExNotice exNotice = exNoticeService.selectByPrimaryKey(id);
        //根据业务数据反馈打印数据json字符串对象
        return ParamsDisposeUtils.createPrintDataParams(exNotice);
    }

    /**
      * 导出数据
      * @param ids  需要导出的记录主键
      * @param request  http请求
      * @param response http响应
      * @return 执行信息
     */
    @RequestMapping(value = "/expdata", method = RequestMethod.POST)
    public Object expData(@RequestParam String ids, HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            if (null != ids && ids.length() > 0) {
                //将ids主键字符串解析为数组
                String[] pks = ids.split(",");
                //执行excel导出业务
                exNoticeService.executeExcelExport(Arrays.asList(pks), response);
            }
            //封装执行信息
            result = ResultUtil.getWebResult(result, SysConstant.SUCCESS_FLAG, SysConstant.DATA_EXP_SUCCESS);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = ResultUtil.getWebResult(result, SysConstant.FAILED_FLAG, SysConstant.DATA_EXP_FAILED);
        }
        return result;
    }

    /**
      * excel模板下载
      * @param request  http请求
      * @param response http响应
      * @return 执行信息
     */
    @RequestMapping(value = "/downloadexceltpl", method = RequestMethod.POST)
    public Object downloadExcelTpl(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            exNoticeService.executeDownloadExcelTpl(response);
            result = ResultUtil.getWebResult(result, SysConstant.SUCCESS_FLAG, SysConstant.DATA_DOWN_SUCCESS);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = ResultUtil.getWebResult(result, SysConstant.FAILED_FLAG, SysConstant.DATA_DOWN_FAILED);
        }
        return result;
    }

    /**
      * excel数据导入
      * @param fileName 需要导入的excel文件
      * @param request  http请求
      * @return 执行信息
     */
    @RequestMapping(value = "/impdata", method = RequestMethod.POST)
    public @ResponseBody Map<String, Object> impData(@RequestParam(value = "excelImpFile", required = false) MultipartFile fileName,
            HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            exNoticeService.executeExcelImport(fileName.getInputStream());
            result = ResultUtil.getWebResult(result, SysConstant.SUCCESS_FLAG, SysConstant.DATA_DOWN_SUCCESS);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            result = ResultUtil.getWebResult(result, SysConstant.FAILED_FLAG, SysConstant.DATA_DOWN_FAILED);
        }
        return result;

    }

    /**
     * 提交工作流
     * @param request  http请求
     * @return uuid
    */
    @RequestMapping(value = "/submitflow", method = RequestMethod.POST)
    public Object submitFlow(@RequestBody ExNotice exNotice, HttpServletRequest request) {

        Map<String, Object> result = new HashMap<String, Object>();
        try {
            ProcessEntity processEntity = new ProcessEntity();
            processEntity.setProcessDefinitionKey(exNotice.getProcessDefinitionKey());
            processEntity.setProcessInstanceName(exNotice.getProcessInstanceName());
            processEntity.setBusinessPk(exNotice.getPkNotice());
            processEntity.setBusinessCode(exNotice.getNoticeCode());
            processEntity.setOrgId("");
            processEntity.setOperatorId(InvocationInfoProxy.getUserid());
            try {
                String userName = java.net.URLDecoder.decode(InvocationInfoProxy.getUsername(), "utf-8");
                userName = java.net.URLDecoder.decode(userName, "utf-8");
                processEntity.setShowTile("[" + userName + "]" + "提交的单据,单号 是:[" + exNotice.getNoticeCode() + "], 请审批!");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            processEntity.setModelUrl("/example/pages/notice/notice.js");
            processEntity.setBusinessTable("ex_notice");
            processEntity.setBusinessPkFiled("pk_notice");

            processEntity.setOtherVariables(EntityUtils.objectToRestVariableList(exNotice));
            JSONObject jsonObj = commonService.submitFlow(processEntity);
            String flag = (String) jsonObj.get("flag");
            if ("fail".equals(flag)) {
                return ResultUtil.getWebResult(result, SysConstant.FAILED_FLAG, SysConstant.DATA_DOWN_FAILED);
            }

            return ResultUtil.getWebResult(result, SysConstant.SUCCESS_FLAG, SysConstant.DATA_DOWN_SUCCESS);
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            return ResultUtil.getWebResult(result, SysConstant.FAILED_FLAG, SysConstant.DATA_DOWN_FAILED);
        }
    }
}
