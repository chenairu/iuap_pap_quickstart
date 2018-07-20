
package com.yonyou.iuap.example.service;

import com.yonyou.iuap.baseservice.attachment.service.GenericAtService;
import com.yonyou.iuap.example.dao.ShowOffSubMapper;
import com.yonyou.iuap.example.entity.ShowOffSub;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShowOffSubAttachmentService extends GenericAtService<ShowOffSub>{

    private ShowOffSubMapper ShowOffSubMapper;

    @Autowired
    public void setShowOffSubMapper(ShowOffSubMapper ShowOffSubMapper) {
        this.ShowOffSubMapper = ShowOffSubMapper;
        super.setMapper(ShowOffSubMapper);
    }
}
