package com.yonyou.iuap.example.asval.web.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.example.asval.entity.ExampleAsVal;
import com.yonyou.iuap.example.asval.service.ExampleAsValService;
import com.yonyou.iuap.mvc.type.SearchParams;
import com.yonyou.uap.ieop.security.entity.DataPermission;
import com.yonyou.uap.ieop.security.sdk.AuthRbacClient;
import iuap.ref.ref.RefClientPageInfo;
import iuap.ref.sdk.refmodel.model.AbstractGridRefModel;
import iuap.ref.sdk.refmodel.vo.RefViewModelVO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

@RestController
@RequestMapping("/restExampleAsVal")
public class ExampleAsValRefController extends AbstractGridRefModel {
    private Logger logger = LoggerFactory.getLogger(ExampleAsValRefController.class);

    @Autowired
    private ExampleAsValService exampleAsValService;

    /**
     * 过滤
     * @param refViewModelVO
     * @return
     */
    @Override
    public List<Map<String, String>> filterRefJSON(@RequestBody RefViewModelVO refViewModelVO) {
        List<Map<String,String>> result = new ArrayList<>();

        /*String clientParam = refViewModelVO.getClientParam();
        Map<String,Object> clientMap = (Map<String, Object>) JSONObject.parse(clientParam);*/

        List<ExampleAsVal> rtnVal = this.exampleAsValService.likeSarch(refViewModelVO.getContent());
        result = buildRtnValsOfRef(rtnVal,isUserDataPower(refViewModelVO));
        return result;
    }

    @Override
    public List<Map<String, String>> matchPKRefJSON(RefViewModelVO refViewModelVO) {

        
        return filterRefJSON(refViewModelVO);
    }

    @Override
    public List<Map<String, String>> matchBlurRefJSON(RefViewModelVO refViewModelVO) {

        return filterRefJSON(refViewModelVO);

    }

    /**
     * 查询参照数据
     * @param refViewModelVo
     * @return
     */
    @Override
    public @ResponseBody Map<String, Object> getCommonRefData(@RequestBody RefViewModelVO refViewModelVo) {
        Map<String, Object> results = new HashMap<String, Object>();
        int pageNum = refViewModelVo.getRefClientPageInfo().getCurrPageIndex() == 0 ? 1
                : refViewModelVo.getRefClientPageInfo().getCurrPageIndex();

        int pageSize = refViewModelVo.getRefClientPageInfo().getPageSize();

        PageRequest request = buildPageRequest(pageNum,pageSize,null);
        String searchParam = StringUtils.isEmpty(refViewModelVo.getContent()) ? null: refViewModelVo.getContent();

        //自定义过滤条件后台配置
        /*String clientParam = refViewModelVo.getClientParam();
        Map<String,Object> clientMap = (Map<String, Object>) JSONObject.parse(clientParam);*/

        Page<ExampleAsVal> pages = this.exampleAsValService.selectAllByPage(request,buildSearchParam(searchParam));
        List<ExampleAsVal> list =pages.getContent();
        if(CollectionUtils.isNotEmpty(list)){
            List<Map<String, String>> tmpList = buildRtnValsOfRef(list,isUserDataPower(refViewModelVo));
            //List<Map<String, String>> tmpList = buildRtnValsOfRef(listData);

            RefClientPageInfo refClientPageInfo = refViewModelVo.getRefClientPageInfo();
            refClientPageInfo.setPageCount(pages.getTotalPages());
            refClientPageInfo.setPageSize(50);
            refViewModelVo.setRefClientPageInfo(refClientPageInfo);

            results.put("dataList",tmpList);
            results.put("refViewModel",refViewModelVo);
        }

        return results;
    }
    private SearchParams buildSearchParam(String searchParam) {
        SearchParams param = new SearchParams();

        Map<String, Object> results = new HashMap<String, Object>();
        if (StringUtils.isNotEmpty(searchParam)) {
            results.put("searchParam", searchParam);
        }

        param.setSearchMap(results);
        return param;
    }

    
    /**
     * 参照数据组装
     * @param list
     * @param isUserDataPower
     */
    private List<Map<String,String>> buildRtnValsOfRef(List<ExampleAsVal> list,boolean isUserDataPower){
        String tenantId = InvocationInfoProxy.getTenantid();
        String sysId = InvocationInfoProxy.getSysid();
        String userId = InvocationInfoProxy.getUserid();
        List<DataPermission> dataPermissions = AuthRbacClient.getInstance().queryDataPerms(tenantId,sysId,userId,"billType");

        Set<String> set = new HashSet<>();
        if(dataPermissions != null && dataPermissions.size()>0){
            for(DataPermission tmp : dataPermissions){
                if(tmp != null) {
                    set.add(tmp.getResourceId());
                }
            }
        }

        List<Map<String,String>> results = new ArrayList<>();

        if(list != null && !list.isEmpty()){
            for(ExampleAsVal asVal:list){
                if(!isUserDataPower || (isUserDataPower&&set.contains(asVal.getId()))){
                    Map<String,String> refDataMap = new HashMap<>();
                    refDataMap.put("id",asVal.getId());
                    refDataMap.put("refname",asVal.getName());
                    refDataMap.put("refcode",asVal.getValue());
                    refDataMap.put("refpk",asVal.getId());

                    results.add(refDataMap);
                }
            }

        }
        return results;
    }
   

    /**
     * 是否需要启用数据权限,通过当前人角色和前端配置共同判断
     * @param refViewModelVO
     * @return
     */
    private boolean isUserDataPower(RefViewModelVO refViewModelVO){
        if(isAdmin()){
            return false;
        }
        boolean isUserDataPower = false;

        String clientParam = refViewModelVO.getClientParam();
        if(clientParam != null && clientParam.trim().length()>0){
            net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(clientParam);
            if(json.containsKey("isUseDataPower")){
                isUserDataPower = json.getBoolean("isUseDataPower");
            }
        }
        return isUserDataPower;
    }
    /**
     * 判断当前登录人是否为管理员用户
     */
    private boolean isAdmin(){
        String userId = InvocationInfoProxy.getUserid();
        if("U001".equals(userId)){
            return true;
        }
        return false;
    }
    /**
     * 构造分页参数
     * @param pageNum
     * @param pageSize
     * @param sortColumn
     * @return
     */
    private PageRequest buildPageRequest(int pageNum, int pageSize,
                                         String sortColumn) {
        Sort sort = null;
        if (("auto".equalsIgnoreCase(sortColumn))
                || (StringUtils.isEmpty(sortColumn))) {
            sort = new Sort(Sort.Direction.ASC, new String[] { "code" });
        } else {
            sort = new Sort(Sort.Direction.DESC, new String[] { sortColumn });
        }
        return new PageRequest(pageNum - 1, pageSize, sort);
    }
}
