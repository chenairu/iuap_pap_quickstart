/*
 * （C）2018 用友网络科技股份有限公司. yonyou Group, All right reserved.
 *
 * 项目名称 : example
 * 创建日期 : 2018年6月14日
 */

package com.yonyou.iuap.example.base.common.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.yonyou.iuap.bpm.web.IBPMBusinessProcessController;
import com.yonyou.iuap.mvc.type.JsonResponse;

/**
  * @description 流程审核回调请求控制
  * @author 姚春雷
  * @date 2018年6月14日 下午12:53:20
  * @version 1.0.0
  *
  *                    修改信息说明：
  * @updateDescription
  * @updateAuthor
  * @updateDate
  */
@RestController
@RequestMapping(value = "/processweb")
public class ProcessWeb implements IBPMBusinessProcessController {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProcessWeb.class);

    /**
      * 流程审核后回调方法
      * @param request  http请求
      * @return json格式的响应
     */
    @RequestMapping(value = "/doApprove", method = RequestMethod.POST)
    @Override
    public Object doApproveAction(@RequestBody Map<String, Object> paramMap, HttpServletRequest request) throws Exception {
        LOGGER.info("================" + JSON.toJSONString(paramMap));
        return new JsonResponse();
    }

    /**
     * 流程终止后回调方法
     * @param request  http请求
     * @return json格式的响应
    */
    @Override
    public JsonResponse doTerminationAction(@RequestBody Map<String, Object> paramMap) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * 流程驳回后回调方法
     * @param request  http请求
     * @return json格式的响应
    */
    @Override
    public JsonResponse doRejectMarkerBillAction(@RequestBody Map<String, Object> paramMap) throws Exception {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public JsonResponse doReject(Map<String, Object> map) throws Exception {
        return null;
    }

    @Override
    public JsonResponse doAddSign(Map<String, Object> map) throws Exception {
        return null;
    }

    @Override
    public JsonResponse doDelegate(Map<String, Object> map) throws Exception {
        return null;
    }

    @Override
    public JsonResponse doAssign(Map<String, Object> map) throws Exception {
        return null;
    }

    @Override
    public JsonResponse doWithdraw(Map<String, Object> map) throws Exception {
        return null;
    }

    @Override
    public JsonResponse doSuspend(Map<String, Object> map) throws Exception {
        return null;
    }

    @Override
    public JsonResponse doActivate(Map<String, Object> map) throws Exception {
        return null;
    }

}
