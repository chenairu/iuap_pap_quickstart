package com.yonyou.iuap.example.sanyorder.entity;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonyou.iuap.baseservice.attachment.entity.AttachmentEntity;
import com.yonyou.iuap.baseservice.attachment.entity.Attachmentable;
import com.yonyou.iuap.baseservice.bpm.entity.AbsBpmModel;
import com.yonyou.iuap.baseservice.entity.annotation.Associative;
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
import java.util.Date;
import java.util.List;
import java.util.Random;

@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name="sany_order")
@Associative(fkName="orderId")
public class SanyOrder extends AbsBpmModel implements Attachmentable {

    @Id
    @GeneratedValue(strategy=Strategy.UUID, module="order")
    @Column(name="id")
    @Condition
    protected String id;//订单ID
    @Condition(match=Match.LIKE)
    @Column(name="ORDERCODE")
    private String orderCode =getBpmBillCode();//订单编号
    @Condition(match=Match.LIKE)
    @Column(name="ORDERNAME")
    private String orderName;//订单名称
    @Condition(match=Match.LIKE)
    @Column(name="SUPPLIER")
    private String supplier;//供应商
    @Condition(match=Match.LIKE)
    @Column(name="supplierName")
    @Reference(code="bd_common_user",srcProperties={ "NAME"}, desProperties={ "showSupplierName"})
    private String supplierName;//供应商名称
    @Condition(match=Match.EQ)
    @Column(name="TYPE")
    private String type;//类型
    @Condition(match=Match.LIKE)
    @Column(name="PURCHASING")
    @Reference(code="common_ref",srcProperties={ "PEONAME"}, desProperties={ "purchasingName"})
    private String purchasing;//采购组织
    @Condition(match=Match.LIKE)
    @Column(name="PURCHASINGGROUP")
    private String purchasingGroup;//采购组
    @Condition(match=Match.EQ)
    @Column(name="VOUCHERDATE")
    private String voucherDate;//凭证日期
    @Condition(match=Match.EQ)
    @Column(name="APPROVALSTATE")
    private Integer approvalState;//审批状态
    @Condition(match=Match.EQ)
    @Column(name="CONFIRMSTATE")
    private Integer confirmState;//确认状态
    @Condition(match=Match.EQ)
    @Column(name="CLOSESTATE")
    private Integer closeState;//关闭状态

    @Transient
    private String type_name;//类型名称
    @Transient
    private String approvalState_name;//关闭状态
    @Transient
    private String confirmState_name;//关闭状态
    @Transient
    private String closeState_name;//关闭状态
    @Transient
    private String purchasingName;//供应商名称

    public String getShowSupplierName() {
        return showSupplierName;
    }

    public void setShowSupplierName(String showSupplierName) {
        this.showSupplierName = showSupplierName;
    }

    @Transient
    private String showSupplierName;//供应商名称


    @Transient
    private List<AttachmentEntity> attachment;

    private String remark;

    public String getPurchasingName() {
        return purchasingName;
    }

    public void setPurchasingName(String purchasingName) {
        this.purchasingName = purchasingName;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public String getOrderName() {
        return orderName;
    }

    public void setOrderName(String orderName) {
        this.orderName = orderName;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPurchasing() {
        return purchasing;
    }

    public void setPurchasing(String purchasing) {
        this.purchasing = purchasing;
    }

    public String getPurchasingGroup() {
        return purchasingGroup;
    }

    public void setPurchasingGroup(String purchasingGroup) {
        this.purchasingGroup = purchasingGroup;
    }

    public String getVoucherDate() {
        return voucherDate;
    }

    public void setVoucherDate(String voucherDate) {
        this.voucherDate = voucherDate;
    }

    public Integer getApprovalState() {
        return approvalState;
    }

    public void setApprovalState(Integer approvalState) {
        this.approvalState = approvalState;
    }

    public Integer getConfirmState() {
        return confirmState;
    }

    public void setConfirmState(Integer confirmState) {
        this.confirmState = confirmState;
    }

    public Integer getCloseState() {
        return closeState;
    }

    public void setCloseState(Integer closeState) {
        this.closeState = closeState;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getType_name() {
        return type_name;
    }

    public void setType_name(String type_name) {
        this.type_name = type_name;
    }

    public String getApprovalState_name() {
        return approvalState_name;
    }

    public void setApprovalState_name(String approvalState_name) {
        this.approvalState_name = approvalState_name;
    }

    public String getConfirmState_name() {
        return confirmState_name;
    }

    public void setConfirmState_name(String confirmState_name) {
        this.confirmState_name = confirmState_name;
    }

    public String getCloseState_name() {
        return closeState_name;
    }

    public void setCloseState_name(String closeState_name) {
        this.closeState_name = closeState_name;
    }
    @Override
    public String getId() {
        return id;
    }
    //base use
    @Override
    public void setId(Serializable id){
        this.id= id.toString();
        super.id = id;
    }


    // mybatis use
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getBpmBillCode() {
        return  DateUtil.format(new Date(),
                "yyyyMMddHHmmss"+new Random().nextInt(10))
                ;
    }

    public List<AttachmentEntity> getAttachment() {
        return attachment;
    }

    public void setAttachment(List<AttachmentEntity> attachment) {
        this.attachment = attachment;
    }



}