package com.yonyou.iuap.example.contacts.validator;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.iuap.example.contacts.entity.ExampleTelBook;
import com.yonyou.iuap.example.contacts.service.ExampleTelBookService;
import com.yonyou.iuap.iweb.exception.ValidException;

/**
 * Created by zhukai on 2016/11/28.
 */
@Component
public class ExampleTelBookValidator {
    @Autowired
    private ExampleTelBookService service;

    public void valid(List<ExampleTelBook> telbookList) {
        if (CollectionUtils.isEmpty(telbookList)) {
            throw new ValidException("提交的数据集为空!");
        }
        StringBuilder builder = new StringBuilder();
        for (ExampleTelBook telbook : telbookList) {
            if (StringUtils.isEmpty(telbook.getId())) {
                if (!service.findByCode(telbook.getPeocode()).isEmpty()) {
                    builder.append(telbook.getPeocode()).append(",");
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
