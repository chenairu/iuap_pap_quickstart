package com.yonyou.iuap.example;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.yonyou.iuap.baseservice.vo.GenericAssoVo;
import com.yonyou.iuap.example.entity.ShowOff;
import com.yonyou.iuap.example.entity.ShowOffDetail;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class App {


    public static void main(String[] args){
        ShowOff entity = new ShowOff();
        entity.setId("");
        entity.setComplete(true);
        entity.setPetId("111");
        entity.setQuantity(new BigDecimal(12.2));
        entity.setShipDate("2017-09-09");
        GenericAssoVo vo = new GenericAssoVo(entity);

        ShowOffDetail detail = new ShowOffDetail();

        detail.setConfirmTime("2019-09-09");
        detail.setConfirmUser("sys");
        detail.setOrderId("uuid");
        detail.setOrderItem(12);


        Map map = new HashMap();
        map.put("showOffDetailList",Lists.newArrayList(detail) );
        vo.setSublist(map);
        System.out.println(JSON.toJSON(vo));
    }
}
