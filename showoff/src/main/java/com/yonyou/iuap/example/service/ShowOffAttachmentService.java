
package com.yonyou.iuap.example.service;

import com.yonyou.iuap.baseservice.attachment.service.GenericAtService;
import com.yonyou.iuap.example.dao.ShowOffMapper;
import com.yonyou.iuap.example.entity.ShowOff;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShowOffAttachmentService extends GenericAtService<ShowOff>{

    private ShowOffMapper ShowOffMapper;

    @Autowired
    public void setShowOffMapper(ShowOffMapper ShowOffMapper) {
        this.ShowOffMapper = ShowOffMapper;
        super.setMapper(ShowOffMapper);
    }
}
