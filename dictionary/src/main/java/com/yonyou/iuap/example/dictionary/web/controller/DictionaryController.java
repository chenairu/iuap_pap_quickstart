package com.yonyou.iuap.example.dictionary.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.base.web.BaseController;
import com.yonyou.iuap.example.common.utils.CommonUtils;
import com.yonyou.iuap.example.dictionary.entity.Dictionary;
import com.yonyou.iuap.example.dictionary.service.DictionaryService;
import com.yonyou.iuap.example.dictionary.validator.DictionaryValidator;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.iuap.persistence.vo.pub.util.StringUtil;
import com.yonyou.uap.wb.process.org.OrganizationBrief;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/example_dictionary")
public class DictionaryController extends BaseController {
	
    public static Logger logger = LoggerFactory.getLogger(DictionaryController.class);

    @Autowired
    private DictionaryService dictionaryService;
    
    @Autowired
    private DictionaryValidator validtor;

    /**
     * 获取档案列表
     * 
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public @ResponseBody Object page(PageRequest pageRequest, SearchParams searchParams) {
    	CommonUtils.decode(searchParams);
        Page<Dictionary> tmpdata = dictionaryService.selectAllByPage(pageRequest, searchParams);
        return buildSuccess(tmpdata);
    }


    /**
     * 保存档案
     * 
     * @param list
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public @ResponseBody Object save(@RequestBody List<Dictionary> list) {
        validtor.valid(list);
    	dictionaryService.save(list);
        return buildSuccess();
    }

    /**
     * 删除档案
     * 
     * @param list
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public @ResponseBody Object delete(@RequestBody List<Dictionary> list) {
    	for(int i=0; i<list.size(); i++) {
    		System.out.println("...........................删除数据："+list.get(i).getId());
    	}
    	dictionaryService.batchDeleteByPrimaryKey(list);
        return buildSuccess();
    }
    @RequestMapping(value = { "/getByIds" }, method = { RequestMethod.POST })
    @ResponseBody
    public JSONObject getByIds(HttpServletRequest request) {
        JSONObject result = new JSONObject();
        String data = request.getParameter("data");
        if (StringUtil.isEmpty(data)) {
            return result;
        }
        JSONArray array = JSON.parseArray(data);

        List<Dictionary> currtypeList = new ArrayList<Dictionary>();
        if (!CollectionUtils.isEmpty(array)) {
            String[] strArray = (String[]) array.toArray(new String[array.size()]);
            currtypeList = this.dictionaryService.getCurrtypeByIds(strArray);
        }
        result.put("data", transformTOBriefEntity(currtypeList));
        return result;
    }
    private List<OrganizationBrief> transformTOBriefEntity(List<Dictionary> currtypeList){
        List<OrganizationBrief> results = new ArrayList<>();
        if(!CollectionUtils.isEmpty(currtypeList)){
            for (Dictionary entity:currtypeList){
                results.add(new OrganizationBrief(entity.getId(),entity.getName(),entity.getCode()));
            }
        }

        return results;
    }
}