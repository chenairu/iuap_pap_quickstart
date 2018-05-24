package com.yonyou.iuap.example.asval.web.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.base.web.BaseController;
import com.yonyou.iuap.example.asval.entity.ExampleAsVal;
import com.yonyou.iuap.example.asval.service.ExampleAsValService;
import com.yonyou.iuap.persistence.vo.pub.util.StringUtil;
import com.yonyou.uap.wb.process.org.OrganizationBrief;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/exampleAsVal")
public class ExampleAsValController extends BaseController {

    @Autowired
    private ExampleAsValService exampleAsValService;

    @RequestMapping(value={"/list"},method = {RequestMethod.GET})
    public @ResponseBody Object list(HttpServletRequest request,@RequestParam(value="code") String code) {

        List<ExampleAsVal> list = exampleAsValService.findProvince(code);
        return buildSuccess(list);
    }

    @RequestMapping(value={"/getIds"},method={RequestMethod.POST})
    @ResponseBody
    public JSONObject getIds(HttpServletRequest request){
        JSONObject result = new JSONObject();
        String data = request.getParameter("data");
        if(StringUtil.isEmpty(data)){
            return result;
        }
        JSONArray arr = JSON.parseArray(data);

        List<ExampleAsVal> asValList = new ArrayList<>();
        if(!CollectionUtils.isEmpty(arr)){
            String[] strArray = arr.toArray(new String[arr.size()]);
            asValList = this.exampleAsValService.getCurrtypeByIds(strArray);
        }
        result.put("data",transformTOBriefEntity(asValList));
        return result;
    }
    private List<OrganizationBrief> transformTOBriefEntity(List<ExampleAsVal> asValList){
        List<OrganizationBrief> results = new ArrayList<>();
        if(!CollectionUtils.isEmpty(asValList)){
            for (ExampleAsVal entity:asValList){
                results.add(new OrganizationBrief(entity.getId(),entity.getValue(),entity.getCode()));
            }
        }

        return results;
    }
}
