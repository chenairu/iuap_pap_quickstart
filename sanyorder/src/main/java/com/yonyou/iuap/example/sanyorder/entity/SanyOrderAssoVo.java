package com.yonyou.iuap.example.sanyorder.entity;

import java.util.List;

public class SanyOrderAssoVo {
    private SanyOrder sanyOrder;

    private List<SanyOrderContract> sanyOrderContractList;

    public SanyOrder getSanyOrder() {
        return sanyOrder;
    }

    public void setSanyOrder(SanyOrder sanyOrder) {
        this.sanyOrder = sanyOrder;
    }

    public List<SanyOrderContract> getSanyOrderContractList() {
        return sanyOrderContractList;
    }

    public void setSanyOrderContractList(List<SanyOrderContract> sanyOrderContractList) {
        this.sanyOrderContractList = sanyOrderContractList;
    }
}
