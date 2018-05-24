package com.yonyou.iuap.example.billcode.validator;

import com.yonyou.iuap.example.billcode.entity.ExampleCustomer;
import com.yonyou.iuap.example.billcode.service.ExampleCustomerService;
import com.yonyou.iuap.iweb.exception.ValidException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import java.util.List;

@Component
public class ExampleCustomerValidator {

    @Autowired
    private ExampleCustomerService exampleCustomerService;


    public void valid(List<ExampleCustomer> example_recordlist) {
        if (CollectionUtils.isEmpty(example_recordlist)) {
            throw new ValidException("提交的数据集为空!");
        }
        StringBuilder builder = new StringBuilder();
        for (ExampleCustomer example_record : example_recordlist) {
            if (StringUtils.isEmpty(example_record.getId())) {
                if (!exampleCustomerService.findByCode(example_record.getCustomerCode()).isEmpty()) {
                    builder.append(example_record.getCustomerCode()).append(",");
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
