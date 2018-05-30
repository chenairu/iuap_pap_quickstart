package com.yonyou.iuap.example.contacts.validator;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.iuap.example.contacts.entity.Organization;
import com.yonyou.iuap.example.contacts.service.OrganizationService;
import com.yonyou.iuap.iweb.exception.ValidException;

@Component
public class OrganizationValidator {
    @Autowired
    private OrganizationService service;
    
    public void valid(List<Organization> institList) {
        if (CollectionUtils.isEmpty(institList)) {
            throw new ValidException("提交的数据集为空!");
        }
        StringBuilder builder = new StringBuilder();
        for (Organization instit : institList) {
            if (StringUtils.isEmpty(instit.getInstitid())) {
                if (!service.queryList("institCode", instit.getInstit_code()).isEmpty()) {
                    builder.append(instit.getInstit_code()).append(",");
                }
            }
        }
        if (builder.toString().length() > 0) {
            String codeStr = builder.deleteCharAt(builder.length() - 1).toString();
            StringBuilder msgBuilder = new StringBuilder("编码为");
            msgBuilder.append(codeStr).append("的记录已经存在!");
            throw new ValidException(msgBuilder.toString());
        }
    }

}