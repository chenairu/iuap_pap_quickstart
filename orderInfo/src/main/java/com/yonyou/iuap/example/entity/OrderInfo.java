package com.yonyou.iuap.example.entity;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.yonyou.iuap.baseservice.attachment.entity.AttachmentEntity;
import com.yonyou.iuap.baseservice.attachment.entity.Attachmentable;
import com.yonyou.iuap.baseservice.bpm.entity.AbsBpmModel;
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
import java.math.BigDecimal;

/**
 * 订单信息
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "order_info")
public class OrderInfo extends AbsBpmModel implements Attachmentable {

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
    @Column(name="order_no")
    private String orderNo;	//编号


    @Condition(match=Match.EQ)
    @Column(name="pur_org")
        @Reference(code="common_ref",srcProperties={ "peoname"}, desProperties={ "purOrgSr"})
    private String purOrg;	//采购单位

    @Transient
    private String purOrgSr;	//参照显示值
    public void setpurOrgSr(String refName){
        this.purOrgSr = refName;
    }
    public String getPurOrgSr(){
        return this.purOrgSr;
    }

    @Condition(match=Match.EQ)
    @Column(name="apply_no")
        @Reference(code="common_ref",srcProperties={ "peoname"}, desProperties={ "petIdSr"})
    private String applyNo;	//供应商编号

    @Transient
    private String petIdSr;	//参照显示值
    public void setpetIdSr(String refName){
        this.petIdSr = refName;
    }
    public String getPetIdSr(){
        return this.petIdSr;
    }

    @Condition(match=Match.EQ)
    @Column(name="pur_group_no")
    private String purGroupNo;	//采购组编号


    @Condition(match=Match.EQ)
    @Column(name="release_time")
    private String releaseTime;	//发布时间


    @Condition(match=Match.EQ)
    @Column(name="confirm_time")
    private String confirmTime;	//确认时间


    @Condition(match=Match.EQ)
    @Column(name="order_type")
    private String orderType;	//订单类型


    @Condition(match=Match.EQ)
    @Column(name="order_state")
    private String orderState;	//订单状态


    @Condition(match=Match.EQ)
    @Column(name="order_amount")
    private BigDecimal orderAmount;	//订单金额


    @Condition(match=Match.EQ)
    @Column(name="is_paid")
    private Boolean isPaid;	//是否付款



    public void setOrderNo(String orderNo){
    this.orderNo = orderNo;
    }
    public String getOrderNo(){
    return this.orderNo;
    }

    public void setPurOrg(String purOrg){
    this.purOrg = purOrg;
    }
    public String getPurOrg(){
    return this.purOrg;
    }

    public void setApplyNo(String applyNo){
    this.applyNo = applyNo;
    }
    public String getApplyNo(){
    return this.applyNo;
    }

    public void setPurGroupNo(String purGroupNo){
    this.purGroupNo = purGroupNo;
    }
    public String getPurGroupNo(){
    return this.purGroupNo;
    }

    public void setReleaseTime(String releaseTime){
    this.releaseTime = releaseTime;
    }
    public String getReleaseTime(){
    return this.releaseTime;
    }

    public void setConfirmTime(String confirmTime){
    this.confirmTime = confirmTime;
    }
    public String getConfirmTime(){
    return this.confirmTime;
    }

    public void setOrderType(String orderType){
    this.orderType = orderType;
    }
    public String getOrderType(){
    return this.orderType;
    }

    public void setOrderState(String orderState){
    this.orderState = orderState;
    }
    public String getOrderState(){
    return this.orderState;
    }

    public void setOrderAmount(BigDecimal orderAmount){
    this.orderAmount = orderAmount;
    }
    public BigDecimal getOrderAmount(){
    return this.orderAmount;
    }

    public void setIsPaid(Boolean isPaid){
    this.isPaid = isPaid;
    }
    public Boolean getIsPaid(){
    return this.isPaid;
    }


	@Transient
	private List<AttachmentEntity> attachment;

	@Override
	public String getBpmBillCode() {
        return  DateUtil.format(new Date(), "yyyyMMddHHmmss"+new Random().nextInt(10))   ;
	}

	public List<AttachmentEntity> getAttachment() {
		return attachment;
	}

	public void setAttachment(List<AttachmentEntity> attachment) {
		this.attachment = attachment;
	}



}