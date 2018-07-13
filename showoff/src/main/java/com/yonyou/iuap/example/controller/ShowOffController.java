package com.yonyou.iuap.example.controller;

import com.yonyou.iuap.baseservice.bpm.controller.GenericBpmController;
import com.yonyou.iuap.example.entity.ShowOff;
import com.yonyou.iuap.example.service.ShowOffService;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.type.SearchParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value="/show_off")
public class ShowOffController extends GenericBpmController<ShowOff>{

	private Logger logger = LoggerFactory.getLogger(ShowOffController.class);

	private ShowOffService ShowOffService;
	
	@Autowired
    public void setShowOffService(ShowOffService ShowOffService) {
        this.ShowOffService = ShowOffService;
        super.setService(ShowOffService);
    }

	@Override
	public Object list(PageRequest pageRequest,
					   @FrontModelExchange(modelType = ShowOff.class) SearchParams searchParams) {
		return super.list(pageRequest,searchParams);
	}

}