package com.yonyou.iuap.example.workorder.web.controller;

import com.yonyou.iuap.baseservice.bpm.controller.GenericBpmController;
import com.yonyou.iuap.context.InvocationInfoProxy;
import com.yonyou.iuap.example.workorder.entity.Workorder;
import com.yonyou.iuap.example.workorder.service.WorkorderService;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.type.SearchParams;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "/example_workorder")
public class WorkorderBPMController extends GenericBpmController<Workorder> {
	private Logger logger = LoggerFactory.getLogger(WorkorderBPMController.class);

    @Override
    public Object callbackSubmit(@RequestBody List<Workorder> list, HttpServletRequest request, HttpServletResponse response) {
//        InvocationInfoProxy.setUserid("2ede26e3e5d54efd84c9d67560076ea8");
//        InvocationInfoProxy.setUsername("test01");
        return super.callbackSubmit(list,request,response);
    }
    @Override
    public Object callbakRecall(@RequestBody List<Workorder> list, HttpServletRequest request, HttpServletResponse response) {
//        InvocationInfoProxy.setUserid("2ede26e3e5d54efd84c9d67560076ea8");
//        InvocationInfoProxy.setUsername("test01");
        return super.callbakRecall(list,request,response);
    }
	/**************************************************************************************/
	@Autowired
	private WorkorderService service;

	@Autowired
	public void setService(WorkorderService service) {
		this.service = service;
		super.setService(service);
	}

	@Override
	public Object list(PageRequest pageRequest,
					   @FrontModelExchange(modelType = Workorder.class) SearchParams searchParams) {
		return super.list(pageRequest,searchParams);
	}

}