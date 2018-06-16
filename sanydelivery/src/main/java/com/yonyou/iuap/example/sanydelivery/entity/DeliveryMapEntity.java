package com.yonyou.iuap.example.sanydelivery.entity;

import java.util.List;

public class DeliveryMapEntity {

	private List<SanyDeliveryDetail> form;
	private List<SanyDelivery> table;
	public List<SanyDeliveryDetail> getForm() {
		return form;
	}
	public void setForm(List<SanyDeliveryDetail> form) {
		this.form = form;
	}
	public List<SanyDelivery> getTable() {
		return table;
	}
	public void setTable(List<SanyDelivery> table) {
		this.table = table;
	}
}
