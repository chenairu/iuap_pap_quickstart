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
import com.yonyou.iuap.example.order.entity.OrderBill;
import com.yonyou.iuap.example.order.service.IOrderBillService;
import com.yonyou.iuap.mvc.annotation.FrontModelExchange;
import com.yonyou.iuap.mvc.constants.RequestStatusEnum;
import com.yonyou.iuap.mvc.type.JsonResponse;
import com.yonyou.iuap.mvc.type.SearchParams;

@RestController
@RequestMapping(value = "/demo_order")
public class OrderBillController extends BaseController {
	
	private Logger logger = LoggerFactory.getLogger(OrderBillController.class);

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public Object list(PageRequest pageRequest,
			@FrontModelExchange(modelType = OrderBill.class) SearchParams searchParams) {
		logger.debug("execute data search.");
		
		Page<OrderBill> page = orderBillService.selectAllByPage(pageRequest, searchParams);
		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("data", page);
		return super.buildMapSuccess(map);
	}
	
	
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	@ResponseBody
	public Object add(@RequestBody OrderBill order, HttpServletRequest request, HttpServletResponse response) {
		JsonResponse jsonResp;
		try {
			orderBillService.saveEntity(order);
			jsonResp = this.buildSuccess(order);
		}catch(Exception exp) {
			logger.error("订单信息保存出错!", exp);
			jsonResp = this.buildError("msg", exp.getMessage(), RequestStatusEnum.FAIL_FIELD);
		}
		return jsonResp;
	}
	
/*	
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	@ResponseBody
	public Object update(@RequestBody OrderBill order, HttpServletRequest request, HttpServletResponse response) {
		JsonResponse jsonResp;
		try {
			orderService.saveEntity(order);
			jsonResp = this.buildSuccess(order);
		}catch(Exception exp) {
			logger.error("订单保存出错!", exp);
			jsonResp = this.buildError("msg", exp.getMessage(), RequestStatusEnum.FAIL_FIELD);
		}
		return jsonResp;
	}*/
	
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	@ResponseBody
	public Object deleteBatch(@RequestBody List<OrderBill> objs, HttpServletRequest request, HttpServletResponse response) throws Exception {
		orderBillService.delete(objs);
		return super.buildSuccess(objs);
	}
	
	
	/***********************************************************/
	@Autowired
	private IOrderBillService orderBillService;
	

}