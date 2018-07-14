package com.yonyou.iuap.example.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonyou.iuap.baseservice.entity.AbsDrModel;
import com.yonyou.iuap.baseservice.entity.annotation.Reference;
import com.yonyou.iuap.baseservice.support.condition.Condition;
import com.yonyou.iuap.baseservice.support.condition.Match;
import com.yonyou.iuap.baseservice.support.generator.GeneratedValue;
import com.yonyou.iuap.baseservice.support.generator.Strategy;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.math.BigDecimal;

@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name="show_off_detail")
public class ShowOffDetail extends AbsDrModel {

    @Id
    @GeneratedValue(strategy=Strategy.UUID)
    @Column(name="id")
    @Condition
    protected String id;//ID
    @Override
    public String getId() {
        return id;
    }
    @Override
    public void setId(Serializable id){
        this.id= id.toString();
        super.id = id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @Condition(match=Match.EQ)
    private String orderId;
    @Condition(match=Match.EQ)
    private String purchaseOrderId;
    @Condition(match=Match.EQ)
    private String purchaseItemId;
    @Condition(match=Match.EQ)
    private String materialId;
    @Condition(match=Match.EQ)
    private Integer orderItem;
    @Condition(match=Match.EQ)
    private Integer materialQty;
    @Condition(match=Match.EQ)
    private BigDecimal materialPrice;
    @Condition(match=Match.EQ)
    private BigDecimal priceUnit;
    @Condition(match=Match.EQ)
    private String confirmTime;

    @Condition(match=Match.EQ)
    @Reference(code="common_ref",srcProperties={ "peoname"}, desProperties={ "confirmUserName"})
    private String confirmUser;
    @Transient
    private String confirmUserName;
    @Condition(match=Match.EQ)
    private Integer deliveryStatus;
    @Condition(match=Match.EQ)
    private BigDecimal deliveryQty;
    @Condition(match=Match.EQ)
    private String deliveryAddr;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPurchaseOrderId() {
        return purchaseOrderId;
    }

    public void setPurchaseOrderId(String purchaseOrderId) {
        this.purchaseOrderId = purchaseOrderId;
    }

    public String getPurchaseItemId() {
        return purchaseItemId;
    }

    public void setPurchaseItemId(String purchaseItemId) {
        this.purchaseItemId = purchaseItemId;
    }

    public String getMaterialId() {
        return materialId;
    }

    public void setMaterialId(String materialId) {
        this.materialId = materialId;
    }

    public Integer getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(Integer orderItem) {
        this.orderItem = orderItem;
    }

    public Integer getMaterialQty() {
        return materialQty;
    }

    public void setMaterialQty(Integer materialQty) {
        this.materialQty = materialQty;
    }

    public BigDecimal getMaterialPrice() {
        return materialPrice;
    }

    public void setMaterialPrice(BigDecimal materialPrice) {
        this.materialPrice = materialPrice;
    }

    public BigDecimal getPriceUnit() {
        return priceUnit;
    }

    public void setPriceUnit(BigDecimal priceUnit) {
        this.priceUnit = priceUnit;
    }

    public String getConfirmTime() {
        return confirmTime;
    }

    public void setConfirmTime(String confirmTime) {
        this.confirmTime = confirmTime;
    }

    public String getConfirmUser() {
        return confirmUser;
    }

    public void setConfirmUser(String confirmUser) {
        this.confirmUser = confirmUser;
    }

    public Integer getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(Integer deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public BigDecimal getDeliveryQty() {
        return deliveryQty;
    }

    public void setDeliveryQty(BigDecimal deliveryQty) {
        this.deliveryQty = deliveryQty;
    }

    public String getDeliveryAddr() {
        return deliveryAddr;
    }

    public void setDeliveryAddr(String deliveryAddr) {
        this.deliveryAddr = deliveryAddr;
    }

    public String getConfirmUserName() {
        return confirmUserName;
    }

    public void setConfirmUserName(String confirmUserName) {
        this.confirmUserName = confirmUserName;
    }
}
