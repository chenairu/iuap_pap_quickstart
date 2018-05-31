package com.yonyou.iuap.example.order.web.controler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.yonyou.iuap.base.web.BaseController;
import com.yonyou.iuap.example.order.entity.OrderDetail;
import com.yonyou.iuap.example.order.service.OrderDetailService;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.type.SearchParams;

@RestController
@RequestMapping(value = "/demo_order_detail")
public class OrderDetailController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(OrderDetailController.class);

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Object list(PageRequest pageRequest,
			@FrontModelExchange(modelType = OrderDetail.class) SearchParams searchParams) {
		Page<OrderDetail> page = orderDetailService.selectAllByPage(pageRequest, searchParams);
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", page);
		return super.buildMapSuccess(map);
	}
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object delete(@RequestBody List<String> detailIds, HttpServletRequest request, HttpServletResponse response) throws Exception {
		orderDetailService.delete(detailIds);
		return super.buildSuccess(detailIds);
	}
		
	/*************************************************************/
	@Autowired
	private OrderDetailService orderDetailService;

}
