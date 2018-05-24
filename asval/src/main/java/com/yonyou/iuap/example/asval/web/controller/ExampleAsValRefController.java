package com.yonyou.iuap.example.asval.web.controller;

import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.example.asval.service.ExampleAsValService;
import iuap.ref.sdk.refmodel.model.AbstractGridRefModel;
import iuap.ref.sdk.refmodel.vo.RefViewModelVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
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
    public List<Map<String, String>> filterRefJSON(RefViewModelVO refViewModelVO) {
        return null;
    }

    @Override
    public List<Map<String, String>> matchPKRefJSON(RefViewModelVO refViewModelVO) {
        return null;
    }

    @Override
    public List<Map<String, String>> matchBlurRefJSON(RefViewModelVO refViewModelVO) {
        return null;
    }

    @Override
    public Map<String, Object> getCommonRefData(RefViewModelVO refViewModelVO) {
        return null;
    }

    private boolean isAdmin(){
        String userId = InvocationInfoProxy.getUserid();
        if("U001".equals(userId)){
            return true;
        }
        return false;
    }
}
