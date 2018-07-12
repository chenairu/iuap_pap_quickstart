package com.yonyou.iuap.example.sanyorder.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonyou.iuap.baseservice.attachment.entity.AttachmentEntity;
import com.yonyou.iuap.baseservice.attachment.entity.Attachmentable;
import com.yonyou.iuap.baseservice.bpm.entity.AbsBpmModel;
import com.yonyou.iuap.baseservice.support.condition.Condition;
import com.yonyou.iuap.baseservice.support.condition.Match;
import com.yonyou.iuap.example.base.utils.date.DateUtil;

import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.List;
import java.util.Random;

@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name="sany_order_contract")
public class SanyOrderContract extends AbsBpmModel implements Attachmentable {

    @Condition(match=Match.EQ)
    private String orderId;
    @Condition(match=Match.EQ)
    private String contractCode;
    @Condition(match=Match.EQ)
    private String contractName;
    @Condition(match=Match.EQ)
    private String maker;
    @Condition(match=Match.EQ)
    private Integer contractType;
    @Condition(match=Match.EQ)
    private String signDate;
    @Condition(match=Match.LIKE)
    private String remark;

    @Transient
    List<AttachmentEntity> attachment;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public String getMaker() {
        return maker;
    }

    public void setMaker(String maker) {
        this.maker = maker;
    }

    public Integer getContractType() {
        return contractType;
    }

    public void setContractType(Integer contractType) {
        this.contractType = contractType;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBillCode() {
        return DateUtil.getCurrentDate("yyyyMMddHHmmss")+(new Random().nextInt(10));
    }

    @Override
    public List<AttachmentEntity> getAttachment() {
        return attachment;
    }

    @Override
    public void setAttachment(List<AttachmentEntity> attachment) {
        this.attachment =attachment;
    }

    @Override
    public String getBpmBillCode() {
          return  cn.hutool.core.date.DateUtil.format(new Date(),
                "yyyyMMddHHmmss"+new Random().nextInt(10))
                ;
    }
}
