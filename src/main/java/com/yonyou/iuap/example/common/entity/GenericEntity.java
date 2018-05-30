package com.yonyou.iuap.example.common.entity;

import java.util.Date;

public interface GenericEntity {
	
	public String getId();
	
	public void setId(String id);
	
	public int getVersion();
	
	public void setVersion(int version);
	
	public int getDr();

	public void setDr(int dr);

	public Date getTs();

	public void setTs(Date ts);

	public Date getLastModified();

	public void setLastModified(Date lastModified);

	public String getLastModifyUser();

	public void setLastModifyUser(String lastModifyUser);

	public Date getCreateTime();

	public void setCreateTime(Date createTime);

	public String getCreateUser();

	public void setCreateUser(String createUser);

}
