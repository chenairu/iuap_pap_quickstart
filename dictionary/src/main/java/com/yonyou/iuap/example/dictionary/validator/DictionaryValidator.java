package com.yonyou.iuap.example.dictionary.validator;

import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.iuap.example.dictionary.entity.Dictionary;
import com.yonyou.iuap.example.dictionary.service.DictionaryService;
import com.yonyou.iuap.iweb.exception.ValidException;

@Component
public class DictionaryValidator {

    public void valid(List<Dictionary> example_recordlist) {
        if (CollectionUtils.isEmpty(example_recordlist)) {
            throw new ValidException("提交的数据集为空!");
        }
        StringBuilder builder = new StringBuilder();
        for (Dictionary example_record : example_recordlist) {
            if (StringUtils.isEmpty(example_record.getId())) {
                if (!service.findByCode(example_record.getCode()).isEmpty()) {
                    builder.append(example_record.getCode()).append(",");
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
    
    /*****************************************************************************/
    @Autowired
    private DictionaryService service;
    
}
