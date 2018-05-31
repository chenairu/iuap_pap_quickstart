package com.yonyou.iuap.example.asval.validator;

import com.yonyou.iuap.example.asval.entity.ExampleAsVal;
import com.yonyou.iuap.example.asval.service.ExampleAsValService;
import com.yonyou.iuap.iweb.exception.ValidException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * com.yonyou.iuap.example.asval.validator
 *
 * @author guoxh
 * @date 2018/5/31 11:36
 * @description
 */
@Component
public class ExampleAsValValidator {

    @Autowired
    private ExampleAsValService exampleAsValService;

    public void validator(List<ExampleAsVal> asValList){
        if(CollectionUtils.isEmpty(asValList)){
            throw new ValidException("提交的数据集为空!");
        }
        StringBuilder builder = new StringBuilder();
        for (ExampleAsVal asval : asValList) {
            if (StringUtils.isEmpty(asval.getId())) {
                if(!exampleAsValService.findByClause(asval).isEmpty()){
                    builder.append("父节点为").append(asval.getPid()).append(",名称为").append(asval.getName()).append(",值为").append(asval.getValue()).append(",");
                }
            }
        }
        if (builder.toString().length() > 0) {
            String codeStr = builder.deleteCharAt(builder.length() - 1).toString();
            StringBuilder msgBuilder = new StringBuilder("");
            msgBuilder.append(codeStr).append("的记录已经存在!");
            throw new ValidException(msgBuilder.toString());
        }
    }
}
