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
 * 演示示例-子表
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "show_off_sub")

public class ShowOffSub extends AbsBpmModel implements Attachmentable {

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

    @Condition(match=Match.LIKE)
    @Column(name="SHOW_OFF_ID")
    private String showOffId;	//主表外键


    @Condition(match=Match.LIKE)
    @Column(name="CONTACT")
        @Reference(code="common_ref",srcProperties={ "peoname"}, desProperties={ "confirmUserName"})
    private String confirmUser;	//联系人

    @Transient
    private String confirmUserName;	//参照显示值
    public void setconfirmUserName(String refName){
        this.confirmUserName = refName;
    }
    public String getConfirmUserName(){
        return this.confirmUserName;
    }

    @Condition(match=Match.EQ)
    @Column(name="SEND_DATE")
    private String sendDate;	//发货日期


    @Condition(match=Match.EQ)
    @Column(name="DELIVER_STATE")
    private String deliverState;	//发货状态


    @Condition(match=Match.LIKE)
    @Column(name="REMARK")
    private String remark;	//备注



    public void setShowOffId(String showOffId){
    this.showOffId = showOffId;
    }
    public String getShowOffId(){
    return this.showOffId;
    }

    public void setConfirmUser(String confirmUser){
    this.confirmUser = confirmUser;
    }
    public String getConfirmUser(){
    return this.confirmUser;
    }

    public void setSendDate(String sendDate){
    this.sendDate = sendDate;
    }
    public String getSendDate(){
    return this.sendDate;
    }

    public void setDeliverState(String deliverState){
    this.deliverState = deliverState;
    }
    public String getDeliverState(){
    return this.deliverState;
    }

    public void setRemark(String remark){
    this.remark = remark;
    }
    public String getRemark(){
    return this.remark;
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