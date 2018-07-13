package com.yonyou.iuap.example.entity;

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
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 演示示例
 */

@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name = "show_off")
@Associative(fkName = "orderId")
public class ShowOff extends AbsBpmModel implements Attachmentable {

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
    @Column(name="pet_id")
        @Reference(code="common_ref",srcProperties={ "peoname"}, desProperties={ "petIdSr"})
    private String petId;	//宠物标识

    @Transient
    private String petIdSr;	//参照显示值
    public void setpetIdSr(String refName){
        this.petIdSr = refName;
    }
    public String getPetIdSr(){
        return this.petIdSr;
    }

    @Condition(match=Match.EQ)
    @Column(name="quantity")
    private BigDecimal quantity;	//单价


    @Condition(match=Match.EQ)
    @Column(name="ship_date")
    private String shipDate;	//发货日期


    @Condition(match=Match.EQ)
    @Column(name="status")
    private String status;	//状态


    @Condition(match=Match.EQ)
    @Column(name="complete")
    private Boolean complete;	//完成状态



    public void setPetId(String petId){
    this.petId = petId;
    }
    public String getPetId(){
    return this.petId;
    }

    public void setQuantity(BigDecimal quantity){
    this.quantity = quantity;
    }
    public BigDecimal getQuantity(){
    return this.quantity;
    }

    public void setShipDate(String shipDate){
    this.shipDate = shipDate;
    }
    public String getShipDate(){
    return this.shipDate;
    }

    public void setStatus(String status){
    this.status = status;
    }
    public String getStatus(){
    return this.status;
    }

    public void setComplete(Boolean complete){
    this.complete = complete;
    }
    public Boolean getComplete(){
    return this.complete;
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